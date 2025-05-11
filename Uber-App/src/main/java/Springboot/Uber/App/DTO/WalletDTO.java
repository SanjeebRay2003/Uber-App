package Springboot.Uber.App.DTO;

import Springboot.Uber.App.Entities.User;
import Springboot.Uber.App.Entities.WalletTransaction;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {

    private Long id;

    private UserDTO user;

    private Double balance;

    private List<WalletTransactionDTO> transactions;
}

