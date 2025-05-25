package Business.Entities;

/**
 * EntityUser represents a user within the CoffeeClicker system.
 * It stores basic credentials and identity information including username, password, and email.
 * This class is typically used for authentication and user management.
 */
public class EntityUser {
    private final String username;
    private final String password;
    private final String email;

    /**
     * Constructs an EntityUser with the given credentials.
     * @param username the user's username
     * @param password the user's password
     * @param email the user's email
     */
    public EntityUser(String username,String password,String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Returns the username of the user.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the email of the user.
     * @return the email
     */
    public String getEmail() {
        return email;
    }
}
