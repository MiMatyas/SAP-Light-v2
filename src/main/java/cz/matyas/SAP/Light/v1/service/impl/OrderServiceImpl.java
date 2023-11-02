package cz.matyas.SAP.Light.v1.service.impl;

import cz.matyas.SAP.Light.v1.dto.OrderDTO;
import cz.matyas.SAP.Light.v1.entity.OrderEntity;
import cz.matyas.SAP.Light.v1.entity.UserEntity;
import cz.matyas.SAP.Light.v1.mapper.OrderMapper;
import cz.matyas.SAP.Light.v1.repository.OrderRepository;
import cz.matyas.SAP.Light.v1.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderMapper orderMapper;
    @Override
    public List<OrderDTO> getAll() {
        List<OrderEntity> orderEntityList = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderEntityList.forEach(orderEntity -> orderDTOList.add(orderMapper.toDTO(orderEntity)));

        return orderDTOList;
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        OrderEntity orderEntity = getOrderEntityOrThrow(id);

        return orderMapper.toDTO(orderEntity);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        OrderEntity createdOrderEntity = orderRepository.save(orderMapper.toEntity(orderDTO));

        return orderMapper.toDTO(createdOrderEntity);
    }
    @Override
    public OrderDTO createOrderForCurrentCustomer(OrderDTO orderDTO) {
        orderDTO.setId(getCurrentUserId());
        OrderEntity createdOrderEntity = orderRepository.save(orderMapper.toEntity(orderDTO));

        return orderMapper.toDTO(createdOrderEntity);
    }

    @Override
    public OrderDTO createOrderForReceiving(OrderDTO orderDTO) {
        orderDTO.setId(getCurrentUserId());

        OrderEntity createdOrderEntity = orderRepository.save(orderMapper.toEntity(orderDTO));

        return orderMapper.toDTO(createdOrderEntity);
    }


    @Override
    public OrderDTO editOrderById(Long id, OrderDTO updateOrderDTO) {
        getOrderEntityOrThrow(id);
        OrderEntity updateOrderEntity = orderMapper.toEntity(updateOrderDTO);
        updateOrderEntity.setId(id);
        orderRepository.save(updateOrderEntity);

        return orderMapper.toDTO(updateOrderEntity);
    }

    @Override
    public OrderDTO deleteOrderById(Long id) {
        OrderEntity deletedOrderEntity = getOrderEntityOrThrow(id);
        orderRepository.delete(deletedOrderEntity);

        return orderMapper.toDTO(deletedOrderEntity);
    }

    @Override
    public List<OrderDTO> getOrderForCurrentCustomer() {
        List<OrderEntity> orderEntityList = orderRepository.findAllByUser(getCurrentUserId());
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderEntityList.forEach(orderEntity -> orderDTOList.add(orderMapper.toDTO(orderEntity)));

        return orderDTOList;
    }

    private OrderEntity getOrderEntityOrThrow(Long id){
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if (orderEntity.isEmpty()){
            throw new EntityNotFoundException("Objednávka s id " + id + " nebyla nalezena");
        }

        return orderEntity.get();
    }

    private Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUserEntity = (UserEntity)authentication.getPrincipal();

        return currentUserEntity.getId();
    }
}
