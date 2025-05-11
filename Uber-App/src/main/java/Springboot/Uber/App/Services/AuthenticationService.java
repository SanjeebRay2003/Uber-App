package Springboot.Uber.App.Services;

import Springboot.Uber.App.DTO.DriverDTO;
import Springboot.Uber.App.DTO.SignUpDTO;
import Springboot.Uber.App.DTO.UserDTO;

public interface AuthenticationService {

    String[] login(String email,String password);

    UserDTO signup(SignUpDTO signUpDTO);

    DriverDTO onboardNewDriver(Long userId,String vehicleId);

    String refreshToken(String refreshToken);
}
