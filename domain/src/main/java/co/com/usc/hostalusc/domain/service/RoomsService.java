package co.com.usc.hostalusc.domain.service;

import co.com.usc.hostalusc.repository.model.common.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RoomsService {
    List<Room> getAllAvailableRooms();
    Page<Room> getAllRooms(Pageable pageable);
    Room getRoomById(Long id);
    Room createRoom(Room room);
    Room updateRoom(Long id, Room room);
}