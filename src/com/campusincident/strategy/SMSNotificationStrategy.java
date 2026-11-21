package com.campusincident.strategy;

public class SMSNotificationStrategy implements NotificationStrategy {

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

        System.out.println("=== SMS NOTIFICATION ===");
        System.out.println("To: " + recipient.trim());
        System.out.println("[SMS] " + message.trim());
        System.out.println("========================");
    }
}
