package cz.matyas.SAP.Light.v1.mapper;

import cz.matyas.SAP.Light.v1.dto.UserDTO;
import cz.matyas.SAP.Light.v1.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "addressIds", expression = "java(getAddressIds(userEntity))")
    UserDTO toDTO(UserEntity userEntity);
    @Mapping(target = "addresses", ignore = true)
    UserEntity toEntity(UserDTO userDTO);

    default List<Long> getAddressIds(UserEntity userEntity){
        List<Long> addressIds = new ArrayList<>();
        userEntity.getAddresses().forEach(addressEntity -> addressIds.add(addressEntity.getId()));

        return addressIds;
    }
}
