package Business.Entities;

public class EntityGenerator extends Thread {
    private EntityGame entityGame;
    private String type;
    private int levelUpgrade;
    boolean active;
    private long sleepRatio;
    private int coffeRatio;

    public EntityGenerator (EntityGame entityGame, String type, int levelUpgrade) {
        this.entityGame = entityGame;
        this.type = type;
        this.levelUpgrade = levelUpgrade;
        active = true;

        switch (type) {
            case "gold": //Produccio base 0.2 cafes - 1s
                // 5seg - 1 cafe
                //considero q levelUpgrade empieza en 0.
                if (levelUpgrade == 0) coffeRatio = 1;
                else coffeRatio = (levelUpgrade + 1);
                sleepRatio = 5000;
                break;
            case "deluxe": //Produccio base 0.5 cafes - 0.7s
                if (levelUpgrade == 0) coffeRatio = 1;
                else coffeRatio = (levelUpgrade + 1);
                sleepRatio = 1400;
                break;
            case "supreme": //Produccio base 30 cafes - 1.3s
                if (levelUpgrade == 0) coffeRatio = 30;
                else coffeRatio = 30 * (levelUpgrade + 1);
                sleepRatio = 1300;
                break;
        }
    }

    void activateGenerator() {
        active = true;
    }

    void desactivateGenerator() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void incrementLevel_upgrade() {
        levelUpgrade++;
        if (type == "supreme") {
            coffeRatio = 30 * (levelUpgrade + 1);
        } else {
            coffeRatio = levelUpgrade + 1;
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
            entityGame.IncrementCoffeeByGenerator(coffeRatio);
            //Como aviso a la view de que esto ha sucedido!
        }
    }
}
