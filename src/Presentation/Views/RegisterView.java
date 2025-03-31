package Presentation.Views;

import java.awt.event.ActionListener;

public class RegisterView {

    public static final String REGISTER_COMMAND = "REGISTER_COMMAND";

    public void showErrorPasswordConfirmationMessage(){
        //TODO que muestre error por pantalla
    }

    public void showErrorEmailFormatMessage(){
        //TODO que muestre error por pantalla
    }

    public void showErrorExistingEmailMessage(){
        //TODO que muestre error por pantalla
    }

    public void showErrorExistingUserMessage(){
        //TODO que muestre error por pantalla
    }

    public void showSuccesfulLRegisterMessage(){
        //TODO que muestre error por pantalla
    }

    public String getUserText(){
        //TODO pillar info del recuadro de texto de user
        return "por rellenar";
    }

    public String getEmailText(){
        //TODO pillar info del recuadro de texto de user
        return "por rellenar";
    }

    public String getPasswordText(){
        //TODO pillar info del recuadro de texto de user
        return "por rellenar";
    }

    public String getConfirmationPasswordText(){
        //TODO pillar info del recuadro de texto de user
        return "por rellenar";
    }

    public void showExceptionErrorMessage(){
        //TODO informar de error al leer BBDD
    }

    public void controllerRegister(ActionListener actionListener) {
        //Descomentar ->
        //jbnLogOut.addActionListener(actionListener);
        //jbnDeleteAccount.addActionListener(actionListener);
        //jbnConfirmDeletion.setActionCommand(actionListener);
    }

}
