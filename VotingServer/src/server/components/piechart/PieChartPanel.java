package server.components.piechart;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PieChartPanel extends JPanel {
    private Map<String, Integer> data;

    public PieChartPanel(Map<String, Integer> initialData) {
        this.data = initialData != null ? initialData : Map.of();
    }

    public void updateResults(Map<String, Integer> newData) {
        this.data = newData != null ? newData : Map.of();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (data.isEmpty()) {
            String message = "Sem dados disponíveis";
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.GRAY);

            int messageWidth = g2d.getFontMetrics().stringWidth(message);
            int messageHeight = g2d.getFontMetrics().getHeight();

            int x = (getWidth() - messageWidth) / 2;
            int y = (getHeight() + messageHeight) / 2 - g2d.getFontMetrics().getDescent();

            g2d.drawString(message, x, y);
        } else {
            int legendWidth = 150;
            int availableWidth = getWidth() - legendWidth;

            PieChartRenderer renderer = new PieChartRenderer(data, availableWidth, getHeight());
            renderer.draw(g2d);
        }
    }
}

