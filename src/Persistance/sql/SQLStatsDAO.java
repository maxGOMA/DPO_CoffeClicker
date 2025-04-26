package Persistance.sql;

import Persistance.PersistanceException;
import Persistance.StatsDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLStatsDAO implements StatsDAO {

    public void saveNewStats(int ID_game, double numCoffees, int minutesPlayed){
        String query = "INSERT INTO stats (ID_Game, Num_Coffees, Minute) VALUES ('" +
                ID_game +  "', '" +
                numCoffees +  "', '" +
                minutesPlayed +  "');";
        SQLConnector.getInstance().insertQuery(query);
    }

    public List<Double> getAllStatsFromGame(int ID_game, int minutesPlayed) throws PersistanceException {
        try {
            ArrayList<Double> minutesList = new ArrayList<Double>();

            for(int i = 1; i <= minutesPlayed; i++){
                String query = "SELECT * FROM stats WHERE (ID_Game = '" + ID_game + "'AND Minute = '" + i + "');";
                ResultSet rs = SQLConnector.getInstance().selectQuery(query);
                if(!rs.next()){
                    return null;
                }
                minutesList.add(rs.getDouble("Num_Coffees"));
            }
            return minutesList;
        } catch (SQLException e) {
            throw new PersistanceException("Couldn't find stats in the database");
        }

    }
}
