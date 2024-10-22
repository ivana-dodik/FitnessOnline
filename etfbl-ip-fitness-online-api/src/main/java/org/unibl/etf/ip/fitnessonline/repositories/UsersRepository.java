package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> getByUsername(String username);

    Optional<UserEntity> getByUsernameAndPasswordAndDeletedIsFalse(String username, String password);

    UserEntity findByUsername(String username);
}
