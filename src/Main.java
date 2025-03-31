import Presentation.Controllers.ControllerLogOut;
import Presentation.Controllers.ControllerLogin;
import Presentation.Controllers.ControllerRegister;
import Presentation.Views.LogOutView;
import Presentation.Views.LoginView;
import Presentation.Views.RegisterView;

public class Main {
    public static void main(String[] args) {
        LogOutView logOutView = new LogOutView();
        LoginView logInView = new LoginView();
        RegisterView registerView = new RegisterView();

        ControllerLogin controllerLogIn = new ControllerLogin(logInView);
        logInView.controllerLogIn(controllerLogIn);

        ControllerRegister controllerRegister = new ControllerRegister(registerView);
        registerView.controllerRegister(controllerRegister);

        ControllerLogOut controllerLogOut = new ControllerLogOut(logOutView);
        logOutView.controllerLogOut(controllerLogOut);

    }
}
