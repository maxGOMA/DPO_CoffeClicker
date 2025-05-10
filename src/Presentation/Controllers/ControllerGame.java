package Presentation.Controllers;

import Business.BusinessException;
import Business.CoffeGenerationListener;
import Business.GameManager;
import Persistance.PersistanceException;
import Presentation.Views.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerGame implements ActionListener, CoffeGenerationListener {
    private GameManager gameManager;
    private GameView gameView;

    public ControllerGame(GameView gameView, GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameView = gameView;

        try {
            gameManager.activateGenerators(this, new String[] {"beans", "coffeeMaker", "TakeAway"});

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
            //TODO mostrar mensaje de error de persistencia.
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
                //TODO mostrar mensaje de error de persistencia.
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
                //TODO mostrar mensaje de error de persistencia.
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
                //TODO mostrar mensaje de error de persistencia.
            }

        }
        else if(command.equals(GameView.SETTINGS_COMMAND)){
            gameView.getApp().showPanel("Settings");
            System.out.println(command);
        }

//        else if (command.equals("UpgradeteGenerator")) {
//            String generatorType = ""; // = TODO identificar que generador me estan clicando?
//            if (gameManager.hasResourcesToUpgradeGenerator(generatorType)) {
//                gameManager.upgradeGenerators(generatorType);
//            } else {
//                //SHOW ERROR FALTA MONEY!!
//            }
//        }
//        else if (command.equals("UpgradeClicker")) {
//            if (gameManager.hasResourcesToUpgradeClicker()) {
//                gameManager.upgradeClicker();
//            } else {
//                //SHOW ERROR FALTA MONEY!!
//            }
//        }
    }


    @Override
    public void newCoffeesGenerated(double numCoffees) {
        gameView.incrementNumCoffees(numCoffees);
    }
}



