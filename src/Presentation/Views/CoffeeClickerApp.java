package Presentation.Views;

import Presentation.Controllers.ControllerGame;
import Presentation.Controllers.ControllerLogin;
import Presentation.Controllers.ControllerMainMenu;
import Presentation.Controllers.ControllerRegister;

import javax.swing.*;
import java.awt.*;

public class CoffeeClickerApp extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public CoffeeClickerApp() {
        setTitle("Coffee Clicker G3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1350, 1080);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("imgs/cafe.png").getImage());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        MainMenuView mainMenuView = new MainMenuView(this);
        ControllerMainMenu controller_main_menu = new ControllerMainMenu(mainMenuView);
        mainMenuView.setController(controller_main_menu);

        LoginView login = new LoginView(this);
        ControllerLogin controller_login = new ControllerLogin(login);
        login.setController(controller_login);

        RegisterView register = new RegisterView(this);
        ControllerRegister controller_register = new ControllerRegister(register);
        register.setController(controller_register);

        LogOutView logout = new LogOutView(this);
        //ControllerLogOut controller_logout = new ControllerLogOut(logout);
        //logout.setController(controller_logout);

        GameView gameView = new GameView(this);
        ControllerGame controllerGame = new ControllerGame(gameView);
        gameView.setController(controllerGame);

        mainPanel.add(mainMenuView, "MainMenuView");
        mainPanel.add(login, "Login");
        mainPanel.add(register, "Register");
        mainPanel.add(logout, "Logout");

        add(mainPanel);
        setVisible(true);
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {
        new CoffeeClickerApp();
    }
}
