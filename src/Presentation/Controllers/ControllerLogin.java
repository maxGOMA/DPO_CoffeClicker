package Presentation.Controllers;

import Business.BusinessException;
import Business.UserManager;
import Persistance.PersistanceException;
import Presentation.Views.LoginView;
import Presentation.Views.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class ControllerLogin implements ActionListener {
    //TODO changeView();
    UserManager userManager;
    private final LoginView loginView;

    public ControllerLogin(LoginView loginView) {
        this.userManager = userManager;
        this.loginView = loginView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(LoginView.LOGIN_COMMAND)) {
           //TODO mirar con Raúl quien hace el view
            try {
                if (!userManager.checkUserRegistered(loginView.getUserText()) || !userManager.checkPassword(loginView.getUserText(), loginView.getPasswordText())) {
                    loginView.showErrorInputMessage();
                } else {
                    loginView.showSuccesfulLoginMessage();
                    userManager.setUser(loginView.getUserText());
                }
            }catch (BusinessException ex){
                LoginView.showExceptionErrorMessage();
            }
        }
    }
}
