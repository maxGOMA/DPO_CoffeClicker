package Presentation.Controllers;

import Business.Entities.EntityUser;
import Business.UserManager;
import Presentation.Views.LoginView;
import Presentation.Views.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public abstract class ControllerRegister implements ActionListener, KeyListener {
    UserManager userManager;
    private final RegisterView RegisterView;

    public ControllerRegister(Presentation.Views.RegisterView registerView, UserManager userManager) {
        RegisterView = registerView;
        this.userManager = userManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(Presentation.Views.RegisterView.REGISTER_COMMAND)) {
            //TODO mirar con Raúl quien hace el view

            if(!userManager.verifyEmailFormat(RegisterView.getEmailText())){
                RegisterView.showErrorEmailFormatMessage();
            }
            else{
                if(!userManager.verifyConfirmationPassword(RegisterView.getConfirmationPasswordText(),RegisterView.getPasswordText())){
                    RegisterView.showErrorPasswordConfirmationMessage();
                }
                else{
                    if(userManager.checkUserRegistered(RegisterView.getUserText())){
                        RegisterView.showErrorExistingUserMessage();
                    }
                    else{
                        if(userManager.emailRegistered(RegisterView.getEmailText())){
                            RegisterView.showErrorExistingEmailMessage();
                        }
                        else{
                            RegisterView.showSuccesfulLRegisterMessage();
                            userManager.setUser(RegisterView.getUserText());
                        }
                    }
                }
            }

        }
    }

    //TODO insertUser();
    //TODO insertEmail();
    //TODO verifyEmailFormat();
    //TODO verifyEmailUnique();
    //TODO insertPassword();
    //TODO verifyConfirmationPassword();
    //TODO changeView();


}