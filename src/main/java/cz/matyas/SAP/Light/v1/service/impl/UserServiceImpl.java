package cz.matyas.SAP.Light.v1.service.impl;

import cz.matyas.SAP.Light.v1.dto.UserDTO;
import cz.matyas.SAP.Light.v1.entity.UserEntity;
import cz.matyas.SAP.Light.v1.mapper.UserMapper;
import cz.matyas.SAP.Light.v1.repository.UserRepository;
import cz.matyas.SAP.Light.v1.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        userEntityList.forEach(userEntity -> userDTOList.add(userMapper.toDTO(userEntity)));

        return userDTOList;
    }

    @Override
    public UserDTO getUserById(Long id) {
        UserEntity userEntity = getUserEntityOrThrow(id);

        return userMapper.toDTO(userEntity);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        return saveUserDTOorThrow(userDTO);
    }

    @Override
    public UserDTO editUserById(Long id, UserDTO updateUserDTO) {
        getUserEntityOrThrow(id);
        updateUserDTO.setId(id);

        return saveUserDTOorThrow(updateUserDTO);
    }

    @Override
    public UserDTO deleteUserById(Long id) {
        UserEntity deletedUserEntity = getUserEntityOrThrow(id);
        userRepository.delete(deletedUserEntity);

        return userMapper.toDTO(deletedUserEntity);
    }

    private UserEntity getUserEntityOrThrow(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new EntityNotFoundException("Uživatel s id " + id + " nebyl nalezen");
        }

        return userEntity.get();
    }

    private UserDTO saveUserDTOorThrow(UserDTO userDTO) {
        UserEntity createdUser = userMapper.toEntity(userDTO);
        createdUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        try {
            createdUser = userRepository.save(createdUser);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("email " + userDTO.getEmail() + "je již obsazen");
        }

        return userMapper.toDTO(createdUser);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity;
        try {
            userEntity = userRepository.findByEmail(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Uživatel s emailem " + username + " neexistuje");
        }
        return userEntity;
    }
}
