package cz.matyas.SAP.Light.v1.repository;

import cz.matyas.SAP.Light.v1.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<GoodsEntity, Long> {
}
