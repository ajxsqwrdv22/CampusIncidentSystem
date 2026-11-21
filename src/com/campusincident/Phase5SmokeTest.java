package com.campusincident;

import com.campusincident.manager.SubscriptionManager;
import com.campusincident.model.Incident;
import com.campusincident.model.IncidentStatus;
import com.campusincident.model.Priority;
import com.campusincident.ui.MainFrame;

import javax.swing.*;
import java.awt.*;

/** Quick no-click regression for the Phase 5 GUI and integrated patterns. */
public final class Phase5SmokeTest {
    private static JTable findTable(Component parent) {
        if (parent instanceof JTable table && table.getColumnCount() == 5) return table;
        if (parent instanceof Container container) {
            for (Component component : container.getComponents()) {
                JTable table = findTable(component);
                if (table != null) return table;
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            MainFrame frame = new MainFrame();
            try {
                JTable table = findTable(frame);
                check(table != null, "Incident JTable exists");
                check(table.getRowCount() == 1, "Seed incident appears on construction");
                check("INC-001".equals(table.getValueAt(0, 0)), "Seed ID matches");

                Incident second = new Incident("INC-002", "Broken switch", "No Ethernet", "Lab 2", Priority.MEDIUM);
                frame.getIncidentManager().addIncident(second);
                SubscriptionManager subscriptions = frame.getSubscriptionManager();
                subscriptions.setChannel(SubscriptionManager.Role.IT_SUPPORT, SubscriptionManager.Channel.SMS);
                subscriptions.setSubscribed(second, SubscriptionManager.Role.IT_SUPPORT, true);
                second.updateStatus(IncidentStatus.IN_PROGRESS);
                check(subscriptions.getNotificationLog().size() == 1, "Observer delivered SMS through Strategy");
                check(subscriptions.getNotificationLog().get(0).contains("SMS"), "SMS recorded");
                subscriptions.setChannel(SubscriptionManager.Role.IT_SUPPORT, SubscriptionManager.Channel.EMAIL);
                second.updateStatus(IncidentStatus.RESOLVED);
                check(subscriptions.getNotificationLog().size() == 2, "Second status notification recorded");
                check(subscriptions.getNotificationLog().get(1).contains("EMAIL"), "Runtime Strategy switch worked");
                subscriptions.setSubscribed(second, SubscriptionManager.Role.IT_SUPPORT, false);
                check(!subscriptions.isSubscribed(second, SubscriptionManager.Role.IT_SUPPORT), "Unsubscribe worked");
                System.out.println("PASS: Phase 5 GUI and integrated Observer/Strategy smoke tests");
            } finally {
                frame.dispose();
            }
        });
    }

    private static void check(boolean passes, String name) {
        if (!passes) throw new AssertionError(name);
        System.out.println("PASS: " + name);
    }
}
