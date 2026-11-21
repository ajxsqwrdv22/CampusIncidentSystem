package com.campusincident.strategy;

public class InAppNotificationStrategy implements NotificationStrategy {

    @Override
    public void sendNotification(String recipient, String message) {

        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException(
                "Notification recipient cannot be empty."
            );
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException(
                "Notification message cannot be empty."
            );
        }

        System.out.println("=== IN-APP NOTIFICATION ===");
        System.out.println("Recipient: " + recipient.trim());
        System.out.println("Notification: " + message.trim());
        System.out.println("===========================");
    }
}
