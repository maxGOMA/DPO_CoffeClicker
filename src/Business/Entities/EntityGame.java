package Business.Entities;

public class EntityGame {
    private final int ID_Game;
    private final String username ;
    private final Double Num_Coffees;
    private final int Gold;
    private final int Deluxe;
    private final int Supreme;
    private final int Upgrade_Gold;
    private final int Upgrade_Deluxe;
    private final int Upgrade_Supreme;
    private final int Upgrade_Clicker;

    public EntityGame(int gold, int upgrade_Clicker, int upgrade_Supreme, int upgrade_Deluxe, int upgrade_Gold, int supreme, int deluxe, Double num_Coffees, String username, int ID_Game) {
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
