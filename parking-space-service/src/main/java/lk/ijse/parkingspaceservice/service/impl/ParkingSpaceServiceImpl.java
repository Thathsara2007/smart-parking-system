package lk.ijse.parkingspaceservice.service.impl;

import lk.ijse.parkingspaceservice.dto.ParkingSpaceDTO;
import lk.ijse.parkingspaceservice.entity.ParkingSpace;
import lk.ijse.parkingspaceservice.feign.ClientParkingSpace;
import lk.ijse.parkingspaceservice.repo.ParkingSpaceRepo;
import lk.ijse.parkingspaceservice.service.ParkingSpaceService;
import lk.ijse.parkingspaceservice.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpaceServiceImpl implements ParkingSpaceService {
    private final ClientParkingSpace clientParkingSpace;
    private final ParkingSpaceRepo parkingSpaceRepo;

    @Autowired
    ModelMapper modelMapper;

    public ParkingSpaceServiceImpl(ClientParkingSpace clientParkingSpace, ParkingSpaceRepo parkingSpaceRepo) {
        this.clientParkingSpace = clientParkingSpace;
        this.parkingSpaceRepo = parkingSpaceRepo;
    }

    @Override
    public int saveParkingSpace(ParkingSpaceDTO parkingSpaceDTO) {
            try {
                ResponseEntity<Boolean> response = clientParkingSpace.existsByEmail(parkingSpaceDTO.getEmail());

                if (response.getBody() != null && response.getBody()) {
                    ParkingSpace parkingSpace = modelMapper.map(parkingSpaceDTO, ParkingSpace.class);
                    parkingSpaceRepo.save(parkingSpace);
                    return VarList.Created;
                } else {
                    return VarList.Not_Acceptable; // Email doesn't exist
                }
            } catch (Exception e) {
                e.printStackTrace(); // log the error
                return VarList.Internal_Server_Error;
            }
    }

    @Override
    public List<ParkingSpaceDTO> getAllParkingSpaces() {
        try {
            List<ParkingSpace> parkingSpaces = parkingSpaceRepo.findAll();
            return modelMapper.map(parkingSpaces, new TypeToken<List<ParkingSpaceDTO>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace(); // log the error
            return null; // or throw a custom exception
        }
    }

    @Override
    public List<ParkingSpaceDTO> getAvailableParkingSpaces() {
        try {
            List<ParkingSpace> parkingSpaces = parkingSpaceRepo.findAllByIsAvailable(true);
            return modelMapper.map(parkingSpaces, new TypeToken<List<ParkingSpaceDTO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace(); // log the error
            return null; // or throw a custom exception
        }
    }

    @Override
    public boolean isParkingSpaceAvailable(String slotCode) {
        try {
            return parkingSpaceRepo.findBySlotCode(slotCode)
                    .map(ParkingSpace::isAvailable)
                    .orElse(false);
        } catch (Exception e) {
            e.printStackTrace(); // log the error
            return false; // or throw a custom exception
        }
    }
}
