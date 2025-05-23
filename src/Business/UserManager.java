package Business;

import Business.Entities.EntityUser;
import Persistance.PersistanceException;
import Persistance.UserDAO;
import Persistance.sql.SQLUserDAO;

import java.util.ArrayList;
import java.util.Objects;

public class UserManager {
    private EntityUser user;
    private final UserDAO userDAO;

    public UserManager() {
        this.userDAO = new SQLUserDAO();
    }

    public boolean checkPassword(String userName, String userPassword) throws BusinessException{
        try {
            return userDAO.verifyPassword(userName, userPassword);
        }catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public boolean checkUserRegistered(String userName) throws BusinessException{
        try {
            return userDAO.usernameRegistered(userName);
        }catch(PersistanceException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void setUser(String userName) throws BusinessException {
        try {
            user = userDAO.getUserFromIdentifier(userName);
        }catch(PersistanceException e){
           throw new BusinessException(e.getMessage());
        }
    }

    public void registerUser(String userName, String userPassword, String userEmail) throws BusinessException{
        try {
            userDAO.registerUser(userName, userPassword, userEmail);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public boolean emailRegistered(String userEmail) throws BusinessException {
        try {
            return userDAO.emailRegistered(userEmail);
        }
        catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void logOut() {
        user = null;
    }

    public void deleteAccount() throws BusinessException {
        try {
            userDAO.deleteUser(user.getUsername());
            user = null;
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public ArrayList<String> getAllUsernames() throws BusinessException {
        try {
            return userDAO.returnAllUsernamesRegistered();
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public String getCurrentUser() {
        return user.getUsername();
    }
}