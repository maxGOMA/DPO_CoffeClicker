package Persistance;

//DAO_USERS - Persistance
//Guardaremos en estructuras "USER": user_name, password, email

import Business.Entities.EntityUser;

import Persistance.PersistanceException;

/**
 * Interface that abstracts the persistence of groups from the rest of the code.
 * In particular, it follows the Data Access Object design pattern, which is commonly used to abstract persistence
 * implementations with a set of generic operations.
 */
public interface UserDAO {

    /**
     * Method that saves a user in the system, persisting it.
     *
     * @param username The new user_name to save.
     * @param password The password from the user to save.
     * @param email The email from the user to save.
     */
    void registerUser(String username, String password, String email);

    /**
     * Method that reads the persisted information, returning all stored courses.
     *
     * @param username user_name from the user to delete.
     */
    void deleteUser(String username);

    /**
     *  Method that returns if the user_name is registrated.
     *
     *  @param username user_name to check if its registrated.
     *  @return Returns true if the user_name is registered in the database.
     */
    boolean usernameRegistered(String username) throws PersistanceException;

    /**
     *  Method that returns if the email is registrated.
     *
     *  @param email email to check if its registrated.
     *  @return Returns true if the email is registered in the database.
     */
    boolean emailRegistered(String email) throws PersistanceException;

    /**
     *  Method that returns if the email and password introduced are valid.
     *
     *  @param username username form the user (already verifyed with usernameRegistered).
     *  @param password password to check.
     *  @return Returns true if the username,password are correct.
     */
    boolean verifyPassword(String username, String password) throws PersistanceException;

    /**
     *  Method that returns the user from the database (class User).
     *
     *  @param username username form the user to access.
     *  @return User from the database with user_name introduced.
     */
    EntityUser getUserFromusername(String username) throws PersistanceException;;
}




