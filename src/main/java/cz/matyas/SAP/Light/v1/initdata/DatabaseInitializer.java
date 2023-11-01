package cz.matyas.SAP.Light.v1.initdata;

import cz.matyas.SAP.Light.v1.entity.AddressEntity;
import cz.matyas.SAP.Light.v1.entity.GoodsEntity;
import cz.matyas.SAP.Light.v1.entity.OrderEntity;
import cz.matyas.SAP.Light.v1.entity.UserEntity;
import cz.matyas.SAP.Light.v1.enums.Country;
import cz.matyas.SAP.Light.v1.enums.Status;
import cz.matyas.SAP.Light.v1.repository.AddressRepository;
import cz.matyas.SAP.Light.v1.repository.GoodsRepository;
import cz.matyas.SAP.Light.v1.repository.OrderRepository;
import cz.matyas.SAP.Light.v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
@Component
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        initGoods();
        initUsers();
        initAddress();
        initOrders();
    }

    private void initOrders() {
        OrderEntity orderEntity1 = new OrderEntity();
        orderEntity1.setCreateDateTime(Date.from(Instant.now()));
        orderEntity1.setStatus(Status.NEW);
        orderEntity1.setUser(userRepository.findById(1L).get());
        orderEntity1.setAddress(addressRepository.findById(1L).get());
        orderEntity1.setGoods(goodsRepository.findAllById(List.of(1L,2L,3L)));

        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setCreateDateTime(Date.from(Instant.now()));
        orderEntity2.setStatus(Status.NEW);
        orderEntity2.setUser(userRepository.findById(2L).get());
        orderEntity2.setAddress(addressRepository.findById(2L).get());
        orderEntity2.setGoods(goodsRepository.findAllById(List.of(4L,5L,6L)));

        orderRepository.saveAll(List.of(orderEntity1, orderEntity2));
    }

    private void initUsers() {
        UserEntity userEntityAdam = new UserEntity();
        userEntityAdam.setFirstName("Adam");
        userEntityAdam.setSurname("Novak");
        userEntityAdam.setEmail("adam.novak@gmail.com");


        UserEntity userEntityPetr = new UserEntity();
        userEntityPetr.setFirstName("Petr");
        userEntityPetr.setSurname("Karadi");
        userEntityPetr.setEmail("petr.karadi@gmail.com");

        userRepository.saveAll(List.of(userEntityAdam, userEntityPetr));

    }

    private void initGoods() {
        GoodsEntity goodsEntity1 = new GoodsEntity();
        goodsEntity1.setPartNumber("111-A");
        goodsEntity1.setPrice(999.9D);
        goodsEntity1.setAvilableQuantity(500);

        GoodsEntity goodsEntity2 = new GoodsEntity();
        goodsEntity2.setPartNumber("222-B");
        goodsEntity2.setPrice(899.9D);
        goodsEntity2.setAvilableQuantity(100);

        GoodsEntity goodsEntity3 = new GoodsEntity();
        goodsEntity3.setPartNumber("111-A");
        goodsEntity3.setPrice(799.9D);
        goodsEntity3.setAvilableQuantity(500);

        GoodsEntity goodsEntity4 = new GoodsEntity();
        goodsEntity4.setPartNumber("333-C");
        goodsEntity4.setPrice(699.9D);
        goodsEntity4.setAvilableQuantity(100);

        GoodsEntity goodsEntity5 = new GoodsEntity();
        goodsEntity5.setPartNumber("444-D");
        goodsEntity5.setPrice(599.9D);
        goodsEntity5.setAvilableQuantity(500);

        GoodsEntity goodsEntity6 = new GoodsEntity();
        goodsEntity6.setPartNumber("555-E");
        goodsEntity6.setPrice(499.9D);
        goodsEntity6.setAvilableQuantity(100);

        goodsRepository.saveAll(Arrays.asList(goodsEntity1, goodsEntity2, goodsEntity3, goodsEntity4, goodsEntity5, goodsEntity6));
    }

    private void initAddress() {
        AddressEntity addressEntityPrague = new AddressEntity();
        addressEntityPrague.setCountry(Country.CZECH);
        addressEntityPrague.setCity("Praha");
        addressEntityPrague.setStreet("Dolní");
        addressEntityPrague.setZipCode(66101);
        addressEntityPrague.setNumberOfHouse(111);
        addressEntityPrague.setUser(userRepository.findById(1L).get());

        AddressEntity addressEntityBrno = new AddressEntity();
        addressEntityBrno.setCountry(Country.CZECH);
        addressEntityBrno.setCity("Brno");
        addressEntityBrno.setStreet("Horní");
        addressEntityBrno.setZipCode(66632);
        addressEntityBrno.setNumberOfHouse(222);
        addressEntityBrno.setUser(userRepository.findById(2L).get());

        addressRepository.saveAll(Arrays.asList(addressEntityPrague, addressEntityBrno));

    }
}