package lk.ijse.vehicleservice.controller;

import lk.ijse.vehicleservice.dto.ResponseDTO;
import lk.ijse.vehicleservice.dto.VehicleDTO;
import lk.ijse.vehicleservice.repo.VehicleRepo;
import lk.ijse.vehicleservice.service.VehicleService;
import lk.ijse.vehicleservice.util.VarList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerVehicle(@RequestBody VehicleDTO vehicleDTO) {
        int response = vehicleService.saveVehicle(vehicleDTO);

        return switch (response) {
            case 201 -> ResponseEntity.status(201)
                    .body(new ResponseDTO(VarList.Created, "Vehicle saved successfully", vehicleDTO));
            case 406 -> ResponseEntity.status(406)
                    .body(new ResponseDTO(VarList.Not_Acceptable, "Email not found in User Service", null));
            case 400 -> ResponseEntity.status(400)
                    .body(new ResponseDTO(VarList.Bad_Request, "Invalid data provided", null));
            default -> ResponseEntity.status(500)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Server error", null));
        };
    }

    @GetMapping("/getAllVehicles")
    public ResponseEntity<ResponseDTO> getAllVehicles() {
        List<VehicleDTO> vehicleList = vehicleService.getAllVehicles();

        if (vehicleList.isEmpty()) {
            return ResponseEntity.status(204)
                    .body(new ResponseDTO(VarList.No_Content, "No vehicles found", null));
        } else {
            return ResponseEntity.status(200)
                    .body(new ResponseDTO(VarList.OK, "Vehicles retrieved successfully", vehicleList));
        }
    }

}
