package cz.matyas.SAP.Light.v1.controller;

import cz.matyas.SAP.Light.v1.dto.AddressDTO;
import cz.matyas.SAP.Light.v1.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping
    List<AddressDTO> getAll(){

        return addressService.getAll();
    }
    @GetMapping("/{addressId}")
    AddressDTO getAddressById(@PathVariable("addressId")Long id){

        return addressService.getAddressById(id);
    }
    @PostMapping("/create")
    AddressDTO createAddress(@RequestBody AddressDTO addressDTO){

        return addressService.createAddress(addressDTO);
    }
    @PutMapping("/edit/{addressId}")
    AddressDTO editAddressById(@PathVariable("addressId")Long id, @RequestBody AddressDTO addressDTO){

        return addressService.editAddressById(id, addressDTO);
    }
    @DeleteMapping("/delete/{addressId}")
    AddressDTO deleteAddressById(@PathVariable("addressId")Long id){

        return addressService.deleteAddressById(id);
    }
}
