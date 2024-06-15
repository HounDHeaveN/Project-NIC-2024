package com.projectapi.Project_NIC.repository;

import com.projectapi.Project_NIC.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {


    Optional<Review> findByApplicationTransactionId(String application_id);
}
