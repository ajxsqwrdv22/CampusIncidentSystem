
package com.campusincident.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        initializeUI();
    }

    private void initializeUI() {

        // Dashboard heading
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel subtitle = new JLabel(
            "Campus incident overview"
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

        // Statistics section
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setOpaque(false);

        statsPanel.add(createStatCard("Total Incidents", "0"));
        statsPanel.add(createStatCard("Active Incidents", "0"));
        statsPanel.add(createStatCard("Resolved Incidents", "0"));

        // Recent incidents section
        JPanel recentPanel = new JPanel(new BorderLayout());
        recentPanel.setBackground(Color.WHITE);
        recentPanel.setBorder(
            new EmptyBorder(20, 20, 20, 20)
        );

        JLabel recentTitle = new JLabel("Recent Incidents");
        recentTitle.setFont(
            new Font("Arial", Font.BOLD, 18)
        );

        JLabel emptyMessage = new JLabel(
            "No incidents reported yet.",
            SwingConstants.CENTER
        );
        emptyMessage.setForeground(Color.GRAY);

        recentPanel.add(recentTitle, BorderLayout.NORTH);
        recentPanel.add(emptyMessage, BorderLayout.CENTER);

        // Group statistics and recent incidents
        JPanel dashboardContent = new JPanel(
            new BorderLayout(0, 20)
        );
        dashboardContent.setOpaque(false);

        statsPanel.setPreferredSize(new Dimension(0, 120));

        dashboardContent.add(statsPanel, BorderLayout.NORTH);
        dashboardContent.add(recentPanel, BorderLayout.CENTER);

        // Assemble dashboard
        add(headingPanel, BorderLayout.NORTH);
        add(dashboardContent, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String label, String value) {

        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(
            new EmptyBorder(20, 20, 20, 20)
        );

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(
            new Font("Arial", Font.BOLD, 28)
        );

        JLabel textLabel = new JLabel(label);
        textLabel.setForeground(Color.GRAY);
        textLabel.setFont(
            new Font("Arial", Font.PLAIN, 13)
        );

        card.add(valueLabel, BorderLayout.CENTER);
        card.add(textLabel, BorderLayout.SOUTH);

        return card;
    }
}
