package Presentation;

import Business.BusinessException;
import Business.GameManager;
import Business.StatManager;
import Business.UserManager;
import Presentation.Controllers.*;
import Presentation.Views.*;

import javax.swing.*;
import java.awt.*;

/**
 * CoffeeClickerApp is the main application window for the Coffee Clicker game.
 * It sets up and manages all views and controllers, and serves as the navigation hub.
 * The app uses a CardLayout to switch between different user interface panels.
 */
public class CoffeeClickerApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UserManager userManager;
    private GameManager gameManager;
    private StatManager statManager;
    private NewGameView newGame;
    private ControllerConfirmation controller_confirmation;

    /**
     * Constructs the application and initializes all managers, views, and controllers.
     * Loads the initial views and sets the window configuration.
     */
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

    /**
     * Creates and displays the NewGameView with the specified name (used when copying a game).
     * @param name the name of the game to pre-fill (if copying); null for a fresh new game
     * @param gameListView the parent view from which the new game screen was requested
     */
    public void createNewGameView(String name, GameListView gameListView){
        newGame = new NewGameView(name);
        ControllerNewGame controller_newgame = new ControllerNewGame(newGame, gameManager, (name != null), gameListView, controller_confirmation, this);
        newGame.setController(controller_newgame);
        mainPanel.add(newGame, "NewGame");
    }

    /**
     * Initializes and displays the SelectGame view, which lists saved games.
     */
    public void createSelectGame() {
        GameListView selectGame = new GameListView();
        ControllerGameList controllerGameList = new ControllerGameList(selectGame, gameManager, statManager, this);
        selectGame.setController(controllerGameList);
        mainPanel.add(selectGame, "SelectGame");
    }

    /**
     * Initializes and displays the GameView for the currently selected game.
     */
    public void createGameScreen() {
        GameView gameView = new GameView();
        ControllerGame controllerGame = new ControllerGame(gameView, gameManager, statManager, this);
        gameView.setController(controllerGame);
        mainPanel.add(gameView, "GameView");
    }

    /**
     * Initializes and displays the statistics graph view.
     */
    public void createStatsGraph() throws BusinessException {
        GraphView graphView = new GraphView();
        ControllerStatistics controllerStatistics = new ControllerStatistics(statManager, userManager, gameManager, graphView, this);
        graphView.setController(controllerStatistics);
        mainPanel.add(graphView, "stats");
    }

    /**
     * Displays the view associated with the specified panel name.
     * @param panelName the string identifier of the view to show
     */
    public void showPanel(String panelName) { cardLayout.show(mainPanel, panelName);}

    /**
     * Exits the application after showing a persistence-related error message.
     * @param exceptionMessage the error message to display
     */
    public void finishProgramDueToPersistanceException(String exceptionMessage) {
        this.setVisible(false);
        this.dispose();
        PopUpErrorView.showErrorPopup(null, exceptionMessage, new ImageIcon("imgs/imageError.png"));
        System.exit(0);
    }
}
