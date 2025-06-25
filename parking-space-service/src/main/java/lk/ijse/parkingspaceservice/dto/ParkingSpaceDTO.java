package lk.ijse.parkingspaceservice.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class ParkingSpaceDTO {
    private Long spaceId;
    private String location;
    private String slotCode;
    private boolean isAvailable;
    private String level;
    private String hourlyRate;
    private String email;
}
