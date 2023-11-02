package cz.matyas.SAP.Light.v1.controller;

import cz.matyas.SAP.Light.v1.dto.AddressDTO;
import cz.matyas.SAP.Light.v1.dto.GoodsDTO;
import cz.matyas.SAP.Light.v1.dto.OrderDTO;
import cz.matyas.SAP.Light.v1.service.AddressService;
import cz.matyas.SAP.Light.v1.service.GoodsService;
import cz.matyas.SAP.Light.v1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@Secured(value = "ROLE_CUSTOMER")
public class CustomerController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    AddressService addressService;
    @GetMapping("/goods")
    List<GoodsDTO> findAllGoods(){

        return goodsService.getAll();
    }
    @PostMapping("/create/order")
    OrderDTO createOrder(@RequestBody OrderDTO orderDTO){

        return orderService.createOrderForCurrentCustomer(orderDTO);
    }
    @PostMapping("/create/address")
    AddressDTO createAddress(@RequestBody AddressDTO addressDTO){

        return addressService.createAddressForUser(addressDTO);
    }
    @GetMapping("/get/orders")
    List<OrderDTO> getCustomerOrders(){

        return orderService.getOrderForCurrentCustomer();
    }
    @GetMapping("/get/address")
    List<AddressDTO> getCustomerAddress(){

        return addressService.getAddressForCurrentUser();
    }
}
