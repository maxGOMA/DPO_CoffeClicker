package Business.Entities;

import Business.CoffeGenerationListener;

public class EntityGenerator extends Thread {
    private EntityGame entityGame;
    private String type;
    private float baseProduction;

    private int levelUpgrade;
    boolean active;
    private long sleepRatio;
    private double coffeRatio;

    private CoffeGenerationListener coffGenlistener;

    public EntityGenerator (EntityGame entityGame, String type, float baseProduction, int levelUpgrade,  float timeIntervalProduction) {
        this.entityGame = entityGame;
        this.type = type;
        this.levelUpgrade = levelUpgrade;
        active = false;

        this.baseProduction = baseProduction;

        if (levelUpgrade == 0) coffeRatio = baseProduction;
        else coffeRatio = baseProduction * (levelUpgrade + 1);
        sleepRatio = (long) (timeIntervalProduction * 1000);
    }

    void activateGenerator(CoffeGenerationListener coffeGenerationListener) {
        active = true;
        this.coffGenlistener = coffeGenerationListener;
    }

    void desactivateGenerator() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void incrementLevel_upgrade() {
        levelUpgrade++;

        coffeRatio = baseProduction * (levelUpgrade + 1);
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
            entityGame.incrementCoffeeByGenerator(coffeRatio);
            coffGenlistener.newCoffeesGenerated(coffeRatio);
        }
    }
}
