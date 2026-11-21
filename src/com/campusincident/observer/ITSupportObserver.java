package com.campusincident.observer;

import com.campusincident.model.Incident;
import com.campusincident.model.IncidentStatus;
import com.campusincident.strategy.NotificationContext;

public class ITSupportObserver implements IncidentObserver {

    private final String name;
    private NotificationContext notificationContext;


    public ITSupportObserver(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "IT Support Staff name cannot be empty."
            );
        }

        this.name = name.trim();
    }

public void setNotificationContext(NotificationContext notificationContext) {

    if (notificationContext == null) {
        throw new IllegalArgumentException(
            "Notification context cannot be null."
        );
    }

    this.notificationContext = notificationContext;
}

    
@Override
public void onIncidentStatusChanged(
        Incident incident,
        IncidentStatus oldStatus,
        IncidentStatus newStatus
) {

    // Build a message describing the status change
    String message =
        "Incident " + incident.getIncidentId()
        + " (" + incident.getTitle() + ")"
        + " changed from " + oldStatus
        + " to " + newStatus + ".";

    // Use the selected strategy if a context is assigned
    if (notificationContext != null) {

        notificationContext.sendNotification(
            name,
            message
        );

    } else {

        // Preserve the original IT Support notification
        System.out.println(
            "[IT SUPPORT] " + name
            + " received an update for incident "
            + incident.getIncidentId()
        );

        System.out.println(
            "Incident: " + incident.getTitle()
        );

        System.out.println(
            "Status changed: "
            + oldStatus + " -> " + newStatus
        );
    }
}

}
