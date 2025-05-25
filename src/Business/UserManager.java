package Business;

import Business.Entities.EntityUser;
import Persistance.PersistanceException;
import Persistance.UserDAO;
import Persistance.sql.SQLUserDAO;

import java.util.ArrayList;
import java.util.Objects;

/**
 * UserManager handles all business logic related to user operations.
 * It acts as an intermediary between the presentation layer and the {@link UserDAO} persistence interface.
 * This includes login verification, registration, account deletion, and user session management.
 */
public class UserManager {
    private EntityUser user;
    private final UserDAO userDAO;

    /**
     * Constructs a UserManager and initializes the persistence implementation.
     */
    public UserManager() {
        this.userDAO = new SQLUserDAO();
    }

    /**
     * Verifies the login credentials for a user.
     * @param userName the username or email to verify
     * @param userPassword the password to verify
     * @return true if the credentials are valid, false otherwise
     * @throws BusinessException if a persistence error occurs
     */
    public boolean checkPassword(String userName, String userPassword) throws BusinessException{
        try {
            return userDAO.verifyPassword(userName, userPassword);
        }catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Checks whether a user is already registered in the system.
     * @param userName the username to check
     * @return true if the user is registered, false otherwise
     * @throws BusinessException if a persistence error occurs
     */
    public boolean checkUserRegistered(String userName) throws BusinessException{
        try {
            return userDAO.usernameRegistered(userName);
        }catch(PersistanceException e){
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Loads the user data for a given username or email and sets it as the current user.
     * @param userName the username or email to load
     * @throws BusinessException if the user cannot be retrieved
     */
    public void setUser(String userName) throws BusinessException {
        try {
            user = userDAO.getUserFromIdentifier(userName);
        }catch(PersistanceException e){
           throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Registers a new user in the system.
     * @param userName the new user's username
     * @param userPassword the user's password
     * @param userEmail the user's email address
     * @throws BusinessException if registration fails
     */
    public void registerUser(String userName, String userPassword, String userEmail) throws BusinessException{
        try {
            userDAO.registerUser(userName, userPassword, userEmail);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Checks if the provided email is already registered in the system.
     * @param userEmail the email to check
     * @return true if the email is registered, false otherwise
     * @throws BusinessException if a persistence error occurs
     */
    public boolean emailRegistered(String userEmail) throws BusinessException {
        try {
            return userDAO.emailRegistered(userEmail);
        }
        catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Logs out the current user by clearing the session.
     */
    public void logOut() {
        user = null;
    }

    /**
     * Deletes the account of the currently logged-in user.
     * @throws BusinessException if the deletion fails
     */
    public void deleteAccount() throws BusinessException {
        try {
            userDAO.deleteUser(user.getUsername());
            user = null;
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of all usernames currently registered in the system.
     * @return a list of usernames
     * @throws BusinessException if the retrieval fails
     */
    public ArrayList<String> getAllUsernames() throws BusinessException {
        try {
            return userDAO.returnAllUsernamesRegistered();
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Returns the username of the currently logged-in user.
     * @return the current user's username
     */
    public String getCurrentUser() {
        return user.getUsername();
    }
}