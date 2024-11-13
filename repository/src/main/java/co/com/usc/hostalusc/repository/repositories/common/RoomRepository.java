package co.com.usc.hostalusc.repository.repositories.common;

import co.com.usc.hostalusc.repository.model.common.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface RoomRepository extends CrudRepository<Room, Long> {

    @Query("select r from Room r where r.status='Available'")
    @Transactional(readOnly = true)
    List<Room> findAllAvailable();

    @Query("select r from Room r")
    @Transactional(readOnly = true)
    Page<Room> findAll(Pageable pageable);

    @Query("select r from Room r where r.roomNumber = ?1")
    @Transactional(readOnly = true)
    Optional<Room> findByRoomNumber(String roomNumber);

    @Query("select r from Room r where r.id = ?1")
    @Transactional(readOnly = true)
    Optional<Room> findById(Long id);
}
