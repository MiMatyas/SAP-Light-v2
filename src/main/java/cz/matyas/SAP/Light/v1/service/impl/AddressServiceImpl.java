package cz.matyas.SAP.Light.v1.service.impl;

import cz.matyas.SAP.Light.v1.dto.AddressDTO;
import cz.matyas.SAP.Light.v1.entity.AddressEntity;
import cz.matyas.SAP.Light.v1.entity.UserEntity;
import cz.matyas.SAP.Light.v1.mapper.AddressMapper;
import cz.matyas.SAP.Light.v1.repository.AddressRepository;
import cz.matyas.SAP.Light.v1.repository.UserRepository;
import cz.matyas.SAP.Light.v1.service.AddressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressMapper addressMapper;
    @Override
    public List<AddressDTO> getAll() {
        List<AddressEntity> addressEntityList = addressRepository.findAll();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        addressEntityList.forEach(addressEntity -> addressDTOList.add(addressMapper.toDTO(addressEntity)));

        return addressDTOList;
    }

    @Override
    public AddressDTO getAddressById(Long id) {

        return addressMapper.toDTO(getAddressEntityOrThrow(id));
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        addressDTO.setId(null);
        AddressEntity createdAddressEntity = addressRepository.save(addressMapper.toEntity(addressDTO));

        return addressMapper.toDTO(createdAddressEntity);
    }

    @Override
    public AddressDTO createAddressForUser(AddressDTO addressDTO) {
        AddressEntity addressEntity = addressMapper.toEntity(addressDTO);
        addressEntity.setUser(userRepository.findById(getCurrentUserId()).get());
        AddressEntity createdAddressEntity = addressRepository.save(addressEntity);

        return addressMapper.toDTO(addressEntity);
    }

    @Override
    public List<AddressDTO> getAddressForCurrentUser() {
        List<AddressEntity> addressEntityList = addressRepository.findAllAddressByUserId(getCurrentUserId());
        List<AddressDTO> addressDTOList = new ArrayList<>();
        addressEntityList.forEach(addressEntity -> addressDTOList.add(addressMapper.toDTO(addressEntity)));

        return addressDTOList;
    }


    @Override
    public AddressDTO editAddressById(Long id, AddressDTO updateAddressDTO) {
        getAddressEntityOrThrow(id);
        AddressEntity updateAddressEntity = addressMapper.toEntity(updateAddressDTO);
        updateAddressEntity.setId(id);
        addressRepository.save(updateAddressEntity);

        return addressMapper.toDTO(updateAddressEntity);
    }

    @Override
    public AddressDTO deleteAddressById(Long id) {
        AddressEntity deletedAddressEntity = getAddressEntityOrThrow(id);
        addressRepository.delete(deletedAddressEntity);

        return addressMapper.toDTO(deletedAddressEntity);
    }

    private AddressEntity getAddressEntityOrThrow(Long id){
        Optional<AddressEntity> addressEntity = addressRepository.findById(id);
        if (addressEntity.isEmpty()){
            throw new EntityNotFoundException("Adresa s id " + id + " nebyla nalezena");
        }

        return addressEntity.get();
    }

    private Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUserEntity = (UserEntity)authentication.getPrincipal();

        return currentUserEntity.getId();
    }
}
