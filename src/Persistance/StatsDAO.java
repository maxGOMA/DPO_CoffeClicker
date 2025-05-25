package Persistance;

import java.util.ArrayList;

/**
 * StatsDAO defines the interface for accessing and managing statistical data
 * related to games in the persistence layer. Implementations of this interface
 * provide methods to read, write, and delete game-related statistics from storage.
 */
public interface StatsDAO {

    /**
     * Retrieves all stored statistics for a specific game.
     *
     * @param ID_game the ID of the game to retrieve statistics for
     * @param minutesPlayed the total minutes played to filter or limit stats
     * @return a list of Double values representing collected statistics
     * @throws PersistanceException if an error occurs during data access
     */
    ArrayList<Double> getAllStatsFromGame(int ID_game, int minutesPlayed)  throws PersistanceException;

    /**
     * Saves a new statistics entry for a specific game.
     * @param ID_game the ID of the game to associate the stat with
     * @param numCoffees the number of coffees recorded at this point in time
     * @param minutesPlayed the number of minutes played at this point
     * @throws PersistanceException if the data cannot be saved
     */
    void saveNewStats(int ID_game, double numCoffees, int minutesPlayed) throws PersistanceException;

    /**
     * Deletes all statistics associated with a specific game.
     * @param ID_game the ID of the game whose stats are to be deleted
     * @throws PersistanceException if the deletion fails
     */
    void deleteStats(int ID_game) throws PersistanceException;

}

