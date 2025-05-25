package Persistance.sql;

import Business.Entities.EntityGame;
import Persistance.GameDAO;
import Persistance.PersistanceException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLGameDAO is the SQL implementation of the {@link Persistance.GameDAO} interface.
 * It handles the persistence and retrieval of game data using SQL queries executed
 * via the {@link SQLConnector} singleton.
 */
public class SQLGameDAO implements GameDAO {
    /**
     * Constructs a new SQLGameDAO instance.
     */
    public SQLGameDAO() {};

    /**
     * Loads a game from the database by its ID.
     * @param IDgame the unique identifier of the game
     * @return the {@link EntityGame} with the corresponding data, or null if not found
     * @throws PersistanceException if the game cannot be retrieved
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
     * Loads a game by name and associated user.
     * @param name the name of the game
     * @param userName the owner of the game
     * @return the {@link EntityGame} with the corresponding data, or null if not found
     * @throws PersistanceException if the game cannot be retrieved
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
     * Inserts a new game record into the database.
     * @param game the game entity to be saved
     * @throws PersistanceException if the insertion fails
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
     * Deletes a game by its ID.
     * @param IDgame the game ID
     * @throws PersistanceException if the deletion fails
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
     * Deletes a game by its name and associated user.
     * @param name the name of the game
     * @param userName the user who owns the game
     * @throws PersistanceException if the deletion fails
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

    /**
     * Deletes all games owned by the specified user.
     * @param username the user whose games will be deleted
     * @throws PersistanceException if the deletion fails
     */
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
     * Retrieves the ID of a game given its name and owner.
     * @param name the name of the game
     * @param userName the username of the owner
     * @return the ID of the game
     * @throws PersistanceException if the query fails
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
     * Gets the level of clicker upgrades for a given game.
     * @param ID_game the game ID
     * @return the clicker upgrade level
     * @throws PersistanceException if the query fails
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
     * Gets the name of the game given its ID.
     * @param ID_game the game ID
     * @return the name of the game
     * @throws PersistanceException if the query fails
     */
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

    /**
     * Marks a game as finished in the database.
     * @param username the user who owns the game
     * @param gameName the name of the game to mark
     * @throws PersistanceException if the update fails
     */
    @Override
    public void setFinished(String username, String gameName) throws PersistanceException {
        try {
            String query = "UPDATE game SET Finished = 1 WHERE (Name_Game = '" + gameName + "'AND username = '" + username + "');";
            SQLConnector.getInstance().insertQuery(query);
        } catch (PersistanceException e) {
            throw new PersistanceException("Couldn't find game name in the database");
        }

    }

    /**
     * Retrieves all games owned by a specific user.
     * @param user the username
     * @return a list of {@link EntityGame} instances
     * @throws PersistanceException if the query fails
     */
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

    /**
     * Gets the names of all finished games owned by a user.
     * @param user the username
     * @return a list of finished game names
     * @throws PersistanceException if the query fails
     */
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

    /**
     * Gets the IDs of all finished games owned by a user.
     * @param user the username
     * @return a list of finished game IDs
     * @throws PersistanceException if the query fails
     */
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

    /**
     * Updates an existing game's information in the database.
     * @param game the game to update
     * @throws PersistanceException if the update fails
     */
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
