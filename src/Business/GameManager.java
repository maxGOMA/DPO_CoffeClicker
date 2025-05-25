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

/**
 * GameManager handles all core business logic related to game lifecycle and generator/shop management.
 * It coordinates data between persistence (via DAOs), the current game state ({@link EntityGame}),
 * and generator statistics for upgrades and production tracking.
 */
public class GameManager {
    private EntityGame entityGame;
    private final GameDAO gameDAO;
    private final GeneratorsDAO generatorsDAO;
    private UserManager userManager;

    /**
     * Constructs a GameManager and binds it to a UserManager.
     * @param userManager the user manager for accessing the current user
     */
    public GameManager(UserManager userManager) {
        entityGame = null;
        this.gameDAO = new SQLGameDAO();
        this.generatorsDAO = new SQLGeneratorsDAO();
        this.userManager = userManager;
    }

    /**
     * Loads a game from persistence by name and sets it as the active game.
     * @param gameName the name of the game
     * @return the loaded {@link EntityGame}
     * @throws BusinessException if loading fails
     */
    public EntityGame setGameFromPersistanceForLoggedInUser(String gameName) throws BusinessException {
        try {
            entityGame = gameDAO.loadInfoGame(gameName, userManager.getCurrentUser());
            return entityGame;
        } catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }

    }

    /**
     * Returns the current active game.
     * @return the current game
     */
    public EntityGame getCurrentGame() {
        return entityGame;
    }

    /**
     * Retrieves all games associated with the currently logged-in user.
     * @return a list of {@link EntityGame} instances
     * @throws BusinessException if retrieval fails
     */
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

    /**
     * Loads a game by name and specific user.
     * @param gameName the name of the game
     * @param username the username who owns the game
     * @return the loaded game
     * @throws BusinessException if loading fails
     */
    public EntityGame returnGameFromUser (String gameName, String username) throws BusinessException {
        try {
            return gameDAO.loadInfoGame(gameName, username);
        } catch (PersistanceException e){
            throw new BusinessException(e.getMessage());
        }

    }

    /**
     * Returns a list of names of all finished games for the specified user.
     * @param username the user to query
     * @return list of finished game names
     * @throws BusinessException if retrieval fails
     */
    public ArrayList<String> getUserFinishedGameNames(String username) throws BusinessException {
        try {
            return gameDAO.getUserFinishedGameNames(username);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Returns a list of IDs of all finished games for the specified user.
     * @param username the user to query
     * @return list of finished game IDs
     * @throws BusinessException if retrieval fails
     */
    public ArrayList<Integer> getUserFinishedGameIds(String username) throws BusinessException {
        try {
            return gameDAO.getUserFinishedGameIDs(username);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Checks if a game name is already registered for the current user.
     * @param gameName the name to check
     * @return true if already registered, false otherwise
     * @throws BusinessException if lookup fails
     */
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

    /**
     * Creates and registers a new game for the current user.
     * @param name the name of the new game
     * @param isCopy true if it's a copy of an existing game, false if new
     * @throws BusinessException if creation fails
     */
    public void createNewGame(String name, Boolean isCopy) throws BusinessException {
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

    /**
     * Deletes a specific game by name for the current user.
     * @param name the name of the game
     * @throws BusinessException if deletion fails
     */
    public void deleteGame(String name) throws BusinessException {
        try {
            gameDAO.deleteGame(name, userManager.getCurrentUser());
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Deletes all games belonging to the current user.
     * @throws BusinessException if deletion fails
     */
    public void deleteAllGamesByUser() throws BusinessException {
        try {
            gameDAO.deleteAllGamesByUser(userManager.getCurrentUser());
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Marks the current game as finished in persistence.
     * @throws BusinessException if update fails
     */
    public void finishCurrentGame() throws BusinessException {
        try {
            gameDAO.setFinished(userManager.getCurrentUser(), entityGame.getName());
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Stops generators and updates the current game in persistence.
     * @throws BusinessException if update fails
     */
    public void endAndUpdateGame() throws BusinessException {
        try {
            entityGame.stopGenerators();
            gameDAO.updateGame(entityGame);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Updates the current game state in persistence.
     * @throws BusinessException if update fails
     */
    public void updateGame() throws BusinessException {
        try {
            gameDAO.updateGame(entityGame);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Retrieves the ID of a game by name for the current user.
     * @param gameName the name of the game
     * @return the game's ID
     * @throws BusinessException if lookup fails
     */
    public int getIDFromGameName(String gameName) throws BusinessException {
        try {
            return gameDAO.getIdGame(gameName, userManager.getCurrentUser());
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Activates generators for the game using the specified listener and generator names.
     * @param listener the listener to receive coffee generation events
     * @param generatorsNames array of generator type names
     * @throws BusinessException if configuration fails
     */
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

    /**
     * Increments coffee count through manual click.
     * @return the updated coffee count
     */
    public double incrementCoffeeByClicker() {
        return entityGame.incrementCoffeeByClicker();
    }

    /**
     * Calculates the current cost of purchasing a generator of the given type.
     * @param generatorType the generator type (e.g., "beans")
     * @return the current cost
     * @throws BusinessException if cost calculation fails
     */
    public double getGeneratorCost(String generatorType) throws BusinessException {
        try {
            return generatorsDAO.getGeneratorBaseCost(generatorType) * Math.pow(generatorsDAO.getGeneratorCostIncrease(generatorType), entityGame.getNumGenerators(generatorType));
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Checks if the user has enough coffees to buy the specified generator.
     * @param generatorType the generator type
     * @return true if the user can afford it
     * @throws BusinessException if cost check fails
     */
    public boolean hasResourcesToBuyGenerator(String generatorType) throws BusinessException {
        return (float) entityGame.getCurrentNumberOfCoffees() >= getGeneratorCost(generatorType);
    }

    /**
     * Buys a new generator and applies its production to the game.
     * @param generatorType the type of generator
     * @param listener the coffee generation listener
     * @throws BusinessException if the transaction fails
     */
    public void buyNewGenerator(String generatorType, CoffeGenerationListener listener) throws BusinessException {
        try {
            entityGame.addNewGenerator(generatorType, listener, getGeneratorCost(generatorType), generatorsDAO.getGeneratorBaseProduction(generatorType), generatorsDAO.getGeneratorProductionInterval(generatorType));
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Returns the upgrade level for the specified generator type.
     * @param generatorType the generator type
     * @return the upgrade level
     */
    public int getGeneratorLevelUpgrade(String generatorType) {
        return entityGame.getUpgradeGenerators(generatorType);
    }

    /**
     * Retrieves upgrade cost list for a given generator.
     * @param generatorType the generator type
     * @return list of upgrade costs
     * @throws BusinessException if retrieval fails
     */
    public ArrayList<Float> getGeneratorUpgradesCosts (String generatorType) throws BusinessException {
        try {
            return generatorsDAO.getGeneratorUpgradesCosts(generatorType);
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Checks if the user has enough resources to upgrade the specified generator.
     * @param generatorType the generator type
     * @return true if upgrade is possible
     * @throws BusinessException if cost check fails
     */
    public boolean hasResourcesToUpgradeGenerator(String generatorType) throws BusinessException {
        return entityGame.getCurrentNumberOfCoffees() >= getGeneratorUpgradesCosts(generatorType).get(entityGame.getUpgradeGenerators(generatorType));
    }

    /**
     * Applies an upgrade to the specified generator.
     * @param generatorType the generator type
     * @throws BusinessException if upgrade fails
     */
    public void upgradeGenerators(String generatorType) throws BusinessException {
        entityGame.upgradeGenerators(generatorType, getGeneratorUpgradesCosts(generatorType).get(entityGame.getUpgradeGenerators(generatorType)));
    }

    /**
     * Returns the current level of the clicker upgrade.
     * @return the clicker upgrade level
     */
    public double getClickerUpgrade() {
        return entityGame.getClickerLevelUpgrade();
    }

    /**
     * Returns the cost required to purchase the next clicker upgrade.
     * @return the next clicker upgrade cost
     */
    public double getNexClickerUpgradeCost() {
        return entityGame.getNextClickerUpgradeCost();
    }

    /**
     * Returns the multiplicative effect of the next clicker upgrade.
     * @return the multiplier of the next clicker upgrade
     */
    public double getNextClickerMultiplicator() {
        return Math.pow(2, entityGame.getClickerLevelUpgrade() + 1);
    }

    /**
     * Checks whether the user has enough coffee to afford the next clicker upgrade.
     * @return true if the user has sufficient resources, false otherwise
     */
    public boolean hasResourcesToUpgradeClicker() {
        return entityGame.getCurrentNumberOfCoffees() >= entityGame.getNextClickerUpgradeCost();
    }

    /**
     * Performs the clicker upgrade by consuming resources and increasing its level.
     */
    public void upgradeClicker() {
        entityGame.upgradeCliker();
    }

    /**
     * Returns the total number of coffees currently collected.
     * @return the total number of coffees
     */
    public double getTotalNumberOfCoffees() {
        return entityGame.getCurrentNumberOfCoffees();
    }

    /**
     * Returns the total number of generators owned for a specific type.
     * @param generatorType the type of generator
     * @return the number of generators of the given type
     */
    public int getTotalNumberOfGenerators(String generatorType) {
        return entityGame.getNumGenerators(generatorType);
    }

    /**
     * Returns the production string of a single unit of the specified generator.
     * @param generatorType the type of generator
     * @return formatted production string for one unit
     * @throws BusinessException if data retrieval fails
     */
    public String getUnitProduction(String generatorType) throws BusinessException {
        try {
            return generatorsDAO.getGeneratorBaseProduction(generatorType) + "c/" + generatorsDAO.getGeneratorProductionInterval(generatorType) + "s";
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Returns the total production of all owned units of a specific generator.
     * @param generatorType the type of generator
     * @return formatted total production string
     * @throws BusinessException if data retrieval fails
     */
    public String getTotalProduction(String generatorType) throws BusinessException {
        try {
            return String.format("%.1f", generatorsDAO.getGeneratorBaseProduction(generatorType) * entityGame.getNumGenerators(generatorType) * Math.pow(2, entityGame.getUpgradeGenerators(generatorType))) + "c/" + generatorsDAO.getGeneratorProductionInterval(generatorType) + "s";
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Returns the percentage of total production contributed by a specific generator type.
     * @param generatorType the type of generator
     * @return the contribution percentage as a formatted string
     * @throws BusinessException if data retrieval fails
     */
    public String getGlobalProduction(String generatorType) throws BusinessException {
        double totalProduction = getCoffeesGeneratedPerSecond();
        if(getTotalNumberOfGenerators(generatorType) == 0) return 0 + " %";
        return String.format("%.1f",(getProductionShare(generatorType) / totalProduction) * 100) + " %";
    }

    /**
     * Calculates the total coffee production per second from all generators.
     * @return the overall production rate in coffees per second
     * @throws BusinessException if data retrieval fails
     */
    public double getCoffeesGeneratedPerSecond() throws BusinessException {
        return getProductionShare("beans") + getProductionShare("coffeeMaker") + (getProductionShare("TakeAway"));
    }

    /**
     * Computes the coffee production rate for a specific generator type.
     * @param generatorType the generator type
     * @return the production rate in coffees per second
     * @throws BusinessException if data retrieval fails
     */
    public double getProductionShare(String generatorType) throws BusinessException {
        try {
            return entityGame.getNumGenerators(generatorType) * (generatorsDAO.getGeneratorBaseProduction(generatorType)/generatorsDAO.getGeneratorProductionInterval(generatorType)) * Math.pow(2, entityGame.getUpgradeGenerators(generatorType));
        } catch (PersistanceException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}