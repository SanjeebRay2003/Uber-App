package Springboot.Uber.App.Services.Implementation;

import Springboot.Uber.App.Entities.Enums.PaymentMethod;
import Springboot.Uber.App.Statergies.PaymentStrategy;
import Springboot.Uber.App.Statergies.Strategy_Implimentation.CashPaymentStrategy;
import Springboot.Uber.App.Statergies.Strategy_Implimentation.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {
    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){

        return switch (paymentMethod){
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
            default -> throw new RuntimeException("Invalid payment method");

        };

    }
}
