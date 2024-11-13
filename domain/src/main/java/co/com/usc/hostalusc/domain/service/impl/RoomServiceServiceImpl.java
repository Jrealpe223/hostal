package co.com.usc.hostalusc.domain.service.impl;

import co.com.usc.hostalusc.domain.exceptions.UscException;
import co.com.usc.hostalusc.domain.service.RoomServiceService;
import co.com.usc.hostalusc.repository.model.common.RoomService;
import co.com.usc.hostalusc.repository.repositories.common.RoomServiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceServiceImpl implements RoomServiceService {

    private final RoomServiceRepository roomServiceRepository;

    public RoomServiceServiceImpl(RoomServiceRepository roomServiceRepository) {
        this.roomServiceRepository = roomServiceRepository;
    }

    @Override
    public List<RoomService> getAllServicesByReservationId(Long reservationId) {
        return roomServiceRepository.findByReservationId(reservationId);
    }

    @Override
    public Page<RoomService> getAllServices(Pageable pageable) {
        return roomServiceRepository.findAll(pageable);
    }

    @Override
    public RoomService getServiceById(Long id) {
        return roomServiceRepository.findById(id).orElseThrow(() -> new UscException("Servicio de habitación no encontrado"));
    }

    @Override
    public RoomService createService(RoomService roomService) {
        if (roomService.getServiceType() == null || roomService.getServiceType().isEmpty()) {
            throw new UscException("El nombre del servicio es obligatorio");
        }
        return roomServiceRepository.save(roomService);
    }

    @Override
    public RoomService updateService(Long id, RoomService roomService) {
        RoomService serviceToUpdate = roomServiceRepository.findById(id).orElseThrow(() -> new UscException("Servicio no encontrado"));

        if (roomService.getServiceType() != null) {
            serviceToUpdate.setServiceType(roomService.getServiceType());
        }
        serviceToUpdate.setStatus(roomService.getStatus());
        serviceToUpdate.setPrice(roomService.getPrice());
        return roomServiceRepository.save(serviceToUpdate);
    }
}