package cz.matyas.SAP.Light.v1.controller;

import cz.matyas.SAP.Light.v1.dto.OrderDTO;
import cz.matyas.SAP.Light.v1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping
    List<OrderDTO> getAllOrders() {

        return orderService.getAll();
    }

    @GetMapping("{orderId}")
    OrderDTO getOrderById(@PathVariable("orderId") Long id) {

        return orderService.getOrderById(id);
    }

    @PostMapping("/create")
    OrderDTO createOrderEntity(@RequestBody OrderDTO orderDTO) {

       return orderService.createOrder(orderDTO);
    }

    @PutMapping("/edit/{orderId}")
    OrderDTO editOrderById(@PathVariable("orderId") Long id, OrderDTO orderDTO) {

        return orderService.editOrderById(id, orderDTO);
    }

    @DeleteMapping("delete/{orderId}")
    OrderDTO deleteOrderById(@PathVariable("orderId") Long id) {

        return orderService.deleteOrderById(id);
    }
}
