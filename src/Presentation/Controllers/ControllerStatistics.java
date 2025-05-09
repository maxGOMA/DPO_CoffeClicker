package Presentation.Controllers;


import Business.BusinessException;
import Business.GameManager;
import Business.StatManager;
import Business.UserManager;
import Presentation.Views.CoffeeClickerApp;
import Presentation.Views.GraphView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControllerStatistics implements ActionListener {
    private final StatManager statManager;
    private final UserManager userManager;
    private final GameManager gameManager;
    private final GraphView graphView;

    private String selectedUsername;

    public ControllerStatistics(StatManager statManager, UserManager userManager, GameManager gameManager, GraphView graphView)  {
        this.statManager = statManager;
        this.userManager = userManager;
        this.gameManager = gameManager;
        this.graphView = graphView;
        this.graphView.setController(this);

        initStatsView();
    }

    public void initStatsView() {
        ArrayList<String> usernames;

        try {
            usernames = userManager.getAllUsernames();
            graphView.setUsernames(usernames);
            if (usernames.isEmpty()) {
                graphView.showNoUsersRegisteredMessage();
            }
        } catch (BusinessException e) {
            //Show persistance error
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(GraphView.USER_SELECTED)) {
            try {

                JComboBox comboBox = (JComboBox) e.getSource();
                selectedUsername = (String) comboBox.getSelectedItem();


                ArrayList<String> finishedGamesNames = gameManager.getUserFinishedGameNames(selectedUsername);

                if (finishedGamesNames.isEmpty()) {
                    graphView.showNoFinishedGamesForSelectedUserMessage();
                } else {
                    graphView.setFinishedGames(finishedGamesNames);
                    graphView.showGameSelectionComboBox();
                }
            } catch (BusinessException excp){
                //Error en persistencia
            }

        } else if (command.equals(GraphView.GAME_SELECTED)) {
            try {
                JComboBox comboBox = (JComboBox) e.getSource();
                String selectedGame = (String) comboBox.getSelectedItem();
                System.out.println(selectedGame + selectedUsername);
                if (selectedGame != null) {
                    graphView.updateStats(statManager.getAllStatsFromGame(gameManager.returnGameFromUser(selectedGame, selectedUsername)));
                }
            } catch (BusinessException excp){
                //Error en persistencia
            }
        }
    }

    public static void main(String[] args) {
        GraphView graphView = new GraphView(new CoffeeClickerApp());
        UserManager um = new UserManager();;
        ControllerStatistics controllerStatistics = new ControllerStatistics(new StatManager(), um, new GameManager(um), graphView);
        graphView.setController(controllerStatistics);

        //Esto lo borrare
        JFrame frame = new JFrame("GraphView Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.add(graphView);
        frame.setVisible(true);
    }



}
