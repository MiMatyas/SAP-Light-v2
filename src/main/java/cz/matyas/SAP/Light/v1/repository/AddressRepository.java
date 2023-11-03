package cz.matyas.SAP.Light.v1.repository;

import cz.matyas.SAP.Light.v1.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    @Query("SELECT a FROM AddressEntity a WHERE a.user.id = :userId")
    List<AddressEntity> findAllAddressByUserId(@Param("userId") Long currentUserId);

    @Query("SELECT a FROM AddressEntity a WHERE a.id = :addressId AND a.user.id = :userId")
    Optional<AddressEntity> findByAddressIdAndUserId(@Param("addressId") Long addressId, @Param("userId") Long UserId);
}
