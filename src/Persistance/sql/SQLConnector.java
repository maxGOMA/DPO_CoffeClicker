package Persistance.sql;

import Business.BusinessException;
import Persistance.JsonDao;
import Persistance.PersistanceException;
import Persistance.impl.JsonConfigDAO;

import java.io.FileNotFoundException;
import java.sql.*;

/**
 * The SQLConnector class will abstract the specifics of the connection to a MySQL database.
 *
 * This class follows the Singleton design pattern to facilitate outside access while maintaining
 * a single instance, as having multiple connectors to a database is generally discouraged.
 *
 * Be aware that this class presents a simplified approach. Configuration parameters SHOULD NOT be
 * hardcoded and the use of Statements COULD be replaced by PreparedStatements to avoid SQL Injection.
 */
public class SQLConnector {

    private static SQLConnector instance = null;

    /**
     * Static method that returns the shared instance managed by the singleton.
     *
     * @return The shared SQLConnector instance.
     */
    public static SQLConnector getInstance() throws PersistanceException {
        if (instance == null ){
            JsonDao jsDao = null;
            try{
                jsDao = new JsonConfigDAO();
                jsDao.loadInfo();
            }catch(FileNotFoundException e){
                System.out.println(e.getMessage());
            }
            instance = new SQLConnector(jsDao.getUsername(), jsDao.getPassword(), jsDao.getHost(), jsDao.getPort(), jsDao.getDbname());
            instance.connect();
        }
        return instance;
    }

    private final String username;
    private final String password;
    private final String url;
    private Connection conn;

    // Parametrized constructor
    private SQLConnector(String username, String password, String ip, int port, String database) {
        this.username = username;
        this.password = password;
        this.url = "jdbc:mysql://" + ip + ":" + port + "/" + database;
    }


    /**
     * Method that starts the inner connection to the database. Ideally, users would disconnect after
     * using the shared instance.
     */
    public void connect() throws PersistanceException {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch(SQLException e) {
            throw new PersistanceException("Couldln't connect to the database");
        }
    }


    /**
     * Method that executes an insertion query to the connected database.
     *
     * @param query String representation of the query to execute.
     */
    public void insertQuery(String query) throws PersistanceException {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            throw new PersistanceException("Couldln't connect to the database");
        }
    }


    /**
     * Method that executes an update query to the connected database.
     * @param query String representation of the query to execute.
     */
    public void updateQuery(String query) throws PersistanceException {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            throw new PersistanceException("Couldln't connect to the database");
        }
    }


    /**
     * Method that executes a deletion query to the connected database.
     * @param query String representation of the query to execute.
     */
    public void deleteQuery(String query) throws PersistanceException {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            throw new PersistanceException("Couldln't connect to the database");
        }

    }


    /**
     * Method that executes a selection query to the connected database.
     *
     * @param query String representation of the query to execute.
     * @return The results of the selection.
     */
    public ResultSet selectQuery(String query) throws PersistanceException {
        ResultSet rs = null;
        try {
            try{
                Statement s = conn.createStatement();
                rs = s.executeQuery(query);
            }catch(NullPointerException e) {
                throw new PersistanceException("Couldln't connect to the database");
            }
        } catch (SQLException e) {
            throw new PersistanceException("Couldln't connect to the database");
        }
        return rs;
    }


    /**
     * Method that closes the inner connection to the database. Ideally, users would disconnect after
     * using the shared instance.
     */
    public void disconnect() throws PersistanceException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new PersistanceException("Couldln't connect to the database");
        }
    }
}
