package com.campusincident.observer;

import com.campusincident.model.Incident;
import com.campusincident.model.IncidentStatus;

public interface IncidentObserver {

    void onIncidentStatusChanged(
        Incident incident,
        IncidentStatus oldStatus,
        IncidentStatus newStatus
    );
}
