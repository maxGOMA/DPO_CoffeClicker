package Presentation.Controllers;

import Business.UserManager;
import Presentation.Views.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.Views.LoginView.showError;

public class ControllerLogin implements ActionListener {
    //TODO changeView();
    private final UserManager userManager;
    private final LoginView loginView;

    public ControllerLogin(LoginView loginView) {
        userManager = new UserManager();
        this.loginView = loginView;
        this.loginView.setController(this);
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

        // Simulación de verificación con la base de datos
        boolean userExists = /*checkUserInDatabase(userEmail);*/ true;
        boolean passwordCorrect = /*checkPasswordInDatabase(userEmail, password);*/ true;

        if (isValid) {
            if (!userExists) {
                showError("USER/EMAIL", "User/Email not found in database!");
                return;
            }

            if (!passwordCorrect) {
                showError("PASSWORD", "Incorrect password!");
                return;
            }

            System.out.println("LOGIN SUCCESSFUL");
            loginView.getApp().showPanel("GameScreen"); // Cambia a la pantalla del juego
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
            loginView.getApp().showPanel("MainMenuView");
        }
    }
}
