
package com.campusincident.observer;

import com.campusincident.model.Incident;
import com.campusincident.model.IncidentStatus;

public class LabTechnicianObserver implements IncidentObserver {

    private final String name;

    public LabTechnicianObserver(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Lab Technician name cannot be empty."
            );
        }

        this.name = name.trim();
    }

    @Override
    public void onIncidentStatusChanged(
            Incident incident,
            IncidentStatus oldStatus,
            IncidentStatus newStatus
    ) {

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
