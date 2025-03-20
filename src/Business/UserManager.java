package Business;

import Business.Entities.EntityUser;
import Persistance.UserDAO;

import java.util.Objects;

public class UserManager {
    private EntityUser user;
    private final UserDAO userDAO;
    //TODO verifyEmailFormat();
    //TODO verifyConfirmationPassword();

    //TODO insertUser();
    //TODO insertEmail();
    //TODO insertPassword();

    public UserManager(UserDAO userDAO) {
        this.userDAO = new SQLUserDAO();
        this.user = null;
    }

    public boolean checkPassword(String userName, String userPassword){
        return userDAO.verifyPassword(userName,userPassword);
    }

    public boolean checkUserRegistered(String userName){
        return userDAO.usernameRegistered(userName);
    }

    public EntityUser setUser(String userName){
        return userDAO.getUserFromUsername(userName);
    }

    public void registerUser(String userName, String userPassword, String userEmail){
         userDAO.registerUser(userName,userPassword,userEmail);
    }

    public boolean emailRegistered(String userEmail){
        return userDAO.emailRegistered(userEmail);
    }

    public boolean verifyEmailFormat(String email){
        return email.contains("@gmail.com");
    }

    public boolean verifyConfirmationPassword(String confirmationPassword,String password){
        return Objects.equals(confirmationPassword, password);
    }
}