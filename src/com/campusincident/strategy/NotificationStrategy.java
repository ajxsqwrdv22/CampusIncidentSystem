package com.campusincident.strategy;

public interface NotificationStrategy {

    void sendNotification(
        String recipient,
        String message
    );
}
