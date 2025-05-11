package Springboot.Uber.App.DTO;

import lombok.Data;

@Data
public class Login_Request_DTO {
    private String email;
    private String password;
}
