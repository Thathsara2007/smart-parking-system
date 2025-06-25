package lk.ijse.parkingspaceservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "parking_space")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParkingSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceId;
    private String location;
    private String slotCode;
    private boolean isAvailable;
    private String level;
    private String hourlyRate;
    private String email;

}
