package com.litamihai.repository;

import com.litamihai.model.TaskModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaskRepository extends MongoRepository<TaskModel, String> {
    Optional<TaskModel> findTaskModelById(String id);
}
