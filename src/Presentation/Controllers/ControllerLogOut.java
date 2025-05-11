package Presentation.Controllers;

import Business.GameManager;
import Presentation.Views.ConfirmationView;
import Presentation.Views.LogOutView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Business.UserManager;

public class ControllerLogOut implements ActionListener {
    private final LogOutView view;
    private final UserManager userManager;
    private ConfirmationView confirmationView;
    private ControllerConfirmation controllerConfirmation;
    private static String ViewBack;

    public ControllerLogOut(LogOutView view, UserManager userManager, ConfirmationView confirmationView, ControllerConfirmation controllerConfirmation) {
        this.view = view;
        this.userManager = userManager;
        this.confirmationView = confirmationView;
        this.controllerConfirmation = controllerConfirmation;
    }

    public static void ViewBack(String viewBack) {
        ViewBack = viewBack;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(view.LOGOUT_COMMAND)) {
            //view.showLogOutMessage();
            view.getApp().showPanel("MainMenuView");
            userManager.logOut();
            System.out.println(e.getActionCommand());
        } else if (e.getActionCommand().equals(view.DELETE_ACCOUNT_COMMAND)) {
            //view.showConfirmationMessage();
            System.out.println(e.getActionCommand());
            confirmationView.setMessage("Warning: The account " + userManager.getUser().getUsername() + " will be deleted.");
            if(ViewBack.equals("Settings")){
                controllerConfirmation.ViewBack("Settings");
            }else{
                controllerConfirmation.ViewBack("SelectGame");
            }
            view.getApp().showPanel("Confirmation");
        }
    }
}




