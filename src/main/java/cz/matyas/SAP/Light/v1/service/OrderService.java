package cz.matyas.SAP.Light.v1.service;

import cz.matyas.SAP.Light.v1.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAll();
    OrderDTO getOrderById(Long id);
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO editOrderById(Long id, OrderDTO updateOrderDTO);
    OrderDTO deleteOrderById(Long id);

    List<OrderDTO> getOrderForCurrentCustomer();
    OrderDTO createOrderForCurrentCustomer(OrderDTO orderDTO);
}
