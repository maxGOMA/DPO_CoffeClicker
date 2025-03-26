package Presentation.Controllers;

import Presentation.Views.LogOutView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Business.UserManager;

public class ControllerLogOut implements ActionListener {
    private final LogOutView view;
    private final UserManager userManager;

    public ControllerLogOut(LogOutView view) {
        this.view = view;
        userManager = new UserManager();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(view.LOGOUT_COMMAND)) {
            view.showLogOutMessage();
            userManager.logOut();
        } else if (e.getActionCommand().equals(view.DELETE_ACCOUNT_COMMAND)) {
            view.showConfirmationMessage();
        } else if (e.getActionCommand().equals(view.CONFIRMATION_COMMAND)) {
            userManager.deleteAccount();
        }
    }
}




