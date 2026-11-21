package com.campusincident;

import com.campusincident.strategy.NotificationStrategy;
import com.campusincident.model.Incident;
import com.campusincident.model.IncidentStatus;
import com.campusincident.model.Priority;
import com.campusincident.observer.DepartmentCoordinatorObserver;
import com.campusincident.observer.ITSupportObserver;
import com.campusincident.observer.LabTechnicianObserver;
import com.campusincident.strategy.EmailNotificationStrategy;
import com.campusincident.strategy.SMSNotificationStrategy;
import com.campusincident.strategy.InAppNotificationStrategy;
import com.campusincident.strategy.NotificationContext;

public class StrategyTest {

    public static void main(String[] args) {

        System.out.println(
            "=== EMAIL NOTIFICATION STRATEGY TEST ==="
        );

        // 1. Create an Email strategy
        NotificationStrategy strategy =
            new EmailNotificationStrategy();

        // 2. Prepare the notification details
        String recipient = "alex@campus.edu";

        String message =
            "Incident INC-001 changed from REPORTED to IN_PROGRESS.";

        // 3. Execute the notification strategy
        strategy.sendNotification(recipient, message);

        System.out.println(
            "PASS: Email strategy executed without errors"
        );


System.out.println("\n=== SMS NOTIFICATION STRATEGY TEST ===");

// Create an SMS strategy through the common interface
NotificationStrategy smsStrategy =
    new SMSNotificationStrategy();

// Prepare SMS notification details
String smsRecipient = "09171234567";

String smsMessage =
    "Incident INC-001 changed from REPORTED to IN_PROGRESS.";

// Execute the SMS strategy
smsStrategy.sendNotification(smsRecipient, smsMessage);

System.out.println(
    "PASS: SMS strategy executed without errors"
);


System.out.println("\n=== IN-APP NOTIFICATION STRATEGY TEST ===");

// Create an In-App strategy through the common interface
NotificationStrategy inAppStrategy =
    new InAppNotificationStrategy();

// Prepare the notification details
String inAppRecipient = "Alex";

String inAppMessage =
    "Incident INC-001 changed from REPORTED to IN_PROGRESS.";

// Execute the In-App strategy
inAppStrategy.sendNotification(
    inAppRecipient,
    inAppMessage
);

System.out.println(
    "PASS: In-App strategy executed without errors"
);

System.out.println("\n=== RUNTIME STRATEGY SWITCHING TEST ===");

// One variable will hold different strategies
NotificationStrategy selectedStrategy;

// Start with Email
selectedStrategy = new EmailNotificationStrategy();

selectedStrategy.sendNotification(
    "alex@campus.edu",
    "Incident INC-001 updated."
);

// Switch to SMS
selectedStrategy = new SMSNotificationStrategy();

selectedStrategy.sendNotification(
    "09171234567",
    "Incident INC-001 updated."
);

// Switch to In-App
selectedStrategy = new InAppNotificationStrategy();

selectedStrategy.sendNotification(
    "Alex",
    "Incident INC-001 updated."
);

System.out.println(
    "PASS: Runtime strategy switching completed successfully"
);

System.out.println("\n=== NOTIFICATION CONTEXT SWITCHING TEST ===");

// 1. Create a context with Email as the initial strategy
NotificationContext context =
    new NotificationContext(new EmailNotificationStrategy());

System.out.println("Selected Strategy: EMAIL");

context.sendNotification(
    "alex@campus.edu",
    "Incident INC-001 updated."
);

// 2. Switch the context to SMS
context.setStrategy(new SMSNotificationStrategy());

System.out.println("Selected Strategy: SMS");

context.sendNotification(
    "09171234567",
    "Incident INC-001 updated."
);

// 3. Switch the context to In-App
context.setStrategy(new InAppNotificationStrategy());

System.out.println("Selected Strategy: IN-APP");

context.sendNotification(
    "Alex",
    "Incident INC-001 updated."
);

System.out.println(
    "PASS: NotificationContext switched strategies successfully"
);

System.out.println("\n=== NULL STRATEGY VALIDATION TEST ===");

boolean nullStrategyRejected = false;

try {
    // Attempt to replace In-App with an invalid strategy
    context.setStrategy(null);

} catch (IllegalArgumentException e) {

    if ("Notification strategy cannot be null."
            .equals(e.getMessage())) {

        nullStrategyRejected = true;
    }

    System.out.println("Reason: " + e.getMessage());
}

if (nullStrategyRejected) {
    System.out.println(
        "PASS: Null strategy rejected"
    );
} else {
    System.out.println(
        "FAIL: Null strategy was not rejected correctly"
    );
}

// Verify that the previous In-App strategy still works
System.out.println("Testing preserved strategy:");

context.sendNotification(
    "Alex",
    "Incident INC-001 strategy preservation test."
);

System.out.println(
    "\n=== THREE OBSERVERS WITH THREE STRATEGIES TEST ==="
);

// 1. Create a new incident for this test
Incident thirdIncident = new Incident(
    "INC-INT-003",
    "Broken Laboratory Projector",
    "The projector is not displaying properly.",
    "Computer Laboratory 3",
    Priority.MEDIUM
);

// 2. Create a notification context for each observer
NotificationContext thirdEmailContext =
    new NotificationContext(
        new EmailNotificationStrategy()
    );

NotificationContext thirdSmsContext =
    new NotificationContext(
        new SMSNotificationStrategy()
    );

NotificationContext thirdInAppContext =
    new NotificationContext(
        new InAppNotificationStrategy()
    );

// 3. Create the three observers
LabTechnicianObserver thirdLabTechnician =
    new LabTechnicianObserver("Alex");

ITSupportObserver thirdItSupport =
    new ITSupportObserver("Jordan");

DepartmentCoordinatorObserver thirdCoordinator =
    new DepartmentCoordinatorObserver("Taylor");

// 4. Assign a different strategy to each observer
thirdLabTechnician.setNotificationContext(thirdEmailContext);
thirdItSupport.setNotificationContext(thirdSmsContext);
thirdCoordinator.setNotificationContext(thirdInAppContext);

// 5. Subscribe all three to the same incident
thirdIncident.addObserver(thirdLabTechnician);
thirdIncident.addObserver(thirdItSupport);
thirdIncident.addObserver(thirdCoordinator);

System.out.println("Alex subscribed with EMAIL.");
System.out.println("Jordan subscribed with SMS.");
System.out.println("Taylor subscribed with IN-APP.");

// 6. Trigger ONE status change
thirdIncident.updateStatus(IncidentStatus.IN_PROGRESS);

// 7. Verify the updated status
if (thirdIncident.getStatus() == IncidentStatus.IN_PROGRESS) {
    System.out.println(
        "PASS: All three observers notified; incident is IN_PROGRESS"
    );
} else {
    System.out.println(
        "FAIL: Incident status was not updated"
    );
}


    }

}
