package com.campusincident.observer;

import com.campusincident.model.IncidentStatus;

public interface IncidentSubject {

    void addObserver(IncidentObserver observer);

    void removeObserver(IncidentObserver observer);

    void notifyObservers(
        IncidentStatus oldStatus,
        IncidentStatus newStatus
    );
}
