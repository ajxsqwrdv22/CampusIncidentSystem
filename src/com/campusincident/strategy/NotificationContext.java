package com.campusincident.strategy;

public class NotificationContext {

    private NotificationStrategy strategy;

    public NotificationContext(NotificationStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(NotificationStrategy strategy) {

        if (strategy == null) {
            throw new IllegalArgumentException(
                "Notification strategy cannot be null."
            );
        }

        this.strategy = strategy;
    }

    public void sendNotification(
            String recipient,
            String message
    ) {
        strategy.sendNotification(recipient, message);
    }
}
