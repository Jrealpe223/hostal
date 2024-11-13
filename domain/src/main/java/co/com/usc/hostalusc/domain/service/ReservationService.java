package co.com.usc.hostalusc.domain.service;

import co.com.usc.hostalusc.repository.model.common.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservationsByUserId(Long userId);
    Page<Reservation> getAllReservations(Pageable pageable);
    Reservation getReservationById(Long id);
    Reservation createReservation(Reservation reservation);
    Reservation updateReservation(Long id, Reservation reservation);
}