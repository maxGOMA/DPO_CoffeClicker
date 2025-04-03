//package Presentation.Controllers;
//
//import Business.Entities.EntityGame;
//import Business.GameManager;
//import Business.UserManager;
//import Presentation.Views.LogOutView;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class ControllerManualClicker implements ActionListener {
//    private final GameView gameView;
//    private UserManager userManager;
//
//    public ControllerManualClicker(GameView gameView) {
//        this.gameView = gameView;
//
//        userManager = new UserManager();
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals(gameView.COOKIE_MANUAL_CLICK_COMMAND)) {
//            double incrementedCoffees;
//            GameManager gameManager = new GameManager();
//            incrementedCoffees = gameManager.IncrementCoffee();
//            //TODO mostrar en la view encima del café cuantos se han sumado
//            gameView.ShowHowManyAdded(incrementedCoffees);
//        }
//    }
//}
