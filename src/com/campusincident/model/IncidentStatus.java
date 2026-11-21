package com.campusincident.model;

public enum IncidentStatus {
    REPORTED,
    IN_PROGRESS,
    RESOLVED;

    public boolean canTransitionTo(IncidentStatus newStatus) {

        if (newStatus == null) {
            return false;
        }

        return switch (this) {
            case REPORTED -> newStatus == IN_PROGRESS;
            case IN_PROGRESS -> newStatus == RESOLVED;
            case RESOLVED -> false;
        };
    }
}
