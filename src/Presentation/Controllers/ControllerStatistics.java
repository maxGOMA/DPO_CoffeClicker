package Presentation.Controllers;


import Business.BusinessException;
import Business.Entities.EntityGame;
import Business.GameManager;
import Business.StatManager;
import Business.UserManager;
import Persistance.PersistanceException;
import Presentation.Views.GraphView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControllerStatistics implements ActionListener {
    private final StatManager statManager;
    private final UserManager userManager;
    private final GameManager gameManager;
    private final GraphView graphView;

    public ControllerStatistics(StatManager statManager, UserManager userManager, GameManager gameManager, GraphView graphView)  {
        this.statManager = statManager;
        this.userManager = userManager;
        this.gameManager = gameManager;
        this.graphView = graphView;
        this.graphView.setController(this);

        initStatsView();
    }

    public void initStatsView() {
        ArrayList<String> usernames = new ArrayList<>();
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
            ArrayList<String> finishedGamesNames = new ArrayList<>();
            JComboBox comboBox = (JComboBox) e.getSource();
            String selectedUser = (String) comboBox.getSelectedItem();

            //finishedGameNames = getUserFinishedGames.
            if (finishedGamesNames.isEmpty()) {
                graphView.showNoFinishedGamesForSelectedUserMessage();
            } else {
                graphView.setFinishedGames(finishedGamesNames);
                graphView.showGameSelectionComboBox();
            }
        } else if (command.equals(GraphView.GAME_SELECTED)) {
            try {
                JComboBox comboBox = (JComboBox) e.getSource();
                String selectedGame = (String) comboBox.getSelectedItem();
                graphView.updateStats(statManager.getAllStatsFromGame(gameManager.getGameFromPersistance(selectedGame)));
            } catch (BusinessException exception){
                //Show persistance error
            }
        }

        //todo BACK COMAND
    }
}
