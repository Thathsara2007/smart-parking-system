package lk.ijse.vehicleservice.service.impl;

import lk.ijse.vehicleservice.dto.VehicleDTO;
import lk.ijse.vehicleservice.entity.Vehicle;
import lk.ijse.vehicleservice.feign.ClientVehicle;
import lk.ijse.vehicleservice.repo.VehicleRepo;
import lk.ijse.vehicleservice.service.VehicleService;
import lk.ijse.vehicleservice.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final ClientVehicle clientVehicle;
    private final VehicleRepo vehicleRepo;

    @Autowired
    ModelMapper modelMapper;

    public VehicleServiceImpl(ClientVehicle clientVehicle, VehicleRepo vehicleRepo) {
        this.clientVehicle = clientVehicle;
        this.vehicleRepo = vehicleRepo;
    }

    @Override
    public int saveVehicle(VehicleDTO vehicleDTO) {
        try {
            ResponseEntity<Boolean> response = clientVehicle.existsByEmail(vehicleDTO.getEmail());

            if (response.getBody() != null && response.getBody()) {
                Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
                vehicleRepo.save(vehicle);
                return VarList.Created;
            } else {
                return VarList.Not_Acceptable;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
        /*try {
            ResponseEntity<Boolean> response = clientVehicle.existsByEmail(vehicleDTO.getEmail());
            if (response.getBody() != null && response.getBody()) {
                Vehicle vehicle;

                if (vehicleDTO.getVehicleId() != null) {
                    // ⚠️ Load existing entity to avoid detached merge issues
                    vehicle = vehicleRepo.findById(vehicleDTO.getVehicleId())
                            .orElse(new Vehicle()); // Or throw if not found
                } else {
                    vehicle = new Vehicle();
                }

                // ✅ Update fields manually (safer)
                vehicle.setLicensePlate(vehicleDTO.getLicensePlate());
                vehicle.setType(vehicleDTO.getType());
                vehicle.setEmail(vehicleDTO.getEmail());

                vehicleRepo.save(vehicle);
                return VarList.Created; // 201
            } else {
                return VarList.Not_Acceptable; // 406
            }
        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error; // 500
        }*/
    }


    @Override
    public List<VehicleDTO> getAllVehicles() {
        return modelMapper.map(vehicleRepo.findAll(), new TypeToken<List<VehicleDTO>>(){}.getType());
    }
}
