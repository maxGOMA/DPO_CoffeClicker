package Business.Entities;

import java.util.ArrayList;

public class EntityGame {
    private final int ID_Game;
    private final String username ;
    private final String name;
    private double numCoffees;
    private int numGoldGenerators;
    private int numDeluxeGenerators;
    private int numSupremeGenerators;
    private int goldLevelUpgrade;
    private int deluxeLevelUpgrade;
    private int supremeLevelUpgrade;
    private int clickerLevelUpgrade;

    ArrayList<EntityGenerator> generators;

    //Si cojo la partida de la persistencia
    public EntityGame(String name, int gold, int upgrade_Clicker, int upgrade_Supreme, int upgrade_Deluxe, int upgrade_Gold, int supreme, int deluxe, Double num_Coffees, String username, int ID_Game) {
        EntityGenerator generator;
        generators = new ArrayList();

        this.name = name;
        this.username = username;
        this.ID_Game = ID_Game;

        this.numGoldGenerators = gold;
        this.numDeluxeGenerators = deluxe;
        this.numSupremeGenerators = supreme;

        this.clickerLevelUpgrade = upgrade_Clicker;
        this.supremeLevelUpgrade = upgrade_Supreme;
        this.deluxeLevelUpgrade = upgrade_Deluxe;
        this.goldLevelUpgrade = upgrade_Gold;

        this.numCoffees = num_Coffees;

        for (int i = 0; i < gold; i++) {
            generator = new EntityGenerator(this,"gold", upgrade_Gold);
            generator.start();
            generators.add(generator);
        }
        for (int i = 0; i < deluxe; i++) {
            generator = new EntityGenerator(this,"deluxe", upgrade_Deluxe);
            generator.start();
            generators.add(generator);
        }
        for (int i = 0; i < supreme; i++) {
            generator = new EntityGenerator(this,"supreme", upgrade_Deluxe);
            generator.start();
            generators.add(generator);
        }
    }

    //Si se trata de una nueva partida.
    public EntityGame(String name, String username, int ID_Game) {
        this.name = name;
        this.username = username;
        this.ID_Game = ID_Game;

        numGoldGenerators = 0;
        numSupremeGenerators = 0;
        numDeluxeGenerators = 0;

        clickerLevelUpgrade = 0;
        supremeLevelUpgrade = 0;
        deluxeLevelUpgrade = 0;
        goldLevelUpgrade = 0;

        numCoffees = 0.0f;

        generators = new ArrayList<>();
    }

    public void stopGenerators () {
        for (EntityGenerator g : generators) {
            g.desactivateGenerator();
        }
    }
    //-------------Funciones compras y upgrades--------------------------------

    public double getCurrentNumberOfCoffees() {
        return numCoffees;
    }

    public int getTotalNumberOfGenerators(String generatorType) {
        switch (generatorType) {
            case "gold":
                return numGoldGenerators;
            case "deluxe":
                return numDeluxeGenerators;
            case "supreme":
                return numSupremeGenerators;
        }
        return 0;
    }

    //TODO acabar aquestes 3 funcionalitats
    public String getUnitProduction(String generatorType) {
        switch (generatorType) {

        }
        return "";
    }

    public String getTotalProduction(String generatorType) {
        switch (generatorType) {

        }
        return "";
    }

    public String getProductionShare(String generatorType) {
        switch (generatorType) {

        }
        return "";
    }

    public double getGeneratorCost(String generatorType) {
        float actualCostOfGenerator = 0;
        float baseCost = 0, numOfGenerators = 0, costIncrement = 0;
        switch (generatorType) {
            case "gold":
                baseCost = 10;
                costIncrement = 1.07f;
                numOfGenerators = numGoldGenerators;
                break;
            case "deluxe":
                baseCost = 150;
                costIncrement = 1.15f;
                numOfGenerators = numDeluxeGenerators;
                break;
            case "supreme":
                baseCost = 2000;
                costIncrement = 1.12f;
                numOfGenerators = numSupremeGenerators;
                break;
        }

        return (float) (baseCost * Math.pow(costIncrement, numOfGenerators));
    }

    public void addNewGenerator(String generatorType) {
        numCoffees = numCoffees - getGeneratorCost(generatorType);
        EntityGenerator newGenerator;
        switch (generatorType) {
            case "gold":
                numGoldGenerators++;
                newGenerator = new EntityGenerator(this,"gold", goldLevelUpgrade);
                newGenerator.start();
                generators.add(newGenerator);
                break;
            case "deluxe":
                numDeluxeGenerators++;
                newGenerator = new EntityGenerator(this,"deluxe", goldLevelUpgrade);
                newGenerator.start();
                generators.add(newGenerator);
                break;
            case "supreme":
                numSupremeGenerators++;
                newGenerator = new EntityGenerator(this,"supreme", goldLevelUpgrade);
                newGenerator.start();
                generators.add(newGenerator);
                break;
        }
    }

    public int getNextUpgradeMultiplicator (String generatorType) {
        switch (generatorType) {
            case "gold":
                return goldLevelUpgrade + 1;
            case "deluxe":
                return deluxeLevelUpgrade + 1;
            case "supreme":
                return supremeLevelUpgrade + 1;
        }
        return 0;
    }

    public double getNextGeneratorUpgradeCost (String generatorType) {
        double baseCostUpgrade = 0;
        int levelUpgrade = 0;
        switch (generatorType) {
            case "gold":
                baseCostUpgrade = 30;
                levelUpgrade = goldLevelUpgrade;
                break;
            case "deluxe":
                baseCostUpgrade = 450;
                levelUpgrade = deluxeLevelUpgrade;
                break;
            case "supreme":
                baseCostUpgrade = 4000;
                levelUpgrade = supremeLevelUpgrade;
                break;
        }

        return baseCostUpgrade * Math.pow(2, levelUpgrade);
    }

    public void upgradeGenerators(String generatorType) {
        numCoffees = numCoffees - getNextGeneratorUpgradeCost(generatorType);
        switch (generatorType) {
            case "gold":
                goldLevelUpgrade++;
                break;
            case "deluxe":
                deluxeLevelUpgrade++;
                break;
            case "supreme":
                supremeLevelUpgrade++;
                break;
        }

        for (EntityGenerator generator : generators ) {
            if (generator.getType() == generatorType) {
                generator.incrementLevel_upgrade();
            }
        }
    }

    public double getNextClickerUpgradeCost() {
        return 5 * Math.pow(2, clickerLevelUpgrade);
    }

    public void upgradeCliker() {
        numCoffees = numCoffees - getNextClickerUpgradeCost();
        clickerLevelUpgrade++;
    }

    public double incrementCoffeeByClicker(){
        // IMPORTANTE: la mejora empieza en 0!!!!
        // Devuelve el numero de cafés que ha incrementado NO EL TOTAL!!
        numCoffees += Math.pow(2, clickerLevelUpgrade);
        return 2^(clickerLevelUpgrade);
    }

    public synchronized void incrementCoffeeByGenerator(double numCoffeesProduced) {
        numCoffees += numCoffeesProduced;
    }

    //getters
    public String getName(){
        return name;
    }

    public int getID_Game() {
        return ID_Game;
    }

    public String getUsername() {
        return username;
    }

    public int getNumGoldGenerators() {
        return numGoldGenerators;
    }

    public int getNumDeluxeGenerators() {
        return numDeluxeGenerators;
    }

    public int getNumSupremeGenerators() {
        return numSupremeGenerators;
    }

    public int getUpgradeGold() {
        return goldLevelUpgrade;
    }

    public int getUpgradeDeluxe() {
        return deluxeLevelUpgrade;
    }

    public int getUpgradeSupreme() {
        return supremeLevelUpgrade;
    }

    public int getClickerLevelUpgrade() {
        return clickerLevelUpgrade;
    }


}
