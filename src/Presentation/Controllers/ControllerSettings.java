package Presentation.Controllers;

import Business.GameManager;
import Business.StatManager;
import Presentation.CoffeeClickerApp;
import Presentation.Views.ConfirmationView;
import Presentation.Views.SettingsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerSettings implements ActionListener {
    private SettingsView view;
    private GameManager gameManager;
    private StatManager statManager;
    private ConfirmationView confirmationView;
    private ControllerConfirmation controllerConfirmation;
    private CoffeeClickerApp app;

    public ControllerSettings(SettingsView view, GameManager gameManager, StatManager statManager, ConfirmationView confirmationView, ControllerConfirmation controllerConfirmation, CoffeeClickerApp app) {
        this.view = view;
        this.gameManager = gameManager;
        this.statManager = statManager;
        this.confirmationView = confirmationView;
        this.controllerConfirmation = controllerConfirmation;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(view.LOGOUT_COMMAND)) {
            ControllerLogOut.ViewBack("Settings");
            statManager.stopStatsGeneration();
            gameManager.endAndUpdateGame();
            app.showPanel("Logout");
        } else if (e.getActionCommand().equals(view.BACK_COMMAND)) {
            app.showPanel("GameView");
        } else if (e.getActionCommand().equals(view.FINISH_COMMAND)){
            confirmationView.setMessage("Warning: Once the game is finished, you will no longer be able to access it.");
            controllerConfirmation.ViewBack("Settings");
            app.showPanel("Confirmation");
        } else if (e.getActionCommand().equals(view.SAVE_COMMAND)) {
            statManager.stopStatsGeneration();
            gameManager.endAndUpdateGame();
            app.createSelectGame();
            app.showPanel("SelectGame");
        }
    }
}




