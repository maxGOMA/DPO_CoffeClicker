package Persistance.sql;

import Business.Entities.EntityGame;
import Persistance.GameDAO;
import Persistance.PersistanceException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLGameDAO implements GameDAO {

    /**
     * Carga la información de una partida desde la base de datos.
     *
     * @param ID_game Identificador único de la partida.
     * @return Un objeto {@link EntityGame} con los datos de la partida.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public EntityGame loadInfoGame(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT * FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return new EntityGame(rs.getInt("Gold"), rs.getInt("Upgrade_Clicker"), rs.getInt("Upgrade_Supreme")
                                  , rs.getInt("Upgrade_Deluxe"), rs.getInt("Upgrade_Gold"), rs.getInt("Supreme")
                                  , rs.getInt("Deluxe"), rs.getDouble("Num_Coffees"), rs.getString("username"), rs.getInt("ID_Game"));
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game in the database");
        }
    }

    /**
     * Obtiene el identificador de una partida desde la base de datos.
     *
     * @param ID_game Identificador único de la partida.
     * @return El identificador de la partida.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public int getIdGame(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT ID_Game FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getInt("ID_Game");
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game id in the database");
        }
    }

    /**
     * Obtiene el nombre de usuario asociado a una partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return El nombre de usuario como una cadena de texto.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public String getUsername(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT username FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getString("username");
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game username in the database");
        }
    }

    /**
     * Obtiene la cantidad de cafés en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de cafés como un valor de tipo {@code Double}.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public Double getNumCoffees(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT Num_Coffees FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getDouble("Num_Coffees");
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game Num_Coffees in the database");
        }
    }

    /**
     * Obtiene la cantidad de mejoras Deluxe en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Deluxe como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public int getDeluxe(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT Deluxe FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getInt("Deluxe");
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game Deluxe in the database");
        }
    }

    /**
     * Obtiene la cantidad de mejoras Supreme en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Supreme como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public int getSupreme(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT Supreme FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getInt("Supreme");
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game Supreme in the database");
        }
    }

    /**
     * Obtiene la cantidad de mejoras Gold en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Gold como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public int getUpgradeGold(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT Upgrade_Gold FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getInt("Upgrade_Gold");
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game Upgrade_Gold in the database");
        }
    }

    /**
     * Obtiene la cantidad de mejoras Deluxe en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Deluxe como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public int getUpgradeDeluxe(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT Upgrade_Deluxe FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getInt("Upgrade_Deluxe");
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game Upgrade_Deluxe in the database");
        }
    }

    /**
     * Obtiene la cantidad de mejoras Supreme en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Supreme como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public int getUpgradeSupreme(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT Upgrade_Supreme FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getInt("Upgrade_Supreme");
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game Upgrade_Supreme in the database");
        }
    }

    /**
     * Obtiene la cantidad de mejoras Clicker en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Clicker como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public int getUpgradeClicker(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT Upgrade_Clicker FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getInt("Upgrade_Clicker");
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game Upgrade_Clicker in the database");
        }
    }

    /**
     * Obtiene la cantidad de oro en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Cantidad de oro como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public int getGold(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT Gold FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getInt("Gold");
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game Gold in the database");
        }
    }

}
