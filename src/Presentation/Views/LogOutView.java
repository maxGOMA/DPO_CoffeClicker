package Presentation.Views;

import java.awt.event.ActionListener;

public class LogOutView {
    //Se definen las etiquetas
    public static final String LOGOUT_COMMAND = "LOGOUT_COMMAND";
    public static final String DELETE_ACCOUNT_COMMAND = "DELETE_ACCOUNT_COMMAND";
    public static final String CONFIRMATION_COMMAND = "CONFIRMATION_COMMAND";

    //Se asignan estos comandos a los componentes de la interfaz despueés de crearlos
    //Descomentar ->
    //jbnLogOut.setActionCommand(LOGOUT_COMMAND); //Botón
    //jbnDeleteAccount.setActionCommand(DELETE_ACCOUNT_COMMAND); //Botón
    //jbnConfirmDeletion.setActionCommand(CONFIRMATION_COMMAND); //Botón

    public void showLogOutMessage() {
        //TODO implementar funcion que me enseñe un mensaje de bye,bye! O de logOut!
    }

    public void showConfirmationMessage() {
        //TODO implementa funcion que enseñe mensaje de confirmacion, mostrando el boton correspondiente.
    }

    public void controllerLogOut(ActionListener actionListener) {
        //Descomentar ->
        //jbnLogOut.addActionListener(actionListener);
        //jbnDeleteAccount.addActionListener(actionListener);
        //jbnConfirmDeletion.setActionCommand(actionListener);
    }

}
