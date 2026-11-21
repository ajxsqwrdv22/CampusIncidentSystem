package com.campusincident.ui;

import com.campusincident.manager.IncidentManager;
import com.campusincident.model.Incident;
import com.campusincident.model.IncidentStatus;
import com.campusincident.model.Priority;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/** Incident reporting and legal status transitions. All changes use the shared manager. */
public class IncidentListPanel extends JPanel {
    private final IncidentManager incidentManager;
    private final Runnable onDataChanged;
    private final DefaultTableModel tableModel;
    private final JTable incidentTable;

    public IncidentListPanel(IncidentManager incidentManager) {
        this(incidentManager, () -> {});
    }

    public IncidentListPanel(IncidentManager incidentManager, Runnable onDataChanged) {
        if (incidentManager == null || onDataChanged == null) {
            throw new IllegalArgumentException("Incident manager and change callback are required.");
        }
        this.incidentManager = incidentManager;
        this.onDataChanged = onDataChanged;
        tableModel = new DefaultTableModel(
                new String[]{"Incident ID", "Title", "Location", "Priority", "Status"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        incidentTable = new JTable(tableModel);
        incidentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        incidentTable.setRowHeight(28);
        incidentTable.setAutoCreateRowSorter(true);
        incidentTable.getTableHeader().setReorderingAllowed(false);
        initializeUI();
        loadIncidents();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        JPanel headingText = new JPanel();
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));
        headingText.setOpaque(false);
        JLabel title = new JLabel("Incident Management");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        JLabel subtitle = new JLabel("Report incidents and track their progress");
        subtitle.setForeground(Color.GRAY);
        headingText.add(title);
        headingText.add(Box.createVerticalStrut(5));
        headingText.add(subtitle);
        heading.add(headingText, BorderLayout.WEST);

        JButton reportButton = new JButton("+ Report Incident");
        reportButton.addActionListener(e -> openCreateDialog());
        heading.add(reportButton, BorderLayout.EAST);

        JPanel body = new JPanel(new BorderLayout(0, 14));
        body.setBackground(Color.WHITE);
        body.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel section = new JLabel("All Incidents");
        section.setFont(new Font("Arial", Font.BOLD, 18));
        body.add(section, BorderLayout.NORTH);
        body.add(new JScrollPane(incidentTable), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.setOpaque(false);
        JButton details = new JButton("View Details");
        JButton status = new JButton("Advance Status");
        JButton refresh = new JButton("Refresh");
        details.addActionListener(e -> showDetails());
        status.addActionListener(e -> advanceStatus());
        refresh.addActionListener(e -> loadIncidents());
        actions.add(details);
        actions.add(status);
        actions.add(refresh);
        body.add(actions, BorderLayout.SOUTH);

        add(heading, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
    }

    public void loadIncidents() {
        String selectedId = getSelectedIncidentId();
        tableModel.setRowCount(0);
        for (Incident incident : incidentManager.getAllIncidents()) {
            tableModel.addRow(new Object[]{
                incident.getIncidentId(), incident.getTitle(), incident.getLocation(),
                incident.getPriority(), incident.getStatus()
            });
        }
        if (selectedId != null) {
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                if (selectedId.equals(tableModel.getValueAt(row, 0))) {
                    int viewRow = incidentTable.convertRowIndexToView(row);
                    incidentTable.setRowSelectionInterval(viewRow, viewRow);
                    break;
                }
            }
        }
    }

    private String getSelectedIncidentId() {
        int viewRow = incidentTable.getSelectedRow();
        if (viewRow < 0) return null;
        return (String) tableModel.getValueAt(incidentTable.convertRowIndexToModel(viewRow), 0);
    }

    private Incident selectedIncident() {
        String id = getSelectedIncidentId();
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Select an incident first.", "No selection", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        return incidentManager.getIncidentById(id);
    }

    private void showDetails() {
        Incident incident = selectedIncident();
        if (incident == null) return;
        JTextArea content = new JTextArea(
            "ID: " + incident.getIncidentId() + "\nTitle: " + incident.getTitle()
            + "\nLocation: " + incident.getLocation() + "\nPriority: " + incident.getPriority()
            + "\nStatus: " + incident.getStatus() + "\n\nDescription:\n" + incident.getDescription()
        );
        content.setEditable(false);
        content.setWrapStyleWord(true);
        content.setLineWrap(true);
        content.setBackground(UIManager.getColor("Panel.background"));
        JOptionPane.showMessageDialog(this, new JScrollPane(content),
                "Incident Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void advanceStatus() {
        Incident incident = selectedIncident();
        if (incident == null) return;
        IncidentStatus next = switch (incident.getStatus()) {
            case REPORTED -> IncidentStatus.IN_PROGRESS;
            case IN_PROGRESS -> IncidentStatus.RESOLVED;
            case RESOLVED -> null;
        };
        if (next == null) {
            JOptionPane.showMessageDialog(this, "This incident is already resolved.");
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
            "Change " + incident.getIncidentId() + " from " + incident.getStatus() + " to " + next
            + "?\nSubscribed observers will be notified.",
            "Confirm Status Change", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) return;
        try {
            incident.updateStatus(next); // Fires the Observer pattern, which uses Strategy contexts.
            onDataChanged.run();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Update failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openCreateDialog() {
        JTextField title = new JTextField(24);
        JTextField location = new JTextField(24);
        JTextArea description = new JTextArea(4, 24);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        JComboBox<Priority> priority = new JComboBox<>(Priority.values());
        priority.setSelectedItem(Priority.MEDIUM);

        JPanel fields = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 5, 5, 5);
        addField(fields, c, "Title", title);
        addField(fields, c, "Description", new JScrollPane(description));
        addField(fields, c, "Location", location);
        addField(fields, c, "Priority", priority);

        while (true) {
            int result = JOptionPane.showConfirmDialog(this, fields, "Report New Incident",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) return;
            try {
                // Incident validates blank fields; the manager rejects duplicate IDs.
                Incident incident = new Incident(nextIncidentId(), title.getText(),
                        description.getText(), location.getText(), (Priority) priority.getSelectedItem());
                incidentManager.addIncident(incident);
                onDataChanged.run();
                JOptionPane.showMessageDialog(this, "Created " + incident.getIncidentId() + ".");
                return;
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Check incident details", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void addField(JPanel panel, GridBagConstraints c, String label, Component input) {
        c.gridx = 0; c.weightx = 0; c.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(label + ":"), c);
        c.gridx = 1; c.weightx = 1; c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(input, c);
        c.gridy++;
    }

    private String nextIncidentId() {
        int sequence = 1;
        while (incidentManager.getIncidentById(String.format("INC-%03d", sequence)) != null) {
            sequence++;
        }
        return String.format("INC-%03d", sequence);
    }
}
