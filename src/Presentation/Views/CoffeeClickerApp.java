package Presentation.Views;

import Business.BusinessException;
import Business.GameManager;
import Business.StatManager;
import Business.UserManager;
import Presentation.Controllers.*;
import javax.swing.*;
import java.awt.*;

public class CoffeeClickerApp extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private UserManager userManager;
    private GameManager gameManager;
    private StatManager statManager;
    private NewGameView newGame;
    private ControllerConfirmation controller_confirmation;

    public CoffeeClickerApp() throws BusinessException{
        try {
            userManager = new UserManager();
            gameManager = new GameManager(userManager);
            statManager = new StatManager();

            setTitle("Coffee Clicker G3");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1350, 1080);
            setLocationRelativeTo(null);
            setIconImage(new ImageIcon("imgs/cafe.png").getImage());

            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);

            LoginView login = new LoginView(this);
            ControllerLogin controller_login = new ControllerLogin(login, userManager);
            login.setController(controller_login);

            MainMenuView mainMenuView = new MainMenuView(this);
            ControllerMainMenu controller_main_menu = new ControllerMainMenu(mainMenuView, login);
            mainMenuView.setController(controller_main_menu);

            RegisterView register = new RegisterView(this);
            ControllerRegister controller_register = new ControllerRegister(register, userManager);
            register.setController(controller_register);

            ConfirmationView confirmationView = new ConfirmationView(this);
            controller_confirmation = new ControllerConfirmation(userManager, confirmationView, gameManager);
            confirmationView.setController(controller_confirmation);

            LogOutView logout = new LogOutView(this);
            ControllerLogOut controller_logout = new ControllerLogOut(logout, userManager, confirmationView, controller_confirmation);
            logout.setController(controller_logout);

            SettingsView settingsView = new SettingsView(this);
            ControllerSettings controllerSettings = new ControllerSettings(settingsView, gameManager, confirmationView, controller_confirmation);
            settingsView.setController(controllerSettings);

            GraphView graphView = new GraphView(this);
            ControllerStatistics controllerStatistics = new ControllerStatistics(statManager, userManager, gameManager, graphView);
            graphView.setController(controllerStatistics);


            mainPanel.add(mainMenuView, "MainMenuView");
            mainPanel.add(login, "Login");
            mainPanel.add(register, "Register");
            mainPanel.add(logout, "Logout");
            mainPanel.add(confirmationView, "Confirmation");
            mainPanel.add(settingsView, "Settings");
            mainPanel.add(graphView, "stats");


            add(mainPanel);
            setVisible(true);
        }catch(BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void createNewGameView(String name, GameListView gameListView){
        newGame = new NewGameView(this, name);
        ControllerNewGame controller_newgame = new ControllerNewGame(newGame, gameManager, (name != null), gameListView, controller_confirmation);
        newGame.setController(controller_newgame);
        mainPanel.add(newGame, "NewGame");
    }

    public void createSelectGame() {
        GameListView selectGame = new GameListView(this);
        ControllerGameList controllerGameList = new ControllerGameList(selectGame, gameManager, controller_confirmation);
        selectGame.setController(controllerGameList);
        mainPanel.add(selectGame, "SelectGame");
    }

    public void createGameScreen() {
        GameView gameView = new GameView(this);
        ControllerGame controllerGame = new ControllerGame(gameView, gameManager);
        gameView.setController(controllerGame);
        mainPanel.add(gameView, "GameView");
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
}
