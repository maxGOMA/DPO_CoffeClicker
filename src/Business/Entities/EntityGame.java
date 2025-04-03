package Business.Entities;

import java.util.ArrayList;

public class EntityGame {
    private final int ID_Game;
    private final String username ;
    private final String name;
    private Double Num_Coffees;
    private int Gold;
    private int Deluxe;
    private int Supreme;
    private int Upgrade_Gold;
    private int Upgrade_Deluxe;
    private int Upgrade_Supreme;
    private int Upgrade_Clicker;

    ArrayList<EntityGenerator> generators;

    //Si cojo la partida de la persistencia
    public EntityGame(String name, int gold, int upgrade_Clicker, int upgrade_Supreme, int upgrade_Deluxe, int upgrade_Gold, int supreme, int deluxe, Double num_Coffees, String username, int ID_Game) {
        this.name = name;
        this.username = username;
        this.ID_Game = ID_Game;

        this.Gold = gold;
        this.Deluxe = deluxe;
        this.Supreme = supreme;

        this.Upgrade_Clicker = upgrade_Clicker;
        this.Upgrade_Supreme = upgrade_Supreme;
        this.Upgrade_Deluxe = upgrade_Deluxe;
        this.Upgrade_Gold = upgrade_Gold;

        this.Num_Coffees = num_Coffees;

        for (int i = 0; i < gold; i++) {
            generators.add(new EntityGenerator(this,"gold", upgrade_Gold));
        }
        for (int i = 0; i < deluxe; i++) {
            generators.add(new EntityGenerator(this,"deluxe", upgrade_Deluxe));
        }
        for (int i = 0; i < supreme; i++) {
            generators.add(new EntityGenerator(this,"supreme", upgrade_Supreme));
        }
    }

    //Si se trata de una nueva partida.
    public EntityGame(String name, String username, int ID_Game) {
        this.name = name;
        this.username = username;
        this.ID_Game = ID_Game;

        Gold = 0;
        Supreme = 0;
        Deluxe = 0;

        Upgrade_Clicker = 0;
        Upgrade_Supreme = 0;
        Upgrade_Deluxe = 0;
        Upgrade_Gold = 0;

        Num_Coffees = 0.0;

        generators = new ArrayList<>();
    }

    void addNewGenerator(String type) {
        switch (type) {
            case "gold":
                Gold++;
                generators.add(new EntityGenerator(this, type, Upgrade_Gold));
                break;
            case "deluxe":
                Deluxe++;
                generators.add(new EntityGenerator(this, type, Upgrade_Deluxe));
                break;
            case "supreme":
                Supreme++;
                generators.add(new EntityGenerator(this, type, Upgrade_Supreme));
                break;
        }
    }

    void Upgrade_Gold () {
        Upgrade_Gold++;
        for (EntityGenerator generator : generators ) {
            if (generator.getType() == "gold") {
                generator.incrementLevel_upgrade();
            }
        }
    }

    void Upgrade_Deluxe() {
        Upgrade_Deluxe++;
        for (EntityGenerator generator : generators ) {
            if (generator.getType() == "deluxe") {
                generator.incrementLevel_upgrade();
            }
        }
    }

    void Upgrade_Supreme() {
        Upgrade_Supreme++;
        for (EntityGenerator generator : generators ) {
            if (generator.getType() == "supreme") {
                generator.incrementLevel_upgrade();
            }
        }
    }

    void Upgrade_Clicker() {
        Upgrade_Clicker++;
    }

    public double IncrementCoffee(){
        //TODO IMPORTANTE: la mejora empieza en 0!!!!
        // Devuelve el numero de cafés que ha incrementado NO EL TOTAL!!
        Num_Coffees += 2^(Upgrade_Clicker);
        return 2^(Upgrade_Clicker);
    }

    public void IncrementCoffeeByGenerator(int numberOfCoffees){
        Num_Coffees += numberOfCoffees;
    }

    public String getName(){
        return name;
    }

    public int getID_Game() {
        return ID_Game;
    }

    public String getUsername() {
        return username;
    }

    public Double getNum_Coffees() {
        return Num_Coffees;
    }

    public int getGold() {
        return Gold;
    }

    public int getDeluxe() {
        return Deluxe;
    }

    public int getSupreme() {
        return Supreme;
    }

    public int getUpgrade_Gold() {
        return Upgrade_Gold;
    }

    public int getUpgrade_Deluxe() {
        return Upgrade_Deluxe;
    }

    public int getUpgrade_Supreme() {
        return Upgrade_Supreme;
    }

    public int getUpgrade_Clicker() {
        return Upgrade_Clicker;
    }

}
