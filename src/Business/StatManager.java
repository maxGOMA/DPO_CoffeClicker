package Business;

import Business.Entities.EntityGame;
import Business.Entities.EntityStatisticsGenerator;
import Persistance.GameDAO;
import Persistance.PersistanceException;
import Persistance.StatsDAO;
import Persistance.sql.SQLGameDAO;
import Persistance.sql.SQLStatsDAO;

import java.util.ArrayList;

/**
 * StatManager handles all business logic related to gameplay statistics.
 * It manages the creation, storage, retrieval, and deletion of game statistics,
 * and coordinates the background generation of statistics during active gameplay.
 */
public class StatManager {
    private final StatsDAO statDAO;
    private final GameDAO gameDAO;
    private EntityStatisticsGenerator statsGenerator;

    /**
     * Constructs a StatManager and initializes the DAOs for stats and games.
     */
    public StatManager(){
        this.statDAO = new SQLStatsDAO();
        this.gameDAO = new SQLGameDAO();
    }

    /**
     * Starts the statistics generation thread for the specified game.
     * This enables background tracking of metrics such as coffee count per minute.
     * @param game the game for which to start generating statistics
     */
    public void initiateStatsGeneration(EntityGame game) {
        statsGenerator = new EntityStatisticsGenerator(this); // o la subclase adecuada
        statsGenerator.activeStaticsGeneratorForGame(game);
        statsGenerator.start();
    }

    /**
     * Stops the active statistics generation thread.
     * This should be called when the game ends or is paused.
     */
    public void stopStatsGeneration() {
        statsGenerator.desactivateStaticsGenerator();
    }

    /**
     * Saves the current state of the game's statistics to the database.
     * @param game the game whose current stats should be saved
     * @throws BusinessException if a persistence error occurs
     */
    public synchronized void saveCurrentStats (EntityGame game) throws BusinessException {
        try {
            statDAO.saveNewStats(game.getIDGame(), game.getCurrentNumberOfCoffees(), game.getMinutesPlayed());
            gameDAO.updateGame(game);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Retrieves all recorded statistics for the given game.
     * @param game the game to retrieve statistics for
     * @return a list of coffee counts recorded over time, or null if the game has no data
     * @throws BusinessException if a persistence error occurs
     */
    public ArrayList<Double> getAllStatsFromGame(EntityGame game) throws BusinessException{
        try{
            if(game.getMinutesPlayed() == 0){
                return null;
            }
            return statDAO.getAllStatsFromGame(game.getIDGame(), game.getMinutesPlayed());
        }
        catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Deletes all statistics associated with a list of game IDs.
     * @param gamesID a list of game IDs whose statistics should be removed
     * @throws BusinessException if a persistence error occurs
     */
    public void deleteAllStatsFromUser(ArrayList<Integer> gamesID) throws BusinessException{
        try {
            for (Integer gameID : gamesID) {
                statDAO.deleteStats(gameID);
            }
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Deletes all statistics associated with a specific game ID.
     *
     * @param gameID the ID of the game whose statistics should be deleted
     * @throws BusinessException if a persistence error occurs
     */
    public void deleteStatsFromGame(int gameID) throws BusinessException{
        try {
            statDAO.deleteStats(gameID);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
