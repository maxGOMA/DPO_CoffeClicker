package Persistance.sql;

import Business.Entities.EntityUser;
import Persistance.GeneratorsDAO;
import Persistance.PersistanceException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGeneratorsDAO implements GeneratorsDAO {

        @Override
        public int getGeneratorBaseCost(String generatorName) throws PersistanceException {
            try {
                String query = "SELECT * FROM generators WHERE Name_Generator  = '" + generatorName + "';";
                ResultSet rs = SQLConnector.getInstance().selectQuery(query);
                rs.next();
                return  rs.getInt("Base_Cost");
            } catch (SQLException e) {
                throw new PersistanceException("Couldn't access generators information");
            }
        }

        @Override
        public float getGeneratorBaseProduction(String generatorName) throws PersistanceException {
            try {
                String query = "SELECT * FROM generators WHERE Name_Generator  = '" + generatorName + "';";
                ResultSet rs = SQLConnector.getInstance().selectQuery(query);
                rs.next();
                return  rs.getFloat("Base_Production");
            } catch (SQLException e) {
                throw new PersistanceException("Couldn't access generators information");
            }
        }

    @Override
        public float getGeneratorProductionInterval(String generatorName) throws PersistanceException {
            try {
                String query = "SELECT * FROM generators WHERE Name_Generator  = '" + generatorName + "';";
                ResultSet rs = SQLConnector.getInstance().selectQuery(query);
                rs.next();
                return  rs.getFloat("Production_Interval");
            } catch (SQLException e) {
                throw new PersistanceException("Couldn't access generators information");
            }
        }

        @Override
        public float getGeneratorCostIncrease(String generatorName) throws PersistanceException {
            try {
                String query = "SELECT * FROM generators WHERE Name_Generator  = '" + generatorName + "';";
                ResultSet rs = SQLConnector.getInstance().selectQuery(query);
                rs.next();
                return  rs.getFloat("Cost_Increase");
            } catch (SQLException e) {
                throw new PersistanceException("Couldn't access generators information");
            }
        }

        @Override
        public ArrayList<Double> getGeneratorUpgradesCosts(String generatorName) throws PersistanceException {
            try {
                ArrayList<Double> upgradesCost = new ArrayList<Double>();
                    String query =  "SELECT * FROM generators WHERE Name_Generator  = '" + generatorName + "';";
                    ResultSet rs = SQLConnector.getInstance().selectQuery(query);
                    if(!rs.next()){
                        return null;
                    }
                upgradesCost.add(rs.getDouble("Num_Coffees"));
                return upgradesCost;
            } catch (SQLException e) {
                throw new PersistanceException("Couldn't find stats in the database");
            }
        }
}
