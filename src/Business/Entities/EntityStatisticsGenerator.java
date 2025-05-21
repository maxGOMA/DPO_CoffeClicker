package Business.Entities;

import Business.StatManager;

public class EntityStatisticsGenerator extends Thread {
    private EntityGame entityGame;
    private StatManager statManager;
    boolean active;
    private long sleepRatio;


    public EntityStatisticsGenerator(StatManager statManager) {
        sleepRatio = 5000;
        active = false;
        this.statManager = statManager;
    }

    public void activeStaticsGeneratorForGame(EntityGame entityGame) {
        this.entityGame = entityGame;
        active = true;
    }

    public void desactivateStaticsGenerator() {
        this.entityGame = null;
        active = false;
    }

    @Override
    public void run() {
        while (active) {
            try {
                Thread.sleep(sleepRatio);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!active) break;
            entityGame.incrementMinutePlayed();
            statManager.saveCurrentStats(entityGame);
            System.out.println("------------------- Name: " + entityGame.getName() + " minute: " + entityGame.getMinutesPlayed() + " currentCoffees: " + entityGame.getCurrentNumberOfCoffees());
        }
    }
}
