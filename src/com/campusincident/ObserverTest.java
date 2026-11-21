package com.campusincident;

import com.campusincident.model.Incident;
import com.campusincident.model.IncidentStatus;
import com.campusincident.model.Priority;
import com.campusincident.observer.LabTechnicianObserver;
import com.campusincident.observer.ITSupportObserver;
import com.campusincident.observer.DepartmentCoordinatorObserver;

public class ObserverTest {

    public static void main(String[] args) {

        System.out.println(
            "=== LAB TECHNICIAN OBSERVER TEST ==="
        );

        // 1. Create an incident
        Incident incident = new Incident(
            "INC-OBS-001",
            "Broken Laboratory Computer",
            "Computer does not turn on.",
            "Computer Laboratory 1",
            Priority.HIGH
        );

        // 2. Create a Lab Technician observer
        LabTechnicianObserver labTechnician =
            new LabTechnicianObserver("Alex");

        // 3. Subscribe the observer to the incident
        incident.addObserver(labTechnician);


// Create an IT Support Staff observer
ITSupportObserver itSupport =
    new ITSupportObserver("Jordan");

// Subscribe IT Support Staff to the same incident
incident.addObserver(itSupport);


// Create a Department Coordinator observer
DepartmentCoordinatorObserver coordinator =
    new DepartmentCoordinatorObserver("Taylor");

// Subscribe the coordinator to the same incident
incident.addObserver(coordinator);

System.out.println("Department Coordinator subscribed.");


System.out.println("IT Support Staff subscribed.");


        System.out.println("Lab Technician subscribed.");

        // 4. Trigger a valid incident status change
        incident.updateStatus(IncidentStatus.IN_PROGRESS);

        // 5. Verify the incident's updated status
        if (incident.getStatus() == IncidentStatus.IN_PROGRESS) {
            System.out.println(
                "PASS: Incident status updated successfully"
            );
        } else {
            System.out.println(
                "FAIL: Incident status was not updated"
            );
        }


System.out.println("\n=== OBSERVER UNSUBSCRIBE TEST ===");

// Unsubscribe the Department Coordinator
incident.removeObserver(coordinator);

System.out.println(
    "Department Coordinator Taylor unsubscribed."
);

// Trigger another valid status change
incident.updateStatus(IncidentStatus.RESOLVED);

// Verify the incident's updated status
if (incident.getStatus() == IncidentStatus.RESOLVED) {
    System.out.println(
        "PASS: Incident status changed to RESOLVED"
    );
} else {
    System.out.println(
        "FAIL: Incident status was not updated"
    );
}


System.out.println("\n=== DUPLICATE OBSERVER TEST ===");

boolean duplicateRejected = false;

try {
    // Alex is already subscribed
    incident.addObserver(labTechnician);

} catch (IllegalArgumentException e) {

    if ("Observer is already subscribed."
            .equals(e.getMessage())) {

        duplicateRejected = true;
    }

    System.out.println("Reason: " + e.getMessage());
}

if (duplicateRejected) {
    System.out.println(
        "PASS: Duplicate observer subscription rejected"
    );
} else {
    System.out.println(
        "FAIL: Duplicate observer subscription not handled correctly"
    );
}

System.out.println("\n=== INVALID UNSUBSCRIBE TEST ===");

boolean invalidUnsubscribeRejected = false;

try {
    // Taylor was already unsubscribed
    incident.removeObserver(coordinator);

} catch (IllegalArgumentException e) {

    if ("Observer is not subscribed."
            .equals(e.getMessage())) {

        invalidUnsubscribeRejected = true;
    }

    System.out.println("Reason: " + e.getMessage());
}

if (invalidUnsubscribeRejected) {
    System.out.println(
        "PASS: Invalid unsubscribe request rejected"
    );
} else {
    System.out.println(
        "FAIL: Invalid unsubscribe request not handled correctly"
    );
}

System.out.println(
    "Current Incident Status: " + incident.getStatus()
);

    }
}
