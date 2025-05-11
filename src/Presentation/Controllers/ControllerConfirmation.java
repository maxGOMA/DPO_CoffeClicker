package Presentation.Controllers;

import Business.GameManager;
import Business.UserManager;
import Presentation.Views.ConfirmationView;
import Presentation.Views.GameListView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerConfirmation implements ActionListener {
    private UserManager userManager;
    private ConfirmationView view;
    private final GameManager gameManager;
    private String GameName;
    private String ViewBack;
    private GameListView gameListView;

    public ControllerConfirmation (UserManager userManager, ConfirmationView view, GameManager gameManager){
        this.userManager = userManager;
        this.view = view;
        this.gameManager = gameManager;
        this.gameListView = gameListView;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
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
               gameManager.deleteAllGamesByUser();
               userManager.deleteAccount();
               view.getApp().showPanel("MainMenuView");
               System.out.println(command + " ACCOUNT");
           }else if(aux.getText().contains("game")){
               //FINALIZAR GAME
               gameManager.updateGame();
               gameManager.setFinished(GameName);
               GameListView.deleteGameSelectedView(GameName);
               view.getApp().showPanel("SelectGame");
               System.out.println(command + " GAME");
           }
        }else if(command.equals(view.CANCEL)){
            view.getApp().showPanel(ViewBack);
            System.out.println(command);
        }
    }
}
