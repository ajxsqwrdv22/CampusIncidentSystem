
package com.campusincident.ui;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    public HeaderPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(23, 44, 73));
        setPreferredSize(new Dimension(1000, 75));

        JLabel title = new JLabel(
            "Campus Incident Management System"
        );

        title.setForeground(Color.WHITE);
        title.setFont(
            new Font("Arial", Font.BOLD, 22)
        );
        title.setBorder(
            BorderFactory.createEmptyBorder(0, 25, 0, 0)
        );

        add(title, BorderLayout.CENTER);
    }
}
