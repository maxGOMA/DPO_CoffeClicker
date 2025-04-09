package Business;

import Business.Entities.EntityUser;
import Persistance.PersistanceException;
import Persistance.UserDAO;
import Persistance.sql.SQLUserDAO;
import java.util.Objects;

public class UserManager {
    private static EntityUser user;
    private final UserDAO userDAO;

    public UserManager() {
        this.userDAO = new SQLUserDAO();
    }

    //UserIdentifier es el email o username.
    public boolean checkPassword (String userIdentifier, String userPassword) throws BusinessException {
        try {
            return userDAO.verifyPassword(userIdentifier, userPassword);
        }catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public boolean checkUserRegistered (String userName) throws BusinessException{
        try {
            return userDAO.usernameRegistered(userName);
        }catch(PersistanceException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public EntityUser setUser (String userName) throws BusinessException {
        try {
            return userDAO.getUserFromusername(userName);
        }catch(PersistanceException e){
           throw  new BusinessException(e.getMessage());
        }
    }

    public void registerUser(String userName, String userPassword, String userEmail) throws BusinessException{
        userDAO.registerUser(userName, userPassword, userEmail);
    }

    public boolean emailRegistered(String userEmail) throws BusinessException {
        try {
            return userDAO.emailRegistered(userEmail);
        }
        catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public boolean verifyEmailFormat(String email){
        return email.contains("@gmail.com");
    }

    public boolean verifyConfirmationPassword(String confirmationPassword,String password){
        return Objects.equals(confirmationPassword, password);
    }

    public void logOut() {
        user = null;
        //TODO supongo borrar y guardar partida
    }

    public void deleteAccount() {
        userDAO.deleteUser(user.getUsername());
        user = null;
        //TODO supongo que borrar partidas y estadisticas usuario
    }

    public EntityUser getUser() {
        return user;
    }
}