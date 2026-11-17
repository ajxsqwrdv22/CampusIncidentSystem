package com.campusincident.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class IncidentListPanel extends JPanel {

    public IncidentListPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        initializeUI();
    }

    private void initializeUI() {

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

        // Placeholder content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(
            new EmptyBorder(20, 20, 20, 20)
        );

        JLabel sectionTitle = new JLabel("All Incidents");
        sectionTitle.setFont(
            new Font("Arial", Font.BOLD, 18)
        );

        JLabel emptyMessage = new JLabel(
            "No incidents recorded yet.",
            SwingConstants.CENTER
        );
        emptyMessage.setForeground(Color.GRAY);

        contentPanel.add(sectionTitle, BorderLayout.NORTH);
        contentPanel.add(emptyMessage, BorderLayout.CENTER);

        // Assemble screen
        add(headingPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
}
