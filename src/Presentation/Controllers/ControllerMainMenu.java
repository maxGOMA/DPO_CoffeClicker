package Presentation.Controllers;

import Presentation.Views.MainMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerMainMenu implements ActionListener {
    private final MainMenuView mainMenuView;

    public ControllerMainMenu(MainMenuView mainMenuView){
        this.mainMenuView = mainMenuView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(MainMenuView.VIEW_LOGIN)) {
            mainMenuView.getApp().showPanel("Login");
        }
        if (command.equals(MainMenuView.VIEW_REGISTER)) {
            mainMenuView.getApp().showPanel("Register");
        }
        if (command.equals(MainMenuView.VIEW_LOGOUT)) {
            mainMenuView.getApp().showPanel("Logout");
        }
        if (command.equals(MainMenuView.VIEW_EXIT)) {
            System.exit(0);
        }
    }
}
