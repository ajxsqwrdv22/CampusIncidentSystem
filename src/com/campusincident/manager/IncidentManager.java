package com.campusincident.manager;

import java.util.ArrayList;
import java.util.List;

import com.campusincident.model.Incident;

public class IncidentManager {

    private final List<Incident> incidents;

    public IncidentManager() {
        this.incidents = new ArrayList<>();
    }


public void addIncident(Incident incident) {

    if (incident == null) {
        throw new IllegalArgumentException(
            "Incident cannot be null."
        );
    }

    for (Incident existingIncident : incidents) {

        if (existingIncident.getIncidentId()
                .equals(incident.getIncidentId())) {

            throw new IllegalArgumentException(
                "An incident with this ID already exists."
            );
        }
    }

    incidents.add(incident);
}

public List<Incident> getAllIncidents() {
    return new ArrayList<>(incidents);
}

public Incident getIncidentById(String incidentId) {

    if (incidentId == null || incidentId.isBlank()) {
        throw new IllegalArgumentException(
            "Incident ID cannot be empty."
        );
    }

    for (Incident incident : incidents) {

        if (incident.getIncidentId().equals(incidentId.trim())) {
            return incident;
        }
    }

    return null;
}

}
