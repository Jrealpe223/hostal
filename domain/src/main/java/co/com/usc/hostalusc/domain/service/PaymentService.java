package co.com.usc.hostalusc.domain.service;

import co.com.usc.hostalusc.repository.model.common.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PaymentService {
    List<Payment> getAllPaymentsByReservationId(Long reservationId);
    Page<Payment> getAllPayments(Pageable pageable);
    Payment getPaymentById(Long id);
    Payment createPayment(Payment payment);
    Payment updatePayment(Long id, Payment payment);
}