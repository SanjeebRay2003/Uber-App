package Springboot.Uber.App.Services.Implementation;

import Springboot.Uber.App.DTO.RideDTO;
import Springboot.Uber.App.DTO.WalletDTO;
import Springboot.Uber.App.Entities.Enums.TransactionMethod;
import Springboot.Uber.App.Entities.Enums.TransactionType;
import Springboot.Uber.App.Entities.Ride;
import Springboot.Uber.App.Entities.User;
import Springboot.Uber.App.Entities.Wallet;
import Springboot.Uber.App.Entities.WalletTransaction;
import Springboot.Uber.App.Exceptions.ResourceNotFoundException;
import Springboot.Uber.App.Repositories.WalletRepository;
import Springboot.Uber.App.Services.WalletService;
import Springboot.Uber.App.Services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Wallet_Service implements WalletService {

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;
    private final WalletTransactionService walletTransactionService;

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
      Wallet wallet = findByUser(user);
      wallet.setBalance(wallet.getBalance()+amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);


     return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet withdrawlMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()-amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

//        walletTransactionService.createNewWalletTransaction(walletTransaction);

        wallet.getTransactions().add(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(()-> new ResourceNotFoundException("Wallet not found with id "+ walletId));
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user)
               .orElseThrow(()-> new ResourceNotFoundException("Wallet not found for user with id "+user.getId()));
    }
}

