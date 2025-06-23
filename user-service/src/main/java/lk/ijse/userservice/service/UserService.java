package lk.ijse.userservice.service;

import lk.ijse.userservice.dto.UserDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

public interface UserService {
    int saveUser(UserDTO userDTO);

    UserDTO getUserByEmail(String email);

    List<UserDTO>getAllUsers();

    UserDTO updateUser(UserDTO userDTO);

    boolean deleteUser(String email);

    boolean existByEmail(String email);
}
