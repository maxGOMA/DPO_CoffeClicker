package Presentation.Controllers;

import Business.BusinessException;
import Business.Entities.EntityUser;
import Business.UserManager;
import Persistance.PersistanceException;
import Presentation.Views.LoginView;
import Presentation.Views.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class ControllerRegister implements ActionListener {
    UserManager userManager;
    private final RegisterView registerView;

    public ControllerRegister(Presentation.Views.RegisterView registerView) {
        this.registerView = registerView;
        userManager = new UserManager();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(Presentation.Views.RegisterView.REGISTER_COMMAND)) {

            if(!userManager.verifyEmailFormat(registerView.getEmailText())){
                registerView.showErrorEmailFormatMessage();
            }
            else{
                if(!userManager.verifyConfirmationPassword(registerView.getConfirmationPasswordText(),registerView.getPasswordText())){
                    registerView.showErrorPasswordConfirmationMessage();
                }
                else{
                    try {
                        if (userManager.checkUserRegistered(registerView.getUserText())) {
                            registerView.showErrorExistingUserMessage();
                        } else {
                            if (userManager.emailRegistered(registerView.getEmailText())) {
                                registerView.showErrorExistingEmailMessage();
                            } else {
                                registerView.showSuccesfulLRegisterMessage();
                                userManager.setUser(registerView.getUserText());
                            }
                        }
                    } catch(BusinessException ex){
                          registerView.showExceptionErrorMessage();
                    }
                }
            }

        }
    }

}