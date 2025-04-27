package Presentation.Controllers;

import Business.CoffeGenerationListener;
import Business.GameManager;
import Presentation.Views.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerGame implements ActionListener, CoffeGenerationListener {
    private GameManager gameManager;
    private GameView gameView;

    public ControllerGame(GameView gameView, GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameView = gameView;

        gameManager.activateGenerators(this);

        //Incializar num coffees.
        gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
        //Inicializar num coffesPerSecond
        gameView.setCoffeesPerSecondValue(gameManager.getCoffesGeneratedPerSecond());
        //Inicializar num de generadores
        gameView.setTotalNumberGenerators("beans", gameManager.getTotalNumberOfGenerators("gold"));
        gameView.setTotalNumberGenerators("maker", gameManager.getTotalNumberOfGenerators("deluxe"));
        gameView.setTotalNumberGenerators("takeaway", gameManager.getTotalNumberOfGenerators("supreme"));
        //Inicializar coste de generadores
        gameView.setActualPriceGenerator("beans", gameManager.getGeneratorCost("gold"));
        gameView.setActualPriceGenerator("maker", gameManager.getGeneratorCost("deluxe"));
        gameView.setActualPriceGenerator("takeaway", gameManager.getGeneratorCost("supreme"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(GameView.CLICKED_COFFEE_COMMAND)) {
            gameView.incrementNumCoffees(gameManager.incrementCoffeeByClicker());
        }

        else if (command.equals(GameView.BUY_BEANS_COMMAND)) {
            if (gameManager.hasResourcesToBuyGenerator("gold")) {
                gameManager.buyNewGenerator("gold", this);
                //Actualizar num coffes (automatico decrementa)
                gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                //Actualizar num generadores
                gameView.setTotalNumberGenerators("beans", gameManager.getTotalNumberOfGenerators("gold"));
                //Actualizar precio del generador
                gameView.setActualPriceGenerator("beans", gameManager.getGeneratorCost("gold"));
                //Actualizar cafes por segundo
                gameView.setCoffeesPerSecondValue(gameManager.getCoffesGeneratedPerSecond());
            } else {
                //Mostrar mensaje de error - No tengo suficiente dinero para comprar un nuevo generador.
                gameView.showErrorMessage("You don't have enough coffees to buy another beans generator.");
            }
        }

        else if (command.equals(GameView.BUY_MAKER_COMMAND)) {
            if (gameManager.hasResourcesToBuyGenerator("deluxe")) {
                gameManager.buyNewGenerator("deluxe", this);
                //Actualizar num coffes (automatico decrementa)
                gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                //Actualizar num generadores
                gameView.setTotalNumberGenerators("maker", gameManager.getTotalNumberOfGenerators("deluxe"));
                //Actualizar precio del generador
                gameView.setActualPriceGenerator("maker", gameManager.getGeneratorCost("deluxe"));
                //Actualizar cafes por segundo
                gameView.setCoffeesPerSecondValue(gameManager.getCoffesGeneratedPerSecond());
            } else {
                //Mostrar mensaje de error - No tengo suficiente dinero para comprar un nuevo generador.
                gameView.showErrorMessage("You don't have enough coffees to buy another coffe maker generator.");
            }
        }

        else if (command.equals(GameView.BUY_TAKEAWAY_COMMAND)) {
            if (gameManager.hasResourcesToBuyGenerator("supreme")) {
                gameManager.buyNewGenerator("supreme", this);
                //Actualizar num coffes (automatico decrementa)
                gameView.setTotalCoffeeLabel(gameManager.getTotalNumberOfCoffees());
                //Actualizar num generadores
                gameView.setTotalNumberGenerators("takeaway", gameManager.getTotalNumberOfGenerators("supreme"));
                //Actualizar precio del generador
                gameView.setActualPriceGenerator("takeaway", gameManager.getGeneratorCost("supreme"));
                //Actualizar cafes por segundo
                gameView.setCoffeesPerSecondValue(gameManager.getCoffesGeneratedPerSecond());
            } else {
                //Mostrar mensaje de error - No tengo suficiente dinero para comprar un nuevo generador.
                gameView.showErrorMessage("You don't have enough coffees to buy another coffe takeaway generator.");
            }
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



