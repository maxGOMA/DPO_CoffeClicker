package Presentation.Controllers;

import Business.BusinessException;
import Business.GameManager;
import Presentation.CoffeeClickerApp;
import Presentation.Views.GameListView;
import Presentation.Views.NewGameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.Views.NewGameView.showError;

public class ControllerNewGame implements ActionListener {
    private NewGameView view;
    private GameManager gameManager;
    private Boolean iscopy;
    private GameListView gameListView;
    private ControllerConfirmation controllerConfirmation;
    private CoffeeClickerApp app;

    public ControllerNewGame(NewGameView view, GameManager gameManager, Boolean iscopy, GameListView gameListView, ControllerConfirmation controllerConfirmation, CoffeeClickerApp app) {
        this.view = view;
        this.gameManager = gameManager;
        this.iscopy = iscopy;
        this.gameListView = gameListView;
        this.controllerConfirmation = controllerConfirmation;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("START")){
            String nameGame = view.getTextFields().get("ENTER GAME NAME").getText().trim();

            try{
                if(gameManager.gameNameAlreadyRegisteredByUser(nameGame)){
                    showError("ENTER GAME NAME", "This game name is already in use.");
                } else {
                    gameManager.createNewGame(nameGame, iscopy);
                    app.createGameScreen();
                    gameListView.setComponentInterPanel(nameGame);
                    app.showPanel("GameView");
                }
            }catch(BusinessException ex){

            }
        }else if(command.equals("CANCEL")){
            view.clearFields();
            view.clearErrorMessages();
            app.showPanel("SelectGame");
        }else if(command.equals("LOGOUT")){
            app.showPanel("Logout");
        }
    }
}
