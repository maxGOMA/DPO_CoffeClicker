package Persistance.sql;

import Business.Entities.EntityGame;
import Persistance.GameDAO;
import Persistance.PersistanceException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            if(!rs.next()){
                return null;
            }
            return new EntityGame(rs.getString("Name_Game"), rs.getInt("Gold"), rs.getInt("Upgrade_Clicker"), rs.getInt("Upgrade_Supreme")
                    , rs.getInt("Upgrade_Deluxe"), rs.getInt("Upgrade_Gold"), rs.getInt("Supreme")
                    , rs.getInt("Deluxe"), rs.getDouble("Num_Coffees"), rs.getString("username"), rs.getInt("ID_Game"));
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game in the database");
        }
    }

    /**
     * Carga la información de un juego desde la base de datos utilizando su nombre.
     *
     * @param name Nombre del juego a buscar en la base de datos.
     * @param userName Nombre del usuario al que pertenece el juego.
     * @return Un objeto {@link EntityGame} con los datos del juego.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public EntityGame loadInfoGame(String name, String userName) throws PersistanceException {
        try {
            String query = "SELECT * FROM game WHERE (Name_Game = '" + name + "'AND username = '" + userName + "');";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            if(!rs.next()){
                return null;
            }
            return new EntityGame(rs.getString("Name_Game"), rs.getInt("Gold"), rs.getInt("Upgrade_Clicker"), rs.getInt("Upgrade_Supreme")
                    , rs.getInt("Upgrade_Deluxe"), rs.getInt("Upgrade_Gold"), rs.getInt("Supreme")
                    , rs.getInt("Deluxe"), rs.getDouble("Num_Coffees"), rs.getString("username"), rs.getInt("ID_Game"));
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game in the database");
        }
    }

    /**
     * Inserta la información de un juego en la base de datos.
     *
     * @param game Objeto de tipo EntityGame que contiene la información del juego a insertar.
     * @throws PersistanceException Si ocurre un error al ejecutar la consulta en la base de datos.
     */
    @Override
    public void setInfoGame(EntityGame game){
        String query = "INSERT INTO game (Name_Game, username, Num_Coffees, Gold, Deluxe, Supreme, Upgrade_Gold, Upgrade_Deluxe, Upgrade_Supreme, Upgrade_Clicker) VALUES ('" +
                game.getName() + "', '" +
                game.getUsername()+ "', '" +
                game.getCurrentNumberOfCoffees() + "', '" +
                game.getNumGoldGenerators()+ "', '" +
                game.getNumDeluxeGenerators()+ "', '" +
                game.getNumSupremeGenerators()+ "', '" +
                game.getUpgradeGold()+ "', '" +
                game.getUpgradeDeluxe()+ "', '" +
                game.getUpgradeSupreme()+ "', '" +
                game.getClickerLevelUpgrade() +
                "');";
        SQLConnector.getInstance().insertQuery(query);
    }

    /**
     * Elimina un juego de la base de datos utilizando su identificador único.
     *
     * Este método construye una consulta SQL DELETE y la ejecuta mediante el conector SQL.
     *
     * @param ID_game El identificador único del juego que se desea eliminar de la base de datos.
     */
    public void deleteGame(int ID_game){
        String query = "DELETE FROM game WHERE ID_game = " + ID_game + ";";
        SQLConnector.getInstance().deleteQuery(query);
    }

    /**
     * Elimina un juego del sistema según su identificador único.
     *
     * @param name El nombre del juego que se desea eliminar.
     * @param userName El nombre del jugador al que pertenece el juego.
     */
    public void deleteGame(String name, String userName) {
        String query = "DELETE FROM game WHERE (Name_Game = '" + name + "'AND username = '" + userName + "');";
        SQLConnector.getInstance().deleteQuery(query);
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

    @Override
    public String getName(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT Name_Game FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getString("Name_Game");
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find game name in the database");
        }
    }

    public List<EntityGame> getGamesByUser(String user) throws PersistanceException {
        try {
            List<EntityGame> games = new ArrayList<>();
            String query = "SELECT * FROM game WHERE username = '" + user + "';";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            while (rs.next()) {
                games.add(new EntityGame(
                        rs.getString("name"),
                        rs.getInt("Gold"),
                        rs.getInt("Upgrade_Clicker"),
                        rs.getInt("Upgrade_Supreme"),
                        rs.getInt("Upgrade_Deluxe"),
                        rs.getInt("Upgrade_Gold"),
                        rs.getInt("Supreme"),
                        rs.getInt("Deluxe"),
                        rs.getDouble("Num_Coffees"),
                        rs.getString("username"),
                        rs.getInt("ID_Game")
                ));
            }

            return games;
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find games in the database");
        }
    }
}