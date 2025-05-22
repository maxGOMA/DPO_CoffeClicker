package Business;

import Business.Entities.EntityGame;
import Business.Entities.EntityUser;
import Persistance.GameDAO;
import Persistance.GeneratorsDAO;
import Persistance.PersistanceException;
import Persistance.sql.SQLGameDAO;
import Persistance.sql.SQLGeneratorsDAO;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private EntityGame entityGame;
    private final GameDAO gameDAO;
    private final GeneratorsDAO generatorsDAO;
    private UserManager userManager;

    public GameManager(UserManager userManager) {
        entityGame = null;
        this.gameDAO = new SQLGameDAO();
        this.generatorsDAO = new SQLGeneratorsDAO();
        this.userManager = userManager;
    }

    //--------------------------Creación del juego-----------------------------------------------------
    //Modificado respecto santi -> BussinessException
    public EntityGame setGameFromPersistanceForLoggedInUser(String gameName) throws BusinessException {
        try {
            entityGame = gameDAO.loadInfoGame(gameName, userManager.getCurrentUser());
            return entityGame;
        } catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }

    }

    public EntityGame getCurrentGame() {
        return entityGame;
    }

    public List<EntityGame> getGamesOfLoggedInUser() throws BusinessException {
        try{
            try{
                return gameDAO.getGamesByUser(userManager.getCurrentUser());
            }catch (PersistanceException e) {
                throw new BusinessException(e.getMessage());
            }
        }catch (NullPointerException e){
            return new ArrayList<>();
        }
    }

    public EntityGame returnGameFromUser (String gameName, String username) throws BusinessException {
        try {

            return gameDAO.loadInfoGame(gameName, username);
        } catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }

    }

    public ArrayList<String> getUserFinishedGameNames(String username) throws BusinessException {
        try {
            return gameDAO.getUserFinishedGameNames(username);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public ArrayList<Integer> getUserFinishedGameIds(String username) throws BusinessException {
        try {
            return gameDAO.getUserFinishedGameIDs(username);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public boolean gameNameAlreadyRegisteredByUser(String gameName) throws BusinessException {
        try {
            if (gameDAO.loadInfoGame(gameName, userManager.getCurrentUser()) != null) {
                return true;
            }
            return false;
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void createNewGame(String name, Boolean isCopy) throws BusinessException{
        try {
            if(isCopy){
                entityGame = new EntityGame(name, userManager.getCurrentUser(), -1, entityGame);
                gameDAO.setInfoGame (entityGame);
                entityGame.setID (gameDAO.getIdGame(entityGame.getName(), userManager.getCurrentUser()));
            }else{
                entityGame = new EntityGame(name, userManager.getCurrentUser(), -1);
                gameDAO.setInfoGame (entityGame);
                entityGame.setID (gameDAO.getIdGame(entityGame.getName(), userManager.getCurrentUser()));
            }
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    public void deleteGame(String name) {
        gameDAO.deleteGame(name, userManager.getCurrentUser());
    }

    public void deleteAllGamesByUser() {
        gameDAO.deleteAllGamesByUser(userManager.getCurrentUser());
    }

    public void finishCurrentGame(){
        System.out.println("Finish current game " + entityGame.getName());
        gameDAO.setFinished(userManager.getCurrentUser(), entityGame.getName());
    }

    public void endAndUpdateGame() {
        entityGame.stopGenerators();
        gameDAO.updateGame(entityGame);
    }

    public void updateGame() {
        gameDAO.updateGame(entityGame);
    }

    public int getIDFromGameName(String gameName) throws BusinessException {
        try {
            return gameDAO.getIdGame(gameName, userManager.getCurrentUser());
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }


    //-----------------Generación de cafes------------------------------------------------------------
    public void activateGenerators(CoffeGenerationListener listener, String[] generatorsNames) throws BusinessException {
        try {
            ArrayList<Float> generatorsBaseProd = new ArrayList<>();
            ArrayList<Float> generatorsIntervalProduction= new ArrayList<>();

            for (String generatorName : generatorsNames) {
                generatorsBaseProd.add(generatorsDAO.getGeneratorBaseProduction(generatorName));
                generatorsIntervalProduction.add(generatorsDAO.getGeneratorProductionInterval(generatorName));
            }

            entityGame.activateGenerators(listener, generatorsBaseProd, generatorsIntervalProduction);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }

    }


    public double incrementCoffeeByClicker() {
        return entityGame.incrementCoffeeByClicker();
    }

    //-------------------Tienda de generadores y mejoras----------------------------------------------

    public double getGeneratorCost(String generatorType) throws BusinessException {
        try {
            return generatorsDAO.getGeneratorBaseCost(generatorType) * Math.pow(generatorsDAO.getGeneratorCostIncrease(generatorType), entityGame.getNumGenerators(generatorType));
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public boolean hasResourcesToBuyGenerator(String generatorType) throws BusinessException {
        return (float) entityGame.getCurrentNumberOfCoffees() >= getGeneratorCost(generatorType);
    }

    public void buyNewGenerator(String generatorType, CoffeGenerationListener listener) throws BusinessException {
            try {
                entityGame.addNewGenerator(generatorType, listener, getGeneratorCost(generatorType), generatorsDAO.getGeneratorBaseProduction(generatorType), generatorsDAO.getGeneratorProductionInterval(generatorType));
            } catch (PersistanceException e) {
                throw new BusinessException(e.getMessage());
            }

    }

    public int getGeneratorLevelUpgrade(String generatorType) {
        return entityGame.getUpgradeGenerators(generatorType);
    }

    public ArrayList<Float> getGeneratorUpgradesCosts (String generatorType) throws BusinessException {
        try {
            return generatorsDAO.getGeneratorUpgradesCosts(generatorType);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public boolean hasResourcesToUpgradeGenerator(String generatorType) throws BusinessException {
        System.out.println("current cost: " + getGeneratorUpgradesCosts(generatorType).get(entityGame.getUpgradeGenerators(generatorType)) + "current coffees: " + entityGame.getCurrentNumberOfCoffees());
        return entityGame.getCurrentNumberOfCoffees() >= getGeneratorUpgradesCosts(generatorType).get(entityGame.getUpgradeGenerators(generatorType));
    }

    public void upgradeGenerators(String generatorType) throws BusinessException {
        entityGame.upgradeGenerators(generatorType, getGeneratorUpgradesCosts(generatorType).get(entityGame.getUpgradeGenerators(generatorType)));
    }

    public boolean hasResourcesToUpgradeClicker() {
        return entityGame.getCurrentNumberOfCoffees() >= entityGame.getNextClickerUpgradeCost();
    }

    public void upgradeClicker() {
        entityGame.upgradeCliker();
    }

    //------------------------Estadísticas--------------------------------------------------------

    public double getTotalNumberOfCoffees() {
        return entityGame.getCurrentNumberOfCoffees();
    }

    public int getTotalNumberOfGenerators(String generatorType) {
        return entityGame.getNumGenerators(generatorType);
    }

    public String getUnitProduction(String generatorType) throws BusinessException {
        try {
            return generatorsDAO.getGeneratorBaseProduction(generatorType) + "c/" + generatorsDAO.getGeneratorProductionInterval(generatorType) + "s";
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public String getTotalProduction(String generatorType) throws BusinessException {
        try {
            return generatorsDAO.getGeneratorBaseProduction(generatorType) * entityGame.getNumGenerators(generatorType) * Math.pow(2, entityGame.getUpgradeGenerators(generatorType)) + "c/" + generatorsDAO.getGeneratorProductionInterval(generatorType) + "s";
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public String getGlobalProduction(String generatorType) throws BusinessException {
        double totalProduction = getCoffeesGeneratedPerSecond();
        if(getTotalNumberOfGenerators(generatorType) == 0) return 0 + " %";
        return (getProductionShare(generatorType) / totalProduction) * 100 + " %";
    }

    public double getCoffeesGeneratedPerSecond() throws BusinessException {
        return getProductionShare("beans") + getProductionShare("coffeeMaker") + (getProductionShare("TakeAway"));
    }


    public double getProductionShare(String generatorType) throws BusinessException {
        try {
            return entityGame.getNumGenerators(generatorType) * (generatorsDAO.getGeneratorBaseProduction(generatorType)/generatorsDAO.getGeneratorProductionInterval(generatorType)) * Math.pow(2, entityGame.getUpgradeGenerators(generatorType));
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}