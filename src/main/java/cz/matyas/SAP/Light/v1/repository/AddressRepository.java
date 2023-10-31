package cz.matyas.SAP.Light.v1.repository;

import cz.matyas.SAP.Light.v1.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
