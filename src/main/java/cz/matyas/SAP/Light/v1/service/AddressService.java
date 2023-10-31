package cz.matyas.SAP.Light.v1.service;

import cz.matyas.SAP.Light.v1.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAll();
    AddressDTO getAddressById(Long id);

    AddressDTO createAddress(AddressDTO addressDTO);
    AddressDTO editAddressById(Long id, AddressDTO updateAddressDTO);
    AddressDTO deleteAddressById(Long id);
}
