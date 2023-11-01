package cz.matyas.SAP.Light.v1.mapper;

import cz.matyas.SAP.Light.v1.dto.UserDTO;
import cz.matyas.SAP.Light.v1.entity.AddressEntity;
import cz.matyas.SAP.Light.v1.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface UserMapper {
    @Mapping(target = "addressIds", expression = "java(getAddressIds(userEntity))")
    UserDTO toDTO(UserEntity userEntity);

    @Mapping(target = "addresses", expression = "java(getAddress(userDTO))")
    UserEntity toEntity(UserDTO userDTO);

    default List<Long> getAddressIds(UserEntity userEntity){
        List<Long> addressIds = new ArrayList<>();
        if (userEntity.getAddresses() != null) {
            userEntity.getAddresses().forEach(addressEntity -> addressIds.add(addressEntity.getId()));
        }

        return addressIds;
    }

    default List<AddressEntity> getAddress(UserDTO userDTO){
        List<AddressEntity> addressEntityList = new ArrayList<>();
        if (userDTO.getAddressIds() != null){
            userDTO.getAddressIds().forEach(addressId -> {
                AddressEntity address = new AddressEntity();
                address.setId(addressId);
                addressEntityList.add(address);
            });
        }

        return addressEntityList;
    }
}
