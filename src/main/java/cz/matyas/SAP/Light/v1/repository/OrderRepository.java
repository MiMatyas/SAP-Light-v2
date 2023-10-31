package cz.matyas.SAP.Light.v1.repository;

import cz.matyas.SAP.Light.v1.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
