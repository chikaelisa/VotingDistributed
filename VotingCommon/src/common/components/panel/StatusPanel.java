package common.components.panel;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private final JLabel statusLabel;

    public StatusPanel() {
        statusLabel = new JLabel();
        statusLabel.setText("Programa iniciado!");

        add(statusLabel);
        setBackground(Color.lightGray);
    }

    public void setStatusLabel(String newStatusLabel) {
        statusLabel.setText(newStatusLabel);
    }
}
