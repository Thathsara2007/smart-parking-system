package lk.ijse.parkingspaceservice.controller;

import lk.ijse.parkingspaceservice.dto.ParkingSpaceDTO;
import lk.ijse.parkingspaceservice.dto.ResponseDTO;
import lk.ijse.parkingspaceservice.service.ParkingSpaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-all")
    public ResponseEntity<ResponseDTO> getAllParkingSpaces() {
        try {
            var parkingSpaces = parkingSpaceService.getAllParkingSpaces();
            if (parkingSpaces != null && !parkingSpaces.isEmpty()) {
                return ResponseEntity.ok(new ResponseDTO(200, "Parking spaces retrieved successfully", parkingSpaces));
            } else {
                return ResponseEntity.status(404)
                        .body(new ResponseDTO(404, "No parking spaces found", null));
            }
        } catch (Exception e) {
            e.printStackTrace(); // log the error
            return ResponseEntity.status(500)
                    .body(new ResponseDTO(500, "Internal server error", null));
        }
    }

    @GetMapping("/available/{slotCode}")
    public ResponseEntity<ResponseDTO> getAvailableParkingSpace(@PathVariable String slotCode) {
        try {
            boolean isAvailable = parkingSpaceService.isParkingSpaceAvailable(slotCode);
            String message = isAvailable ? "Parking space is available" : "Parking space is not available";
            return ResponseEntity.ok(new ResponseDTO(200, message, isAvailable));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseDTO(500, "Internal server error", null));

        }
    }
}
