package Presentation.Controllers;


import Business.BusinessException;
import Business.GameManager;
import Business.StatManager;
import Business.UserManager;
import Presentation.CoffeeClickerApp;
import Presentation.Views.GraphView;
import Presentation.Views.PopUpErrorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControllerStatistics implements ActionListener {
    private StatManager statManager;
    private UserManager userManager;
    private GameManager gameManager;
    private GraphView graphView;
    private CoffeeClickerApp app;
    private String selectedUsername;

    public ControllerStatistics(StatManager statManager, UserManager userManager, GameManager gameManager, GraphView graphView, CoffeeClickerApp app) throws BusinessException {
        this.statManager = statManager;
        this.userManager = userManager;
        this.gameManager = gameManager;
        this.graphView = graphView;
        this.app = app;
        this.graphView.setController(this);
        try {
            initStatsView();
        }catch(BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void initStatsView() throws BusinessException {
        ArrayList<String> usernames;

        try {
            usernames = userManager.getAllUsernames();
            graphView.setUsernames(usernames);
            if (usernames.isEmpty()) {
                graphView.showNoUsersRegisteredMessage();
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
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
                PopUpErrorView.showErrorPopup(null, excp.getMessage(), new ImageIcon("imgs/imageError.png"));
            }


        } else if (command.equals(GraphView.GAME_SELECTED)) {
            try {
                JComboBox comboBox = (JComboBox) e.getSource();
                String selectedGame = (String) comboBox.getSelectedItem();
                if (selectedGame != null) {
                    graphView.updateStats(statManager.getAllStatsFromGame(gameManager.returnGameFromUser(selectedGame, selectedUsername)));
                }
            } catch (BusinessException excp){
                //Error en persistencia
                PopUpErrorView.showErrorPopup(null, excp.getMessage(), new ImageIcon("imgs/imageError.png"));
            }
        } else if (command.equals(GraphView.BACK_COMMAND)){
                app.showPanel("GameView");
        }
    }

}
