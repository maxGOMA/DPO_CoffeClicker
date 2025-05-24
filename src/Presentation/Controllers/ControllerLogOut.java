package Presentation.Controllers;

import Presentation.CoffeeClickerApp;
import Presentation.Views.ConfirmationView;
import Presentation.Views.LogOutView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Business.UserManager;

public class ControllerLogOut implements ActionListener {
    private LogOutView view;
    private UserManager userManager;
    private ConfirmationView confirmationView;
    private ControllerConfirmation controllerConfirmation;
    private static String viewBack;
    private CoffeeClickerApp app;

    public ControllerLogOut(LogOutView view, UserManager userManager, ConfirmationView confirmationView, ControllerConfirmation controllerConfirmation, CoffeeClickerApp app) {
        this.view = view;
        this.userManager = userManager;
        this.confirmationView = confirmationView;
        this.controllerConfirmation = controllerConfirmation;
        this.app = app;
    }

    public static void viewBack(String viewBack) {
        ControllerLogOut.viewBack = viewBack;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(view.LOGOUT_COMMAND)) {
            app.showPanel("MainMenuView");
            userManager.logOut();
        } else if (e.getActionCommand().equals(view.DELETE_ACCOUNT_COMMAND)) {
            confirmationView.setMessage("Warning: The account " + userManager.getCurrentUser() + " will be deleted.");
            if(viewBack.equals("Settings")){
                controllerConfirmation.viewBack("Settings");
            }else{
                controllerConfirmation.viewBack("SelectGame");
            }
            app.showPanel("Confirmation");
        }
    }
}




