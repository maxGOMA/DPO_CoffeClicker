package Persistance.sql;

import Persistance.PersistanceException;
import Persistance.StatsDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * SQLStatsDAO is the SQL implementation of the {@link Persistance.StatsDAO} interface.
 * It manages the persistence and retrieval of gameplay statistics (e.g., number of coffees over time)
 * using a relational database through {@link SQLConnector}.
 */
public class SQLStatsDAO implements StatsDAO {

    /**
     * Constructs a new SQLStatsDAO instance.
     */
    public SQLStatsDAO() {};

    /**
     * Persists a new statistics record for the specified game and minute.
     * @param ID_game the ID of the game to associate the statistic with
     * @param numCoffees the number of coffees recorded at the given minute
     * @param minutesPlayed the current minute of gameplay to record
     * @throws PersistanceException if an error occurs while saving to the database
     */
    public void saveNewStats(int ID_game, double numCoffees, int minutesPlayed) throws PersistanceException{
        try {
            String query = "INSERT INTO stats (ID_Game, Minute, Num_Coffees) VALUES ('" +
                    ID_game + "', '" +
                    minutesPlayed + "', '" +
                    numCoffees + "');";
            SQLConnector.getInstance().insertQuery(query);
        } catch (PersistanceException e) {
            throw new PersistanceException("Couldn't save stats in database.");
        }
    }

    /**
     * Retrieves all recorded statistics for a given game up to the specified minute.
     * @param ID_game the ID of the game
     * @param minutesPlayed the total number of minutes played
     * @return a list of doubles representing coffees recorded at each minute,
     *         or null if any data point is missing
     * @throws PersistanceException if an error occurs during retrieval
     */
    public ArrayList<Double> getAllStatsFromGame(int ID_game, int minutesPlayed) throws PersistanceException {
        try {
            ArrayList<Double> minutesList = new ArrayList<Double>();

            for(int i = 1; i <= minutesPlayed; i++){
                String query = "SELECT * FROM stats WHERE (ID_Game = '" + ID_game + "'AND Minute = '" + i + "');";
                ResultSet rs = SQLConnector.getInstance().selectQuery(query);
                if(!rs.next()){
                    return null;
                }
                minutesList.add(rs.getDouble("Num_Coffees"));
            }
            return minutesList;
        } catch (SQLException|PersistanceException e) {
            throw new PersistanceException("Couldn't find stats in the database");
        }

    }

    /**
     * Deletes all statistics entries associated with a specific game.
     * @param ID_game the ID of the game whose stats should be deleted
     * @throws PersistanceException if deletion fails
     */
    public void deleteStats(int ID_game) throws PersistanceException {
        try {
            String query = "DELETE FROM stats WHERE (ID_Game = '" + ID_game + "');";
            SQLConnector.getInstance().deleteQuery(query);
        } catch (PersistanceException e) {
            throw  new PersistanceException("Can't delete stats from database.");
        }
    }

}
