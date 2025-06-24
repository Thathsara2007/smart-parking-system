package lk.ijse.vehicleservice.repo;

import lk.ijse.vehicleservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {
    Vehicle findByLicensePlate(String licensePlate);

    // Method to find vehicles by email
    List<Vehicle> findAllByEmail(String email);
}
