
package com.campusincident;

import com.campusincident.model.IncidentStatus;

public class IncidentStatusTest {

    public static void main(String[] args) {

        System.out.println(
            "=== REPORTED TO IN_PROGRESS TEST ==="
        );

        boolean allowed = IncidentStatus.REPORTED
                .canTransitionTo(IncidentStatus.IN_PROGRESS);

        if (allowed) {

            System.out.println(
                "PASS: REPORTED can transition to IN_PROGRESS"
            );

        } else {

            System.out.println(
                "FAIL: Valid status transition was rejected"
            );
        }


System.out.println(
    "\n=== IN_PROGRESS TO RESOLVED TEST ==="
);

boolean resolutionAllowed = IncidentStatus.IN_PROGRESS
        .canTransitionTo(IncidentStatus.RESOLVED);

if (resolutionAllowed) {

    System.out.println(
        "PASS: IN_PROGRESS can transition to RESOLVED"
    );

} else {

    System.out.println(
        "FAIL: Valid resolution transition was rejected"
    );
}

System.out.println(
    "\n=== REPORTED TO RESOLVED TEST ==="
);

boolean directResolutionAllowed = IncidentStatus.REPORTED
        .canTransitionTo(IncidentStatus.RESOLVED);

if (!directResolutionAllowed) {

    System.out.println(
        "PASS: Direct REPORTED to RESOLVED transition was rejected"
    );

} else {

    System.out.println(
        "FAIL: Invalid status transition was allowed"
    );
}


System.out.println(
    "\n=== RESOLVED TO IN_PROGRESS TEST ==="
);

boolean reopeningAllowed = IncidentStatus.RESOLVED
        .canTransitionTo(IncidentStatus.IN_PROGRESS);

if (!reopeningAllowed) {

    System.out.println(
        "PASS: RESOLVED to IN_PROGRESS transition was rejected"
    );

} else {

    System.out.println(
        "FAIL: Resolved incident was allowed to reopen"
    );
}

System.out.println(
    "\n=== NULL STATUS TRANSITION TEST ==="
);

boolean nullTransitionAllowed = IncidentStatus.REPORTED
        .canTransitionTo(null);

if (!nullTransitionAllowed) {

    System.out.println(
        "PASS: Null status transition was rejected"
    );

} else {

    System.out.println(
        "FAIL: Null status transition was allowed"
    );
}

System.out.println(
    "\n=== SAME STATUS TRANSITION TEST ==="
);

boolean sameStatusAllowed = IncidentStatus.REPORTED
        .canTransitionTo(IncidentStatus.REPORTED);

if (!sameStatusAllowed) {

    System.out.println(
        "PASS: Transition to the same status was rejected"
    );

} else {

    System.out.println(
        "FAIL: Transition to the same status was allowed"
    );
}

    }
}
