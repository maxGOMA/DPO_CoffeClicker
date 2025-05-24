package Presentation.Controllers;

import Business.BusinessException;
import Business.UserManager;
import Presentation.CoffeeClickerApp;
import Presentation.Views.LoginView;
import Presentation.Views.PopUpErrorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.Views.LoginView.showError;

public class ControllerLogin implements ActionListener {
    private UserManager userManager;
    private LoginView loginView;
    private CoffeeClickerApp app;

    public ControllerLogin(LoginView loginView, UserManager userManager, CoffeeClickerApp app) {
        this.userManager = userManager;
        this.loginView = loginView;
        this.loginView.setController(this);
        this.app = app;
    }

    private void validateAndLogin() {
        String userEmail = loginView.getTextFields().get("USER/EMAIL").getText().trim();
        String password = loginView.getTextFields().get("PASSWORD").getText().trim();

        loginView.clearErrorMessages();
        boolean isValid = true;

        if (userEmail.isEmpty()) {
            showError("USER/EMAIL", "User/Email field cannot be empty!");
            isValid = false;
        }

        if (password.isEmpty()) {
            showError("PASSWORD", "Password field cannot be empty!");
            isValid = false;
        }

        boolean errorDatabase = false;
        boolean userExists = false, passwordCorrect = false;

        try {
            if (userEmail.contains("@")) {
                userExists = userManager.emailRegistered(userEmail);
            } else {
                userExists = userManager.checkUserRegistered(userEmail);
            }
        } catch (BusinessException e) {
            errorDatabase = true;
        }

        if (userExists) {
            try {
                passwordCorrect = userManager.checkPassword(userEmail, password);
            } catch (BusinessException e){
                errorDatabase = true;
            }
        }

        if (isValid) {
            if (!errorDatabase) {
                if (!userExists) {
                    showError("USER/EMAIL", "User/Email not found in database!");
                } else {
                    if (!passwordCorrect) {
                        showError("PASSWORD", "Incorrect password!");
                    } else {
                        try{
                            userManager.setUser(userEmail);
                            app.createSelectGame();
                            app.showPanel("SelectGame");
                        }catch(BusinessException e){
                            app.finishProgramDueToPersistanceException(e.getMessage());
                        }
                    }
                }
            } else {
                showError("USER/EMAIL", "Problem accessing the database!");
                showError("PASSWORD", "Problem accessing the database!");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(LoginView.LOGIN_COMMAND)) {
            validateAndLogin();
        } else if (command.equals(LoginView.BACK_COMMAND)) {
            loginView.clearFields();
            loginView.clearErrorMessages();
            app.showPanel("MainMenuView");
        }
    }
}
