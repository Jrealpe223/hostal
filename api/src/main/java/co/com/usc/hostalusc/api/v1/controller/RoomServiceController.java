package co.com.usc.hostalusc.api.v1.controller;


import co.com.usc.hostalusc.api.utils.ResponseUtils;
import co.com.usc.hostalusc.domain.contracts.MessageCode;
import co.com.usc.hostalusc.domain.contracts.OutputContract;
import co.com.usc.hostalusc.domain.service.RoomServiceService;
import co.com.usc.hostalusc.repository.model.common.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1/room-services")
@Tag(name = "Room Service Controller")
@Slf4j
public class RoomServiceController {

    private final RoomServiceService roomServiceService;

    public RoomServiceController(RoomServiceService roomServiceService) {
        this.roomServiceService = roomServiceService;
    }

    @Operation(summary = "Get all room services by reservation ID")
    @GetMapping(value = "/reservation/{reservationId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getAllServicesByReservationId(@PathVariable("reservationId") Long reservationId) {
        log.info("Ejecuta servicio getAllServicesByReservationId con reservationId: {}", reservationId);
        try {
            List<RoomService> services = roomServiceService.getAllServicesByReservationId(reservationId);
            return ResponseUtils.buildOutputContractSuccessResponse(services, HttpStatus.OK.value(), (long) services.size());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error getAllServicesByReservationId() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Get all room services with pagination")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getAllServices(Pageable pageable) {
        log.info("Ejecuta servicio getAllServices con paginación: {}", pageable);
        try {
            Page<RoomService> services = roomServiceService.getAllServices(pageable);
            return ResponseUtils.buildOutputContractSuccessResponse(services.getContent(), HttpStatus.OK.value(), services.getTotalElements());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error getAllServices() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Get room service by ID")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getServiceById(@PathVariable("id") Long id) {
        log.info("Ejecuta servicio getServiceById con ID: {}", id);
        try {
            RoomService service = roomServiceService.getServiceById(id);
            return ResponseUtils.buildOutputContractSuccessResponse(service, HttpStatus.OK.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error getServiceById() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Create a new room service")
    @PostMapping(value = "/save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> createService(@RequestBody RoomService roomService) {
        log.info("Ejecuta servicio createService con body: {}", roomService);
        try {
            RoomService createdService = roomServiceService.createService(roomService);
            return ResponseUtils.buildOutputContractSuccessResponse(createdService, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error createService() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Update a room service")
    @PutMapping(value = "/update/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> updateService(@RequestBody RoomService roomService, @PathVariable("id") Long id) {
        log.info("Ejecuta servicio updateService con ID {} y body: {}", id, roomService);
        try {
            RoomService updatedService = roomServiceService.updateService(id, roomService);
            return ResponseUtils.buildOutputContractSuccessResponse(updatedService, HttpStatus.OK.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error updateService() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}