package Business;

import Business.Entities.EntityGame;
import Business.Entities.EntityStatisticsGenerator;
import Persistance.GameDAO;
import Persistance.PersistanceException;
import Persistance.StatsDAO;
import Persistance.sql.SQLGameDAO;
import Persistance.sql.SQLStatsDAO;

import java.util.ArrayList;


public class StatManager {
    private final StatsDAO statDAO;
    private final GameDAO gameDAO;
    private EntityStatisticsGenerator statsGenerator;

    public StatManager(){
        this.statDAO = new SQLStatsDAO();
        this.gameDAO = new SQLGameDAO();
    }

    public void initiateStatsGeneration(EntityGame game) {
        statsGenerator = new EntityStatisticsGenerator(this); // o la subclase adecuada
        statsGenerator.activeStaticsGeneratorForGame(game);
        statsGenerator.start();
    }

    public void stopStatsGeneration() {
        statsGenerator.desactivateStaticsGenerator();
    }

    public synchronized void saveCurrentStats (EntityGame game) {
        statDAO.saveNewStats(game.getIDGame(), game.getCurrentNumberOfCoffees(), game.getMinutesPlayed());
        gameDAO.updateGame(game);
    }

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

    public void deleteAllStatsFromUser(ArrayList<Integer> gamesID) {
        for (Integer gameID: gamesID) {
            statDAO.deleteStats(gameID);
        }
    }


    public void deleteStatsFromGame(int gameID) {
        statDAO.deleteStats(gameID);
    }

}
