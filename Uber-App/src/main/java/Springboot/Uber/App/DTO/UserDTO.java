package Springboot.Uber.App.DTO;

import Springboot.Uber.App.Entities.Enums.Role;
import Springboot.Uber.App.Exception_Handlers.ApiError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    private Long id;
//    private LocalDateTime time;
    private String name;
    private String email;

    Set<Role> roles;
//    private ApiError error;

}
