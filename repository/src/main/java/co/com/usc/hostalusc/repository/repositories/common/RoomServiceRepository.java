package co.com.usc.hostalusc.repository.repositories.common;

import co.com.usc.hostalusc.repository.model.common.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomServiceRepository extends CrudRepository<RoomService, Long> {

    @Query("select rs from RoomService rs where rs.reservation.id = ?1")
    @Transactional(readOnly = true)
    List<RoomService> findByReservationId(Long reservationId);

    @Query("select rs from RoomService rs")
    @Transactional(readOnly = true)
    Page<RoomService> findAll(Pageable pageable);

    @Query("select rs from RoomService rs where rs.id = ?1")
    @Transactional(readOnly = true)
    Optional<RoomService> findById(Long id);

    @Query("select rs from RoomService rs where rs.serviceType = ?1")
    @Transactional(readOnly = true)
    List<RoomService> findByServiceType(String serviceType);
}