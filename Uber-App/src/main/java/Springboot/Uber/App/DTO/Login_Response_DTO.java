package Springboot.Uber.App.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login_Response_DTO {
    private String accessToken;
//    private String refreshToken;
}
