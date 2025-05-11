package Springboot.Uber.App.Services;

import Springboot.Uber.App.Entities.Enums.PaymentStatus;
import Springboot.Uber.App.Entities.Payment;
import Springboot.Uber.App.Entities.Ride;

public interface PaymentService {
    void processPayment(Ride ride);
    Payment createNewPayment(Ride ride);
    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);
}
