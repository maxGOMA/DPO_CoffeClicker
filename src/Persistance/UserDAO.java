package Persistance;

//DAO_USERS - Persistance
//Guardaremos en estructuras "USER": user_name, password, email

import Business.Entities.EntityUser;

/**
 * Interface that abstracts the persistence of groups from the rest of the code.
 * In particular, it follows the Data Access Object design pattern, which is commonly used to abstract persistence
 * implementations with a set of generic operations.
 */
public interface UserDAO {

    /**
     * Method that saves a user in the system, persisting it.
     *
     * @param userName The new user_name to save.
     * @param password The password from the user to save.
     * @param email The email from the user to save.
     */
    void registerUser(String userName, String password, String email);

    /**
     * Method that reads the persisted information, returning all stored courses.
     *
     * @param userName user_name from the user to delete.
     */
    void deleteUser(String userName);

    /**
     *  Method that returns if the user_name is registrated.
     *
     *  @param userName user_name to check if its registrated.
     *  @return Returns true if the user_name is registered in the database.
     */
    boolean userNameRegistered(String userName);

    /**
     *  Method that returns if the email is registrated.
     *
     *  @param email email to check if its registrated.
     *  @return Returns true if the email is registered in the database.
     */
    boolean emailRegistered(String email);


    /**
     *  Method that returns if the email and password introduced are valid.
     *
     *  @param userName userName form the user (already verifyed with userNameRegistered).
     *  @param password password to check.
     *  @return Returns true if the userName,password are correct.
     */
    boolean verifyPassword(String userName, String password);

    /**
     *  Method that returns the user from the database (class User).
     *
     *  @param userName userName form the user to access.
     *  @return User from the database with user_name introduced.
     */
    EntityUser getUserFromUserName(String userName);
}




