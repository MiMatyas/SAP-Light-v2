package cz.matyas.SAP.Light.v1.service;

import cz.matyas.SAP.Light.v1.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAll();
    UserDTO getUserById(Long id);
    UserDTO createUser(UserDTO userDTO);
    UserDTO editUserById(Long id, UserDTO updateUserDTO);
    UserDTO deleteUserById(Long id);
}
