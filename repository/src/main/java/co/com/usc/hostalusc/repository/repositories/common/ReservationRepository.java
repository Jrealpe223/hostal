package co.com.usc.hostalusc.repository.repositories.common;

import co.com.usc.hostalusc.repository.model.common.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    @Query("select r from Reservation r where r.user.id = ?1 and r.reservationStatus = 'Confirmed'")
    @Transactional(readOnly = true)
    List<Reservation> findConfirmedByUserId(Long userId);

    @Query("select r from Reservation r")
    @Transactional(readOnly = true)
    Page<Reservation> findAll(Pageable pageable);

    @Query("select r from Reservation r where r.id = ?1")
    @Transactional(readOnly = true)
    Optional<Reservation> findById(Long id);

    @Query("select r from Reservation r where r.room.id = ?1 and r.checkInDate <= ?2 and r.checkOutDate >= ?2")
    @Transactional(readOnly = true)
    Optional<Reservation> findActiveReservationForRoom(Long roomId, java.util.Date date);
}