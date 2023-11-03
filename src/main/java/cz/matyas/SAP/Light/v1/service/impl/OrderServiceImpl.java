package cz.matyas.SAP.Light.v1.service.impl;

import cz.matyas.SAP.Light.v1.dto.OrderDTO;
import cz.matyas.SAP.Light.v1.entity.AddressEntity;
import cz.matyas.SAP.Light.v1.entity.GoodsEntity;
import cz.matyas.SAP.Light.v1.entity.OrderEntity;
import cz.matyas.SAP.Light.v1.entity.UserEntity;
import cz.matyas.SAP.Light.v1.enums.Status;
import cz.matyas.SAP.Light.v1.mapper.OrderMapper;
import cz.matyas.SAP.Light.v1.repository.AddressRepository;
import cz.matyas.SAP.Light.v1.repository.GoodsRepository;
import cz.matyas.SAP.Light.v1.repository.OrderRepository;
import cz.matyas.SAP.Light.v1.repository.UserRepository;
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
    GoodsRepository goodsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    AddressRepository addressRepository;

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
        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);
        orderEntity.setStatus(Status.NEW);
        orderEntity.setUser(userRepository.findById(getCurrentUserId()).get());
        AddressEntity currentUserAddress = findByAddressIdAndUserIdOrThrow(orderDTO.getAddressId(), getCurrentUserId());
        orderEntity.setAddress(currentUserAddress);
        List<GoodsEntity> goodsEntityList = goodsRepository.findAllById(orderDTO.getGoodsIds());
        orderEntity.setGoods(goodsEntityList);
        OrderEntity createdOrderEntity = orderRepository.save(orderEntity);

        return orderMapper.toDTO(createdOrderEntity);
    }

    @Override
    public OrderDTO createOrderForReceiving(OrderDTO orderDTO) {
        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);
        orderEntity.setStatus(Status.NEW);
        orderEntity.setUser(userRepository.findById(getCurrentUserId()).get());
        AddressEntity currentUserAddress = findByAddressIdAndUserIdOrThrow(orderDTO.getAddressId(), getCurrentUserId());
        orderEntity.setAddress(currentUserAddress);
        List<GoodsEntity> goodsEntityList = goodsRepository.findAllById(orderDTO.getGoodsIds());
        orderEntity.setGoods(goodsEntityList);
        OrderEntity createdOrderEntity = orderRepository.save(orderEntity);

        return orderMapper.toDTO(createdOrderEntity);
    }

    private AddressEntity findByAddressIdAndUserIdOrThrow(Long addressId, Long currentUserId) {
        Optional<AddressEntity> addressEntity = addressRepository.findByAddressIdAndUserId(addressId, currentUserId);
        if (addressEntity.isEmpty()){
            throw new EntityNotFoundException("Uživateli nepatří adresa s id "+ addressId);
        }

        return addressEntity.get();
    }

    @Override
    public List<OrderDTO> getReceivingOrders() {
        List<OrderEntity> orderEntityList = orderRepository.findAllOrdersForReceiving();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderEntityList.forEach(orderEntity -> orderDTOList.add(orderMapper.toDTO(orderEntity)));

        return orderDTOList;
    }

    @Override
    public List<OrderDTO> getCustomersOrders() {
        List<OrderEntity> orderEntityList = orderRepository.findAllOrdersForCustomers();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderEntityList.forEach(orderEntity -> orderDTOList.add(orderMapper.toDTO(orderEntity)));

        return orderDTOList;
    }

    @Override
    public OrderDTO completeReceivingOrder(Long id) {
        OrderEntity orderEntity = getOrderForReceivingOrThrow(id);

        List<GoodsEntity> receivingGoods = orderEntity.getGoods();
        receivingGoods.forEach(goodsEntity -> {
            goodsEntity.setAvailableQuantity(goodsEntity.getAvailableQuantity()+1);
        });
        goodsRepository.saveAll(receivingGoods);

        orderEntity.setStatus(Status.SHIPPED);
        orderRepository.save(orderEntity);

        return orderMapper.toDTO(orderEntity);
    }

    @Override
    public OrderDTO completeCustomerOrder(Long id) {
        OrderEntity orderEntity = getOrderForCustomerOrThrow(id);

        List<GoodsEntity> receivingGoods = orderEntity.getGoods();
        receivingGoods.forEach(goodsEntity -> {
            goodsEntity.setAvailableQuantity(goodsEntity.getAvailableQuantity()+1);
        });
        goodsRepository.saveAll(receivingGoods);

        orderEntity.setStatus(Status.RECEIVED);
        orderRepository.save(orderEntity);

        return orderMapper.toDTO(orderEntity);
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

    private OrderEntity getOrderForCustomerOrThrow(Long id){
        Optional<OrderEntity> orderEntity = orderRepository.findOrdersForReceivingById(id);
        if (orderEntity.isEmpty()){
            throw new EntityNotFoundException("Objednávka s id " + id + " neexistuje nebo nenáleží Zákazníkovy nebo je již odeslaná");
        }

        return orderEntity.get();
    }

    private OrderEntity getOrderForReceivingOrThrow(Long id){
        Optional<OrderEntity> orderEntity = orderRepository.findOrdersForCustomersById(id);
        if (orderEntity.isEmpty()){
            throw new EntityNotFoundException("Objednávka s id " + id + " neexistuje nebo není pro příjem nebo je již přijatá");
        }

        return orderEntity.get();
    }


    private Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUserEntity = (UserEntity)authentication.getPrincipal();

        return currentUserEntity.getId();
    }
}
