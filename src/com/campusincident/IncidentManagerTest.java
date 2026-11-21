
package com.campusincident;

import java.util.List;

import com.campusincident.manager.IncidentManager;
import com.campusincident.model.Incident;
import com.campusincident.model.Priority;

public class IncidentManagerTest {

    public static void main(String[] args) {

        // Create an IncidentManager
        IncidentManager manager = new IncidentManager();

        // Create a valid incident
        Incident incident = new Incident(
            "INC-001",
            "Broken Laboratory Computer",
            "Computer does not turn on",
            "Computer Laboratory 1",
            Priority.HIGH
        );

        System.out.println("=== ADD INCIDENT TEST ===");

        try {
            // Attempt to store the incident
            manager.addIncident(incident);

            System.out.println(
                "PASS: Valid incident added without errors"
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                "FAIL: Valid incident was rejected"
            );

            System.out.println(
                "Reason: " + e.getMessage()
            );
        }


System.out.println("\n=== DUPLICATE INCIDENT ID TEST ===");

// Create another incident with the SAME ID
Incident duplicateIncident = new Incident(
    "INC-001",
    "Broken Projector",
    "Projector is not displaying images",
    "Room 203",
    Priority.MEDIUM
);

boolean duplicateRejected = false;

try {
    manager.addIncident(duplicateIncident);

} catch (IllegalArgumentException e) {

    if ("An incident with this ID already exists."
            .equals(e.getMessage())) {

        duplicateRejected = true;
    }

    System.out.println("Reason: " + e.getMessage());
}

if (duplicateRejected) {

    System.out.println(
        "PASS: Duplicate incident ID was rejected"
    );

} else {

    System.out.println(
        "FAIL: Duplicate incident ID was not rejected correctly"
    );
}


System.out.println("\n=== NULL INCIDENT TEST ===");

boolean nullRejected = false;

try {
    // Attempt to add a null incident
    manager.addIncident(null);

} catch (IllegalArgumentException e) {

    if ("Incident cannot be null."
            .equals(e.getMessage())) {

        nullRejected = true;
    }

    System.out.println("Reason: " + e.getMessage());
}

if (nullRejected) {

    System.out.println(
        "PASS: Null incident was rejected"
    );

} else {

    System.out.println(
        "FAIL: Null incident was not rejected correctly"
    );
}


System.out.println("\n=== GET ALL INCIDENTS TEST ===");

// Retrieve the incidents stored by the manager
List<Incident> allIncidents = manager.getAllIncidents();

// Verify that exactly one incident was stored
if (allIncidents.size() == 1
        && "INC-001".equals(
                allIncidents.get(0).getIncidentId())) {

    System.out.println(
        "PASS: getAllIncidents() returned the correct incident"
    );

} else {

    System.out.println(
        "FAIL: getAllIncidents() returned unexpected results"
    );
}

System.out.println(
    "Number of stored incidents: " + allIncidents.size()
);


System.out.println("\n=== MULTIPLE INCIDENTS TEST ===");

// Create a second valid incident
Incident secondIncident = new Incident(
    "INC-002",
    "Internet Connection Issue",
    "Computers cannot access the internet",
    "Computer Laboratory 2",
    Priority.MEDIUM
);

// Add it to the existing manager
manager.addIncident(secondIncident);

// Retrieve the updated list
List<Incident> multipleIncidents = manager.getAllIncidents();

// Verify that both incidents are present in order
if (multipleIncidents.size() == 2
        && "INC-001".equals(
                multipleIncidents.get(0).getIncidentId())
        && "INC-002".equals(
                multipleIncidents.get(1).getIncidentId())) {

    System.out.println(
        "PASS: Both incidents retrieved in the correct order"
    );

} else {

    System.out.println(
        "FAIL: Multiple incident retrieval was incorrect"
    );
}

// Display the stored incidents
for (Incident storedIncident : multipleIncidents) {
    System.out.println(
        storedIncident.getIncidentId()
        + " - "
        + storedIncident.getTitle()
    );
}

System.out.println(
    "Number of stored incidents: " + multipleIncidents.size()
);

System.out.println("\n=== LIST PROTECTION TEST ===");

// Get a separate copy of the incident list
List<Incident> returnedList = manager.getAllIncidents();

// Clear only the returned list
returnedList.clear();

// Retrieve the manager's incidents again
List<Incident> storedIncidents = manager.getAllIncidents();

// Verify the returned list is empty,
// while the manager still contains both incidents
if (returnedList.isEmpty()
        && storedIncidents.size() == 2
        && "INC-001".equals(
                storedIncidents.get(0).getIncidentId())
        && "INC-002".equals(
                storedIncidents.get(1).getIncidentId())) {

    System.out.println(
        "PASS: Internal incident list is protected"
    );

} else {

    System.out.println(
        "FAIL: Internal incident list was modified"
    );
}

System.out.println(
    "Returned List Size: " + returnedList.size()
);

System.out.println(
    "Manager List Size: " + storedIncidents.size()
);

System.out.println("\n=== FIND EXISTING INCIDENT TEST ===");

// Search for the second incident
Incident foundIncident = manager.getIncidentById("INC-002");

// Check that the correct incident was returned
if (foundIncident != null
        && "INC-002".equals(foundIncident.getIncidentId())
        && "Internet Connection Issue".equals(
                foundIncident.getTitle())) {

    System.out.println(
        "PASS: Existing incident found correctly"
    );

} else {

    System.out.println(
        "FAIL: Existing incident lookup failed"
    );
}

// Display the search result
if (foundIncident != null) {
    System.out.println(
        "Found: " + foundIncident.getIncidentId()
        + " - " + foundIncident.getTitle()
    );
}


System.out.println("\n=== NONEXISTENT INCIDENT TEST ===");

// Search for an ID that is not stored
Incident missingIncident = manager.getIncidentById("INC-999");

// Verify that no incident was found
if (missingIncident == null) {

    System.out.println(
        "PASS: Nonexistent incident returned null"
    );

} else {

    System.out.println(
        "FAIL: Unexpected incident was returned"
    );
}

// Display a friendly message
if (missingIncident == null) {

    System.out.println(
        "Incident INC-999 was not found."
    );
}


System.out.println("\n=== BLANK ID SEARCH TEST ===");

boolean blankIdRejected = false;

try {
    // Attempt to search using a whitespace-only ID
    manager.getIncidentById("   ");

} catch (IllegalArgumentException e) {

    if ("Incident ID cannot be empty."
            .equals(e.getMessage())) {

        blankIdRejected = true;
    }

    System.out.println("Reason: " + e.getMessage());
}

// Verify that the expected validation exception occurred
if (blankIdRejected) {

    System.out.println(
        "PASS: Blank incident ID search was rejected"
    );

} else {

    System.out.println(
        "FAIL: Blank incident ID search was not rejected correctly"
    );
}


System.out.println("\n=== NULL ID SEARCH TEST ===");

boolean nullIdRejected = false;

try {
    // Attempt to search using a null incident ID
    manager.getIncidentById(null);

} catch (IllegalArgumentException e) {

    if ("Incident ID cannot be empty."
            .equals(e.getMessage())) {

        nullIdRejected = true;
    }

    System.out.println("Reason: " + e.getMessage());
}

// Verify that the expected exception occurred
if (nullIdRejected) {

    System.out.println(
        "PASS: Null incident ID search was rejected"
    );

} else {

    System.out.println(
        "FAIL: Null incident ID search was not rejected correctly"
    );
}

    }
}
