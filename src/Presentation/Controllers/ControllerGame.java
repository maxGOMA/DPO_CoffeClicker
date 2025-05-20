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
                    gameView.showErrorMessage("You don't have enough coffees to buy another beans generator.");
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
                    gameView.showErrorMessage("You don't have enough coffees to buy another coffe maker generator.");
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
                    gameView.showErrorMessage("You don't have enough coffees to buy another coffe takeaway generator.");
                }
            } catch (BusinessException exception) {
                PopUpErrorView.showErrorPopup(null, exception.getMessage(), new ImageIcon("imgs/imageError.png"));
            }

        }
        else if(command.equals(GameView.SETTINGS_COMMAND)){
            gameManager.updateGame();
            gameView.getApp().showPanel("Settings");
            System.out.println(command);
        }
        else if(command.equals(GameView.STATS_COMMAND)) {
            gameManager.updateGame();
            gameView.getApp().createStatsGraph();
            gameView.getApp().showPanel("stats");
            System.out.println(command);
        }

    }


    @Override
    public void newCoffeesGenerated(double numCoffees) {
        gameView.incrementNumCoffees(numCoffees);
    }
}



