package co.com.usc.hostalusc.domain.service.impl;

import co.com.usc.hostalusc.domain.exceptions.UscException;
import co.com.usc.hostalusc.domain.service.ReservationService;
import co.com.usc.hostalusc.repository.model.common.Reservation;
import co.com.usc.hostalusc.repository.repositories.common.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> getAllReservationsByUserId(Long userId) {
        return reservationRepository.findConfirmedByUserId(userId);
    }

    @Override
    public Page<Reservation> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new UscException("Reservación no encontrada"));
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        if (reservation.getCheckInDate() == null || reservation.getCheckOutDate() == null) {
            throw new UscException("Las fechas de check-in y check-out son obligatorias");
        }
        if (reservation.getCheckInDate().after(reservation.getCheckOutDate())) {
            throw new UscException("La fecha de check-in debe ser antes de la fecha de check-out");
        }
        reservation.setReservationDate(new Date());
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation reservationToUpdate = reservationRepository.findById(id).orElseThrow(() -> new UscException("Reservación no encontrada"));

        if (reservation.getCheckInDate() != null && reservation.getCheckOutDate() != null) {
            if (reservation.getCheckInDate().after(reservation.getCheckOutDate())) {
                throw new UscException("La fecha de check-in debe ser antes de la fecha de check-out");
            }
            reservationToUpdate.setCheckInDate(reservation.getCheckInDate());
            reservationToUpdate.setCheckOutDate(reservation.getCheckOutDate());
        }
        return reservationRepository.save(reservationToUpdate);
    }
}