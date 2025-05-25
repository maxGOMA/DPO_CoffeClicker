package Business.Entities;

import Business.CoffeGenerationListener;
import java.util.ArrayList;

/**
 * EntityGame represents a single gameplay session in CoffeeClicker.
 * It holds all relevant data such as generator counts, upgrade levels,
 * coffee resources, and runtime behavior for production and statistics.
 */
public class EntityGame {
    private int IDGame;
    private String username ;
    private String name;
    private double numCoffees;
    private int numBeansGenerators;
    private int numCoffeeMakerGenerators;
    private int numTakeAwayGenerators;
    private int beansLevelUpgrade;
    private int coffeMakerLevelUpgrade;
    private int takeAwayLevelUpgrade;
    private int clickerLevelUpgrade;
    private int minutesPlayed;
    private int finished;

    private ArrayList<EntityGenerator> generators;

    /**
     * Constructor used when loading a game from persistence.
     */
    public EntityGame(String name, int beansGenerators, int upgradeClicker, int upgradeTakeAway, int upgradeCoffeeMaker, int upgradeBeans, int takeAwayGenerators, int coffeeMakersGenerators, Double numCoffees, String username, int IDGame,int minutesPlayed, int finished) {
        generators = new ArrayList();

        this.name = name;
        this.username = username;
        this.IDGame = IDGame;

        this.numBeansGenerators = beansGenerators;
        this.numCoffeeMakerGenerators = coffeeMakersGenerators;
        this.numTakeAwayGenerators = takeAwayGenerators;

        this.clickerLevelUpgrade = upgradeClicker;
        this.beansLevelUpgrade = upgradeBeans;
        this.coffeMakerLevelUpgrade = upgradeCoffeeMaker;
        this.takeAwayLevelUpgrade = upgradeTakeAway;

        this.numCoffees = numCoffees;
        this.minutesPlayed = minutesPlayed;

        this.finished = finished;
    }

    /**
     * Constructor used for a new game with default values.
     */
    public EntityGame(String name, String username, int IDGame) {
        this.name = name;
        this.username = username;
        this.IDGame = IDGame;

        numBeansGenerators = 0;
        numCoffeeMakerGenerators = 0;
        numTakeAwayGenerators = 0;

        clickerLevelUpgrade = 0;
        takeAwayLevelUpgrade = 0;
        coffeMakerLevelUpgrade = 0;
        beansLevelUpgrade = 0;

        numCoffees = 0.0f;
        minutesPlayed = 0;

        generators = new ArrayList<>();
        finished = 0;
    }

    /**
     * Constructor used to create a copy of an existing game.
     */
    public EntityGame(String name, String username, int IDGame, EntityGame copy) {
        this.name = name;
        this.username = username;
        this.IDGame = IDGame;

        numBeansGenerators = copy.getNumGenerators("beans");
        numCoffeeMakerGenerators = copy.getNumGenerators("coffeeMaker");
        numTakeAwayGenerators = copy.getNumGenerators("TakeAway");

        clickerLevelUpgrade = copy.getClickerLevelUpgrade();
        takeAwayLevelUpgrade = copy.getUpgradeGenerators("TakeAway");
        coffeMakerLevelUpgrade = copy.getUpgradeGenerators("coffeeMaker");
        beansLevelUpgrade = copy.getUpgradeGenerators("beans");

        numCoffees = copy.getCurrentNumberOfCoffees();
        minutesPlayed = 0;

        generators = new ArrayList<>();
        finished = copy.getFinished();
    }

    /**
     * Activates the generators for this game session.
     * @param listener callback listener for coffee generation
     * @param generatorsBaseProduction list of base production values
     * @param generatorsIntervalProduction list of generation intervals
     */
    public void activateGenerators(CoffeGenerationListener listener, ArrayList<Float> generatorsBaseProduction, ArrayList<Float> generatorsIntervalProduction) {
        EntityGenerator generator;

        if (numBeansGenerators > 0) {
            generator =  new EntityGenerator(this,"beans", generatorsBaseProduction.get(0), beansLevelUpgrade, generatorsIntervalProduction.get(0), numBeansGenerators);
            generator.activateGenerator(listener);
            generator.start();
            generators.add(generator);
        }

        if (numCoffeeMakerGenerators > 0) {
            generator = new EntityGenerator(this,"coffeeMaker", generatorsBaseProduction.get(1), coffeMakerLevelUpgrade, generatorsIntervalProduction.get(1), numCoffeeMakerGenerators);
            generator.activateGenerator(listener);
            generator.start();
            generators.add(generator);
        }

        if (numTakeAwayGenerators > 0) {
            generator = new EntityGenerator(this,"TakeAway", generatorsBaseProduction.get(2), beansLevelUpgrade, generatorsIntervalProduction.get(2), numTakeAwayGenerators);
            generator.activateGenerator(listener);
            generator.start();
            generators.add(generator);
        }
    }

    /**
     * Sets the ID of the game.
     * @param ID the new game ID
     */
    public void setID(int ID) {
        IDGame = ID;
    }

    /**
     * Increments the total minutes played in the session.
     */
    public synchronized void incrementMinutePlayed() {
        minutesPlayed++;
    }

    /**
     * Simulates a manual click and adds coffee based on the current clicker level.
     * @return amount of coffee gained
     */
    public double incrementCoffeeByClicker(){
        numCoffees += Math.pow(2, clickerLevelUpgrade);
        return Math.pow(2, clickerLevelUpgrade); //Devuelve el numero de cafés que ha incrementado, no el total actual!
    }

    /**
     * Adds coffee produced automatically by generators.
     * @param numCoffeesProduced amount of coffee to add
     */
    public synchronized void incrementCoffeeByGenerator(double numCoffeesProduced) {
        numCoffees += numCoffeesProduced;
    }

    /**
     * Stops all active generators.
     */
    public void stopGenerators () {
        for (EntityGenerator g : generators) {
            g.desactivateGenerator();
        }
    }

    /**
     * Adds a new generator of the specified type to the game.
     * @param generatorType type of generator to add
     * @param listener the generation listener
     * @param generatorCost the cost to deduct
     * @param generatorBaseProduction base production value
     * @param timeIntervalProduction production interval
     */
    public void addNewGenerator(String generatorType, CoffeGenerationListener listener, double generatorCost, float generatorBaseProduction, float timeIntervalProduction) {
        numCoffees = numCoffees - generatorCost;
        switch (generatorType) {
            case "beans":
                numBeansGenerators++;
                break;
            case "coffeeMaker":
                numCoffeeMakerGenerators++;
                break;
            case "TakeAway":
                numTakeAwayGenerators++;
                break;
        }

        boolean flagFound = false;

        for (int i = 0; i < generators.size(); i++) {
            if (generators.get(i).getType() == generatorType) {
                generators.get(i).incrementNumGenerators();
                flagFound = true;
                break;
            }
        }

        if (!flagFound) {
            EntityGenerator generator = new EntityGenerator(this, generatorType, generatorBaseProduction, 0, timeIntervalProduction, 0);
            generator.incrementNumGenerators();
            generator.activateGenerator(listener);
            generator.start();
            generators.add(generator);
        }
    }

    /**
     * Applies an upgrade to the specified generator type.
     * @param generatorType the generator to upgrade
     * @param upgradeCost cost of the upgrade
     */
    public void upgradeGenerators(String generatorType, Float upgradeCost) {
        numCoffees = numCoffees - (double) upgradeCost;
        switch (generatorType) {
            case "beans":
                beansLevelUpgrade++;
                break;
            case "coffeeMaker":
                coffeMakerLevelUpgrade++;
                break;
            case "TakeAway":
                takeAwayLevelUpgrade++;
                break;
        }

        for (EntityGenerator generator : generators ) {
            if (generator.getType() == generatorType) {
                generator.incrementLevelUpgrade();
                break;
            }
        }
    }

    /**
     * Returns the cost required for the next clicker upgrade.
     * @return upgrade cost
     */
    public double getNextClickerUpgradeCost() {
        return 5 * Math.pow(4, clickerLevelUpgrade);
    }

    /**
     * Applies a clicker upgrade, increasing its multiplier.
     */
    public void upgradeCliker() {
        numCoffees = numCoffees - getNextClickerUpgradeCost();
        clickerLevelUpgrade++;
    }

    /**
     * @return the name of the game
     */
    public String getName(){
        return name;
    }

    /**
     * @return the game ID
     */
    public int getIDGame() {
        return IDGame;
    }

    /**
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the number of generators for the specified type.
     * @param generatorType the generator type
     * @return the number of owned generators
     */
    public int getNumGenerators(String generatorType) {
        switch (generatorType) {
            case "beans":
                return numBeansGenerators;
            case "coffeeMaker":
                return numCoffeeMakerGenerators;
            case "TakeAway":
                return numTakeAwayGenerators;
        }
        return 0;
    }

    /**
     * Returns the upgrade level of a specific generator.
     * @param generatorType the generator type
     * @return upgrade level
     */
    public int getUpgradeGenerators(String generatorType) {
        switch (generatorType) {
            case "beans":
                return beansLevelUpgrade;
            case "coffeeMaker":
                return coffeMakerLevelUpgrade;
            case "TakeAway":
                return takeAwayLevelUpgrade;
        }
        return 0;
    }

    /**
     * @return the clicker upgrade level
     */
    public int getClickerLevelUpgrade() {
        return clickerLevelUpgrade;
    }

    /**
     * @return total minutes played
     */
    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    /**
     * @return current number of coffees
     */
    public double getCurrentNumberOfCoffees() {
        return numCoffees;
    }

    /**
     * @return 1 if game is finished, 0 otherwise
     */
    public int getFinished() {
        return finished;
    }
}
