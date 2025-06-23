package lk.ijse.userservice.service.impl;

import lk.ijse.userservice.dto.UserDTO;
import lk.ijse.userservice.entity.User;
import lk.ijse.userservice.repo.UserRepo;
import lk.ijse.userservice.service.UserService;
import lk.ijse.userservice.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int saveUser(UserDTO userDTO) {
        boolean isExist = userRepo.existsByEmail(userDTO.getEmail());

        if (isExist) {
            return VarList.Unauthorized;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            User user = modelMapper.map(userDTO, User.class);
            userRepo.save(user);
            return VarList.Created;
        }
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        if (userRepo.existsByEmail(email)) {
            User user = userRepo.getReferenceById(email);
            return modelMapper.map(user, UserDTO.class);
        } else {
            return null;
        }
    }

    @Override
    public List<UserDTO>getAllUsers() {
        return modelMapper.map(userRepo.findAll(), new TypeToken<List<UserDTO>>(){}.getType());
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        if (userRepo.existsByEmail(userDTO.getEmail())) {
            User user = modelMapper.map(userDTO, User.class);
            userRepo.save(user);
            return userDTO;
        } else {
            return null; // or throw an exception
        }
    }

    @Override
    public boolean deleteUser(String email) {
        if (userRepo.existsByEmail(email)) {
            User deleteUser = userRepo.getReferenceById(email);
            userRepo.delete(deleteUser);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepo.findById(email).isPresent();
    }
}
