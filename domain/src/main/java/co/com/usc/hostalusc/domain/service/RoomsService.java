package co.com.usc.hostalusc.domain.service;

import co.com.usc.hostalusc.repository.model.common.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface RoomsService {
    List<Room> getAllAvailableRooms();
    Page<Room> getAllRooms(Pageable pageable);
    Room getRoomById(Long id);
    Room createRoom(Room room);
    Room updateRoom(Long id, Room room);
    List<Room> getAvailableRooms(java.util.Date startDate, java.util.Date endDate, Integer capacity, Integer dormitories);
    Room getRoomByRoomNumber(String roomNumber);

}