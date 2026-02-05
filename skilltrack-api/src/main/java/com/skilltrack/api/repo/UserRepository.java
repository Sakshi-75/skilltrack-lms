package com.skilltrack.api.repo;

import com.skilltrack.common.domain.user.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByIdAndDeletedFalse(UUID id);

    Optional<UserEntity> findByEmailAndDeletedFalse(String email);
}
