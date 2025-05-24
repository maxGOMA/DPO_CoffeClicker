package Business.Entities;

import Business.CoffeGenerationListener;

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

    public void activateGenerator(CoffeGenerationListener coffeGenerationListener) {
        active = true;
        this.coffGenlistener = coffeGenerationListener;
    }

    public void desactivateGenerator() {
        active = false;
    }

    public void incrementLevelUpgrade() {
        levelUpgrade++;
        coffeRatio = baseProduction * Math.pow(2, levelUpgrade);
    }

    public void incrementNumGenerators() {
        numGeneratorsMultiplicator++;
    }

    public String getType() {
        return type;
    }

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
