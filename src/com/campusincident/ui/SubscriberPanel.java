package com.campusincident.ui;

import com.campusincident.manager.IncidentManager;
import com.campusincident.manager.SubscriptionManager;
import com.campusincident.manager.SubscriptionManager.Channel;
import com.campusincident.manager.SubscriptionManager.Role;
import com.campusincident.model.Incident;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/** Subscribe/unsubscribe concrete observers per incident and change their notification strategies. */
public class SubscriberPanel extends JPanel {
    private final IncidentManager incidentManager;
    private final SubscriptionManager subscriptions;
    private final JComboBox<IncidentChoice> incidentSelector = new JComboBox<>();
    private final Map<Role, JCheckBox> toggles = new EnumMap<>(Role.class);
    private final Map<Role, JComboBox<Channel>> channelSelectors = new EnumMap<>(Role.class);
    private final JTextArea notifications = new JTextArea();
    private boolean updatingControls;

    public SubscriberPanel(IncidentManager incidentManager, SubscriptionManager subscriptions) {
        if (incidentManager == null || subscriptions == null) {
            throw new IllegalArgumentException("Incident and subscription managers are required.");
        }
        this.incidentManager = incidentManager;
        this.subscriptions = subscriptions;
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(25, 25, 25, 25));
        initializeUI();
        subscriptions.setNotificationsChangedListener(this::refreshNotifications);
        refreshSubscribers();
    }

    private void initializeUI() {
        JPanel heading = new JPanel();
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.setOpaque(false);
        JLabel title = new JLabel("Subscriber Management");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        JLabel subtitle = new JLabel("Choose observers and their notification strategies per incident");
        subtitle.setForeground(Color.GRAY);
        heading.add(title);
        heading.add(Box.createVerticalStrut(5));
        heading.add(subtitle);

        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel selection = new JPanel(new BorderLayout(0, 8));
        selection.setOpaque(false);
        JLabel choose = new JLabel("Select Incident");
        choose.setFont(new Font("Arial", Font.BOLD, 15));
        selection.add(choose, BorderLayout.NORTH);
        selection.add(incidentSelector, BorderLayout.CENTER);
        incidentSelector.addActionListener(e -> refreshControls());
        content.add(selection, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 18));
        center.setOpaque(false);
        JPanel observers = new JPanel();
        observers.setLayout(new BoxLayout(observers, BoxLayout.Y_AXIS));
        observers.setOpaque(false);
        JLabel available = new JLabel("Available Subscribers");
        available.setFont(new Font("Arial", Font.BOLD, 18));
        available.setAlignmentX(Component.LEFT_ALIGNMENT);
        observers.add(available);
        observers.add(Box.createVerticalStrut(10));

        for (Role role : Role.values()) {
            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.setBackground(new Color(245, 247, 250));
            row.setBorder(new EmptyBorder(12, 15, 12, 15));
            JCheckBox enabled = new JCheckBox(role.label + " (" + role.recipient + ")");
            enabled.setOpaque(false);
            JComboBox<Channel> channel = new JComboBox<>(Channel.values());
            channel.setToolTipText("Notification strategy for " + role.label);
            toggles.put(role, enabled);
            channelSelectors.put(role, channel);
            row.add(enabled, BorderLayout.CENTER);
            row.add(channel, BorderLayout.EAST);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            observers.add(row);
            observers.add(Box.createVerticalStrut(8));
            enabled.addActionListener(e -> {
                if (updatingControls) return;
                Incident incident = selectedIncident();
                if (incident != null) subscriptions.setSubscribed(incident, role, enabled.isSelected());
            });
            channel.addActionListener(e -> {
                if (updatingControls) return;
                Channel selected = (Channel) channel.getSelectedItem();
                if (selected != null) subscriptions.setChannel(role, selected);
            });
        }
        center.add(observers, BorderLayout.NORTH);

        JPanel logArea = new JPanel(new BorderLayout(0, 8));
        logArea.setOpaque(false);
        JLabel logLabel = new JLabel("Notification Activity (simulated)");
        logLabel.setFont(new Font("Arial", Font.BOLD, 16));
        notifications.setEditable(false);
        notifications.setLineWrap(true);
        notifications.setWrapStyleWord(true);
        notifications.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        notifications.setText("No status-change notifications yet.");
        JScrollPane logScroll = new JScrollPane(notifications);
        logScroll.setPreferredSize(new Dimension(100, 160));
        logArea.add(logLabel, BorderLayout.NORTH);
        logArea.add(logScroll, BorderLayout.CENTER);
        center.add(logArea, BorderLayout.CENTER);
        content.add(center, BorderLayout.CENTER);

        JLabel note = new JLabel("Subscriptions are per incident; each role's channel can be switched at runtime.");
        note.setForeground(Color.GRAY);
        content.add(note, BorderLayout.SOUTH);
        add(heading, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }

    public void refreshSubscribers() {
        Incident previous = selectedIncident();
        String previousId = previous == null ? null : previous.getIncidentId();
        updatingControls = true;
        try {
            incidentSelector.removeAllItems();
            int selection = -1;
            for (Incident incident : incidentManager.getAllIncidents()) {
                incidentSelector.addItem(new IncidentChoice(incident));
                if (incident.getIncidentId().equals(previousId)) selection = incidentSelector.getItemCount() - 1;
            }
            if (incidentSelector.getItemCount() > 0) incidentSelector.setSelectedIndex(Math.max(0, selection));
        } finally {
            updatingControls = false;
        }
        refreshControls();
        refreshNotifications();
    }

    private Incident selectedIncident() {
        IncidentChoice choice = (IncidentChoice) incidentSelector.getSelectedItem();
        return choice == null ? null : choice.incident;
    }

    private void refreshControls() {
        if (updatingControls) return;
        Incident incident = selectedIncident();
        updatingControls = true;
        try {
            incidentSelector.setEnabled(incident != null);
            for (Role role : Role.values()) {
                JCheckBox toggle = toggles.get(role);
                toggle.setEnabled(incident != null);
                toggle.setSelected(incident != null && subscriptions.isSubscribed(incident, role));
                JComboBox<Channel> channel = channelSelectors.get(role);
                channel.setEnabled(incident != null);
                channel.setSelectedItem(subscriptions.getChannel(role));
            }
        } finally {
            updatingControls = false;
        }
    }

    private void refreshNotifications() {
        List<String> entries = subscriptions.getNotificationLog();
        notifications.setText(entries.isEmpty() ? "No status-change notifications yet."
                : String.join("\n\n", entries));
        notifications.setCaretPosition(notifications.getDocument().getLength());
    }

    private static class IncidentChoice {
        final Incident incident;
        IncidentChoice(Incident incident) { this.incident = incident; }
        @Override public String toString() {
            return incident.getIncidentId() + " — " + incident.getTitle();
        }
    }
}
