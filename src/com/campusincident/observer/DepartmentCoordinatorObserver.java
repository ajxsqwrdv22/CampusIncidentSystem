package com.campusincident.observer;

import com.campusincident.model.Incident;
import com.campusincident.model.IncidentStatus;

public class DepartmentCoordinatorObserver implements IncidentObserver {

    private final String name;

    public DepartmentCoordinatorObserver(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Department Coordinator name cannot be empty."
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
            "[DEPARTMENT COORDINATOR] " + name
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
