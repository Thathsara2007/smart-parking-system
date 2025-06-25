package lk.ijse.parkingreservationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
@EnableDiscoveryClient
public class ParkingReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingReservationServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
