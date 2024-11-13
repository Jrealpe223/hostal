package co.com.usc.hostalusc.domain.service.impl;

import co.com.usc.hostalusc.domain.exceptions.UscException;
import co.com.usc.hostalusc.domain.service.PaymentService;
import co.com.usc.hostalusc.repository.model.common.Payment;
import co.com.usc.hostalusc.repository.repositories.common.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> getAllPaymentsByReservationId(Long reservationId) {
        return paymentRepository.findCompletedPaymentsByReservationId(reservationId);
    }

    @Override
    public Page<Payment> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new UscException("Pago no encontrado"));
    }

    @Override
    public Payment createPayment(Payment payment) {
        if (payment.getAmount() == null || payment.getAmount() <= 0) {
            throw new UscException("El monto del pago debe ser mayor a cero");
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Long id, Payment payment) {
        Payment paymentToUpdate = paymentRepository.findById(id).orElseThrow(() -> new UscException("Pago no encontrado"));
        paymentToUpdate.setAmount(payment.getAmount());
        paymentToUpdate.setPaymentStatus(payment.getPaymentStatus());
        paymentToUpdate.setPaymentMethod(payment.getPaymentMethod());
        return paymentRepository.save(paymentToUpdate);
    }
}
