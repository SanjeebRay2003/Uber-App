package Springboot.Uber.App.Services.Implementation;

import Springboot.Uber.App.DTO.WalletTransactionDTO;
import Springboot.Uber.App.Entities.WalletTransaction;
import Springboot.Uber.App.Repositories.WalletTransactionRepository;
import Springboot.Uber.App.Services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransaction_Service implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);

    }
}
