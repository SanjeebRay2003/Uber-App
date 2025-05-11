package Springboot.Uber.App.services.impl;


import Springboot.Uber.App.DTO.SignUpDTO;
import Springboot.Uber.App.DTO.UserDTO;
import Springboot.Uber.App.Entities.Enums.Role;
import Springboot.Uber.App.Entities.User;
import Springboot.Uber.App.Repositories.UserRepository;
import Springboot.Uber.App.Security.JWTService;
import Springboot.Uber.App.Services.Implementation.Authentication_Service;
import Springboot.Uber.App.Services.Implementation.Customer_Service;
import Springboot.Uber.App.Services.Implementation.Driver_Service;
import Springboot.Uber.App.Services.Implementation.Wallet_Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Authentication_ServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Customer_Service customerService;

    @Mock
    private Wallet_Service walletService;

    @Mock
    private Driver_Service driverService;

    @Spy
    private ModelMapper modelMapper;

    @Spy
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private Authentication_Service authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(Set.of(Role.CUSTOMER));
    }

    @Test
    void testLogin_whenSuccess() {
//        arrange
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtService.generateAccessToken(any(User.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

//        act
        String[] tokens = authService.login(user.getEmail(), user.getPassword());

//        assert
        assertThat(tokens).hasSize(2);
        assertThat(tokens[0]).isEqualTo("accessToken");
        assertThat(tokens[1]).isEqualTo("refreshToken");
    }

    @Test
    void testSignup_whenSuccess() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        SignUpDTO signupDto = new SignUpDTO();
        signupDto.setEmail("test@example.com");
        signupDto.setPassword("password");
        UserDTO userDto = authService.signup(signupDto);

        // Assert
        assertThat(userDto).isNotNull();
        assertThat(userDto.getEmail()).isEqualTo(signupDto.getEmail());
        verify(customerService).createNewCustomer(any(User.class));
        verify(walletService).createNewWallet(any(User.class));
    }
}