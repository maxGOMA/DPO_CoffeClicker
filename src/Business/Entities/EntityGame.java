package Business.Entities;

public class EntityGame {
    private final int ID_Game;
    private final String username ;
    private final String name;
    private Double Num_Coffees;
    private final int Gold;
    private final int Deluxe;
    private final int Supreme;
    private final int Upgrade_Gold;
    private final int Upgrade_Deluxe;
    private final int Upgrade_Supreme;
    private final int Upgrade_Clicker;

    public EntityGame(String name, int gold, int upgrade_Clicker, int upgrade_Supreme, int upgrade_Deluxe, int upgrade_Gold, int supreme, int deluxe, Double num_Coffees, String username, int ID_Game) {
        this.name = name;
        Gold = gold;
        Upgrade_Clicker = upgrade_Clicker;
        Upgrade_Supreme = upgrade_Supreme;
        Upgrade_Deluxe = upgrade_Deluxe;
        Upgrade_Gold = upgrade_Gold;
        Supreme = supreme;
        Deluxe = deluxe;
        Num_Coffees = num_Coffees;
        this.username = username;
        this.ID_Game = ID_Game;
    }

    public EntityGame(String name, String username, int ID_Game) {
        this.name = name;
        this.Gold = 0;
        Upgrade_Clicker = 0;
        Upgrade_Supreme = 0;
        Upgrade_Deluxe = 0;
        Upgrade_Gold = 0;
        Supreme = 0;
        Deluxe = 0;
        Num_Coffees = 0.0;
        this.username = username;
        this.ID_Game = ID_Game;
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

    public double IncrementCoffee(){
        //TODO IMPORTANTE: la mejora empieza en 0!!!!
        // Devuelve el numero de cafés que ha incrementado NO EL TOTAL!!
        Num_Coffees += 2^(Upgrade_Clicker);
        return 2^(Upgrade_Clicker);
    }

}
