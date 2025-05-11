package Springboot.Uber.App.DTO;

import Springboot.Uber.App.Entities.Enums.TransactionMethod;
import Springboot.Uber.App.Entities.Enums.TransactionType;
import Springboot.Uber.App.Entities.Ride;
import Springboot.Uber.App.Entities.Wallet;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransactionDTO {
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;


    private RideDTO ride;

    private String transactionId;

    private WalletDTO wallet;

    private LocalDateTime timeStamp;
}
