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

import static Presentation.Views.GameView.UPG_BEANS_COMMAND;

public class ControllerGame implements ActionListener, CoffeGenerationListener {
    private GameManager gameManager;
    private GameView gameView;
    private CoffeeClickerApp app;

    public ControllerGame (GameView gameView, GameManager gameManager, StatManager statManager, CoffeeClickerApp app) {
        this.gameManager = gameManager;
        this.gameView = gameView;
        this.app = app;
        try {
            gameManager.activateGenerators(this, new String[] {"beans", "coffeeMaker", "TakeAway"});
            statManager.initiateStatsGeneration(gameManager.getCurrentGame());
            //Incializar num coffees.
            gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
            //Inicializar num coffesPerSecond
            gameView.setCoffeesPerSecondValue(gameManager.getCoffeesGeneratedPerSecond());
            //Inicializar num de generadores
            gameView.setTotalNumberGenerators("beans", gameManager.getTotalNumberOfGenerators("beans"));
            gameView.setTotalNumberGenerators("coffeeMaker", gameManager.getTotalNumberOfGenerators("coffeeMaker"));
            gameView.setTotalNumberGenerators("TakeAway", gameManager.getTotalNumberOfGenerators("TakeAway"));
            //Inicializar coste de generadores
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(GameView.CLICKED_COFFEE_COMMAND)) {
            gameView.incrementNumCoffees(gameManager.incrementCoffeeByClicker());
        }

        else if (command.equals(GameView.BUY_BEANS_COMMAND)) {
            try {
                if (gameManager.hasResourcesToBuyGenerator("beans")) {
                    gameManager.buyNewGenerator("beans", this);
                    //Actualizar num coffes (automatico decrementa)
                    gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                    //Actualizar num generadores
                    gameView.setTotalNumberGenerators("beans", gameManager.getTotalNumberOfGenerators("beans"));
                    //Actualizar precio del generador
                    gameView.setActualPriceGenerator("beans", gameManager.getGeneratorCost("beans"));
                    //Actualizar cafes por segundo
                    gameView.setCoffeesPerSecondValue(gameManager.getCoffeesGeneratedPerSecond());
                    gameView.updateGeneratorsShopTable(gameManager.getGeneratorCost("beans"), gameManager.getUnitProduction("beans"), gameManager.getGeneratorCost("coffeeMaker"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getGeneratorCost("TakeAway"), gameManager.getUnitProduction("TakeAway"));
                    gameView.updateGeneratorsInfoTable(gameManager.getTotalNumberOfGenerators("beans"), gameManager.getTotalNumberOfGenerators("coffeeMaker"), gameManager.getTotalNumberOfGenerators("TakeAway"), gameManager.getUnitProduction("beans"), gameManager.getUnitProduction("coffeeMaker"), gameManager.getUnitProduction("TakeAway"), gameManager.getTotalProduction("beans"), gameManager.getTotalProduction("coffeeMaker"), gameManager.getTotalProduction("TakeAway"), gameManager.getGlobalProduction("beans"), gameManager.getGlobalProduction("coffeeMaker"), gameManager.getGlobalProduction("TakeAway"));
                    gameManager.updateGame();
                } else {
                    //Mostrar mensaje de error - No tengo suficiente dinero para comprar un nuevo generador.
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            } catch (BusinessException exception) {
                app.finishProgramDueToPersistanceException(exception.getMessage());
            }

        }

        else if (command.equals(GameView.BUY_MAKER_COMMAND)) {
            try {
                if (gameManager.hasResourcesToBuyGenerator("coffeeMaker")) {
                    gameManager.buyNewGenerator("coffeeMaker", this);
                    //Actualizar num coffes (automatico decrementa)
                    gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                    //Actualizar num generadores
                    gameView.setTotalNumberGenerators("coffeeMaker", gameManager.getTotalNumberOfGenerators("coffeeMaker"));
                    //Actualizar precio del generador
                    gameView.setActualPriceGenerator("coffeeMaker", gameManager.getGeneratorCost("coffeeMaker"));
                    //Actualizar cafes por segundo
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

        else if (command.equals(GameView.BUY_TAKEAWAY_COMMAND)) {
            try {
                if (gameManager.hasResourcesToBuyGenerator("TakeAway")) {
                    gameManager.buyNewGenerator("TakeAway", this);
                    //Actualizar num coffes (automatico decrementa)
                    gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                    //Actualizar num generadores
                    gameView.setTotalNumberGenerators("TakeAway", gameManager.getTotalNumberOfGenerators("TakeAway"));
                    //Actualizar precio del generador
                    gameView.setActualPriceGenerator("TakeAway", gameManager.getGeneratorCost("TakeAway"));
                    //Actualizar cafes por segundo
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

        else if (command.equals(UPG_BEANS_COMMAND)) {
            try {
                if (gameManager.hasResourcesToUpgradeGenerator("beans")) {
                    if (gameManager.getTotalNumberOfGenerators("beans") > 0) {
                        gameView.buyUpgrade(gameManager.getGeneratorLevelUpgrade("beans"), "beans");
                        gameManager.upgradeGenerators("beans");
                        if (gameManager.getGeneratorLevelUpgrade("beans") < 3) gameView.unlockUpgrade(gameManager.getGeneratorLevelUpgrade("beans"), "beans");
                        //Actualizar num coffes (automatico decrementa)
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

        else if (command.equals(GameView.UPG_MAKER_COMMAND)) {
            try {
                if (gameManager.hasResourcesToUpgradeGenerator("coffeeMaker")) {
                    if (gameManager.getTotalNumberOfGenerators("coffeeMaker") > 0) {
                        gameView.buyUpgrade(gameManager.getGeneratorLevelUpgrade("coffeeMaker"), "coffeeMaker");
                        gameManager.upgradeGenerators("coffeeMaker");
                        if (gameManager.getGeneratorLevelUpgrade("coffeeMaker") < 3) gameView.unlockUpgrade(gameManager.getGeneratorLevelUpgrade("coffeeMaker"), "coffeeMaker");
                        //Actualizar num coffes (automatico decrementa)
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

        else if (command.equals(GameView.UPG_TAKEAWAY_COMMAND)) {
            try {
                if (gameManager.hasResourcesToUpgradeGenerator("TakeAway")) {
                    if (gameManager.getTotalNumberOfGenerators("TakeAway") > 0) {
                            gameView.buyUpgrade(gameManager.getGeneratorLevelUpgrade("TakeAway"), "TakeAway");
                            gameManager.upgradeGenerators("TakeAway");
                            if (gameManager.getGeneratorLevelUpgrade("TakeAway") < 3) gameView.unlockUpgrade(gameManager.getGeneratorLevelUpgrade("TakeAway"), "TakeAway");
                            //Actualizar num coffes (automatico decrementa)
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

        else if(command.equals(GameView.SETTINGS_COMMAND)){
            try {
                gameManager.updateGame();
                app.showPanel("Settings");
            } catch (BusinessException ex) {
                app.finishProgramDueToPersistanceException(ex.getMessage());
            }
        }

        else if(command.equals(GameView.STATS_COMMAND)) {
            try {
                gameManager.updateGame();
                app.createStatsGraph();
                app.showPanel("stats");
            } catch (BusinessException exception) {
                app.finishProgramDueToPersistanceException(exception.getMessage());
            }
        }

        else if (command.equals(GameView.CLICK_UPGRADE_COMMAND)) {
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


    @Override
    public void newCoffeesGenerated(double numCoffees) {
        gameView.incrementNumCoffees(numCoffees);
    }
}



