package cz.matyas.SAP.Light.v1.initdata;

import cz.matyas.SAP.Light.v1.entity.AddressEntity;
import cz.matyas.SAP.Light.v1.entity.GoodsEntity;
import cz.matyas.SAP.Light.v1.entity.OrderEntity;
import cz.matyas.SAP.Light.v1.entity.UserEntity;
import cz.matyas.SAP.Light.v1.enums.Country;
import cz.matyas.SAP.Light.v1.enums.Role;
import cz.matyas.SAP.Light.v1.enums.Status;
import cz.matyas.SAP.Light.v1.repository.AddressRepository;
import cz.matyas.SAP.Light.v1.repository.GoodsRepository;
import cz.matyas.SAP.Light.v1.repository.OrderRepository;
import cz.matyas.SAP.Light.v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        UserEntity userEntitySuperAdmin = new UserEntity();
        userEntitySuperAdmin.setFirstName("Super");
        userEntitySuperAdmin.setSurname("Admin");
        userEntitySuperAdmin.setEmail("1");
        userEntitySuperAdmin.setPassword(passwordEncoder.encode("1"));
        userEntitySuperAdmin.setRole(Role.SUPER_ADMIN);

        UserEntity userEntityAdmin = new UserEntity();
        userEntityAdmin.setFirstName("Adam");
        userEntityAdmin.setSurname("Novak");
        userEntityAdmin.setEmail("adam.novak@gmail.com");
        userEntityAdmin.setPassword(passwordEncoder.encode("adamnovak"));
        userEntityAdmin.setRole(Role.ADMIN);

        UserEntity userEntityOperator = new UserEntity();
        userEntityOperator.setFirstName("Petr");
        userEntityOperator.setSurname("Karadi");
        userEntityOperator.setEmail("petr.karadi@gmail.com");
        userEntityOperator.setPassword(passwordEncoder.encode("petrkkaradi"));
        userEntityOperator.setRole(Role.OPERATOR);

        UserEntity userEntityCustomer = new UserEntity();
        userEntityCustomer.setFirstName("Marcel");
        userEntityCustomer.setSurname("Raisig");
        userEntityCustomer.setEmail("mardec.raisig@gmail.com");
        userEntityCustomer.setPassword(passwordEncoder.encode("mardelraisig"));
        userEntityCustomer.setRole(Role.CUSTOMER);

        userRepository.saveAll(List.of(userEntitySuperAdmin, userEntityAdmin, userEntityOperator, userEntityCustomer));

    }

    private void initGoods() {
        GoodsEntity goodsEntity1 = new GoodsEntity();
        goodsEntity1.setPartNumber("111-A");
        goodsEntity1.setPrice(999.9D);
        goodsEntity1.setAvailableQuantity(500);

        GoodsEntity goodsEntity2 = new GoodsEntity();
        goodsEntity2.setPartNumber("222-B");
        goodsEntity2.setPrice(899.9D);
        goodsEntity2.setAvailableQuantity(100);

        GoodsEntity goodsEntity3 = new GoodsEntity();
        goodsEntity3.setPartNumber("111-A");
        goodsEntity3.setPrice(799.9D);
        goodsEntity3.setAvailableQuantity(500);

        GoodsEntity goodsEntity4 = new GoodsEntity();
        goodsEntity4.setPartNumber("333-C");
        goodsEntity4.setPrice(699.9D);
        goodsEntity4.setAvailableQuantity(100);

        GoodsEntity goodsEntity5 = new GoodsEntity();
        goodsEntity5.setPartNumber("444-D");
        goodsEntity5.setPrice(599.9D);
        goodsEntity5.setAvailableQuantity(500);

        GoodsEntity goodsEntity6 = new GoodsEntity();
        goodsEntity6.setPartNumber("555-E");
        goodsEntity6.setPrice(499.9D);
        goodsEntity6.setAvailableQuantity(100);

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