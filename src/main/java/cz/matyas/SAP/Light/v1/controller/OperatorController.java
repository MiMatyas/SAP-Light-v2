package cz.matyas.SAP.Light.v1.controller;

import cz.matyas.SAP.Light.v1.dto.OrderDTO;
import cz.matyas.SAP.Light.v1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operator")
@Secured(value = {"ROLE_OPERATOR", "ROLE_SUPER_ADMIN"})
public class OperatorController {
    @Autowired
    OrderService orderService;
    @GetMapping("/receiving")
    List<OrderDTO> getReceivingOrders(){

        return orderService.getReceivingOrders();
    }
    @GetMapping("/customer")
    List<OrderDTO> getCustomersOrders(){

        return orderService.getCustomersOrders();
    }
    @PostMapping("/receiving/{orderId}")
    OrderDTO completeReceivingOrder(@PathVariable("orderId") Long id){

        return orderService.completeReceivingOrder(id);
    }

    @PostMapping("/customer/{orderId}")
    OrderDTO completeCustomerOrder(@PathVariable("orderId") Long id){

        return orderService.completeCustomerOrder(id);
    }
}
