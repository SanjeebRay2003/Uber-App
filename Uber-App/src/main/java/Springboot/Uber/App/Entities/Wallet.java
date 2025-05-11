package Springboot.Uber.App.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,optional = false,cascade = CascadeType.DETACH)
    private User user;

    private Double balance;

    @OneToMany(mappedBy = "wallet",fetch = FetchType.LAZY)
    private List<WalletTransaction> transactions;
}
