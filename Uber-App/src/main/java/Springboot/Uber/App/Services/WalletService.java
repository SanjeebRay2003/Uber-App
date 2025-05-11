package Springboot.Uber.App.Services;

import Springboot.Uber.App.Entities.Enums.TransactionMethod;
import Springboot.Uber.App.Entities.Ride;
import Springboot.Uber.App.Entities.User;
import Springboot.Uber.App.Entities.Wallet;

public interface WalletService {

    Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);
    Wallet withdrawlMoneyFromWallet(User user,Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);
    void withdrawAllMoneyFromWallet();
    Wallet findWalletById(Long walletId);
    Wallet createNewWallet(User user);
    Wallet findByUser(User user);


}
