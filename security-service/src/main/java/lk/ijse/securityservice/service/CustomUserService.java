package lk.ijse.securityservice.service;

import lk.ijse.securityservice.dto.UserDTO;
import lk.ijse.securityservice.feign.UserInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {
    private final UserInterface userInterface;

    public CustomUserService(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            ResponseEntity<UserDTO> response = userInterface.getUserByEmail(username);
            UserDTO userDTO = response.getBody();

            if (userDTO == null) {
                throw new UsernameNotFoundException("User not found with email: " + username);
            }

            return new User(
                    userDTO.getEmail(),
                    userDTO.getPassword(),
                    List.of(new SimpleGrantedAuthority(userDTO.getRole()))
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserDTO getUser(String email) {
        ResponseEntity<UserDTO> response = userInterface.getUserByEmail(email);
        UserDTO userDTO = response.getBody();

        if (userDTO == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return userDTO;
    }
}
