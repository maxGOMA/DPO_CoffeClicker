package Presentation.Controllers;

import Business.UserManager;
import Presentation.Views.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public abstract class ControllerLogin implements ActionListener, KeyListener {
    //TODO changeView();
    UserManager userManager;
    private final LoginView loginView;

    public ControllerLogin(UserManager userManager, LoginView loginView) {
        this.userManager = userManager;
        this.loginView = loginView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(LoginView.LOGIN_COMMAND)) {
           //TODO mirar con Raúl quien hace el view
            if(!userManager.checkUserRegistered(loginView.getUserText()) || !userManager.checkPassword(loginView.getUserText(), loginView.getPasswordText())){
                loginView.showErrorInputMessage();
            }
            else{
                loginView.showSuccesfulLoginMessage();
                userManager.setUser(loginView.getUserText());
            }
        }
    }
}
