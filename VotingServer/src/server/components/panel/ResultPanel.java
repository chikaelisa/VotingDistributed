package server.components.panel;

import server.components.piechart.PieChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ResultPanel extends JPanel {
    private final JLabel titleLabel;
    private final PieChartPanel pieChartPanel;

    public ResultPanel() {
        setLayout(new BorderLayout());

        titleLabel = new JLabel("Resultados parciais da votação", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        pieChartPanel = new PieChartPanel(null);
        add(pieChartPanel, BorderLayout.CENTER);
    }

    public void updateResults(Map<String, Integer> votes) {
        pieChartPanel.updateResults(votes);
    }

    public void setFinalResult() {
        titleLabel.setText("Resultados finais da votação");
    }
}
