package Presentation.Controllers;

import Presentation.CoffeeClickerApp;
import Presentation.Views.LoginView;
import Presentation.Views.MainMenuView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerMainMenu implements ActionListener {
    private MainMenuView mainMenuView;
    private LoginView logInView;
    private CoffeeClickerApp app;

    public ControllerMainMenu(MainMenuView mainMenuView, LoginView logInView, CoffeeClickerApp app) {
        this.mainMenuView = mainMenuView;
        this.logInView = logInView;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(MainMenuView.VIEW_LOGIN)) {
            logInView.clearFields();
            logInView.clearErrorMessages();
            app.showPanel("Login");
        }
        if (command.equals(MainMenuView.VIEW_REGISTER)) {
            app.showPanel("Register");
        }
        if (command.equals(MainMenuView.VIEW_EXIT)) {
            System.exit(0);
        }
    }
}
