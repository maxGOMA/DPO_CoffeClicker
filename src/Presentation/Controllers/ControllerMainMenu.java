package Presentation.Controllers;

import Presentation.CoffeeClickerApp;
import Presentation.Views.LoginView;
import Presentation.Views.MainMenuView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller responsible for managing the main menu interactions.
 */
public class ControllerMainMenu implements ActionListener {
    private MainMenuView mainMenuView;
    private LoginView logInView;
    private CoffeeClickerApp app;

    /**
     * Constructor for the main menu controller.
     * @param app reference to the main application instance
     */
    public ControllerMainMenu(MainMenuView mainMenuView, LoginView logInView, CoffeeClickerApp app) {
        this.mainMenuView = mainMenuView;
        this.logInView = logInView;
        this.app = app;
    }

    /**
     * Handles actions triggered in the main menu.
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(mainMenuView.VIEW_LOGIN)) {
            logInView.clearFields();
            logInView.clearErrorMessages();
            app.showPanel("Login");
        }
        if (command.equals(mainMenuView.VIEW_REGISTER)) {
            app.showPanel("Register");
        }
        if (command.equals(mainMenuView.VIEW_EXIT)) {
            System.exit(0);
        }
    }
}
