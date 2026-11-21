package com.campusincident.ui;

import com.campusincident.manager.IncidentManager;
import com.campusincident.manager.SubscriptionManager;
import com.campusincident.model.Incident;
import com.campusincident.model.Priority;

import javax.swing.*;
import java.awt.*;

/** Application shell: owns one shared manager and coordinates views. */
public class MainFrame extends JFrame {
    private final IncidentManager incidentManager = new IncidentManager();
    private final SubscriptionManager subscriptionManager = new SubscriptionManager();
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private DashboardPanel dashboardPanel;
    private IncidentListPanel incidentListPanel;
    private SubscriberPanel subscriberPanel;

    public MainFrame() {
        setTitle("Campus Incident Management System");
        setSize(1080, 700);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        seedDemoIncident();
        initializeUI();
    }

    private void seedDemoIncident() {
        // Demo data; remove this line/method when a blank initial table is desired.
        incidentManager.addIncident(new Incident("INC-001", "Broken Projector",
                "The projector in Room 101 is not working.", "Room 101", Priority.HIGH));
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        HeaderPanel headerPanel = new HeaderPanel();
        SidebarPanel sidebarPanel = new SidebarPanel();
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        dashboardPanel = new DashboardPanel(incidentManager);
        incidentListPanel = new IncidentListPanel(incidentManager, this::refreshViews);
        subscriberPanel = new SubscriberPanel(incidentManager, subscriptionManager);

        contentPanel.add(dashboardPanel, "DASHBOARD");
        contentPanel.add(incidentListPanel, "INCIDENTS");
        contentPanel.add(subscriberPanel, "SUBSCRIBERS");
        cardLayout.show(contentPanel, "DASHBOARD");

        sidebarPanel.setNavigationListener(screen -> {
            refreshViews();
            cardLayout.show(contentPanel, screen);
        });
        add(headerPanel, BorderLayout.NORTH);
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void refreshViews() {
        dashboardPanel.refreshDashboard();
        incidentListPanel.loadIncidents();
        subscriberPanel.refreshSubscribers();
    }

    public IncidentManager getIncidentManager() { return incidentManager; }
    public SubscriptionManager getSubscriptionManager() { return subscriptionManager; }
}
