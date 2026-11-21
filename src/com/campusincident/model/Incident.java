package com.campusincident.model;
import java.util.ArrayList;
import java.util.List;

import com.campusincident.observer.IncidentObserver;
import com.campusincident.observer.IncidentSubject;

public class Incident implements IncidentSubject {

    private final String incidentId;
    private String title;
    private String description;
    private String location;
    private Priority priority;
    private IncidentStatus status;
    private final List<IncidentObserver> observers;

    public Incident(
        String incidentId,
        String title,
        String description,
        String location,
        Priority priority
) {
    if (incidentId == null || incidentId.isBlank()) {
        throw new IllegalArgumentException(
            "Incident ID cannot be empty."
        );
    }

    this.incidentId = incidentId.trim();

    setTitle(title);
    setDescription(description);
    setLocation(location);
    setPriority(priority);

    this.status = IncidentStatus.REPORTED;
    this.observers = new ArrayList<>();

}



public String getIncidentId() {
    return incidentId;
}

public String getTitle() {
    return title;
}

public String getDescription() {
    return description;
}

public String getLocation() {
    return location;
}

public Priority getPriority() {
    return priority;
}

public IncidentStatus getStatus() {
    return status;
}


public void setTitle(String title) {
    if (title == null || title.isBlank()) {
        throw new IllegalArgumentException(
            "Incident title cannot be empty."
        );
    }

    this.title = title.trim();
}

public void setDescription(String description) {
    if (description == null || description.isBlank()) {
        throw new IllegalArgumentException(
            "Incident description cannot be empty."
        );
    }

    this.description = description.trim();
}

public void setLocation(String location) {
    if (location == null || location.isBlank()) {
        throw new IllegalArgumentException(
            "Incident location cannot be empty."
        );
    }

    this.location = location.trim();
}

public void setPriority(Priority priority) {
    if (priority == null) {
        throw new IllegalArgumentException(
            "Incident priority cannot be null."
        );
    }

    this.priority = priority;
}


public void updateStatus(IncidentStatus newStatus) {

    if (newStatus == null) {
        throw new IllegalArgumentException(
            "New incident status cannot be null."
        );
    }

    if (!status.canTransitionTo(newStatus)) {
        throw new IllegalStateException(
            "Cannot change incident status from "
            + status + " to " + newStatus
        );
    }
    IncidentStatus oldStatus = this.status;
    this.status = newStatus;
    notifyObservers(oldStatus, newStatus);
}
@Override
public void addObserver(IncidentObserver observer) {

    if (observer == null) {
        throw new IllegalArgumentException(
            "Observer cannot be null."
        );
    }

    if (observers.contains(observer)) {
        throw new IllegalArgumentException(
            "Observer is already subscribed."
        );
    }

    observers.add(observer);
}
@Override
public void removeObserver(IncidentObserver observer) {

    if (observer == null) {
        throw new IllegalArgumentException(
            "Observer cannot be null."
        );
    }

    if (!observers.contains(observer)) {
        throw new IllegalArgumentException(
            "Observer is not subscribed."
        );
    }

    observers.remove(observer);
}

@Override
public void notifyObservers(
        IncidentStatus oldStatus,
        IncidentStatus newStatus
) {
    for (IncidentObserver observer : new ArrayList<>(observers)) {

        observer.onIncidentStatusChanged(
            this,
            oldStatus,
            newStatus
        );
    }
}

}
