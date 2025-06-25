package lk.ijse.parkingspaceservice.controller;

import lk.ijse.parkingspaceservice.dto.ParkingSpaceDTO;
import lk.ijse.parkingspaceservice.dto.ResponseDTO;
import lk.ijse.parkingspaceservice.service.ParkingSpaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parking-space")
public class ParkingSpaceController {
    private final ParkingSpaceService parkingSpaceService;

    public ParkingSpaceController(ParkingSpaceService parkingSpaceService) {
        this.parkingSpaceService = parkingSpaceService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> saveParkingSpace(@RequestBody ParkingSpaceDTO parkingSpaceDTO){
        int response = parkingSpaceService.saveParkingSpace(parkingSpaceDTO);

        return switch (response) {
            case 201 -> ResponseEntity.status(201)
                    .body(new ResponseDTO(201, "Parking space saved successfully", parkingSpaceDTO));
            case 406 -> ResponseEntity.status(406)
                    .body(new ResponseDTO(406, "Email not found in User Service", null));
            case 400 -> ResponseEntity.status(400)
                    .body(new ResponseDTO(400, "Invalid data provided", null));
            default -> ResponseEntity.status(500)
                    .body(new ResponseDTO(500, "Server error", null));
        };
    }
}
