package Presentation.Controllers;

import Business.BusinessException;
import Business.GameManager;
import Business.StatManager;
import Business.UserManager;
import Presentation.CoffeeClickerApp;
import Presentation.Views.ConfirmationView;
import Presentation.Views.GameListView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller responsible for confirming critical user actions.
 */
public class ControllerConfirmation implements ActionListener {
    private UserManager userManager;
    private GameManager gameManager;
    private StatManager statManager;
    private ConfirmationView view;
    private String viewBack;
    private CoffeeClickerApp app;

    /**
     * Constructs the confirmation controller.
     * @param userManager the user manager
     * @param view the confirmation view
     * @param gameManager the game manager
     * @param statManager the statistics manager
     * @param app the main application
     */
    public ControllerConfirmation (UserManager userManager, ConfirmationView view, GameManager gameManager, StatManager statManager, CoffeeClickerApp app) {
        this.userManager = userManager;
        this.view = view;
        this.gameManager = gameManager;
        this.statManager = statManager;
        this.app = app;
    }

    /**
     * Sets the view to return to in case of cancellation.
     * @param viewBack name of the view
     */
    public void viewBack(String viewBack){
        this.viewBack = viewBack;
    }

    /**
     * Handles confirmation and cancellation actions.
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command.equals(view.CONFIRM)){
           JLabel aux = (JLabel) view.getPanel().getComponent(5);
           if(aux.getText().contains("account")){
               try {
                   gameManager.deleteAllGamesByUser();
                   statManager.deleteAllStatsFromUser(gameManager.getUserFinishedGameIds(userManager.getCurrentUser()));
                   userManager.deleteAccount();
                   app.showPanel("MainMenuView");
               } catch (BusinessException ex) {
                   app.finishProgramDueToPersistanceException(ex.getMessage());
               }
           }else if(aux.getText().contains("game")){
               try {
                   statManager.stopStatsGeneration();
                   gameManager.finishCurrentGame();
                   gameManager.endAndUpdateGame();
                   GameListView.deleteGameSelectedView(gameManager.getCurrentGame().getName());
                   app.showPanel("SelectGame");
               } catch (BusinessException ex) {
                   app.finishProgramDueToPersistanceException(ex.getMessage());
               }
           }
        }else if(command.equals(view.CANCEL)){
            app.showPanel(viewBack);
        }
    }
}
