package Springboot.Uber.App.Entities;

import Springboot.Uber.App.Entities.Enums.TransactionMethod;
import Springboot.Uber.App.Entities.Enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_wallet_transaction_wallet",columnList = "wallet_id"),
        @Index(name = "idx_wallet_transaction_ride",columnList = "ride_id"),
})
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    @OneToOne
    private Ride ride;

    private String transactionId;

    @ManyToOne
    private Wallet wallet;

    @CreationTimestamp
    private LocalDateTime timeStamp;


}
