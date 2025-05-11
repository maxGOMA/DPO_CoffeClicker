package Presentation.Views;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class GraphViewTest {
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
