package Persistance;

import java.util.ArrayList;

public interface StatsDAO {

    ArrayList<Double> getAllStatsFromGame(int ID_game, int minutesPlayed)  throws PersistanceException;

    void saveNewStats(int ID_game, double numCoffees, int minutesPlayed);

    void deleteStats(int ID_game);

}

