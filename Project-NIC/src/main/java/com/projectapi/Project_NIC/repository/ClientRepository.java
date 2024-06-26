package com.projectapi.Project_NIC.repository;

import com.projectapi.Project_NIC.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {
    @Query("{'client_id': ?0}")
    Optional<Client> findByClientId(String client_id);
    Optional<Client> findByMobileNo(long mobileNo);
}
