package Business;

import Business.Entities.EntityGame;
import Persistance.GameDAO;
import Persistance.PersistanceException;
import Persistance.sql.SQLGameDAO;

public class GameManager {
    private static EntityGame entityGame;
    private final GameDAO gameDAO;
    private UserManager userManager;

    public GameManager() {
        entityGame = null;
        this.gameDAO = new SQLGameDAO();
        this.userManager = new UserManager();
    }

    //Modificado respecto santi -> BussinessException
    public void getGameFromPersistance(String gameName) throws BusinessException {
        try {
            entityGame = gameDAO.loadInfoGame(gameName, userManager.getUser().getUsername());
        } catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }

    }

    public boolean gameNameAlreadyRegisteredByUser(String gameName) throws BusinessException {
        try {
            if (gameDAO.loadInfoGame(gameName, userManager.getUser().getUsername()) != null) {
                return true;
            }
            return false;
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void createNewGame(String name) {
            entityGame = new EntityGame(name, userManager.getUser().getUsername(), -1);
            gameDAO.setInfoGame(entityGame);
    }

    public void deleteGame(String name) {
        gameDAO.deleteGame(name, userManager.getUser().getUsername());
    }

    public void endGame() {
        entityGame.stopGenerators();
        //TODO añadir todo lo de guardar en la persistencia los datos.
    }

    public double getGeneratorCost(String generatorType) {
        return entityGame.getGeneratorCost(generatorType);
    }

    public boolean hasResourcesToBuyGenerator(String generatorType) {
        return (float) entityGame.getCurrentNumberOfCoffees() >= entityGame.getGeneratorCost(generatorType);
    }

    public void buyNewGenerator(String generatorType) {
        entityGame.addNewGenerator(generatorType);
    }

    public boolean hasResourcesToUpgradeGenerator(String generatorType) {
        return entityGame.getCurrentNumberOfCoffees() >= entityGame.getNextGeneratorUpgradeCost(generatorType);
    }

    public void upgradeGenerators(String generatorType) {
        entityGame.upgradeGenerators(generatorType);
    }

    public boolean hasResourcesToUpgradeClicker() {
        return entityGame.getCurrentNumberOfCoffees() >= entityGame.getNextClickerUpgradeCost();
    }

    public void upgradeClicker() {
        entityGame.upgradeCliker();
    }

    public int getTotalNumberOfGenerators(String generatorType) {
        return entityGame.getTotalNumberOfGenerators(generatorType);
    }

    public String getUnitProduction(String generatorType) {
        return entityGame.getUnitProduction(generatorType);
    }

    public String getTotalProduction(String generatorType) {
        return entityGame.getTotalProduction(generatorType);
    }

    public String getProductionShare(String generatorType) {
        return entityGame.getProductionShare(generatorType);
    }

    public double incrementCoffeeByClicker() {
        return entityGame.incrementCoffeeByClicker();
    }
}