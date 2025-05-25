package Business.Entities;

import Business.CoffeGenerationListener;

/**
 * EntityGenerator represents a production unit in the game that generates coffee
 * over time based on its upgrade level and quantity.
 * It runs in a background thread and notifies a {@link CoffeGenerationListener}
 * of new coffee generation events.
 */
public class EntityGenerator extends Thread {
    private EntityGame entityGame;
    private String type;
    private float baseProduction;

    private int levelUpgrade;
    private boolean active;
    private long sleepRatio;
    private double coffeRatio;

    private int numGeneratorsMultiplicator;
    private CoffeGenerationListener coffGenlistener;

    /**
     * Constructs an EntityGenerator.
     * @param entityGame the game to which this generator belongs
     * @param type the generator type
     * @param baseProduction the base coffee production per unit
     * @param levelUpgrade the current upgrade level
     * @param timeIntervalProduction the production interval in seconds
     * @param numGeneratorsMultiplicator the initial quantity of this generator type
     */
    public EntityGenerator (EntityGame entityGame, String type, float baseProduction, int levelUpgrade,  float timeIntervalProduction, int numGeneratorsMultiplicator) {
        this.entityGame = entityGame;
        this.type = type;
        this.levelUpgrade = levelUpgrade;
        active = false;

        this.baseProduction = baseProduction;

        this.numGeneratorsMultiplicator = numGeneratorsMultiplicator;

        if (levelUpgrade == 0) coffeRatio = baseProduction;
        else coffeRatio = baseProduction * (levelUpgrade + 1);
        sleepRatio = (long) (timeIntervalProduction * 1000);
    }

    /**
     * Activates the generator and registers the listener to receive generation updates.
     * @param coffeGenerationListener the listener to notify on new coffee generation
     */
    public void activateGenerator(CoffeGenerationListener coffeGenerationListener) {
        active = true;
        this.coffGenlistener = coffeGenerationListener;
    }

    /**
     * Deactivates the generator and stops coffee production.
     */
    public void desactivateGenerator() {
        active = false;
    }

    /**
     * Increases the upgrade level of the generator and recalculates its production.
     */
    public void incrementLevelUpgrade() {
        levelUpgrade++;
        coffeRatio = baseProduction * Math.pow(2, levelUpgrade);
    }

    /**
     * Increments the number of owned generators of this type.
     */
    public void incrementNumGenerators() {
        numGeneratorsMultiplicator++;
    }

    /**
     * Returns the type of this generator.
     * @return the generator type as a String
     */
    public String getType() {
        return type;
    }

    /**
     * Runs the generator thread, producing coffee at regular intervals and notifying the listener.
     */
    @Override
    public void run() {
        while (active) {
            try {
                Thread.sleep(sleepRatio);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            entityGame.incrementCoffeeByGenerator(coffeRatio * numGeneratorsMultiplicator);
            coffGenlistener.newCoffeesGenerated(coffeRatio * numGeneratorsMultiplicator);
        }
    }
}
