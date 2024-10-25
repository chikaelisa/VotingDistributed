package server.components.panel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Random;

public class ResultPanel extends JPanel {
    private DefaultPieDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public ResultPanel(String[] candidates) {
        dataset = new DefaultPieDataset();
        chart = ChartFactory.createPieChart(
                "Resultados parciais da votação", dataset, true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();

        for (String candidate : candidates) {
            Random rand = new Random();
            plot.setSectionPaint(candidate, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
        }

        chartPanel = new ChartPanel(chart);
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
    }

    public void updateResults(Map<String, Integer> votes) {
        dataset.clear();
        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
    }

    public void setFinalResult() {
        chart.setTitle("Resultados finais da votação");
    }
}
