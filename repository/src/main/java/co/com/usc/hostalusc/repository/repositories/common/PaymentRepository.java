package co.com.usc.hostalusc.repository.repositories.common;

import co.com.usc.hostalusc.repository.model.common.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

    @Query("select p from Payment p where p.reservation.id = ?1 and p.paymentStatus = 'Completed'")
    @Transactional(readOnly = true)
    List<Payment> findCompletedPaymentsByReservationId(Long reservationId);

    @Query("select p from Payment p")
    @Transactional(readOnly = true)
    Page<Payment> findAll(Pageable pageable);

    @Query("select p from Payment p where p.id = ?1")
    @Transactional(readOnly = true)
    Optional<Payment> findById(Long id);

    @Query("select p from Payment p where p.paymentMethod = ?1")
    @Transactional(readOnly = true)
    List<Payment> findByPaymentMethod(String paymentMethod);
}