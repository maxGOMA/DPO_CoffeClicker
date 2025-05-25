package Business.Entities;

import Business.BusinessException;
import Business.StatManager;

/**
 * EntityStatisticsGenerator is a background thread responsible for periodically collecting
 * and saving gameplay statistics for a specific {@link EntityGame}.
 * It interacts with the {@link StatManager} to persist coffee production data at fixed intervals.
 */
public class EntityStatisticsGenerator extends Thread {
    private EntityGame entityGame;
    private StatManager statManager;
    private boolean active;
    private long sleepRatio;

    /**
     * Constructs a new statistics generator bound to a {@link StatManager}.
     * By default, statistics are collected every 60 seconds.
     * @param statManager the manager responsible for saving statistics
     */
    public EntityStatisticsGenerator(StatManager statManager) {
        sleepRatio = 60000;
        active = false;
        this.statManager = statManager;
    }

    /**
     * Activates statistics collection for the given game.
     * @param entityGame the game instance to track
     */
    public void activeStaticsGeneratorForGame(EntityGame entityGame) {
        this.entityGame = entityGame;
        active = true;
    }

    /**
     * Deactivates the statistics generator and stops tracking the current game.
     */
    public void desactivateStaticsGenerator() {
        this.entityGame = null;
        active = false;
    }

    /**
     * Starts the background thread and periodically collects statistics
     * while the generator is active. Increments the game time and delegates
     * stats saving to the {@link StatManager}.
     */
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
            try {
                statManager.saveCurrentStats(entityGame);
            } catch (BusinessException e) {
            }
        }
    }
}
