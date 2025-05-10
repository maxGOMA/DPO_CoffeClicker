package Business;

import Business.Entities.EntityGame;
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
    public EntityGame getGameFromPersistance(String gameName) throws BusinessException {
        try {
            entityGame = gameDAO.loadInfoGame(gameName, userManager.getUser().getUsername());
            return entityGame;
        } catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }

    }

    public List<EntityGame> getGamesByUser() throws BusinessException {
        GameDAO gameDao = new SQLGameDAO();
        List< EntityGame > games;
        try{
            try{
                games = gameDao.getGamesByUser(userManager.getUser().getUsername());
            }catch (PersistanceException e) {
                throw new BusinessException(e.getMessage());
            }
        }catch (NullPointerException e){
            games = new ArrayList<>();
        }

        return games;
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

    public void createNewGame(String name) throws BusinessException{
            try {
                entityGame = new EntityGame(name, userManager.getUser().getUsername(), -1);
                gameDAO.setInfoGame(entityGame);
                entityGame.setID(gameDAO.getIdGame(entityGame.getName(), userManager.getUser().getUsername()));
            } catch (PersistanceException e) {
                throw new BusinessException(e.getMessage());
            }
    }

    public void endGame() {
        entityGame.stopGenerators();
        //TODO añadir todo lo de guardar en la persistencia los datos.
    }

    public void deleteGame(String name) {
        gameDAO.deleteGame(name, userManager.getUser().getUsername());
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

    //Formula coste del nuevo upgrade = 1/4 coste generador * 2 (level upgrade actual del generador)
    public double getNextUprgadeGeneratorCost(String generatorType) throws BusinessException {
        try {
            return generatorsDAO.getGeneratorBaseCost(generatorType)/4 * Math.pow(2, entityGame.getUpgradeGenerators(generatorType));
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public boolean hasResourcesToUpgradeGenerator(String generatorType) throws BusinessException {
        return entityGame.getCurrentNumberOfCoffees() >= getNextUprgadeGeneratorCost(generatorType);
    }

    public void upgradeGenerators(String generatorType) throws BusinessException {
        entityGame.upgradeGenerators(generatorType, getNextUprgadeGeneratorCost(generatorType));
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
            return generatorsDAO.getGeneratorBaseProduction(generatorType) * entityGame.getNumGenerators(generatorType) + "c/" + generatorsDAO.getGeneratorProductionInterval(generatorType) + "s";
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public String getGlobalProduction(String generatorType) throws BusinessException {
        try {
            Float totalProduction = (generatorsDAO.getGeneratorBaseProduction("beans") * entityGame.getNumGenerators("beans") + generatorsDAO.getGeneratorBaseProduction("coffeeMaker") * entityGame.getNumGenerators("coffeeMaker") + generatorsDAO.getGeneratorBaseProduction("TakeAway") * entityGame.getNumGenerators("TakeAway"));
            return (generatorsDAO.getGeneratorBaseProduction(generatorType) * entityGame.getNumGenerators(generatorType)) / totalProduction + " %";
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public double getCoffeesGeneratedPerSecond() throws BusinessException {
        return getProductionShare("beans") + getProductionShare("coffeeMaker") + (getProductionShare("TakeAway"));
    }


    public double getProductionShare(String generatorType) throws BusinessException {
        try {
            return entityGame.getNumGenerators(generatorType) * (generatorsDAO.getGeneratorBaseProduction(generatorType)/generatorsDAO.getGeneratorProductionInterval(generatorType)) * (entityGame.getUpgradeGenerators(generatorType) + 1);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}