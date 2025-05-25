package Persistance.sql;

import Business.Entities.EntityUser;
import Persistance.PersistanceException;
import Persistance.UserDAO;
import Presentation.Views.PopUpErrorView;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * SQLUserDAO is the SQL implementation of the {@link Persistance.UserDAO} interface.
 * It provides all persistence logic to manage users in the CoffeeClicker system using a relational database.
 * Queries are executed via the {@link Persistance.sql.SQLConnector} singleton.
 */
public class SQLUserDAO implements UserDAO {
    public SQLUserDAO() {};

    /**
     * Method that saves a user in the system, persisting it.
     * @param username The new user_name to save.
     * @param password The password from the user to save.
     * @param email The email from the user to save.
     */
    @Override
    public void registerUser(String username, String password, String email) throws PersistanceException{
        try {
            String query = "INSERT INTO users (username, email, password) VALUES ('" +
                    username + "', '" +
                    email + "', '" +
                    password +
                    "');";

            SQLConnector.getInstance().insertQuery(query);
        } catch (PersistanceException e) {
            throw new PersistanceException(e.getMessage());
        }
    }

    /**
     * Method that reads the persisted information, returning all stored courses.
     * @param username user_name from the user to delete.
     */
    @Override
    public void deleteUser(String username) throws PersistanceException {
        try {
            String query = "DELETE FROM users WHERE username = '" + username + "';";
            SQLConnector.getInstance().deleteQuery(query);
        } catch (PersistanceException e) {
            throw new PersistanceException(e.getMessage());
        }
    }

    /**
     *  Method that returns if the user_name is registrated.
     *  @param username user_name to check if its registrated.
     *  @return Returns true if the user_name is registered in the database.
     */
    @Override
    public boolean usernameRegistered(String username) throws PersistanceException {
        try {
            String query = "SELECT * FROM users WHERE username = '" + username + "';";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            return rs.next();
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Couldn't validate username");
        }
    }

    /**
     *  Method that returns if the email is registrated.
     *  @param email email to check if its registrated.
     *  @return Returns true if the email is registered in the database.
     */
    @Override
    public boolean emailRegistered(String email) throws PersistanceException {
        try {
            String query = "SELECT * FROM users WHERE email = '" + email + "';";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            return rs.next();
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Couldn't validate email");
        }
    }


    /**
     *  Method that returns if the email and password introduced are valid.
     *  @param userIdentifier username or email from the user (already verifyed with usernameRegistered or emailRegistered).
     *  @param password password to check.
     *  @return Returns true if the username,password are correct.
     */
    @Override
    public boolean verifyPassword(String userIdentifier, String password) throws PersistanceException {
        try {
            String query = "SELECT * FROM users WHERE (username = '" + userIdentifier + "' OR email = '" + userIdentifier + "' ) AND password = '" + password + "';";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            return rs.next();
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Couldn't verify password");
        }
    }

    /**
     * Method that returns all the usernames registered in the system.
     * @return Returns a list with the usernames.
     */
    @Override
    public ArrayList<String> returnAllUsernamesRegistered() throws PersistanceException {
        ArrayList<String> usernames = new ArrayList<>();
        try {
            String query = "SELECT * FROM users";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Can't access users information");
        }
        return usernames;
    }

    /**
     *  Method that returns the user from the database (class User).
     *  @param usernameOrEmail username form the user to access.
     *  @return User from the database with user_name introduced.
     */
    @Override
    public EntityUser getUserFromIdentifier(String usernameOrEmail) throws PersistanceException {
        try {
            try{
                String query = "SELECT * FROM users WHERE (username = '" + usernameOrEmail + "' OR email = '" + usernameOrEmail + "');";
                ResultSet rs = SQLConnector.getInstance().selectQuery(query);
                rs.next();
                return new EntityUser(rs.getString("username"), rs.getString("email"), rs.getString("password"));
            }catch(NullPointerException e){
                throw new PersistanceException("Unable to connect to the database");
            }
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Couldn't find user in the database");
        }
    }
}



