package cz.matyas.SAP.Light.v1.repository;

import cz.matyas.SAP.Light.v1.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    @Query("SELECT a FROM AddressEntity a WHERE a.user.id = :userId")
    List<AddressEntity> findAllAddressByUserId(@Param("userId") Long currentUserId);
}
