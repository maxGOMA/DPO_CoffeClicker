package Presentation.Views;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class GraphViewTest {
//    public void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Graph Test");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(800, 600);
//
//            // Simulamos CoffeeClickerApp con null si no es necesario en este test
//            GraphView graphView = new GraphView(null);
//
//            // Datos de prueba: cafés generados por minuto
//            ArrayList<Double> testStats = new ArrayList<>();
//            Random random = new Random();
//            for (int i = 0; i < 10; i++) {
//                testStats.add(30 + random.nextDouble() * 70); // entre 30 y 100
//            }
//
//            graphView.updateStats(testStats);
//
//            frame.add(graphView);
//            frame.setVisible(true);
//
//            // Forzar repintado
//            graphView.repaint();
//        });
//    }
    public static void main(String[] args) {
        // Crear ventana principal
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("GraphView Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);

            // Crear instancia de GraphView con una clase dummy de app
            CoffeeClickerApp dummyApp = new CoffeeClickerApp(); // Si necesitas una clase falsa

            GraphView graphView = new GraphView(dummyApp);

            // Simular datos de prueba
            ArrayList<Double> stats = new ArrayList<>();
            stats.add(10.0);
            stats.add(25.0);
            stats.add(18.0);
            stats.add(30.0);
            stats.add(40.0);
            graphView.updateStats(stats);

            ArrayList<String> users = new ArrayList<>();
            users.add("Alice");
            users.add("Bob");
            users.add("Charlie");
            graphView.setUsernames(users);

            ArrayList<String> games = new ArrayList<>();
            games.add("Game1");
            games.add("Game2");
            games.add("Game3");
            graphView.setFinishedGames(games);

            graphView.showGameSelectionComboBox();
            graphView.showNoFinishedGamesForSelectedUserMessage();


            frame.add(graphView);
            frame.setVisible(true);
        });
    }

}
