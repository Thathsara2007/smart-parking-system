package lk.ijse.securityservice.controller;

import lk.ijse.securityservice.dto.AuthDTO;
import lk.ijse.securityservice.dto.ResponseDTO;
import lk.ijse.securityservice.dto.UserDTO;
import lk.ijse.securityservice.service.CustomUserService;
import lk.ijse.securityservice.util.JwtUtil;
import lk.ijse.securityservice.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/security")
@CrossOrigin
public class SecurityController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomUserService customUserService;

    public SecurityController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, CustomUserService customUserService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.customUserService = customUserService;
    }


    @PostMapping(path = "/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) {
        System.out.println();
        UserDTO loadedUser = customUserService.getUser(userDTO.getEmail());
        System.out.println();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid Credentials", e.getMessage()));
        }

        if (loadedUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        String token = jwtUtil.generateToken(loadedUser);
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        AuthDTO authDTO = new AuthDTO();
        authDTO.setUser(loadedUser);
        authDTO.setToken(token);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(VarList.Created, "Success", authDTO));
    }
}
