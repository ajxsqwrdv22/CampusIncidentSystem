
package com.campusincident.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class SidebarPanel extends JPanel {

    private JButton dashboardButton;
    private JButton incidentsButton;
    private JButton subscribersButton;

    public SidebarPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(41, 62, 91));
        setPreferredSize(new Dimension(200, 0));

        initializeUI();
    }

    private void initializeUI() {

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(
            new BoxLayout(navigationPanel, BoxLayout.Y_AXIS)
        );

        navigationPanel.setBackground(new Color(41, 62, 91));
        navigationPanel.setBorder(
            new EmptyBorder(20, 10, 10, 10)
        );

        dashboardButton = createNavigationButton("Dashboard");
        incidentsButton = createNavigationButton("Incidents");
        subscribersButton = createNavigationButton("Subscribers");

        navigationPanel.add(dashboardButton);
        navigationPanel.add(Box.createVerticalStrut(10));

        navigationPanel.add(incidentsButton);
        navigationPanel.add(Box.createVerticalStrut(10));

        navigationPanel.add(subscribersButton);

        add(navigationPanel, BorderLayout.NORTH);
    }

    private JButton createNavigationButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 75, 105));

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        Dimension buttonSize = new Dimension(180, 45);

        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setMinimumSize(buttonSize);

        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

public void setNavigationListener(Consumer<String> listener) {

    dashboardButton.addActionListener(
        e -> listener.accept("DASHBOARD")
    );

    incidentsButton.addActionListener(
        e -> listener.accept("INCIDENTS")
    );

    subscribersButton.addActionListener(
        e -> listener.accept("SUBSCRIBERS")
    );
}

}
