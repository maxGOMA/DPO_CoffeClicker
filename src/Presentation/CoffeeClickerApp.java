package Presentation;

import Business.BusinessException;
import Business.GameManager;
import Business.StatManager;
import Business.UserManager;
import Presentation.Controllers.*;
import Presentation.Views.*;

import javax.swing.*;
import java.awt.*;

public class CoffeeClickerApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UserManager userManager;
    private GameManager gameManager;
    private StatManager statManager;
    private NewGameView newGame;
    private ControllerConfirmation controller_confirmation;

    public CoffeeClickerApp() {
        try {
            userManager = new UserManager();
            gameManager = new GameManager(userManager);
            statManager = new StatManager();

            setTitle("Coffee Clicker G3");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1350, 1080);
            setLocationRelativeTo(null);
            setIconImage(new ImageIcon("imgs/cafe.png").getImage());
            setResizable(false);

            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);

            LoginView login = new LoginView();
            ControllerLogin controller_login = new ControllerLogin(login, userManager, this);
            login.setController(controller_login);

            MainMenuView mainMenuView = new MainMenuView();
            ControllerMainMenu controller_main_menu = new ControllerMainMenu(mainMenuView, login, this);
            mainMenuView.setController(controller_main_menu);

            RegisterView register = new RegisterView();
            ControllerRegister controller_register = new ControllerRegister(register, userManager, this);
            register.setController(controller_register);

            ConfirmationView confirmationView = new ConfirmationView();
            controller_confirmation = new ControllerConfirmation(userManager, confirmationView, gameManager, statManager, this);
            confirmationView.setController(controller_confirmation);

            LogOutView logout = new LogOutView();
            ControllerLogOut controller_logout = new ControllerLogOut(logout, userManager, confirmationView, controller_confirmation, this);
            logout.setController(controller_logout);

            SettingsView settingsView = new SettingsView();
            ControllerSettings controllerSettings = new ControllerSettings(settingsView, gameManager, statManager, confirmationView, controller_confirmation, this);
            settingsView.setController(controllerSettings);

            mainPanel.add(mainMenuView, "MainMenuView");
            mainPanel.add(login, "Login");
            mainPanel.add(register, "Register");
            mainPanel.add(logout, "Logout");
            mainPanel.add(confirmationView, "Confirmation");
            mainPanel.add(settingsView, "Settings");

            createStatsGraph();

            add(mainPanel);
            setVisible(true);
        } catch (BusinessException e) {
            finishProgramDueToPersistanceException(e.getMessage());
        }
    }

    public void createNewGameView(String name, GameListView gameListView){
        newGame = new NewGameView(name);
        ControllerNewGame controller_newgame = new ControllerNewGame(newGame, gameManager, (name != null), gameListView, controller_confirmation, this);
        newGame.setController(controller_newgame);
        mainPanel.add(newGame, "NewGame");
    }

    public void createSelectGame() {
        GameListView selectGame = new GameListView();
        ControllerGameList controllerGameList = new ControllerGameList(selectGame, gameManager, statManager, this);
        selectGame.setController(controllerGameList);
        mainPanel.add(selectGame, "SelectGame");
    }

    public void createGameScreen() {
        GameView gameView = new GameView(this);
        ControllerGame controllerGame = new ControllerGame(gameView, gameManager, statManager, this);
        gameView.setController(controllerGame);
        mainPanel.add(gameView, "GameView");
    }

    public void createStatsGraph() throws BusinessException {
        GraphView graphView = new GraphView();
        ControllerStatistics controllerStatistics = new ControllerStatistics(statManager, userManager, gameManager, graphView, this);
        graphView.setController(controllerStatistics);
        mainPanel.add(graphView, "stats");
    }

    public void showPanel(String panelName) { cardLayout.show(mainPanel, panelName);}

    public void finishProgramDueToPersistanceException(String exceptionMessage) {
        this.setVisible(false);
        this.dispose();
        PopUpErrorView.showErrorPopup(null, exceptionMessage, new ImageIcon("imgs/imageError.png"));
        System.exit(0);
    }
}
