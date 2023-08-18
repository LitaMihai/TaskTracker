package com.litamihai.controller;

import com.litamihai.model.AcceptNotificationsModel;
import com.litamihai.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/reminder")
public class ReminderController {
    private final ReminderService reminderService;

    @Autowired
    public ReminderController(ReminderService reminderService) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        this.reminderService = reminderService;
        this.reminderService.initWebPush();
    }

    @PostMapping("/notifications")
    public ResponseEntity<AcceptNotificationsModel> acceptNotifications(@RequestBody AcceptNotificationsModel model) {
        // If not subscribed, then subscribe.
        if(!reminderService.isSubscribed()) {
            try {
                reminderService.initWebPush();
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
                throw new RuntimeException(e);
            }
        }
        AcceptNotificationsModel acceptNotificationsModel1 = reminderService.acceptNotifications(model);
        return new ResponseEntity<>(acceptNotificationsModel1, HttpStatus.OK);
    }
}
