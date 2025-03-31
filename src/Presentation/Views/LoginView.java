package Presentation.Views;

import java.awt.event.ActionListener;

public class LoginView {
    public static final String LOGIN_COMMAND = "LOGIN_COMMAND";

    public void showErrorInputMessage(){
        //TODO que muestre error por pantalla
    }

    public String getUserText(){
        //TODO pillar info del recuadro de texto de user
        return "por rellenar";
    }

    public String getPasswordText(){
        //TODO pillar info del recuadro de texto de password
        return "por rellenar";
    }

    public void showSuccesfulLoginMessage(){
        //TODO al entrar pwd y user si correcto enseña mensaje de que se ha entrado
    }

    public static void showExceptionErrorMessage(){
        //TODO informar de error al leer BBDD
    }

    public void controllerLogIn(ActionListener actionListener) {
        //Descomentar ->
        //jbnLogOut.addActionListener(actionListener);
        //jbnDeleteAccount.addActionListener(actionListener);
        //jbnConfirmDeletion.setActionCommand(actionListener);
    }
}
