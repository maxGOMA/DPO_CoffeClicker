package Presentation.Controllers;

import Business.BusinessException;
import Business.GameManager;
import Business.StatManager;
import Business.UserManager;
import Presentation.CoffeeClickerApp;
import Presentation.Views.ConfirmationView;
import Presentation.Views.GameListView;
import Presentation.Views.PopUpErrorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerConfirmation implements ActionListener {
    private UserManager userManager;
    private GameManager gameManager;
    private StatManager statManager;
    private ConfirmationView view;
    private String ViewBack;
    private CoffeeClickerApp app;

    public ControllerConfirmation (UserManager userManager, ConfirmationView view, GameManager gameManager, StatManager statManager, CoffeeClickerApp app) {
        this.userManager = userManager;
        this.view = view;
        this.gameManager = gameManager;
        this.statManager = statManager;
        this.app = app;
    }

    public void ViewBack(String ViewBack){
        this.ViewBack = ViewBack;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command.equals(view.CONFIRM)){
           JLabel aux = (JLabel) view.getPanel().getComponent(5);
           if(aux.getText().contains("account")){
                //BORRAR CUENTA
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
                   //FINALIZAR GAME
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
            app.showPanel(ViewBack);
        }
    }
}
