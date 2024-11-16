package co.com.usc.hostalusc.domain.service.impl;

import co.com.usc.hostalusc.domain.exceptions.UscException;
import co.com.usc.hostalusc.domain.service.RoomsService;
import co.com.usc.hostalusc.repository.model.common.Room;
import co.com.usc.hostalusc.repository.repositories.common.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RoomServiceImpl implements RoomsService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllAvailableRooms() {
        return roomRepository.findAllAvailable();
    }

    @Override
    public Page<Room> getAllRooms(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new UscException("Habitación no encontrada"));
    }

    @Override
    public Room createRoom(Room room) {
        if (room.getRoomNumber() == null || room.getRoomNumber().isEmpty()) {
            throw new UscException("El número de la habitación es obligatorio");
        }
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long id, Room room) {
        Room roomToUpdate = roomRepository.findById(id).orElseThrow(() -> new UscException("Habitación no encontrada"));

        if (room.getRoomNumber() != null) {
            roomToUpdate.setRoomNumber(room.getRoomNumber());
        }
        if (room.getPricePerNight() != null) {
            roomToUpdate.setPricePerNight(room.getPricePerNight());
        }
        roomToUpdate.setStatus(room.getStatus());
        return roomRepository.save(roomToUpdate);
    }

    @Override
    public List<Room> getAvailableRoomsByDateRange(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new UscException("Las fechas de inicio y fin son obligatorias");
        }
        if (startDate.after(endDate)) {
            throw new UscException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        return roomRepository.findAvailableRoomsByDateRange(startDate, endDate);
    }

}
