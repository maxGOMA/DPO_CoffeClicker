package Presentation.Controllers;

import Business.BusinessException;
import Business.CoffeGenerationListener;
import Business.GameManager;
import Business.StatManager;
import Presentation.CoffeeClickerApp;
import Presentation.Views.GameView;
import Presentation.Views.PopUpErrorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller responsible for managing in-game interactions, including upgrades, clicks, and view transitions.
 * Implements ActionListener to respond to UI events and CoffeGenerationListener to react to generator updates.
 */
public class ControllerGame implements ActionListener, CoffeGenerationListener {
    private GameManager gameManager;
    private GameView gameView;
    private CoffeeClickerApp app;

    /**
     * Constructs the game controller and initializes UI values from the current game state.
     * @param gameView the game view component
     * @param gameManager the game manager
     * @param statManager the statistics manager
     * @param app the main application instance
     */
    public ControllerGame (GameView gameView, GameManager gameManager, StatManager statManager, CoffeeClickerApp app) {
        this.gameManager = gameManager;
        this.gameView = gameView;
        this.app = app;
        try {
            gameManager.activateGenerators(this, new String[] {"beans", "coffeeMaker", "TakeAway"});
            statManager.initiateStatsGeneration(gameManager.getCurrentGame());
            gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
            gameView.setCoffeesPerSecondValue(gameManager.getCoffeesGeneratedPerSecond());
            gameView.setTotalNumberGenerators("beans", gameManager.getTotalNumberOfGenerators("beans"));
            gameView.setTotalNumberGenerators("coffeeMaker", gameManager.getTotalNumberOfGenerators("coffeeMaker"));
            gameView.setTotalNumberGenerators("TakeAway", gameManager.getTotalNumberOfGenerators("TakeAway"));
            gameView.setActualPriceGenerator("beans", gameManager.getGeneratorCost("beans"));
            gameView.setActualPriceGenerator("coffeeMaker", gameManager.getGeneratorCost("coffeeMaker"));
            gameView.setActualPriceGenerator("TakeAway", gameManager.getGeneratorCost("TakeAway"));

            gameView.updateGeneratorsInfoTable(gameManager.getTotalNumberOfGenerators("beans"), gameManager.getTotalNumberOfGenerators("coffeeMaker"), gameManager.getTotalNumberOfGenerators("TakeAway"), gameManager.getUnitProduction("beans"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getUnitProduction("TakeAway"), gameManager.getTotalProduction("beans"), gameManager.getTotalProduction("coffeeMaker"), gameManager.getTotalProduction("TakeAway"), gameManager.getGlobalProduction("beans"), gameManager.getGlobalProduction("coffeeMaker"), gameManager.getGlobalProduction("TakeAway"));
            gameView.updateGeneratorsShopTable(gameManager.getGeneratorCost("beans"), gameManager.getUnitProduction("beans"), gameManager.getGeneratorCost("coffeeMaker"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getGeneratorCost("TakeAway"), gameManager.getUnitProduction("TakeAway"));

            gameView.initUpgradeGrid(gameManager.getGeneratorLevelUpgrade("beans"), gameManager.getGeneratorLevelUpgrade("coffeeMaker"), gameManager.getGeneratorLevelUpgrade("TakeAway"), gameManager.getGeneratorUpgradesCosts("beans"), gameManager.getGeneratorUpgradesCosts("coffeeMaker"), gameManager.getGeneratorUpgradesCosts("TakeAway"));
            gameView.updateUpgradeInfoTable(gameManager.getGeneratorLevelUpgrade("beans"), gameManager.getGeneratorLevelUpgrade("coffeeMaker"), gameManager.getGeneratorLevelUpgrade("TakeAway"), gameManager.getGeneratorUpgradesCosts("beans"), gameManager.getGeneratorUpgradesCosts("coffeeMaker"), gameManager.getGeneratorUpgradesCosts("TakeAway"));
            if (gameManager.getClickerUpgrade() == 10) {
                gameView.blockClickerButton();
            } else {
                gameView.updateClickerButton(gameManager.getNexClickerUpgradeCost(), gameManager.getNextClickerMultiplicator());
            }
        } catch (BusinessException e) {
            app.finishProgramDueToPersistanceException(e.getMessage());
        }
    }

    /**
     * Handles actions triggered by the game view components.
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(gameView.CLICKED_COFFEE_COMMAND)) {
            gameView.incrementNumCoffees(gameManager.incrementCoffeeByClicker());
        }

        else if (command.equals(gameView.BUY_BEANS_COMMAND)) {
            try {
                if (gameManager.hasResourcesToBuyGenerator("beans")) {
                    gameManager.buyNewGenerator("beans", this);
                    gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                    gameView.setTotalNumberGenerators("beans", gameManager.getTotalNumberOfGenerators("beans"));
                    gameView.setActualPriceGenerator("beans", gameManager.getGeneratorCost("beans"));
                    gameView.setCoffeesPerSecondValue(gameManager.getCoffeesGeneratedPerSecond());
                    gameView.updateGeneratorsShopTable(gameManager.getGeneratorCost("beans"), gameManager.getUnitProduction("beans"), gameManager.getGeneratorCost("coffeeMaker"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getGeneratorCost("TakeAway"), gameManager.getUnitProduction("TakeAway"));
                    gameView.updateGeneratorsInfoTable(gameManager.getTotalNumberOfGenerators("beans"), gameManager.getTotalNumberOfGenerators("coffeeMaker"), gameManager.getTotalNumberOfGenerators("TakeAway"), gameManager.getUnitProduction("beans"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getUnitProduction("TakeAway"), gameManager.getTotalProduction("beans"), gameManager.getTotalProduction("coffeeMaker"), gameManager.getTotalProduction("TakeAway"), gameManager.getGlobalProduction("beans"), gameManager.getGlobalProduction("coffeeMaker"), gameManager.getGlobalProduction("TakeAway"));
                    gameManager.updateGame();
                } else {
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            } catch (BusinessException exception) {
                app.finishProgramDueToPersistanceException(exception.getMessage());
            }

        }

        else if (command.equals(gameView.BUY_MAKER_COMMAND)) {
            try {
                if (gameManager.hasResourcesToBuyGenerator("coffeeMaker")) {
                    gameManager.buyNewGenerator("coffeeMaker", this);
                    gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                    gameView.setTotalNumberGenerators("coffeeMaker", gameManager.getTotalNumberOfGenerators("coffeeMaker"));
                    gameView.setActualPriceGenerator("coffeeMaker", gameManager.getGeneratorCost("coffeeMaker"));
                    gameView.setCoffeesPerSecondValue(gameManager.getCoffeesGeneratedPerSecond());
                    gameView.updateGeneratorsShopTable(gameManager.getGeneratorCost("beans"), gameManager.getUnitProduction("beans"), gameManager.getGeneratorCost("coffeeMaker"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getGeneratorCost("TakeAway"), gameManager.getUnitProduction("TakeAway"));
                    gameView.updateGeneratorsInfoTable(gameManager.getTotalNumberOfGenerators("beans"), gameManager.getTotalNumberOfGenerators("coffeeMaker"), gameManager.getTotalNumberOfGenerators("TakeAway"), gameManager.getUnitProduction("beans"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getUnitProduction("TakeAway"), gameManager.getTotalProduction("beans"), gameManager.getTotalProduction("coffeeMaker"), gameManager.getTotalProduction("TakeAway"), gameManager.getGlobalProduction("beans"), gameManager.getGlobalProduction("coffeeMaker"), gameManager.getGlobalProduction("TakeAway"));
                    gameManager.updateGame();
                } else {
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            } catch (BusinessException exception) {
                app.finishProgramDueToPersistanceException(exception.getMessage());
            }
        }

        else if (command.equals(gameView.BUY_TAKEAWAY_COMMAND)) {
            try {
                if (gameManager.hasResourcesToBuyGenerator("TakeAway")) {
                    gameManager.buyNewGenerator("TakeAway", this);
                    gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                    gameView.setTotalNumberGenerators("TakeAway", gameManager.getTotalNumberOfGenerators("TakeAway"));
                    gameView.setActualPriceGenerator("TakeAway", gameManager.getGeneratorCost("TakeAway"));
                    gameView.setCoffeesPerSecondValue(gameManager.getCoffeesGeneratedPerSecond());
                    gameView.updateGeneratorsShopTable(gameManager.getGeneratorCost("beans"), gameManager.getUnitProduction("beans"), gameManager.getGeneratorCost("coffeeMaker"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getGeneratorCost("TakeAway"), gameManager.getUnitProduction("TakeAway"));
                    gameView.updateGeneratorsInfoTable(gameManager.getTotalNumberOfGenerators("beans"), gameManager.getTotalNumberOfGenerators("coffeeMaker"), gameManager.getTotalNumberOfGenerators("TakeAway"), gameManager.getUnitProduction("beans"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getUnitProduction("TakeAway"), gameManager.getTotalProduction("beans"), gameManager.getTotalProduction("coffeeMaker"), gameManager.getTotalProduction("TakeAway"), gameManager.getGlobalProduction("beans"), gameManager.getGlobalProduction("coffeeMaker"), gameManager.getGlobalProduction("TakeAway"));
                    gameManager.updateGame();
                } else {
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            } catch (BusinessException exception) {
                app.finishProgramDueToPersistanceException(exception.getMessage());
            }
        }

        else if (command.equals(gameView.UPG_BEANS_COMMAND)) {
            try {
                if (gameManager.hasResourcesToUpgradeGenerator("beans")) {
                    if (gameManager.getTotalNumberOfGenerators("beans") > 0) {
                        gameView.buyUpgrade(gameManager.getGeneratorLevelUpgrade("beans"), "beans");
                        gameManager.upgradeGenerators("beans");
                        if (gameManager.getGeneratorLevelUpgrade("beans") < 3) gameView.unlockUpgrade(gameManager.getGeneratorLevelUpgrade("beans"), "beans");
                        gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                        gameView.setCoffeesPerSecondValue(gameManager.getCoffeesGeneratedPerSecond());
                        gameView.updateUpgradeInfoTable(gameManager.getGeneratorLevelUpgrade("beans"), gameManager.getGeneratorLevelUpgrade("coffeeMaker"), gameManager.getGeneratorLevelUpgrade("TakeAway"), gameManager.getGeneratorUpgradesCosts("beans"), gameManager.getGeneratorUpgradesCosts("coffeeMaker"), gameManager.getGeneratorUpgradesCosts("TakeAway"));
                        gameView.updateGeneratorsInfoTable(gameManager.getTotalNumberOfGenerators("beans"), gameManager.getTotalNumberOfGenerators("coffeeMaker"), gameManager.getTotalNumberOfGenerators("TakeAway"), gameManager.getUnitProduction("beans"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getUnitProduction("TakeAway"), gameManager.getTotalProduction("beans"), gameManager.getTotalProduction("coffeeMaker"), gameManager.getTotalProduction("TakeAway"), gameManager.getGlobalProduction("beans"), gameManager.getGlobalProduction("coffeeMaker"), gameManager.getGlobalProduction("TakeAway"));
                        gameManager.updateGame();
                    } else {
                        PopUpErrorView.showErrorPopup(null, "Compra un generador primero", new ImageIcon("imgs/standingPerson.png"));
                    }
                } else {
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            } catch (BusinessException ex) {
                app.finishProgramDueToPersistanceException(ex.getMessage());
            }
        }

        else if (command.equals(gameView.UPG_MAKER_COMMAND)) {
            try {
                if (gameManager.hasResourcesToUpgradeGenerator("coffeeMaker")) {
                    if (gameManager.getTotalNumberOfGenerators("coffeeMaker") > 0) {
                        gameView.buyUpgrade(gameManager.getGeneratorLevelUpgrade("coffeeMaker"), "coffeeMaker");
                        gameManager.upgradeGenerators("coffeeMaker");
                        if (gameManager.getGeneratorLevelUpgrade("coffeeMaker") < 3) gameView.unlockUpgrade(gameManager.getGeneratorLevelUpgrade("coffeeMaker"), "coffeeMaker");
                        gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                        gameView.setCoffeesPerSecondValue(gameManager.getCoffeesGeneratedPerSecond());
                        gameView.updateUpgradeInfoTable(gameManager.getGeneratorLevelUpgrade("beans"), gameManager.getGeneratorLevelUpgrade("coffeeMaker"), gameManager.getGeneratorLevelUpgrade("TakeAway"), gameManager.getGeneratorUpgradesCosts("beans"), gameManager.getGeneratorUpgradesCosts("coffeeMaker"), gameManager.getGeneratorUpgradesCosts("TakeAway"));
                        gameView.updateGeneratorsInfoTable(gameManager.getTotalNumberOfGenerators("beans"), gameManager.getTotalNumberOfGenerators("coffeeMaker"), gameManager.getTotalNumberOfGenerators("TakeAway"), gameManager.getUnitProduction("beans"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getUnitProduction("TakeAway"), gameManager.getTotalProduction("beans"), gameManager.getTotalProduction("coffeeMaker"), gameManager.getTotalProduction("TakeAway"), gameManager.getGlobalProduction("beans"), gameManager.getGlobalProduction("coffeeMaker"), gameManager.getGlobalProduction("TakeAway"));
                        gameManager.updateGame();
                    } else {
                        PopUpErrorView.showErrorPopup(null, "Compra un generador primero!", new ImageIcon("imgs/standingPerson.png"));
                    }
                } else {
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            } catch (BusinessException ex) {
                app.finishProgramDueToPersistanceException(ex.getMessage());
            }
        }

        else if (command.equals(gameView.UPG_TAKEAWAY_COMMAND)) {
            try {
                if (gameManager.hasResourcesToUpgradeGenerator("TakeAway")) {
                    if (gameManager.getTotalNumberOfGenerators("TakeAway") > 0) {
                            gameView.buyUpgrade(gameManager.getGeneratorLevelUpgrade("TakeAway"), "TakeAway");
                            gameManager.upgradeGenerators("TakeAway");
                            if (gameManager.getGeneratorLevelUpgrade("TakeAway") < 3) gameView.unlockUpgrade(gameManager.getGeneratorLevelUpgrade("TakeAway"), "TakeAway");
                            gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                            gameView.setCoffeesPerSecondValue(gameManager.getCoffeesGeneratedPerSecond());
                            gameView.updateUpgradeInfoTable(gameManager.getGeneratorLevelUpgrade("beans"), gameManager.getGeneratorLevelUpgrade("coffeeMaker"), gameManager.getGeneratorLevelUpgrade("TakeAway"), gameManager.getGeneratorUpgradesCosts("beans"), gameManager.getGeneratorUpgradesCosts("coffeeMaker"), gameManager.getGeneratorUpgradesCosts("TakeAway"));
                            gameView.updateGeneratorsInfoTable(gameManager.getTotalNumberOfGenerators("beans"), gameManager.getTotalNumberOfGenerators("coffeeMaker"), gameManager.getTotalNumberOfGenerators("TakeAway"), gameManager.getUnitProduction("beans"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getUnitProduction("TakeAway"), gameManager.getTotalProduction("beans"), gameManager.getTotalProduction("coffeeMaker"), gameManager.getTotalProduction("TakeAway"), gameManager.getGlobalProduction("beans"), gameManager.getGlobalProduction("coffeeMaker"), gameManager.getGlobalProduction("TakeAway"));
                            gameManager.updateGame();
                    } else {
                        PopUpErrorView.showErrorPopup(null, "Compra un generador primero", new ImageIcon("imgs/standingPerson.png"));
                    }
                } else {
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            } catch (BusinessException ex) {
                app.finishProgramDueToPersistanceException(ex.getMessage());
            }
        }

        else if(command.equals(gameView.SETTINGS_COMMAND)){
            try {
                gameManager.updateGame();
                app.showPanel("Settings");
            } catch (BusinessException ex) {
                app.finishProgramDueToPersistanceException(ex.getMessage());
            }
        }

        else if(command.equals(gameView.STATS_COMMAND)) {
            try {
                gameManager.updateGame();
                app.createStatsGraph();
                app.showPanel("stats");
            } catch (BusinessException exception) {
                app.finishProgramDueToPersistanceException(exception.getMessage());
            }
        }

        else if (command.equals(gameView.CLICK_UPGRADE_COMMAND)) {
            if (gameManager.hasResourcesToUpgradeClicker() && gameManager.getClickerUpgrade() != 10) {
                gameManager.upgradeClicker();
                gameView.updateClickerButton(gameManager.getNexClickerUpgradeCost(), gameManager.getNextClickerMultiplicator());
                gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                if (gameManager.getClickerUpgrade() == 10) {
                    gameView.blockClickerButton();
                }
            } else {
                if (gameManager.getClickerUpgrade() == 10) {
                    PopUpErrorView.showErrorPopup(null, "No puedes mejorar mas el clicker!", new ImageIcon("imgs/standingPerson.png"));
                } else {
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            }
        }
    }

    /**
     * Responds to the generation of new coffees by the generators.
     * Updates the total coffee count and production rate in the UI.
     */
    @Override
    public void newCoffeesGenerated(double numCoffees) {
        gameView.incrementNumCoffees(numCoffees);
    }
}



