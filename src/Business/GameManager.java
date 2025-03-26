package Business;

import Business.Entities.EntityGame;
import Persistance.GameDAO;
import Persistance.PersistanceException;
import Persistance.sql.SQLGameDAO;


public class GameManager {
    private static EntityGame entityGame;
    private final GameDAO gameDAO;

    public GameManager() {
        entityGame = null;
        this.gameDAO = new SQLGameDAO();
    }
    
    public void setGameFromPersistance(String name) throws BusinessException {
        try{
            entityGame = gameDAO.loadInfoGame(name);
        }
        catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }

    }

    public double IncrementCoffee() {
        return entityGame.IncrementCoffee();
    }
}