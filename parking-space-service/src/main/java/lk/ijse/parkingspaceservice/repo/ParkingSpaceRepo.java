package lk.ijse.parkingspaceservice.repo;

import lk.ijse.parkingspaceservice.entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSpaceRepo extends JpaRepository<ParkingSpace, Long> {
    List<ParkingSpace> findAllByIsAvailable(boolean isAvailable);
    Optional<ParkingSpace> findBySlotCode(String slotCode);

}
