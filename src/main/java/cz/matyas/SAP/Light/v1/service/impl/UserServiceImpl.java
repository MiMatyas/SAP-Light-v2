package cz.matyas.SAP.Light.v1.service.impl;

import cz.matyas.SAP.Light.v1.dto.UserDTO;
import cz.matyas.SAP.Light.v1.entity.UserEntity;
import cz.matyas.SAP.Light.v1.mapper.UserMapper;
import cz.matyas.SAP.Light.v1.repository.UserRepository;
import cz.matyas.SAP.Light.v1.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Override
    public List<UserDTO> getAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        userEntityList.forEach(userEntity -> userDTOList.add(userMapper.toDTO(userEntity)));

        return userDTOList;
    }

    @Override
    public UserDTO getUserById(Long id) {
        UserEntity userEntity = geUserEntityOrThrow(id);

        return userMapper.toDTO(userEntity);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity createdUser = userRepository.save(userMapper.toEntity(userDTO));

        return userMapper.toDTO(createdUser);
    }

    @Override
    public UserDTO editUserById(Long id, UserDTO updateUserDTO) {
        geUserEntityOrThrow(id);
        UserEntity updateUserEntity = userMapper.toEntity(updateUserDTO);
        updateUserEntity.setId(id);
        userRepository.save(updateUserEntity);

        return userMapper.toDTO(updateUserEntity);
    }

    @Override
    public UserDTO deleteUserById(Long id) {
        UserEntity deletedUserEntity = geUserEntityOrThrow(id);
        userRepository.delete(deletedUserEntity);

        return userMapper.toDTO(deletedUserEntity);
    }
    private UserEntity geUserEntityOrThrow(Long id){
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()){
            throw new EntityNotFoundException("Uživatel s id " + id + " nebyl nalezen");
        }

        return userEntity.get();
    }
}
