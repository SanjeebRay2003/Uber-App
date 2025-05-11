package Springboot.Uber.App.Controller;

import Springboot.Uber.App.DTO.*;
import Springboot.Uber.App.Services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/authentication")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @PostMapping("signUp")
    ResponseEntity<UserDTO> singUp(@RequestBody SignUpDTO signUpDTO){
        return new ResponseEntity<>(authenticationService.signup(signUpDTO), HttpStatus.CREATED);
    }


    @PostMapping("/onBoardNewDriver/{userId}")
    @Secured("ROLE_ADMIN")
    ResponseEntity<DriverDTO> onBoardNewDriver (@PathVariable Long userId,
                                                @RequestBody OnBoardDriverDTO onBoardDriverDTO){
        return new ResponseEntity<>(authenticationService.onboardNewDriver(userId,onBoardDriverDTO.getVehicleId()), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Login_Response_DTO> login(@RequestBody Login_Request_DTO loginRequestDto,
                                                    HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){
       String token[] = authenticationService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());
        Cookie cookie = new Cookie("token",token[1]);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
       return ResponseEntity.ok(new Login_Response_DTO(token[0]));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Login_Response_DTO> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

        String accessToken = authenticationService.refreshToken(refreshToken);

        return ResponseEntity.ok(new Login_Response_DTO(accessToken));
    }


}
