package Persistance.sql;

import Business.Entities.EntityGame;
import Persistance.GameDAO;
import Persistance.PersistanceException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLGameDAO implements GameDAO {

    public SQLGameDAO() {};

    /**
     * Carga la información de una partida desde la base de datos.
     *
     * @param IDgame Identificador único de la partida.
     * @return Un objeto {@link EntityGame} con los datos de la partida.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public EntityGame loadInfoGame(int IDgame) throws PersistanceException {
        try {
            String query = "SELECT * FROM game WHERE ID_game = " + IDgame + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            if(!rs.next()){
                return null;
            }
            return new EntityGame(rs.getString("Name_Game"), rs.getInt("Num_Beans_Generators"), rs.getInt("Upgrade_Clicker"), rs.getInt("Upgrade_TakeAway_Generators")
                    , rs.getInt("Upgrade_CoffeeMakers_Generators"), rs.getInt("Upgrade_Beans_Generators"), rs.getInt("Num_TakeAway_Generators")
                    , rs.getInt("Num_CoffeeMakers_Generators"), rs.getDouble("Num_Coffees"), rs.getString("username"), rs.getInt("ID_Game"),rs.getInt("Mins_Played"), rs.getInt("Finished"));
        } catch (SQLException | PersistanceException e) {
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
            return new EntityGame(rs.getString("Name_Game"), rs.getInt("Num_Beans_Generators"), rs.getInt("Upgrade_Clicker"), rs.getInt("Upgrade_TakeAway_Generators")
                    , rs.getInt("Upgrade_CoffeeMakers_Generators"), rs.getInt("Upgrade_Beans_Generators"), rs.getInt("Num_TakeAway_Generators")
                    , rs.getInt("Num_CoffeeMakers_Generators"), rs.getDouble("Num_Coffees"), rs.getString("username"), rs.getInt("ID_Game"),rs.getInt("Mins_Played"), rs.getInt("Finished"));
        } catch (SQLException | PersistanceException e) {
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
    public void setInfoGame(EntityGame game) throws PersistanceException {
        try {
            String query = "INSERT INTO game (Name_Game, username, Num_Coffees, Num_Beans_Generators, Num_CoffeeMakers_Generators, Num_TakeAway_Generators, Upgrade_Beans_Generators, Upgrade_CoffeeMakers_Generators, Upgrade_TakeAway_Generators, Upgrade_Clicker, Mins_Played) VALUES ('" +
                    game.getName() + "', '" +
                    game.getUsername()+ "', '" +
                    game.getCurrentNumberOfCoffees() + "', '" +
                    game.getNumGenerators("beans") + "', '" +
                    game.getNumGenerators("coffeeMaker") + "', '" +
                    game.getNumGenerators("TakeAway") + "', '" +
                    game.getUpgradeGenerators("beans") + "', '" +
                    game.getUpgradeGenerators("coffeeMaker") + "', '" +
                    game.getUpgradeGenerators("TakeAway") + "', '" +
                    game.getClickerLevelUpgrade() +
                    game.getClickerLevelUpgrade() + "', '" +
                    game.getMinutesPlayed() +
                    "');";
            SQLConnector.getInstance().insertQuery(query);
        } catch (PersistanceException e) {
            throw new PersistanceException("Couldn't find game in the database");
        }
    }

    /**
     * Elimina un juego de la base de datos utilizando su identificador único.
     *
     * Este método construye una consulta SQL DELETE y la ejecuta mediante el conector SQL.
     *
     * @param IDgame El identificador único del juego que se desea eliminar de la base de datos.
     */
    @Override
    public void deleteGame(int IDgame) throws PersistanceException {
        try {
            String query = "DELETE FROM game WHERE ID_game = " + IDgame + ";";
            SQLConnector.getInstance().deleteQuery(query);
        } catch (PersistanceException ex) {
            throw new PersistanceException("Couldn't find game in the database");
        }

    }

    /**
     * Elimina un juego del sistema según su identificador único.
     *
     * @param name El nombre del juego que se desea eliminar.
     * @param userName El nombre del jugador al que pertenece el juego.
     */
    @Override
    public void deleteGame(String name, String userName) throws PersistanceException {
        try {
            String query = "DELETE FROM game WHERE (Name_Game = '" + name + "'AND username = '" + userName + "');";
            SQLConnector.getInstance().deleteQuery(query);
        } catch (PersistanceException e) {
            throw new PersistanceException("Couldn't find game in the database");
        }
    }

    @Override
    public void deleteAllGamesByUser(String username) throws PersistanceException {
        try {
            String query = "DELETE FROM game WHERE (username = '" + username + "');";
            SQLConnector.getInstance().deleteQuery(query);
        } catch (PersistanceException e) {
            throw new PersistanceException("Couldn't find game in the database");
        }
    }

    /**
     * Obtiene el identificador de una partida desde la base de datos.
     *
     * @param name El nombre del juego que se desea eliminar.
     * @param userName El nombre del jugador al que pertenece el juego.
     * @return El identificador de la partida.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public int getIdGame(String name, String userName) throws PersistanceException {
        try {
            String query = "SELECT ID_Game FROM game WHERE (Name_Game = '" + name + "'AND username = '" + userName + "');";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getInt("ID_Game");
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Couldn't find game id in the database");
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

    @Override
    public String getName(int ID_game) throws PersistanceException {
        try {
            String query = "SELECT Name_Game FROM game WHERE ID_game = " + ID_game + ";";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return rs.getString("Name_Game");
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Couldn't find game name in the database");
        }
    }

    @Override
    public void setFinished(String username, String gameName) throws PersistanceException {
        try {
            String query = "UPDATE game SET Finished = 1 WHERE (Name_Game = '" + gameName + "'AND username = '" + username + "');";
            SQLConnector.getInstance().insertQuery(query);
        } catch (PersistanceException e) {
            throw new PersistanceException("Couldn't find game name in the database");
        }

    }

    @Override
    public List<EntityGame> getGamesByUser(String user) throws PersistanceException {
        try {
            List<EntityGame> games = new ArrayList<>();
            String query = "SELECT * FROM game WHERE username = '" + user + "';";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            while (rs.next()) {
                games.add( new EntityGame(rs.getString("Name_Game"), rs.getInt("Num_Beans_Generators"), rs.getInt("Upgrade_Clicker"), rs.getInt("Upgrade_TakeAway_Generators")
                        , rs.getInt("Upgrade_CoffeeMakers_Generators"), rs.getInt("Upgrade_Beans_Generators"), rs.getInt("Num_TakeAway_Generators")
                        , rs.getInt("Num_CoffeeMakers_Generators"), rs.getDouble("Num_Coffees"), rs.getString("username"), rs.getInt("ID_Game"),rs.getInt("Mins_Played"), rs.getInt("Finished")));
            }
            return games;
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Couldn't find games in the database");
        }
    }

    @Override
    public ArrayList<String> getUserFinishedGameNames(String user) throws PersistanceException {
        ArrayList<String> games = new ArrayList<>();
        try {
            String query = "SELECT * FROM game WHERE username = '" + user + "' AND Finished = 1;";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            while (rs.next()) {
                games.add(rs.getString("Name_Game"));
            }
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Can't access games information");
        }
        return games;
    }

    @Override
    public ArrayList<Integer> getUserFinishedGameIDs(String user) throws PersistanceException {
        ArrayList<Integer> gamesIds = new ArrayList<>();
        try {
            String query = "SELECT * FROM game WHERE username = '" + user + "' AND Finished = 1;";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            while (rs.next()) {
                gamesIds.add(rs.getInt("Game_ID"));
            }
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Can't access games information");
        }
        return gamesIds;
    }

    @Override
    public void updateGame(EntityGame game) throws PersistanceException {
        try {
            String query = "UPDATE game SET Num_Coffees = " + game.getCurrentNumberOfCoffees() +
                    ", Num_Beans_Generators = " + game.getNumGenerators("beans") +
                    ", Num_CoffeeMakers_Generators = " + game.getNumGenerators("coffeeMaker") +
                    ", Num_TakeAway_Generators = " + game.getNumGenerators("TakeAway") +
                    ", Upgrade_Beans_Generators = " + game.getUpgradeGenerators("beans") +
                    ", Upgrade_CoffeeMakers_Generators = " + game.getUpgradeGenerators("coffeeMaker") +
                    ", Upgrade_TakeAway_Generators = " + game.getUpgradeGenerators("TakeAway") +
                    ", Upgrade_Clicker = " + game.getClickerLevelUpgrade() +
                    ", Mins_Played = " + game.getMinutesPlayed() +
                    " WHERE Name_Game = '" + game.getName() + "';";
            SQLConnector.getInstance().insertQuery(query);
        } catch (PersistanceException e) {
            throw new PersistanceException("Can't access games information");
        }

    }
}
