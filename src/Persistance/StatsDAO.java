package Persistance;

import java.util.List;

public interface StatsDAO {

    List<Double> getAllStatsFromGame(int ID_game, int minutesPlayed)  throws PersistanceException;

    void saveNewStats(int ID_game, double numCoffees, int minutesPlayed);

}

