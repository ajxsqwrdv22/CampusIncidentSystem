package com.campusincident.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SubscriberPanel extends JPanel {

    public SubscriberPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        initializeUI();
    }

    private void initializeUI() {

        // Page heading
        JLabel title = new JLabel("Subscriber Management");
        title.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel subtitle = new JLabel(
            "Manage incident notification subscribers"
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
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(
            new EmptyBorder(20, 20, 20, 20)
        );

        // Incident selection
        JPanel selectionPanel = new JPanel(new BorderLayout(0, 8));
        selectionPanel.setOpaque(false);

        JLabel selectLabel = new JLabel("Select Incident");
        selectLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JComboBox<String> incidentComboBox = new JComboBox<>();
        incidentComboBox.addItem("No incidents available");
        incidentComboBox.setEnabled(false);

        selectionPanel.add(selectLabel, BorderLayout.NORTH);
        selectionPanel.add(incidentComboBox, BorderLayout.CENTER);

        // Available subscribers
        JPanel subscriberList = new JPanel();
        subscriberList.setLayout(
            new BoxLayout(subscriberList, BoxLayout.Y_AXIS)
        );
        subscriberList.setOpaque(false);

        JLabel listTitle = new JLabel("Available Subscribers");
        listTitle.setFont(new Font("Arial", Font.BOLD, 18));
        listTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        subscriberList.add(listTitle);
        subscriberList.add(Box.createVerticalStrut(15));

        subscriberList.add(
            createSubscriberRow("Laboratory Technician")
        );
        subscriberList.add(Box.createVerticalStrut(10));

        subscriberList.add(
            createSubscriberRow("IT Support Staff")
        );
        subscriberList.add(Box.createVerticalStrut(10));

        subscriberList.add(
            createSubscriberRow("Department Coordinator")
        );

        contentPanel.add(selectionPanel, BorderLayout.NORTH);
        contentPanel.add(subscriberList, BorderLayout.CENTER);

        // Assemble the screen
        add(headingPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSubscriberRow(String name) {

        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(245, 247, 250));
        row.setBorder(new EmptyBorder(12, 15, 12, 15));

        JLabel nameLabel = new JLabel(name);

        JLabel statusLabel = new JLabel("Not Subscribed");
        statusLabel.setForeground(Color.GRAY);

        row.add(nameLabel, BorderLayout.WEST);
        row.add(statusLabel, BorderLayout.EAST);

        row.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 50)
        );
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        return row;
    }
}
