package Presentation.Views;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class GraphViewTest {
    public void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Graph Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Simulamos CoffeeClickerApp con null si no es necesario en este test
            GraphView graphView = new GraphView(null);

            // Datos de prueba: cafés generados por minuto
            ArrayList<Double> testStats = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                testStats.add(30 + random.nextDouble() * 70); // entre 30 y 100
            }

            graphView.updateStats(testStats);

            frame.add(graphView);
            frame.setVisible(true);

            // Forzar repintado
            graphView.repaint();
        });
    }
}
