package Springboot.Uber.App.Statergies.Strategy_Implimentation;

import Springboot.Uber.App.Entities.Customer;
import Springboot.Uber.App.Entities.Driver;
import Springboot.Uber.App.Entities.Enums.PaymentStatus;
import Springboot.Uber.App.Entities.Enums.TransactionMethod;
import Springboot.Uber.App.Entities.Payment;
import Springboot.Uber.App.Repositories.CustomerRepository;
import Springboot.Uber.App.Repositories.PaymentRepository;
import Springboot.Uber.App.Services.CustomerService;
import Springboot.Uber.App.Services.PaymentService;
import Springboot.Uber.App.Services.WalletService;
import Springboot.Uber.App.Statergies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
    private final PaymentRepository paymentRepository;


//    customer = 250 , driver = 400
//    ride cost = 100, commission = 30
//    customer = 250 - 100 = 150
//    driver = 400 + (100 - 30) = 470


    @Override
    @Transactional
    public void processPayment(Payment payment) {

        Customer customer = payment.getRide().getCustomer();
        Driver driver = payment.getRide().getDriver();
        walletService.withdrawlMoneyFromWallet(customer.getUser(),payment.getAmount(),
                null,payment.getRide(), TransactionMethod.RIDE);

        double driversCut = payment.getAmount()*(1-PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(),driversCut,null,
                payment.getRide(),TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);


    }
}
