package co.com.usc.hostalusc.api.v1.controller;

import co.com.usc.hostalusc.api.utils.ResponseUtils;
import co.com.usc.hostalusc.domain.contracts.MessageCode;
import co.com.usc.hostalusc.domain.contracts.OutputContract;
import co.com.usc.hostalusc.domain.service.ReservationService;
import co.com.usc.hostalusc.repository.model.common.Reservation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1/reservations")
@Tag(name = "Reservation Controller")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Get all reservations for a user")
    @GetMapping(value = "/user/{userId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getReservationsByUserId(@PathVariable("userId") Long userId) {
        log.info("Fetching all reservations for user ID: {}", userId);
        try {
            List<Reservation> reservations = reservationService.getAllReservationsByUserId(userId);
            return ResponseUtils.buildOutputContractSuccessResponse(reservations, HttpStatus.OK.value(), (long) reservations.size());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error getReservationsByUserId() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Get all reservations with pagination")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getAllReservations(Pageable pageable) {
        log.info("Fetching all reservations with pagination");
        try {
            Page<Reservation> reservations = reservationService.getAllReservations(pageable);
            return ResponseUtils.buildOutputContractSuccessResponse(reservations.getContent(), HttpStatus.OK.value(), reservations.getTotalElements());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error getAllReservations() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Get reservation by ID")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getReservationById(@PathVariable("id") Long id) {
        log.info("Fetching reservation with ID: {}", id);
        try {
            Reservation reservation = reservationService.getReservationById(id);
            return ResponseUtils.buildOutputContractSuccessResponse(reservation, HttpStatus.OK.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error getReservationById() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Create new reservation")
    @PostMapping(value = "/save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> createReservation(@RequestBody Reservation reservation) {
        log.info("Creating new reservation with details: {}", reservation);
        try {
            Reservation createdReservation = reservationService.createReservation(reservation);
            return ResponseUtils.buildOutputContractSuccessResponse(createdReservation, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error createReservation() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Update reservation")
    @PutMapping(value = "/update/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> updateReservation(@PathVariable("id") Long id, @RequestBody Reservation reservation) {
        log.info("Updating reservation with ID: {}", id);
        try {
            Reservation updatedReservation = reservationService.updateReservation(id, reservation);
            return ResponseUtils.buildOutputContractSuccessResponse(updatedReservation, HttpStatus.OK.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error updateReservation() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Get active reservation for a room on a specific date")
    @GetMapping(value = "/active/{roomId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getActiveReservationForRoom(
            @PathVariable("roomId") Long roomId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        log.info("Fetching active reservation for room ID: {} on date: {}", roomId, date);
        try {
            Reservation reservation = reservationService.getActiveReservationForRoom(roomId, date);
            return ResponseUtils.buildOutputContractSuccessResponse(reservation, HttpStatus.OK.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse(
                    "Error getActiveReservationForRoom() -> " + ex.getMessage(),
                    MessageCode.INTERVAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }
}