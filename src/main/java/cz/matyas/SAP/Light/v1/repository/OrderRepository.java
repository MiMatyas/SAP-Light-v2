package cz.matyas.SAP.Light.v1.repository;

import cz.matyas.SAP.Light.v1.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT e FROM OrderEntity e WHERE e.user.id = :userId")
    List<OrderEntity> findAllByUser(@Param("userId") Long id);
}
