package Presentation.Controllers;

import Business.BusinessException;
import Business.Entities.EntityGame;
import Business.GameManager;
import Business.StatManager;
import Presentation.CoffeeClickerApp;
import Presentation.Views.GameListView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controller responsible for switching between active games.
 */
public class ControllerGameList implements ActionListener {
    private GameManager gameManager;
    private StatManager statManager;
    private GameListView view;
    private static String name;
    private CoffeeClickerApp app;

    /**
     * Constructor for the game list controller.
     *
     * @param gameListView the game list view
     * @param gameManager the game manager
     * @param app the main application instance
     */
    public ControllerGameList(GameListView gameListView, GameManager gameManager, StatManager statManager, CoffeeClickerApp app) {
        this.gameManager = gameManager;
        this.statManager = statManager;
        this.view = gameListView;
        this.app = app;
    }
    /**
     * Extracts the game name from a command string.
     *
     * @param command the action command containing the name
     * @return the extracted game name
     */
    private String findName(String command){
        String name = "";
        Boolean guardar = false;
        for(int i = 0; i < command.length(); i++){
            if(guardar){
                name += command.charAt(i);
            }
            if(command.charAt(i) == '_'){
                guardar = true;
            }
        }
        return name;
    }

    /**
     * Retrieves the list of games for the currently logged-in user.
     *
     * @return list of user's games or null in case of error
     */
    public List<EntityGame> getGamesByUser(){
        try {
            return gameManager.getGamesOfLoggedInUser();
        } catch (BusinessException e) {
            app.finishProgramDueToPersistanceException(e.getMessage());
        }
        return null;
    }

    /**
     * Handles selection of a game from the list.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("CONFIRM_DELETE")){
            try {
                statManager.deleteStatsFromGame(gameManager.getIDFromGameName(name));
                gameManager.deleteGame(name);
                view.getPanelConfirmation().setVisible(false);
                view.getnewGameButton().setVisible(true);
                view.deleteGameSelectedView(name);
            } catch (BusinessException ex) {
                app.finishProgramDueToPersistanceException(ex.getMessage());
            }
        }else if(command.equals("CANCEL")){
            view.getPanelConfirmation().setVisible(false);
            view.getnewGameButton().setVisible(true);
            view.clearGameSelected();

        }else if(command.equals("NEWGAME")){
            app.createNewGameView(null, view);
            app.showPanel("NewGame");

        }else if(command.contains("COPY")){
            name = findName(command);
            EntityGame game = null;
            try {
                game = gameManager.setGameFromPersistanceForLoggedInUser(name);
            } catch (BusinessException ex) {
                app.finishProgramDueToPersistanceException(ex.getMessage());
            }
            app.createNewGameView(game.getName(), view);
            app.showPanel("NewGame");

        } else if(command.contains("DELETE")) {
            name = findName(command);
            view.getnewGameButton().setVisible(false);
            view.getPanelConfirmation().setVisible(true);
            view.paintGameSelected(name);

        }else if(command.equals("LOGOUT")){
            ControllerLogOut.viewBack("SelectGame");
            app.showPanel("Logout");

        }else if(command.contains("START")){
            name = findName(command);
            try {
                gameManager.setGameFromPersistanceForLoggedInUser(name);
            } catch (BusinessException ex) {
                app.finishProgramDueToPersistanceException(ex.getMessage());
            }
            app.createGameScreen();
            app.showPanel("GameView");
        }
    }


    /**
     * Creates a rotating title effect on the provided JLabel.
     * @param rotatingText an array containing the rotating text (single-element array for mutability)
     * @param txtTitle the label whose text will rotate
     * @return a Timer that performs the text rotation
     */
    public static Timer timerListener(String[] rotatingText, JLabel txtTitle){
        Timer timer = new Timer(67, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotatingText[0] = rotatingText[0].substring(1) + rotatingText[0].charAt(0);
                txtTitle.setText(rotatingText[0]);
            }
        });
        return timer;
    }

    /**
     * Adds mouse listeners to start and stop the rotation of the title when hovering.
     * @param timer the timer controlling the rotation
     * @param txtTitle the label being hovered
     * @param txt the default text
     * @param rotatingText the rotating text array
     * @param fullText the full static text
     */
    public static void txtTitleListener(Timer timer, JLabel txtTitle, String txt, String[] rotatingText, String fullText){
        txtTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                timer.start();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                timer.stop();
                txtTitle.setText(txt); // Opcional: restaurar texto original
                rotatingText[0] = fullText; // Reiniciar desde el principio
            }
        });
    }

    /**
     * Adds mouse listener to change button icons on hover.
     * @param button the button to apply the listener to
     * @param iconEntered icon to show when mouse enters
     * @param iconExited icon to show when mouse exits
     */
    public static void mouseListener(JButton button, ImageIcon iconEntered, ImageIcon iconExited){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(iconEntered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(iconExited);
            }
        });
    }
}