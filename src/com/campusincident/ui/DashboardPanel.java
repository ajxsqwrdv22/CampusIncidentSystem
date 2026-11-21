package com.campusincident.ui;

import com.campusincident.manager.IncidentManager;
import com.campusincident.model.Incident;
import com.campusincident.model.IncidentStatus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Dashboard stats and recent incidents are calculated from the shared manager. */
public class DashboardPanel extends JPanel {
    private final IncidentManager incidentManager;
    private final JLabel totalValue = new JLabel("0");
    private final JLabel activeValue = new JLabel("0");
    private final JLabel resolvedValue = new JLabel("0");
    private final DefaultTableModel recentModel = new DefaultTableModel(
            new String[]{"ID", "Incident", "Status"}, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };

    public DashboardPanel(IncidentManager incidentManager) {
        if (incidentManager == null) throw new IllegalArgumentException("IncidentManager is required.");
        this.incidentManager = incidentManager;
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(25, 25, 25, 25));
        initializeUI();
        refreshDashboard();
    }

    private void initializeUI() {
        JPanel heading = new JPanel();
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.setOpaque(false);
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        JLabel subtitle = new JLabel("Campus incident overview");
        subtitle.setForeground(Color.GRAY);
        heading.add(title);
        heading.add(Box.createVerticalStrut(5));
        heading.add(subtitle);

        JPanel stats = new JPanel(new GridLayout(1, 3, 15, 0));
        stats.setOpaque(false);
        stats.add(statCard("Total Incidents", totalValue));
        stats.add(statCard("Active Incidents", activeValue));
        stats.add(statCard("Resolved Incidents", resolvedValue));
        stats.setPreferredSize(new Dimension(0, 120));

        JPanel recent = new JPanel(new BorderLayout(0, 15));
        recent.setBackground(Color.WHITE);
        recent.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel recentTitle = new JLabel("Recent Incidents");
        recentTitle.setFont(new Font("Arial", Font.BOLD, 18));
        JTable table = new JTable(recentModel);
        table.setRowHeight(27);
        table.setEnabled(false);
        recent.add(recentTitle, BorderLayout.NORTH);
        recent.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel content = new JPanel(new BorderLayout(0, 20));
        content.setOpaque(false);
        content.add(stats, BorderLayout.NORTH);
        content.add(recent, BorderLayout.CENTER);
        add(heading, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }

    private JPanel statCard(String label, JLabel value) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        value.setFont(new Font("Arial", Font.BOLD, 28));
        JLabel text = new JLabel(label);
        text.setForeground(Color.GRAY);
        card.add(value, BorderLayout.CENTER);
        card.add(text, BorderLayout.SOUTH);
        return card;
    }

    public void refreshDashboard() {
        List<Incident> incidents = incidentManager.getAllIncidents();
        long resolved = incidents.stream().filter(i -> i.getStatus() == IncidentStatus.RESOLVED).count();
        totalValue.setText(String.valueOf(incidents.size()));
        activeValue.setText(String.valueOf(incidents.size() - resolved));
        resolvedValue.setText(String.valueOf(resolved));
        recentModel.setRowCount(0);
        for (int i = incidents.size() - 1; i >= Math.max(0, incidents.size() - 8); i--) {
            Incident incident = incidents.get(i);
            recentModel.addRow(new Object[]{incident.getIncidentId(), incident.getTitle(), incident.getStatus()});
        }
    }
}
