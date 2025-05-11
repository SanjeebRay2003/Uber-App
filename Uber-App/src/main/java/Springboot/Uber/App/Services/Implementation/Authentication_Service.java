package Springboot.Uber.App.Services.Implementation;

import Springboot.Uber.App.DTO.DriverDTO;
import Springboot.Uber.App.DTO.SignUpDTO;
import Springboot.Uber.App.DTO.UserDTO;
import Springboot.Uber.App.Entities.Driver;
import Springboot.Uber.App.Entities.Enums.Role;
import Springboot.Uber.App.Entities.User;
import Springboot.Uber.App.Exceptions.ResourceNotFoundException;
import Springboot.Uber.App.Exceptions.RuntimeConflictException;
import Springboot.Uber.App.Repositories.UserRepository;
import Springboot.Uber.App.Security.JWTService;
import Springboot.Uber.App.Services.AuthenticationService;
import Springboot.Uber.App.Services.DriverService;
import Springboot.Uber.App.Services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static Springboot.Uber.App.Entities.Enums.Role.DRIVER;

@Service
@RequiredArgsConstructor
public class Authentication_Service implements AuthenticationService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final Customer_Service customerService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    @Override
    public String[] login(String email, String password) {
        String tokens[] = new String[2];
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

       User user = (User) authentication.getPrincipal();

       String accessToken = jwtService.generateAccessToken(user);
       String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken,refreshToken};
    }

    @Override
    @Transactional
    public UserDTO signup(SignUpDTO signUpDTO) {
        User user = userRepository.findByEmail(signUpDTO.getEmail()).orElse(null);
        if (user != null)
            throw new RuntimeConflictException("Can not SignUp, user already exist with this email" + signUpDTO.getEmail());

        User mappedUser = modelMapper.map(signUpDTO, User.class);
        mappedUser.setRoles(Set.of(Role.CUSTOMER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);

        // create user related entities
        customerService.createNewCustomer(savedUser);

        //TODO add wallet related service here
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public DriverDTO onboardNewDriver(Long userId, String vehicleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        if (user.getRoles().contains(DRIVER)) {
            throw new RuntimeException("User with id " + userId + " is already a Driver");
        }

        Driver createdDriver = Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();

        user.getRoles().add(DRIVER);
        userRepository.save(user);
        Driver savedDriver = driverService.createNewDriver(createdDriver);
        return modelMapper.map(savedDriver, DriverDTO.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " +
                "with id: "+userId));

        return jwtService.generateAccessToken(user);
    }
}
