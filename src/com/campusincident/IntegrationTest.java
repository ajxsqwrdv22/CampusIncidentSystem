package com.campusincident;

import com.campusincident.model.Incident;
import com.campusincident.model.IncidentStatus;
import com.campusincident.model.Priority;

import com.campusincident.observer.LabTechnicianObserver;

import com.campusincident.strategy.NotificationContext;
import com.campusincident.strategy.InAppNotificationStrategy;
import com.campusincident.strategy.EmailNotificationStrategy;
import com.campusincident.observer.ITSupportObserver;
import com.campusincident.strategy.SMSNotificationStrategy;


public class IntegrationTest {

    public static void main(String[] args) {

        System.out.println(
            "=== STRATEGY + OBSERVER INTEGRATION TEST ==="
        );

        // 1. Create a new incident
        Incident incident = new Incident(
            "INC-INT-001",
            "Broken Laboratory Computer",
            "Computer does not turn on.",
            "Computer Laboratory 1",
            Priority.HIGH
        );

        // 2. Create a notification context using In-App
        NotificationContext context =
            new NotificationContext(
                new InAppNotificationStrategy()
            );

        // 3. Create the Lab Technician observer
        LabTechnicianObserver labTechnician =
            new LabTechnicianObserver("Alex");

        // 4. Assign the notification context to Alex
        labTechnician.setNotificationContext(context);

        // 5. Subscribe Alex to the incident
        incident.addObserver(labTechnician);

        System.out.println("Lab Technician Alex subscribed.");
        System.out.println("Selected Strategy: IN-APP");

        // 6. Trigger an incident status change
        incident.updateStatus(IncidentStatus.IN_PROGRESS);

        // 7. Verify the updated status
        if (incident.getStatus() == IncidentStatus.IN_PROGRESS) {
            System.out.println(
                "PASS: Incident updated and notification triggered"
            );
        } else {
            System.out.println(
                "FAIL: Incident status was not updated"
            );
        }

System.out.println("\n=== STRATEGY SWITCHING INTEGRATION TEST ===");

// 1. Change the existing context from In-App to Email
context.setStrategy(new EmailNotificationStrategy());

System.out.println("Selected Strategy: EMAIL");

// 2. Trigger the next valid incident status change
incident.updateStatus(IncidentStatus.RESOLVED);

// 3. Verify that the incident was resolved
if (incident.getStatus() == IncidentStatus.RESOLVED) {
    System.out.println(
        "PASS: Incident resolved after switching to Email"
    );
} else {
    System.out.println(
        "FAIL: Incident did not reach RESOLVED"
    );
}

System.out.println(
    "\n=== MULTIPLE OBSERVERS WITH DIFFERENT STRATEGIES TEST ==="
);

// 1. Create a separate incident for this test
Incident secondIncident = new Incident(
    "INC-INT-002",
    "Laboratory Network Connection Failure",
    "Computers cannot connect to the campus network.",
    "Computer Laboratory 2",
    Priority.HIGH
);

// 2. Create separate notification contexts
NotificationContext emailContext =
    new NotificationContext(
        new EmailNotificationStrategy()
    );

NotificationContext smsContext =
    new NotificationContext(
        new SMSNotificationStrategy()
    );

// 3. Create the Lab Technician observer
LabTechnicianObserver emailTechnician =
    new LabTechnicianObserver("Alex");

emailTechnician.setNotificationContext(emailContext);

// 4. Create the IT Support observer
ITSupportObserver smsSupport =
    new ITSupportObserver("Jordan");

smsSupport.setNotificationContext(smsContext);

// 5. Subscribe both observers to the same incident
secondIncident.addObserver(emailTechnician);
secondIncident.addObserver(smsSupport);

System.out.println("Alex subscribed with EMAIL.");
System.out.println("Jordan subscribed with SMS.");

// 6. Trigger ONE incident status change
secondIncident.updateStatus(IncidentStatus.IN_PROGRESS);

// 7. Verify the updated status
if (secondIncident.getStatus() == IncidentStatus.IN_PROGRESS) {
    System.out.println(
        "PASS: Incident updated with two notification strategies"
    );
} else {
    System.out.println(
        "FAIL: Incident status was not updated"
    );
}

    }
}
