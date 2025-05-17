package Presentation.Controllers;

import Business.BusinessException;
import Business.GameManager;
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

    public ControllerNewGame(NewGameView view, GameManager gameManager, Boolean iscopy, GameListView gameListView, ControllerConfirmation controllerConfirmation) {
        this.view = view;
        this.gameManager = gameManager;
        this.iscopy = iscopy;
        this.gameListView = gameListView;
        this.controllerConfirmation = controllerConfirmation;
    }

    private String findName(String command){
        String name = "";
        Boolean guardar = false;
        for(int i = 0; i < command.length(); i++){
            if(guardar){
                name += command.charAt(i);
            }
            if(command.charAt(i) == '_'){
                guardar = true;
            }
        }
        return name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("START")){
            String nameGame = view.getTextFields().get("ENTER GAME NAME").getText().trim();

            try{
                if(gameManager.gameNameAlreadyRegisteredByUser(nameGame)){
                    //error
                    showError("ENTER GAME NAME", "This game name is already in use.");
                } else {
                    controllerConfirmation.setGameName(nameGame);
                    gameManager.createNewGame(nameGame, iscopy);
                    view.getApp().createGameScreen();
                    gameListView.setComponentInterPanel(nameGame);
                    view.getApp().showPanel("GameView");
                }
            }catch(BusinessException ex){

            }
        }else if(command.equals("CANCEL")){
            view.clearFields();
            view.clearErrorMessages();
            view.getApp().showPanel("SelectGame");
        }else if(command.equals("LOGOUT")){
            view.getApp().showPanel("Logout");
        }
    }
}
