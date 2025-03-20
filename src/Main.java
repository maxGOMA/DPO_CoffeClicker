import Presentation.Controllers.ControllerLogOut;
import Presentation.Controllers.ControllerLogin;
import Presentation.Controllers.ControllerRegister;
import Presentation.Views.LogOutView;

public class Main {
    public static void main(String[] args) {
        LogOutView logOutView = new LogOutView();
        LoginView logInView = new LogInView();
        RegisterView registerView = new RegisterView();

        ControllerLogin controllerLogIn = new ControllerLogin();
        logInView.controllerLogIn(controllerLogIn);

        ControllerRegister controllerRegister = new ControllerRegister();
        registerView.controllerRegister(controllerRegister);

        ControllerLogOut controllerLogOut = new ControllerLogOut(logOutView);
        logOutView.controllerLogOut(controllerLogOut);

    }
}
