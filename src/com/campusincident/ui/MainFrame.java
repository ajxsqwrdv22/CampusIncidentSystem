package com.campusincident.ui;

import javax.swing.*;
import java.awt.*;
import com.campusincident.manager.IncidentManager;
import com.campusincident.model.Incident;
import com.campusincident.model.Priority;


public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private Incident sampleIncident = new Incident(
        "INC-001",
        "Broken Projector",
        "The projector in Room 101 is not working.",
        "Room 101",
        Priority.HIGH
    );

    
    private final IncidentManager incidentManager = new IncidentManager();

    public MainFrame() {
        incidentManager.addIncident(sampleIncident);
        setTitle("Campus Incident Management System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeUI();
    }

    private void initializeUI() {
        // Set the main window layout
        setLayout(new BorderLayout());

        // Create application sections
        HeaderPanel headerPanel = new HeaderPanel();
        SidebarPanel sidebarPanel = new SidebarPanel();

         
        //CardLayout for contentPanel to switch between different views
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        //Application Screens
        DashboardPanel dashboardPanel = new DashboardPanel();
        IncidentListPanel incidentListPanel = new IncidentListPanel(incidentManager);
        SubscriberPanel subscriberPanel = new SubscriberPanel();
        

        contentPanel.add(dashboardPanel, "DASHBOARD");
        contentPanel.add(incidentListPanel, "INCIDENTS");
        contentPanel.add(subscriberPanel, "SUBSCRIBERS");
        cardLayout.show(contentPanel, "DASHBOARD");


sidebarPanel.setNavigationListener(screenName -> {
    cardLayout.show(contentPanel, screenName);
});


        // Arrange sections inside the JFrame
        add(headerPanel, BorderLayout.NORTH);
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }


public IncidentManager getIncidentManager() {
    return incidentManager;
}
}
