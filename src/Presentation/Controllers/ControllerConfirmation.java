package Presentation.Controllers;

import Business.BusinessException;
import Business.GameManager;
import Business.StatManager;
import Business.UserManager;
import Presentation.Views.ConfirmationView;
import Presentation.Views.GameListView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerConfirmation implements ActionListener {
    private UserManager userManager;
    private final GameManager gameManager;
    private final StatManager statManager;
    private ConfirmationView view;
    private String ViewBack;

    public ControllerConfirmation (UserManager userManager, ConfirmationView view, GameManager gameManager, StatManager statManager){
        this.userManager = userManager;
        this.view = view;
        this.gameManager = gameManager;
        this.statManager = statManager;
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
                   view.getApp().showPanel("MainMenuView");
               } catch (BusinessException ex) {
                   //TODO MOSTRAR ERROR DE PERSISTENCIA
               }
           }else if(aux.getText().contains("game")){
               //FINALIZAR GAME
               statManager.stopStatsGeneration();
               gameManager.finishCurrentGame();
               gameManager.endAndUpdateGame();
               GameListView.deleteGameSelectedView(gameManager.getCurrentGame().getName());
               view.getApp().showPanel("SelectGame");
           }
        }else if(command.equals(view.CANCEL)){
            view.getApp().showPanel(ViewBack);
        }
    }
}
