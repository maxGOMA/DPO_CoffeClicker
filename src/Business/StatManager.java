package Business;

import Business.Entities.EntityGame;
import Business.Entities.EntityStatisticsGenerator;
import Persistance.PersistanceException;
import Persistance.StatsDAO;
import Persistance.sql.SQLStatsDAO;

import java.util.ArrayList;
import java.util.List;


public class StatManager {
    private final StatsDAO statDAO;
    private EntityStatisticsGenerator statsGenerator;


    public StatManager(){
        this.statDAO = new SQLStatsDAO();
    }

    public void initiateStatsGeneration(EntityGame game) {
        statsGenerator = new EntityStatisticsGenerator(this); // o la subclase adecuada
        statsGenerator.activeStaticsGeneratorForGame(game);
        statsGenerator.start();
    }

    public void stopStatsGeneration() {
        statsGenerator.desactivateStaticsGenerator();
    }

    public synchronized void  saveCurrentStats(EntityGame game){
        statDAO.saveNewStats(game.getID_Game(), game.getCurrentNumberOfCoffees(), game.getMinutesPlayed());
    }

    public ArrayList<Double> getAllStatsFromGame(EntityGame game) throws BusinessException{
        try{
            if(game.getMinutesPlayed() == 0){
                return null;
            }
            return statDAO.getAllStatsFromGame(game.getID_Game(), game.getMinutesPlayed());
        }
        catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }
    }


    public void deleteStats(EntityGame game) {
        statDAO.deleteStats(game.getID_Game());
    }

}
