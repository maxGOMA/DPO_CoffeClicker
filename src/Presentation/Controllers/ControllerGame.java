package Presentation.Controllers;

import Business.BusinessException;
import Business.CoffeGenerationListener;
import Business.GameManager;
import Business.StatManager;
import Persistance.PersistanceException;
import Presentation.Views.GameView;
import Presentation.Views.PopUpErrorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.Views.GameView.UPG_BEANS_COMMAND;

public class ControllerGame implements ActionListener, CoffeGenerationListener {
    private GameManager gameManager;
    private GameView gameView;

    public ControllerGame (GameView gameView, GameManager gameManager, StatManager statManager) {
        this.gameManager = gameManager;
        this.gameView = gameView;

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

            gameView.initUpgradeGrid(gameManager.getGeneratorLevelUpgrade("beans"), gameManager.getGeneratorLevelUpgrade("coffeeMaker"), gameManager.getGeneratorLevelUpgrade("TakeAway"), gameManager.getGeneratorUpgradesCosts("beans"), gameManager.getGeneratorUpgradesCosts("coffeeMaker"), gameManager.getGeneratorUpgradesCosts("TakeAway"));

        } catch (BusinessException e) {
            System.out.println(e.getMessage());
            PopUpErrorView.showErrorPopup(null, e.getMessage(), new ImageIcon("imgs/imageError.png"));
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
                } else {
                    //Mostrar mensaje de error - No tengo suficiente dinero para comprar un nuevo generador.
                    //gameView.showErrorMessage("You don't have enough coffees to buy another beans generator.");
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            } catch (BusinessException exception) {
                PopUpErrorView.showErrorPopup(null, exception.getMessage(), new ImageIcon("imgs/imageError.png"));
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
                } else {
                    //Mostrar mensaje de error - No tengo suficiente dinero para comprar un nuevo generador.
                    //gameView.showErrorMessage("You don't have enough coffees to buy another coffe maker generator.");
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            } catch (BusinessException exception) {
                PopUpErrorView.showErrorPopup(null, exception.getMessage(), new ImageIcon("imgs/imageError.png"));
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
                } else {
                    //Mostrar mensaje de error - No tengo suficiente dinero para comprar un nuevo generador.
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                    //gameView.showErrorMessage("You don't have enough coffees to buy another coffe takeaway generator.");
                }
            } catch (BusinessException exception) {
                PopUpErrorView.showErrorPopup(null, exception.getMessage(), new ImageIcon("imgs/imageError.png"));
            }
        }

        else if (command.equals(UPG_BEANS_COMMAND)) {
            System.out.println(UPG_BEANS_COMMAND);
            try {
                if (gameManager.hasResourcesToUpgradeGenerator("beans")) {
                    System.out.println("upgrading beans");
                    System.out.println(gameManager.getGeneratorLevelUpgrade("beans"));
                    gameView.buyUpgrade(gameManager.getGeneratorLevelUpgrade("beans"), "beans");
                    gameManager.upgradeGenerators("beans");
                    System.out.println(gameManager.getGeneratorLevelUpgrade("beans"));
                    gameView.unlockUpgrade(gameManager.getGeneratorLevelUpgrade("beans"), "beans");
                    //Actualizar num coffes (automatico decrementa)
                    gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                } else {
                    //TODO MENSAJE DE NO SE PUEDE
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            } catch (BusinessException ex) {
                PopUpErrorView.showErrorPopup(null, ex.getMessage(), new ImageIcon("imgs/imageError.png"));
            }
        }

        else if (command.equals(GameView.UPG_MAKER_COMMAND)) {
            System.out.println(GameView.UPG_MAKER_COMMAND);
            try {
                if (gameManager.hasResourcesToUpgradeGenerator("coffeeMaker")) {
                    System.out.println("upgrading coffeMaker");
                    System.out.println(gameManager.getGeneratorLevelUpgrade("beans"));
                    gameView.buyUpgrade(gameManager.getGeneratorLevelUpgrade("coffeeMaker"), "coffeeMaker");
                    gameManager.upgradeGenerators("coffeeMaker");
                    System.out.println(gameManager.getGeneratorLevelUpgrade("beans"));
                    gameView.unlockUpgrade(gameManager.getGeneratorLevelUpgrade("coffeeMaker"), "coffeeMaker");
                    //Actualizar num coffes (automatico decrementa)
                    gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                } else {
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            } catch (BusinessException ex) {
                PopUpErrorView.showErrorPopup(null, ex.getMessage(), new ImageIcon("imgs/imageError.png"));
            }
        }

        else if (command.equals(GameView.UPG_TAKEAWAY_COMMAND)) {
            System.out.println(GameView.UPG_MAKER_COMMAND);
            try {
                if (gameManager.hasResourcesToUpgradeGenerator("TakeAway")) {
                    System.out.println("upgrading takeAway");
                    gameView.buyUpgrade(gameManager.getGeneratorLevelUpgrade("TakeAway"), "TakeAway");
                    gameManager.upgradeGenerators("TakeAway");
                    gameView.unlockUpgrade(gameManager.getGeneratorLevelUpgrade("TakeAway"), "TakeAway");
                    //Actualizar num coffes (automatico decrementa)
                    gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                } else {
                    //TODO MENSAJE DE NO SE PUEDE
                    PopUpErrorView.showErrorPopup(null, "No tienes cafes suficientes!", new ImageIcon("imgs/coin.png"));
                }
            } catch (BusinessException ex) {
                PopUpErrorView.showErrorPopup(null, ex.getMessage(), new ImageIcon("imgs/imageError.png"));
            }
        }

        else if(command.equals(GameView.SETTINGS_COMMAND)){
            gameManager.updateGame();
            gameView.getApp().showPanel("Settings");
            System.out.println(command);
        }
        else if(command.equals(GameView.STATS_COMMAND)) {
            try {
                gameManager.updateGame();
                gameView.getApp().createStatsGraph();
                gameView.getApp().showPanel("stats");
                System.out.println(command);
            } catch (BusinessException exception) {
                PopUpErrorView.showErrorPopup(null, exception.getMessage(), new ImageIcon("imgs/imageError.png"));
            }
        }

    }


    @Override
    public void newCoffeesGenerated(double numCoffees) {
        gameView.incrementNumCoffees(numCoffees);
    }
}



