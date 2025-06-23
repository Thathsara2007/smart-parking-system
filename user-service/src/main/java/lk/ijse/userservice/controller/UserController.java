package lk.ijse.userservice.controller;

import lk.ijse.userservice.dto.AuthDTO;
import lk.ijse.userservice.dto.ResponseDTO;
import lk.ijse.userservice.dto.UserDTO;
import lk.ijse.userservice.service.UserService;
import lk.ijse.userservice.util.JwtUtil;
import lk.ijse.userservice.util.VarList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final JwtUtil jwtUtil;

    private final UserService userService;

    public UserController(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserDTO userDTO) {
        int response = userService.saveUser(userDTO);

        switch (response) {
            case 201: // Created
                String token = jwtUtil.generateToken(userDTO);
                AuthDTO authDTO = new AuthDTO();
                authDTO.setUser(userDTO);
                authDTO.setToken(token);
                return ResponseEntity.status(201).body(new ResponseDTO(201, "User created successfully", authDTO));
            case 401: // Unauthorized
                return ResponseEntity.status(401).body(new ResponseDTO(401, "Email already exists", null));
            case 400: // Bad Request
                return ResponseEntity.status(400).body(new ResponseDTO(400, "Invalid user data", null));
            default:
                return ResponseEntity.status(500).body(new ResponseDTO(500, "Internal Server Error", null));
        }
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body( null);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllUsers() {
        List<UserDTO> userList = userService.getAllUsers();
        if (userList.isEmpty()) {
            return ResponseEntity.status(204)
                    .body(new ResponseDTO(204, "No users found", null));
        } else {
            return ResponseEntity.status(200)
                    .body(new ResponseDTO(200, "Users retrieved successfully", userList));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(userDTO);
        if (updatedUser != null) {
            return ResponseEntity.status(200)
                    .body(new ResponseDTO(200, "User updated successfully", updatedUser));
        } else {
            return ResponseEntity.status(404)
                    .body(new ResponseDTO(404, "User not found", null));
        }
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String email) {
        boolean isDeleted = userService.deleteUser(email);
        if (isDeleted) {
            return ResponseEntity.status(200)
                    .body(new ResponseDTO(200, "User deleted successfully", null));
        } else {
            return ResponseEntity.status(404)
                    .body(new ResponseDTO(404, "User not found", null));
        }
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        // Check if the user exists by email
        boolean exists = userService.existByEmail(email);
        return ResponseEntity.ok(exists);
    }

}
