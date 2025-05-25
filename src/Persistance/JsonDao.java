package Persistance;


import Business.Entities.EntityGame;

/**
 * JsonDao defines the interface for loading and retrieving configuration
 * data from a JSON source. This typically includes database connection parameters
 * such as username, password, host, port, and database name.
 */
public interface JsonDao {

    /**
     * Loads the configuration information from the JSON file.
     * This should be called before accessing any other getter methods.
     */
    void loadInfo();

    /**
     * Retrieves the configured username from the JSON.
     * @return the username as a String
     */
    String getUsername();

    /**
     * Retrieves the configured password from the JSON.
     * @return the password as a String
     */
    String getPassword();

    /**
     * Retrieves the configured database host from the JSON.
     * @return the host as a String
     */
    String getHost();

    /**
     * Retrieves the configured database port from the JSON.
     * @return the port as an int
     */
    int getPort();

    /**
     * Retrieves the configured database name from the JSON.
     * @return the database name as a String
     */
    String getDbname();
}




