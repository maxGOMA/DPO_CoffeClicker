package Business.Entities;

import Business.CoffeGenerationListener;

public class EntityGenerator extends Thread {
    private EntityGame entityGame;
    private String type;
    private int levelUpgrade;
    boolean active;
    private long sleepRatio;
    private double coffeRatio;

    private CoffeGenerationListener coffGenlistener;

    public EntityGenerator (EntityGame entityGame, String type, int levelUpgrade) {
        this.entityGame = entityGame;
        this.type = type;
        this.levelUpgrade = levelUpgrade;
        active = false;

        switch (type) {
            case "gold": //Produccio base 0.2 cafes - 1s
                // 5seg - 1 cafe
                //considero q levelUpgrade empieza en 0.
                if (levelUpgrade == 0) coffeRatio = 0.2;
                else coffeRatio = (0.2 * (levelUpgrade + 1));
                sleepRatio = 1000;
                break;
            case "deluxe": //Produccio base 0.5 cafes - 0.7s
                if (levelUpgrade == 0) coffeRatio = 0.5;
                else coffeRatio = (0.5 * (levelUpgrade + 1));
                sleepRatio = 700;
                break;
            case "supreme": //Produccio base 30 cafes - 1.3s
                if (levelUpgrade == 0) coffeRatio = 30;
                else coffeRatio = 30 * (levelUpgrade + 1);
                sleepRatio = 1300;
                break;
        }
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
        switch (type) {
            case "gold":
                coffeRatio = (0.2 * (levelUpgrade + 1));
                break;
            case "deluxe":
                coffeRatio = (0.5 * (levelUpgrade + 1));
                break;
            case "supreme":
                coffeRatio = 30 * (levelUpgrade + 1);
                break;
        }
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
