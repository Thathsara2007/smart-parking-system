package lk.ijse.parkingspaceservice.service;

import lk.ijse.parkingspaceservice.dto.ParkingSpaceDTO;

import java.util.List;

public interface ParkingSpaceService {
    int saveParkingSpace(ParkingSpaceDTO parkingSpaceDto);

    List<ParkingSpaceDTO> getAllParkingSpaces();

    List<ParkingSpaceDTO> getAvailableParkingSpaces();

    boolean isParkingSpaceAvailable(String slotCode);
}
