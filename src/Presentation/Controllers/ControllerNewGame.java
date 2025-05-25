package Presentation.Controllers;

import Business.BusinessException;
import Business.GameManager;
import Presentation.CoffeeClickerApp;
import Presentation.Views.GameListView;
import Presentation.Views.NewGameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.Views.NewGameView.showError;

/**
 * Controller responsible for starting a new game.
 */
public class ControllerNewGame implements ActionListener {
    private NewGameView view;
    private GameManager gameManager;
    private Boolean isCopy;
    private GameListView gameListView;
    private CoffeeClickerApp app;

    /**
     * Constructor for the new game controller.
     * @param gameManager the game manager
     * @param view the new game view
     * @param app the main application instance
     */
    public ControllerNewGame(NewGameView view, GameManager gameManager, Boolean isCopy, GameListView gameListView, ControllerConfirmation controllerConfirmation, CoffeeClickerApp app) {
        this.view = view;
        this.gameManager = gameManager;
        this.isCopy = isCopy;
        this.gameListView = gameListView;
        this.app = app;
    }

    /**
     * Handles actions performed in the new game view.
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("START")){
            String nameGame = view.getTextFields().get("ENTER GAME NAME").getText().trim();

            try{
                if(gameManager.gameNameAlreadyRegisteredByUser(nameGame)){
                    showError("ENTER GAME NAME", "This game name is already in use.");
                } else {
                    gameManager.createNewGame(nameGame, isCopy);
                    app.createGameScreen();
                    gameListView.setComponentInterPanel(nameGame);
                    app.showPanel("GameView");
                }
            }catch (BusinessException ex){
                app.finishProgramDueToPersistanceException(ex.getMessage());
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
