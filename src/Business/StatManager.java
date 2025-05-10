package Business;

import Business.Entities.EntityGame;
import Persistance.PersistanceException;
import Persistance.StatsDAO;
import Persistance.sql.SQLStatsDAO;
import java.util.List;


public class StatManager {
    private final StatsDAO statDAO;

    public StatManager(){
        this.statDAO = new SQLStatsDAO();
    }

    public void saveCurrentStats(EntityGame game){
        statDAO.saveNewStats(game.getID_Game(), game.getCurrentNumberOfCoffees(), game.getMinutesPlayed());
    }

    public List<Double> getAllStatsFromGame(EntityGame game) throws BusinessException{
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


}
