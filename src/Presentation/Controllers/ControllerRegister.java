package Presentation.Controllers;

import Business.BusinessException;
import Business.UserManager;
import Presentation.CoffeeClickerApp;
import Presentation.Views.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import static Presentation.Views.RegisterView.showError;

public class ControllerRegister implements ActionListener {
    private UserManager userManager;
    private RegisterView registerView;
    private CoffeeClickerApp app;

    public ControllerRegister(RegisterView registerView, UserManager userManager, CoffeeClickerApp app) {
        this.registerView = registerView;
        this.userManager = userManager;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command.equals(RegisterView.REGISTER_COMMAND)){
            validateAndRegister();
        }
        if(command.equals(RegisterView.BACK_COMMAND)){
            registerView.clearFields();
            registerView.clearErrorMessages();
            app.showPanel("MainMenuView");
        }

    }

    private void validateAndRegister() {
        String user = registerView.getTextFields().get("USER").getText().trim();
        String email = registerView.getTextFields().get("EMAIL").getText().trim();
        String password = registerView.getTextFields().get("PASSWORD").getText().trim();
        String confirmPassword = registerView.getTextFields().get("CONFIRM PASSWORD").getText().trim();

        registerView.clearErrorMessages();
        boolean isValid = true;

        if (user.isEmpty()) {
            showError("USER", "User field cannot be empty!");
            isValid = false;
        } else {
            try {
                if (userManager.checkUserRegistered(user)) {
                    showError("USER", "Username is already registered!");
                    isValid = false;
                }
            } catch (BusinessException e) {
                showError("USER", "Could not acces the database!");
                isValid = false;
            }
        }

        if (email.isEmpty() || !isValidEmail(email)) {
            showError("EMAIL", "Invalid email format!");
            isValid = false;
        } else {
            try {
                if (userManager.emailRegistered(email)) {
                    showError("EMAIL", "Email is already registered!");
                    isValid = false;
                }
            } catch (BusinessException e) {
                showError("EMAIL", "Could not acces the database!");
                isValid = false;
            }
        }

        if (!isValidPassword(password)) {
            showError("PASSWORD", "Password must have at least 8 characters, including uppercase, lowercase, and a number!");
            isValid = false;
        }

        if (confirmPassword.isEmpty()) {
            showError("CONFIRM PASSWORD", "Confirmation password cannot be empty!");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            showError("CONFIRM PASSWORD", "Passwords do not match!");
            isValid = false;
        }

        if (isValid) {
            try {
                userManager.registerUser(user, password, email);
                userManager.setUser(user);
                registerView.clearFields();
                registerView.clearErrorMessages();
                app.showPanel("MainMenuView");
            } catch (BusinessException e){
                app.finishProgramDueToPersistanceException(e.getMessage());
            }

        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*");
    }

}