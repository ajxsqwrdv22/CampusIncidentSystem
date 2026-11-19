
package com.campusincident.observer;

import com.campusincident.model.Incident;
import com.campusincident.model.IncidentStatus;
import com.campusincident.strategy.NotificationContext;

public class LabTechnicianObserver implements IncidentObserver {

    private final String name;
    private NotificationContext notificationContext;

    public LabTechnicianObserver(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Lab Technician name cannot be empty."
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

    // Build the incident status-change message
    String message =
        "Incident " + incident.getIncidentId()
        + " (" + incident.getTitle() + ")"
        + " changed from " + oldStatus
        + " to " + newStatus + ".";

    // Use the selected notification strategy if available
    if (notificationContext != null) {

        notificationContext.sendNotification(
            name,
            message
        );

    } else {

        // Preserve the original console notification
        System.out.println(
            "[LAB TECHNICIAN] " + name
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
