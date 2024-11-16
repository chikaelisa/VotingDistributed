package server.components.piechart;

import java.awt.*;
import java.util.Map;

public class PieChartRenderer {
    private final Map<String, Integer> data;
    private final int panelWidth;
    private final int panelHeight;
    private final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.CYAN};

    public PieChartRenderer(Map<String, Integer> data, int panelWidth, int panelHeight) {
        this.data = data;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    public void draw(Graphics2D g2d) {
        if (data.isEmpty()) return;

        int total = data.values().stream().mapToInt(Integer::intValue).sum();

        int diameter = Math.min(panelWidth, panelHeight) - 20;
        int x = (panelWidth - diameter) / 2;
        int y = (panelHeight - diameter) / 2;

        double startAngle = 0;
        int i = 0;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            double angle = entry.getValue() / (double) total * 360;
            g2d.setColor(colors[i % colors.length]);
            g2d.fillArc(x, y, diameter, diameter, (int) startAngle, (int) Math.round(angle));
            startAngle += angle;
            i++;
        }

        drawLegend(g2d, x + diameter + 20, y, data, total);
    }

    private void drawLegend(Graphics2D g2d, int x, int y, Map<String, Integer> data, int total) {
        int legendY = y;

        int i = 0;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            String key = entry.getKey();
            int votes = entry.getValue();
            double percentage = (votes / (double) total) * 100;

            g2d.setColor(colors[i % colors.length]);
            g2d.fillRect(x, legendY, 15, 15);

            g2d.setColor(Color.BLACK);
            String legendText = String.format("%s: %d votos (%.1f%%)", key, votes, percentage);
            g2d.drawString(legendText, x + 30, legendY + 15);

            legendY += 30;
            i++;
        }
    }
}

