package cz.matyas.SAP.Light.v1.mapper;

import cz.matyas.SAP.Light.v1.dto.AddressDTO;
import cz.matyas.SAP.Light.v1.entity.AddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDTO toDTO(AddressEntity addressEntity);

    AddressEntity toEntity(AddressDTO addressDTO);
}