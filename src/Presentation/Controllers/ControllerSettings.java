package Presentation.Controllers;

import Business.GameManager;
import Business.StatManager;
import Presentation.Views.ConfirmationView;
import Presentation.Views.SettingsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerSettings implements ActionListener {
    private final SettingsView view;
    private final GameManager gameManager;
    private final StatManager statManager;

    private ConfirmationView confirmationView;
    private String GameName;
    private ControllerConfirmation controllerConfirmation;

    public ControllerSettings(SettingsView view, GameManager gameManager, StatManager statManager, ConfirmationView confirmationView, ControllerConfirmation controllerConfirmation) {
        this.view = view;
        this.gameManager = gameManager;
        this.statManager = statManager;
        this.confirmationView = confirmationView;
        this.controllerConfirmation = controllerConfirmation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(view.LOGOUT_COMMAND)) {
            ControllerLogOut.ViewBack("Settings");
            statManager.stopStatsGeneration();
            gameManager.endAndUpdateGame();
            view.getApp().showPanel("Logout");
            System.out.println(e.getActionCommand());
        } else if (e.getActionCommand().equals(view.BACK_COMMAND)) {
            System.out.println(e.getActionCommand());
            view.getApp().showPanel("GameView");
        } else if (e.getActionCommand().equals(view.FINISH_COMMAND)){
            System.out.println(e.getActionCommand());
            confirmationView.setMessage("Warning: Once the game is finished, you will no longer be able to access it.");
            controllerConfirmation.ViewBack("Settings");
            view.getApp().showPanel("Confirmation");
        } else if (e.getActionCommand().equals(view.SAVE_COMMAND)) {
            statManager.stopStatsGeneration();
            gameManager.endAndUpdateGame();
            view.getApp().createSelectGame();
            view.getApp().showPanel("SelectGame");
        }
    }
}




