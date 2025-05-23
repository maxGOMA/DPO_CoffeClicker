package Persistance.sql;

import Persistance.PersistanceException;
import Persistance.StatsDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLStatsDAO implements StatsDAO {
    public SQLStatsDAO() {};

    public void saveNewStats(int ID_game, double numCoffees, int minutesPlayed){
        String query = "INSERT INTO stats (ID_Game, Minute, Num_Coffees) VALUES ('" +
                ID_game +  "', '" +
                minutesPlayed +  "', '" +
                numCoffees +  "');";
        SQLConnector.getInstance().insertQuery(query);
    }

    public ArrayList<Double> getAllStatsFromGame(int ID_game, int minutesPlayed) throws PersistanceException {
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

    public void deleteStats(int ID_game){
            String query = "DELETE FROM stats WHERE (ID_Game = '" + ID_game + "');";
            SQLConnector.getInstance().deleteQuery(query);
    }

}
