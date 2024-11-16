package co.com.usc.hostalusc.api.v1.controller;

import co.com.usc.hostalusc.api.utils.ResponseUtils;
import co.com.usc.hostalusc.domain.contracts.MessageCode;
import co.com.usc.hostalusc.domain.contracts.OutputContract;
import co.com.usc.hostalusc.domain.service.RoomsService;
import co.com.usc.hostalusc.repository.model.common.Room;
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
@RequestMapping(path = "/v1/rooms")
@Tag(name = "Room Controller")
@Slf4j
public class RoomController {

    private final RoomsService roomService;

    public RoomController(RoomsService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Get all available rooms")
    @GetMapping(value = "/available", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getAvailableRooms() {
        log.info("Fetching all available rooms");
        try {
            List<Room> rooms = roomService.getAllAvailableRooms();
            return ResponseUtils.buildOutputContractSuccessResponse(rooms, HttpStatus.OK.value(), (long) rooms.size());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error getAvailableRooms() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Get all rooms with pagination")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getAllRooms(Pageable pageable) {
        log.info("Fetching all rooms with pagination");
        try {
            Page<Room> rooms = roomService.getAllRooms(pageable);
            return ResponseUtils.buildOutputContractSuccessResponse(rooms.getContent(), HttpStatus.OK.value(), rooms.getTotalElements());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error getAllRooms() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Get room by ID")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getRoomById(@PathVariable("id") Long id) {
        log.info("Fetching room with ID: {}", id);
        try {
            Room room = roomService.getRoomById(id);
            return ResponseUtils.buildOutputContractSuccessResponse(room, HttpStatus.OK.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error getRoomById() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Create new room")
    @PostMapping(value = "/save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> createRoom(@RequestBody Room room) {
        log.info("Creating new room with details: {}", room);
        try {
            Room createdRoom = roomService.createRoom(room);
            return ResponseUtils.buildOutputContractSuccessResponse(createdRoom, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error createRoom() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Update room")
    @PutMapping(value = "/update/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> updateRoom(@PathVariable("id") Long id, @RequestBody Room room) {
        log.info("Updating room with ID: {}", id);
        try {
            Room updatedRoom = roomService.updateRoom(id, room);
            return ResponseUtils.buildOutputContractSuccessResponse(updatedRoom, HttpStatus.OK.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error updateRoom() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Get available rooms by date range and filters")
    @GetMapping(value = "/available", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getAvailableRooms(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date endDate,
            @RequestParam("capacity") Integer capacity,
            @RequestParam("dormitories") Integer dormitories) {

        log.info("Fetching available rooms with startDate: {}, endDate: {}, capacity: {}, dormitories: {}",
                startDate, endDate, capacity, dormitories);
        try {
            List<Room> rooms = roomService.getAvailableRooms(startDate, endDate, capacity, dormitories);
            return ResponseUtils.buildOutputContractSuccessResponse(rooms, HttpStatus.OK.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse(
                    "Error getAvailableRooms() -> " + ex.getMessage(),
                    MessageCode.INTERVAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    @Operation(summary = "Get room by room number")
    @GetMapping(value = "/room-number/{roomNumber}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getRoomByRoomNumber(@PathVariable("roomNumber") String roomNumber) {
        log.info("Fetching room with room number: {}", roomNumber);
        try {
            Room room = roomService.getRoomByRoomNumber(roomNumber);
            return ResponseUtils.buildOutputContractSuccessResponse(room, HttpStatus.OK.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse(
                    "Error getRoomByRoomNumber() -> " + ex.getMessage(),
                    MessageCode.INTERVAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }
}