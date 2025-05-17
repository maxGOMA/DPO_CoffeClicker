package Presentation.Controllers;

import Business.BusinessException;
import Business.Entities.EntityGame;
import Business.GameManager;
import Business.StatManager;
import Presentation.Views.GameListView;
import Presentation.Views.PopUpErrorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static Presentation.Views.GameListView.NEW_GAME;

public class ControllerGameList implements ActionListener {
    private GameManager gameManager;
    private StatManager statManager;
    private GameListView view;
    private static String name;

    //variable de la view

    public ControllerGameList(GameListView gameListView, GameManager gameManager, ControllerConfirmation controllerConfirmation, StatManager statManager) {
        this.gameManager = gameManager;
        this.statManager = statManager;
        this.view = gameListView;
        //this.view.setController();
        //view CUANDO SE CREE
    }

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

    public List<EntityGame> getGamesByUser(){
        try {
            return gameManager.getGamesOfLoggedInUser();
        } catch (BusinessException e) {
            System.out.println(e.getMessage());
            PopUpErrorView.showErrorPopup(null, e.getMessage(), new ImageIcon("imgs/imageError.png"));
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("CONFIRM_DELETE")){
            try {
                System.out.println(command);
                statManager.deleteStatsFromGame(gameManager.getIDFromGameName(name));
                gameManager.deleteGame(name);
                view.getPanelConfirmation().setVisible(false);
                view.getnewGameButton().setVisible(true);
                view.deleteGameSelectedView(name);
            } catch (BusinessException ex) {
                //TODO lanzar persistance exception
            }
        }else if(command.equals("CANCEL")){

            System.out.println(command);
            view.getPanelConfirmation().setVisible(false);
            view.getnewGameButton().setVisible(true);
            view.clearGameSelected();

        }else if(command.equals("NEWGAME")){
            //PANTALLA NEW GAME
            view.getApp().createNewGameView(null, view);
            view.getApp().showPanel("NewGame");
            System.out.println(command);

        }else if(command.contains("COPY")){
            name = findName(command);
            EntityGame game = null;
            try {
                game = gameManager.setGameFromPersistanceForLoggedInUser(name);
            } catch (BusinessException ex) {
                PopUpErrorView.showErrorPopup(null, ex.getMessage(), new ImageIcon("imgs/imageError.png"));
            }
            // PASA AL NEW GAME CON LOS DATOS DE ESTE
            view.getApp().createNewGameView(game.getName(), view);
            view.getApp().showPanel("NewGame");
            System.out.println(command);

        } else if(command.contains("DELETE")) {
            System.out.println(command);
            name = findName(command);
            view.getnewGameButton().setVisible(false);
            view.getPanelConfirmation().setVisible(true);
            view.paintGameSelected(name);

        }else if(command.equals("LOGOUT")){
            ControllerLogOut.ViewBack("SelectGame");
            view.getApp().showPanel("Logout");
            System.out.println("LOGOUT");

        }else if(command.contains("START")){
            // COMIENZA EL JUEGO
            name = findName(command);
            try {
                System.out.println("name: " + name);
                gameManager.setGameFromPersistanceForLoggedInUser(name);
            } catch (BusinessException ex) {
                PopUpErrorView.showErrorPopup(null, ex.getMessage(), new ImageIcon("imgs/imageError.png"));
            }
            view.getApp().createGameScreen();
            view.getApp().showPanel("GameView");
            System.out.println(command);

        }
    }



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

    public static void  MouseListener(JButton button, ImageIcon Iconentered, ImageIcon Iconexited){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(Iconentered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(Iconexited);
            }
        });
    }
}