package Presentation.Views;

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
}
