package Presentation.Controllers;

import Presentation.Views.LoginView;
import Presentation.Views.MainMenuView;
import Presentation.Views.PopUpErrorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerMainMenu implements ActionListener {
    private final MainMenuView mainMenuView;
    private LoginView logInView;

    public ControllerMainMenu(MainMenuView mainMenuView, LoginView logInView){
        this.mainMenuView = mainMenuView;
        this.logInView = logInView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(MainMenuView.VIEW_LOGIN)) {
            logInView.clearFields();
            logInView.clearErrorMessages();
            mainMenuView.getApp().showPanel("Login");
        }
        if (command.equals(MainMenuView.VIEW_REGISTER)) {
            mainMenuView.getApp().showPanel("Register");
        }
        if (command.equals(MainMenuView.VIEW_EXIT)) {
            System.exit(0);
        }
    }
}
