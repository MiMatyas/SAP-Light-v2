package cz.matyas.SAP.Light.v1.repository;

import cz.matyas.SAP.Light.v1.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.user.id = :userId")
    List<OrderEntity> findAllByUser(@Param("userId") Long id);

    @Query("SELECT o FROM OrderEntity o WHERE o.user.role = ADMIN")
    List<OrderEntity> findAllOrdersForReceiving();
    @Query("SELECT o FROM OrderEntity o WHERE o.user.role != ADMIN")
    List<OrderEntity> findAllOrdersForCustomers();
    @Query("SELECT o FROM OrderEntity o WHERE o.user.role = ADMIN AND o.id = :orderId AND o.status = NEW")
    Optional<OrderEntity> findOrdersForReceivingById(@Param("orderId") Long id);
    @Query("SELECT o FROM OrderEntity o WHERE o.user.role != ADMIN AND o.id = :orderId AND o.status = NEW")
    Optional<OrderEntity> findOrdersForCustomersById(@Param("orderId") Long id);
}
