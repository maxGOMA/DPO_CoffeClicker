package Business.Entities;

import Business.CoffeGenerationListener;
import java.util.ArrayList;

public class EntityGame {
    private int IDGame;
    private String username ;
    private String name;
    private double numCoffees;
    private int numBeansGenerators;
    private int numCoffeeMakerGenerators;
    private int numTakeAwayGenerators;
    private int beansLevelUpgrade;
    private int coffeMakerLevelUpgrade;
    private int takeAwayLevelUpgrade;
    private int clickerLevelUpgrade;
    private int minutesPlayed;
    private int finished;

    ArrayList<EntityGenerator> generators;

    //-----------Inicialización del juego----------------------------------
    //Si recupero la partida de la persistencia
    public EntityGame(String name, int beansGenerators, int upgradeClicker, int upgradeTakeAway, int upgradeCoffeeMaker, int upgradeBeans, int takeAwayGenerators, int coffeeMakersGenerators, Double numCoffees, String username, int IDGame,int minutesPlayed, int finished) {
        generators = new ArrayList();

        this.name = name;
        this.username = username;
        this.IDGame = IDGame;

        this.numBeansGenerators = beansGenerators;
        this.numCoffeeMakerGenerators = coffeeMakersGenerators;
        this.numTakeAwayGenerators = takeAwayGenerators;

        this.clickerLevelUpgrade = upgradeClicker;
        this.beansLevelUpgrade = upgradeBeans;
        this.coffeMakerLevelUpgrade = upgradeCoffeeMaker;
        this.takeAwayLevelUpgrade = upgradeTakeAway;

        this.numCoffees = numCoffees;
        this.minutesPlayed = minutesPlayed;

        this.finished = finished;
    }

    //Si se trata de una nueva partida.
    public EntityGame(String name, String username, int IDGame) {
        this.name = name;
        this.username = username;
        this.IDGame = IDGame;

        numBeansGenerators = 0;
        numCoffeeMakerGenerators = 0;
        numTakeAwayGenerators = 0;

        clickerLevelUpgrade = 0;
        takeAwayLevelUpgrade = 0;
        coffeMakerLevelUpgrade = 0;
        beansLevelUpgrade = 0;

        numCoffees = 0.0f;
        minutesPlayed = 0;

        generators = new ArrayList<>();
        finished = 0;
    }

    //Para copiar de un game ya creado
    public EntityGame(String name, String username, int IDGame, EntityGame copy) {
        this.name = name;
        this.username = username;
        this.IDGame = IDGame;

        numBeansGenerators = copy.getNumGenerators("beans");
        numCoffeeMakerGenerators = copy.getNumGenerators("coffeeMaker");
        numTakeAwayGenerators = copy.getNumGenerators("TakeAway");

        clickerLevelUpgrade = copy.getClickerLevelUpgrade();
        takeAwayLevelUpgrade = copy.getUpgradeGenerators("TakeAway");
        coffeMakerLevelUpgrade = copy.getUpgradeGenerators("coffeeMaker");
        beansLevelUpgrade = copy.getUpgradeGenerators("beans");

        numCoffees = copy.getCurrentNumberOfCoffees();
        minutesPlayed = 0;

        generators = new ArrayList<>();
        finished = copy.getFinished();
    }

    public void activateGenerators(CoffeGenerationListener listener, ArrayList<Float> generatorsBaseProduction, ArrayList<Float> generatorsIntervalProduction) {
        EntityGenerator generator;

        if (numBeansGenerators > 0) {
            generator =  new EntityGenerator(this,"beans", generatorsBaseProduction.get(0), beansLevelUpgrade, generatorsIntervalProduction.get(0), numBeansGenerators);
            generator.activateGenerator(listener);
            generator.start();
            generators.add(generator);
        }

        if (numCoffeeMakerGenerators > 0) {
            generator = new EntityGenerator(this,"coffeeMaker", generatorsBaseProduction.get(1), coffeMakerLevelUpgrade, generatorsIntervalProduction.get(1), numCoffeeMakerGenerators);
            generator.activateGenerator(listener);
            generator.start();
            generators.add(generator);
        }

        if (numTakeAwayGenerators > 0) {
            generator = new EntityGenerator(this,"TakeAway", generatorsBaseProduction.get(2), beansLevelUpgrade, generatorsIntervalProduction.get(2), numTakeAwayGenerators);
            generator.activateGenerator(listener);
            generator.start();
            generators.add(generator);
        }
    }

    public void setID(int ID) {
        IDGame = ID;
    }

    public synchronized void incrementMinutePlayed() {
        minutesPlayed++;
    }

    //-------------Funciones generación de cafes------------------------------
    public double incrementCoffeeByClicker(){
        numCoffees += Math.pow(2, clickerLevelUpgrade);
        return Math.pow(2, clickerLevelUpgrade); //Devuelve el numero de cafés que ha incrementado, no el total actual!
    }

    public synchronized void incrementCoffeeByGenerator(double numCoffeesProduced) {
        numCoffees += numCoffeesProduced;
    }

    public void stopGenerators () {
        for (EntityGenerator g : generators) {
            g.desactivateGenerator();
        }
    }

    //-------------Funciones compras y upgrades--------------------------------

    public void addNewGenerator(String generatorType, CoffeGenerationListener listener, double generatorCost, float generatorBaseProduction, float timeIntervalProduction) {
        numCoffees = numCoffees - generatorCost;
        switch (generatorType) {
            case "beans":
                numBeansGenerators++;
                break;
            case "coffeeMaker":
                numCoffeeMakerGenerators++;
                break;
            case "TakeAway":
                numTakeAwayGenerators++;
                break;
        }

        boolean flagFound = false;

        for (int i = 0; i < generators.size(); i++) {
            if (generators.get(i).getType() == generatorType) {
                generators.get(i).incrementNumGenerators();
                flagFound = true;
                break;
            }
        }

        if (!flagFound) {
            EntityGenerator generator = new EntityGenerator(this, generatorType, generatorBaseProduction, 0, timeIntervalProduction, 0);
            generator.incrementNumGenerators();
            generator.activateGenerator(listener);
            generator.start();
            generators.add(generator);
        }
    }

    //Upgraders
    public void upgradeGenerators(String generatorType, Float upgradeCost) {
        numCoffees = numCoffees - (double) upgradeCost;
        switch (generatorType) {
            case "beans":
                beansLevelUpgrade++;
                break;
            case "coffeeMaker":
                coffeMakerLevelUpgrade++;
                break;
            case "TakeAway":
                takeAwayLevelUpgrade++;
                break;
        }

        for (EntityGenerator generator : generators ) {
            if (generator.getType() == generatorType) {
                generator.incrementLevel_upgrade();
                break;
            }
        }
    }

    public double getNextClickerUpgradeCost() {
        return 5 * Math.pow(4, clickerLevelUpgrade);
    }

    public void upgradeCliker() {
        numCoffees = numCoffees - getNextClickerUpgradeCost();
        clickerLevelUpgrade++;
    }


    //-----------------GETTERS Y ESTADÍSTICAS--------------------------------------------------------
    public String getName(){
        return name;
    }

    public int getIDGame() {
        return IDGame;
    }

    public String getUsername() {
        return username;
    }

    public int getNumGenerators(String generatorType) {
        switch (generatorType) {
            case "beans":
                return numBeansGenerators;
            case "coffeeMaker":
                return numCoffeeMakerGenerators;
            case "TakeAway":
                return numTakeAwayGenerators;
        }
        return 0;
    }

    public int getUpgradeGenerators(String generatorType) {
        switch (generatorType) {
            case "beans":
                return beansLevelUpgrade;
            case "coffeeMaker":
                return coffeMakerLevelUpgrade;
            case "TakeAway":
                return takeAwayLevelUpgrade;
        }
        return 0;
    }

    public int getClickerLevelUpgrade() {
        return clickerLevelUpgrade;
    }

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public double getCurrentNumberOfCoffees() {
        return numCoffees;
    }

    public int getFinished() {
        return finished;
    }
}
