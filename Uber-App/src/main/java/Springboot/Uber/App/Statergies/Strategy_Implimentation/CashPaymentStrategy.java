package Springboot.Uber.App.Statergies.Strategy_Implimentation;

import Springboot.Uber.App.Entities.Driver;
import Springboot.Uber.App.Entities.Enums.PaymentStatus;
import Springboot.Uber.App.Entities.Enums.TransactionMethod;
import Springboot.Uber.App.Entities.Payment;
import Springboot.Uber.App.Entities.Wallet;
import Springboot.Uber.App.Repositories.PaymentRepository;
import Springboot.Uber.App.Services.PaymentService;
import Springboot.Uber.App.Services.WalletService;
import Springboot.Uber.App.Statergies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    // cash payment to driver = 100 , commission = 0.3(30rs)
    // 100 - 30 = 70
    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Wallet driverWallet = walletService.findByUser(driver.getUser());
        Double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;
        walletService.withdrawlMoneyFromWallet(driver.getUser(),platformCommission,null,payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
