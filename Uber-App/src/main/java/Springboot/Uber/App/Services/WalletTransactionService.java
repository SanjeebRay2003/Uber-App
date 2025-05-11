package Springboot.Uber.App.Services;

import Springboot.Uber.App.DTO.WalletTransactionDTO;
import Springboot.Uber.App.Entities.WalletTransaction;

public interface WalletTransactionService {
    void createNewWalletTransaction(WalletTransaction walletTransaction);

}
