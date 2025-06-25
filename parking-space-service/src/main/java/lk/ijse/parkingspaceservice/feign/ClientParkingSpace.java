package lk.ijse.parkingspaceservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE")
public interface ClientParkingSpace {
    @GetMapping("/user-service/api/v1/user/check")
    ResponseEntity<Boolean> existsByEmail(@RequestParam("email") String email);
}