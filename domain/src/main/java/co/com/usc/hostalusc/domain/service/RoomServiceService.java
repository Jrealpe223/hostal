package co.com.usc.hostalusc.domain.service;

import co.com.usc.hostalusc.repository.model.common.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RoomServiceService {
    List<RoomService> getAllServicesByReservationId(Long reservationId);
    Page<RoomService> getAllServices(Pageable pageable);
    RoomService getServiceById(Long id);
    RoomService createService(RoomService roomService);
    RoomService updateService(Long id, RoomService roomService);
}