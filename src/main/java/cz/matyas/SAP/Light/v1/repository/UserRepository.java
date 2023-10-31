package cz.matyas.SAP.Light.v1.repository;

import cz.matyas.SAP.Light.v1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
