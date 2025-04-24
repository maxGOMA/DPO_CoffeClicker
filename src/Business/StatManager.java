package Business;

import Business.Entities.EntityGame;
import Persistance.PersistanceException;
import Persistance.StatsDAO;
import Persistance.sql.SQLStatsDAO;

import java.util.List;


public class StatManager {
    private static EntityGame entityGame;
    private final StatsDAO statDAO;


    public  StatManager(){
        entityGame = null;
        this.statDAO = new SQLStatsDAO();
    }

    public void saveNewStats(){
        statDAO.saveNewStats(entityGame.getID_Game(),entityGame.getCurrentNumberOfCoffees(), entityGame.getMinutesPlayed());
    }

    public List<Double> getAllStatsFromGame() throws BusinessException{
        try{
            if(entityGame.getMinutesPlayed() == 0){
                return null;
            }
            return statDAO.getAllStatsFromGame(entityGame.getID_Game(),entityGame.getMinutesPlayed());
        }
        catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }
    }


}
