package Persistance.sql;

import Business.Entities.EntityUser;
import Persistance.PersistanceException;
import Persistance.UserDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {

    /**
     * Method that saves a user in the system, persisting it.
     *
     * @param username The new user_name to save.
     * @param password The password from the user to save.
     * @param email The email from the user to save.
     */
    @Override
    public void registerUser(String username, String password, String email) {
        String query = "INSERT INTO user (username, email, password) VALUES ('" +
                username + "', '" +
                email + "', '" +
                password +
                "');";

        SQLConnector.getInstance().insertQuery(query);
    }

    /**
     * Method that reads the persisted information, returning all stored courses.
     *
     * @param username user_name from the user to delete.
     */
    @Override
    public void deleteUser(String username) {
        String query = "DELETE FROM user WHERE username = '" + username + "';";
        SQLConnector.getInstance().deleteQuery(query);
    }

    /**
     *  Method that returns if the user_name is registrated.
     *
     *  @param username user_name to check if its registrated.
     *  @return Returns true if the user_name is registered in the database.
     */
    @Override
    public boolean usernameRegistered(String username) throws PersistanceException {
        try {
            String query = "SELECT * FROM user WHERE username = '" + username + "';";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            return rs.next();
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't validate username");
        }
    }

    /**
     *  Method that returns if the email is registrated.
     *
     *  @param email email to check if its registrated.
     *  @return Returns true if the email is registered in the database.
     */
    @Override
    public boolean emailRegistered(String email) throws PersistanceException {
        try {
            String query = "SELECT * FROM user WHERE email = '" + email + "';";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            return rs.next();
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't validate email");
        }
    }


    /**
     *  Method that returns if the email and password introduced are valid.
     *
     *  @param username username form the user (already verifyed with usernameRegistered).
     *  @param password password to check.
     *  @return Returns true if the username,password are correct.
     */
    @Override
    public boolean verifyPassword(String username, String password) throws PersistanceException {
        try {
            String query = "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "';";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            return rs.next();
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't verify password");
        }
    }

    /**
     *  Method that returns the user from the database (class User).
     *
     *  @param username username form the user to access.
     *  @return User from the database with user_name introduced.
     */
    @Override
    public EntityUser getUserFromusername(String username) throws PersistanceException {
        try {
            String query = "SELECT * FROM user WHERE username = '" + username + "';";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return new EntityUser(rs.getString("username"), rs.getString("email"), rs.getString("password"));
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find user in the database");
        }

    }
}



