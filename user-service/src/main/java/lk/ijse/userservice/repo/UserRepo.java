package lk.ijse.userservice.repo;

import lk.ijse.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
}
