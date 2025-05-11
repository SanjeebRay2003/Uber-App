package Springboot.Uber.App.Statergies;

import Springboot.Uber.App.Entities.Payment;

public interface PaymentStrategy {
    Double PLATFORM_COMMISSION = 0.3;
    void processPayment(Payment payment);
}
