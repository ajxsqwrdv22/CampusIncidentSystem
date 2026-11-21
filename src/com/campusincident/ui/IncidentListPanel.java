
package com.campusincident.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import com.campusincident.manager.IncidentManager;
import com.campusincident.model.Incident;

public class IncidentListPanel extends JPanel {

    private final IncidentManager incidentManager;
    private final DefaultTableModel tableModel;
    private final JTable incidentTable;

    public IncidentListPanel(IncidentManager incidentManager) {

        if (incidentManager == null) {
            throw new IllegalArgumentException(
                "IncidentManager cannot be null."
            );
        }

        this.incidentManager = incidentManager;

        // Define table columns
        String[] columnNames = {
            "Incident ID",
            "Title",
            "Location",
            "Priority",
            "Status"
        };

        // Create a non-editable table model
        tableModel = new DefaultTableModel(columnNames, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create the incident table
        incidentTable = new JTable(tableModel);

        // Allow only one selected row
        incidentTable.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        // Build the interface after initializing the table
        initializeUI();
    }


public void loadIncidents() {

    // Remove existing rows to prevent duplicates
    tableModel.setRowCount(0);

    System.out.println(
        "DEBUG: Loading incidents into the IncidentListPanel. Total incidents: " + incidentManager.getAllIncidents().size()
    );
    // Retrieve all incidents from the shared manager
    for (Incident incident : incidentManager.getAllIncidents()) {

        Object[] row = {
            incident.getIncidentId(),
            incident.getTitle(),
            incident.getLocation(),
            incident.getPriority(),
            incident.getStatus()
        };

        // Add the incident to the table
        tableModel.addRow(row);
    }
}


    private void initializeUI() {

        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Page heading
        JLabel title = new JLabel("Incident Management");
        title.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel subtitle = new JLabel(
            "Manage campus incident reports"
        );
        subtitle.setForeground(Color.GRAY);

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(
            new BoxLayout(headingPanel, BoxLayout.Y_AXIS)
        );
        headingPanel.setOpaque(false);

        headingPanel.add(title);
        headingPanel.add(Box.createVerticalStrut(5));
        headingPanel.add(subtitle);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(
            new EmptyBorder(20, 20, 20, 20)
        );

        JLabel sectionTitle = new JLabel("All Incidents");
        sectionTitle.setFont(
            new Font("Arial", Font.BOLD, 18)
        );

        // Put JTable inside JScrollPane
        JScrollPane scrollPane = new JScrollPane(incidentTable);

        // Place title above table
        contentPanel.add(sectionTitle, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Assemble screen
        add(headingPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
}
