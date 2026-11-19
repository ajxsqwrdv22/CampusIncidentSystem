package com.campusincident;

import com.campusincident.model.Incident;
import com.campusincident.model.IncidentStatus;
import com.campusincident.model.Priority;

public class IncidentTest {

    public static void main(String[] args) {

        Incident incident = new Incident(
            "INC-001",
            "Laboratory Computer Malfunction",
            "Computer does not turn on",
            "Computer Laboratory 2",
            Priority.HIGH
        );

        System.out.println("=== INCIDENT TEST ===");
        System.out.println("ID: " + incident.getIncidentId());
        System.out.println("Title: " + incident.getTitle());
        System.out.println("Description: " + incident.getDescription());
        System.out.println("Location: " + incident.getLocation());
        System.out.println("Priority: " + incident.getPriority());
        System.out.println("Status: " + incident.getStatus());

        if (incident.getStatus() == IncidentStatus.REPORTED) {
            System.out.println("PASS: Initial status is REPORTED");
        } else {
            System.out.println("FAIL: Initial status is incorrect");
        }

System.out.println("\n=== VALIDATION TEST ===");

try {
    Incident invalidIncident = new Incident(
        "INC-002",
        "",
        "Computer does not turn on",
        "Computer Laboratory 2",
        Priority.HIGH
    );

    System.out.println("FAIL: Empty title was accepted");

} catch (IllegalArgumentException e) {

    System.out.println("PASS: Empty title was rejected");
    System.out.println("Reason: " + e.getMessage());

}


System.out.println("\n=== BLANK ID TEST ===");

try {
    new Incident(
        "   ",
        "Internet Connection Interrupted",
        "No internet connection in the computer laboratory",
        "Computer Laboratory 1",
        Priority.HIGH
    );

    System.out.println("FAIL: Blank incident ID was accepted");

} catch (IllegalArgumentException e) {

    System.out.println("PASS: Blank incident ID was rejected");
    System.out.println("Reason: " + e.getMessage());

}

System.out.println("\n=== NULL PRIORITY TEST ===");

try {
    new Incident(
        "INC-003",
        "Broken Laboratory Projector",
        "Projector cannot display images",
        "Room 203",
        null
    );

    System.out.println("FAIL: Null priority was accepted");

} catch (IllegalArgumentException e) {

    System.out.println("PASS: Null priority was rejected");
    System.out.println("Reason: " + e.getMessage());

}



System.out.println("\n=== EMPTY DESCRIPTION TEST ===");

try {
    new Incident(
        "INC-004",
        "Laboratory Internet Interruption",
        "",
        "Computer Laboratory 1",
        Priority.MEDIUM
    );

    System.out.println(
        "FAIL: Empty description was accepted"
    );

} catch (IllegalArgumentException e) {

    if ("Incident description cannot be empty."
            .equals(e.getMessage())) {

        System.out.println(
            "PASS: Empty description was rejected"
        );

    } else {
        System.out.println(
            "FAIL: Unexpected validation error"
        );
    }

    System.out.println("Reason: " + e.getMessage());
}

System.out.println("\n=== BLANK LOCATION TEST ===");

try {
    new Incident(
        "INC-005",
        "Electrical Interruption",
        "Several classrooms have no electricity",
        "   ",
        Priority.HIGH
    );

    System.out.println(
        "FAIL: Blank location was accepted"
    );

} catch (IllegalArgumentException e) {

    if ("Incident location cannot be empty."
            .equals(e.getMessage())) {

        System.out.println(
            "PASS: Blank location was rejected"
        );

    } else {
        System.out.println(
            "FAIL: Unexpected validation error"
        );
    }

    System.out.println("Reason: " + e.getMessage());
}


System.out.println("\n=== TITLE UPDATE TEST ===");

// Create an incident with valid information
Incident updateTest = new Incident(
    "INC-006",
    "Broken Computer",
    "Computer cannot boot",
    "Computer Laboratory 1",
    Priority.HIGH
);

// Update the incident title
updateTest.setTitle("  Computer Power Supply Failure  ");

// Verify that the title was updated and trimmed
if ("Computer Power Supply Failure".equals(
        updateTest.getTitle())) {

    System.out.println("PASS: Incident title updated correctly");

} else {

    System.out.println("FAIL: Incident title update failed");
}

// Display the updated title
System.out.println("Updated Title: " + updateTest.getTitle());



System.out.println("\n=== DESCRIPTION UPDATE TEST ===");

// Update the existing incident's description
updateTest.setDescription(
    "  Computer displays a black screen after startup.  "
);

// Verify that the description was updated and trimmed
if ("Computer displays a black screen after startup."
        .equals(updateTest.getDescription())) {

    System.out.println(
        "PASS: Incident description updated correctly"
    );

} else {

    System.out.println(
        "FAIL: Incident description update failed"
    );
}

// Display the updated description
System.out.println(
    "Updated Description: " + updateTest.getDescription()
);


System.out.println("\n=== LOCATION UPDATE TEST ===");

// Update the location of the existing incident
updateTest.setLocation("  Computer Laboratory 3  ");

// Verify that the location was updated and trimmed
if ("Computer Laboratory 3".equals(
        updateTest.getLocation())) {

    System.out.println(
        "PASS: Incident location updated correctly"
    );

} else {

    System.out.println(
        "FAIL: Incident location update failed"
    );
}

// Display the updated location
System.out.println(
    "Updated Location: " + updateTest.getLocation()
);


System.out.println("\n=== PRIORITY UPDATE TEST ===");

// Update the priority of the existing incident
updateTest.setPriority(Priority.MEDIUM);

// Verify that the priority changed from HIGH to MEDIUM
if (updateTest.getPriority() == Priority.MEDIUM) {

    System.out.println(
        "PASS: Incident priority updated correctly"
    );

} else {

    System.out.println(
        "FAIL: Incident priority update failed"
    );
}

// Display the updated priority
System.out.println(
    "Updated Priority: " + updateTest.getPriority()
);


System.out.println("\n=== INVALID TITLE UPDATE TEST ===");

// Save the current valid title
String originalTitle = updateTest.getTitle();

boolean exceptionCaught = false;

try {
    // Attempt to replace the title with an invalid value
    updateTest.setTitle("");

} catch (IllegalArgumentException e) {

    if ("Incident title cannot be empty."
            .equals(e.getMessage())) {

        exceptionCaught = true;
    }

    System.out.println("Reason: " + e.getMessage());
}

// Verify that the invalid update was rejected
// AND the original title was preserved
if (exceptionCaught
        && originalTitle.equals(updateTest.getTitle())) {

    System.out.println(
        "PASS: Invalid title rejected and original preserved"
    );

} else {

    System.out.println(
        "FAIL: Invalid title update was not handled correctly"
    );
}

System.out.println(
    "Current Title: " + updateTest.getTitle()
);


System.out.println("\n=== INVALID PRIORITY UPDATE TEST ===");

// Save the existing valid priority
Priority originalPriority = updateTest.getPriority();

boolean priorityExceptionCaught = false;

try {
    // Attempt an invalid priority update
    updateTest.setPriority(null);

} catch (IllegalArgumentException e) {

    if ("Incident priority cannot be null."
            .equals(e.getMessage())) {

        priorityExceptionCaught = true;
    }

    System.out.println("Reason: " + e.getMessage());
}

// Verify rejection AND preservation of existing priority
if (priorityExceptionCaught
        && updateTest.getPriority() == originalPriority) {

    System.out.println(
        "PASS: Invalid priority rejected and original preserved"
    );

} else {

    System.out.println(
        "FAIL: Invalid priority update was not handled correctly"
    );
}

System.out.println(
    "Current Priority: " + updateTest.getPriority()
);


System.out.println("\n=== VALID STATUS UPDATE TEST ===");

// Create a new incident for status testing
Incident statusTestIncident = new Incident(
    "INC-007",
    "Broken Laboratory Air Conditioner",
    "Air conditioner is not cooling properly",
    "Computer Laboratory 3",
    Priority.MEDIUM
);

// Verify the initial status
boolean initiallyReported =
        statusTestIncident.getStatus() == IncidentStatus.REPORTED;

// Perform the status update
statusTestIncident.updateStatus(IncidentStatus.IN_PROGRESS);

// Verify that the status actually changed
boolean updatedToInProgress =
        statusTestIncident.getStatus() == IncidentStatus.IN_PROGRESS;

if (initiallyReported && updatedToInProgress) {

    System.out.println(
        "PASS: Status changed from REPORTED to IN_PROGRESS"
    );

} else {

    System.out.println(
        "FAIL: Valid status update did not work correctly"
    );
}

System.out.println(
    "Current Status: " + statusTestIncident.getStatus()
);

System.out.println("\n=== RESOLVE INCIDENT TEST ===");

// Confirm that the incident is currently IN_PROGRESS
boolean initiallyInProgress =
        statusTestIncident.getStatus() == IncidentStatus.IN_PROGRESS;

// Update the incident to RESOLVED
statusTestIncident.updateStatus(IncidentStatus.RESOLVED);

// Verify that the incident is now RESOLVED
boolean successfullyResolved =
        statusTestIncident.getStatus() == IncidentStatus.RESOLVED;

if (initiallyInProgress && successfullyResolved) {

    System.out.println(
        "PASS: Status changed from IN_PROGRESS to RESOLVED"
    );

} else {

    System.out.println(
        "FAIL: Incident resolution did not work correctly"
    );
}

System.out.println(
    "Current Status: " + statusTestIncident.getStatus()
);

System.out.println("\n=== INVALID STATUS UPDATE TEST ===");

// Save the incident's current status
IncidentStatus originalStatus = statusTestIncident.getStatus();

boolean invalidTransitionRejected = false;

try {
    // Attempt to reopen a resolved incident
    statusTestIncident.updateStatus(IncidentStatus.IN_PROGRESS);

} catch (IllegalStateException e) {

    String expectedMessage =
            "Cannot change incident status from "
            + "RESOLVED to IN_PROGRESS";

    if (expectedMessage.equals(e.getMessage())) {
        invalidTransitionRejected = true;
    }

    System.out.println("Reason: " + e.getMessage());
}

// Verify the expected exception was thrown
// AND the original status was preserved
if (invalidTransitionRejected
        && statusTestIncident.getStatus() == originalStatus) {

    System.out.println(
        "PASS: Invalid status update rejected and original preserved"
    );

} else {

    System.out.println(
        "FAIL: Invalid status update was not handled correctly"
    );
}

System.out.println(
    "Current Status: " + statusTestIncident.getStatus()
);

System.out.println("\n=== NULL STATUS UPDATE TEST ===");

// Save the current status before attempting the update
IncidentStatus statusBeforeNullUpdate =
        statusTestIncident.getStatus();

boolean nullStatusRejected = false;

try {
    // Attempt to update the incident with a null status
    statusTestIncident.updateStatus(null);

} catch (IllegalArgumentException e) {

    if ("New incident status cannot be null."
            .equals(e.getMessage())) {

        nullStatusRejected = true;
    }

    System.out.println("Reason: " + e.getMessage());
}

// Verify the expected exception AND unchanged status
if (nullStatusRejected
        && statusTestIncident.getStatus() == statusBeforeNullUpdate
        && statusTestIncident.getStatus() == IncidentStatus.RESOLVED) {

    System.out.println(
        "PASS: Null status rejected and original status preserved"
    );

} else {

    System.out.println(
        "FAIL: Null status update was not handled correctly"
    );
}

System.out.println(
    "Current Status: " + statusTestIncident.getStatus()
);

System.out.println("\n=== SAME STATUS UPDATE TEST ===");

// Save the current status
IncidentStatus statusBeforeRepeat =
        statusTestIncident.getStatus();

boolean sameStatusRejected = false;

try {
    // Attempt to set RESOLVED again
    statusTestIncident.updateStatus(IncidentStatus.RESOLVED);

} catch (IllegalStateException e) {

    String expectedMessage =
            "Cannot change incident status from "
            + "RESOLVED to RESOLVED";

    if (expectedMessage.equals(e.getMessage())) {
        sameStatusRejected = true;
    }

    System.out.println("Reason: " + e.getMessage());
}

// Verify rejection and unchanged status
if (sameStatusRejected
        && statusTestIncident.getStatus() == statusBeforeRepeat
        && statusTestIncident.getStatus() == IncidentStatus.RESOLVED) {

    System.out.println(
        "PASS: Same-status update rejected and original preserved"
    );

} else {

    System.out.println(
        "FAIL: Same-status update was not handled correctly"
    );
}

System.out.println(
    "Current Status: " + statusTestIncident.getStatus()
);

    }

    
}
