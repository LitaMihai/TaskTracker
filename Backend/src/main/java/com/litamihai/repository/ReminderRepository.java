package com.litamihai.repository;

import com.litamihai.model.AcceptNotificationsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReminderRepository extends MongoRepository<AcceptNotificationsModel, String> { }
