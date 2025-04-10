package Presentation.Controllers;

import Business.GameManager;
import Presentation.Views.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerShop implements ActionListener {
    GameManager gameManager;

    public ControllerShop() {
        gameManager = new GameManager();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("BuyGenerator")) {
            String generatorType = ""; // = TODO identificar que generador me estan clicando?
            if (gameManager.hasResourcesToBuyGenerator(generatorType)) {
                gameManager.buyNewGenerator(generatorType);
            } else {
                //SHOW ERROR FALTA MONEY!!
            }
        }
        else if (command.equals("UpgradeteGenerator")) {
            String generatorType = ""; // = TODO identificar que generador me estan clicando?
            if (gameManager.hasResourcesToUpgradeGenerator(generatorType)) {
                gameManager.upgradeGenerators(generatorType);
            } else {
                //SHOW ERROR FALTA MONEY!!
            }
        }
        else if (command.equals("UpgradeClicker")) {
            if (gameManager.hasResourcesToUpgradeClicker()) {
                gameManager.upgradeClicker();
            } else {
                //SHOW ERROR FALTA MONEY!!
            }
        }
    }

}
