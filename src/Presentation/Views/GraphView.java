package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GraphView extends JPanel {
    public static final String BACK_COMMAND = "BACK_COMMAND";
    private final CoffeeClickerApp app;
    private Font coffeeClickerFont;
    private ArrayList<String> idPlayer;
    private ArrayList<String> idGame;
    private ArrayList<Double> stats;

    public GraphView (CoffeeClickerApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        coffeeClickerFont = MainMenuView.loadCustomFont();
        stats = new ArrayList<>();
    }

    public void updateStats(ArrayList<Double> newStats) {
        stats = newStats;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();
        int margin = 50;

        g2.setColor(Color.LIGHT_GRAY); //TODO cambiar color
        g2.fillRect(0, 0, width, height);

        //TODO if there is no info return

        double xMin = Double.MAX_VALUE;
        double yMin = Double.MAX_VALUE;
        double xMax = Double.MIN_VALUE;
        double yMax = Double.MIN_VALUE;

        for (Double stat : stats) {
            double x = stats.indexOf(stat);
            double y = stat;
            if (x < xMin) xMin = x;
            if (x > xMax) xMax = x;
            if (y < yMin) yMin = y;
            if (y > yMax) yMax = y;
        }

        double xScale = (width - 2 * margin) / (xMax);
        double yScale = (height - 2 * margin) / (yMax);

        g2.setColor(Color.BLACK);
        g2.drawLine(margin, height - margin, width - margin, height - margin);
        g2.drawLine(margin, height - margin, margin, margin);

        g2.drawString("Time (minutes)", (width - margin) / 2, height - 10);
        g2.drawString("Coffees Generated (units)", margin, margin - 10);

        int scale = 15;
        for (int i = 0; i <= scale; i++) {
            int x = margin + (i * (width - 2 * margin)) / scale;
            int y = height - margin - (i * (height - 2 * margin)) / scale;

            g2.drawLine(x, height - margin - 5, x, height - margin + 5);
            String xLabel = String.format("%.1f", xMin + (i * (xMax - xMin)) / scale);
            g2.drawString(xLabel, x - 10, height - margin + 20);

            g2.drawLine(margin - 5, y, margin + 5, y);
            String yLabel = String.format("%.1f", yMin + (i * (yMax - yMin)) / scale);
            g2.drawString(yLabel, margin - 35, y + 5);
        }

        g2.setColor(Color.RED);
        int previousX = -1;
        int previousY = -1;

        for (Double stat : stats) {
            int x = margin + 5 + (int) ((stats.indexOf(stat)- xMin) * xScale);
            int y = height - margin - (int) ((stat - yMin) * yScale);

            g2.fillOval(x - 3, y - 3, 6, 6);

            if (previousX != -1 && previousY != -1) {
                g2.drawLine(previousX, previousY, x, y);
            }

            previousX = x;
            previousY = y;
        }
    }
}

