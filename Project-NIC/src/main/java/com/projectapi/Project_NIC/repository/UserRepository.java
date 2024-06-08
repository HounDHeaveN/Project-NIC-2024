package com.projectapi.Project_NIC.repository;

import com.projectapi.Project_NIC.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
}
