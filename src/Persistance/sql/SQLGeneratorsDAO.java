package Persistance.sql;

import Persistance.GeneratorsDAO;
import Persistance.PersistanceException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGeneratorsDAO implements GeneratorsDAO {
        public SQLGeneratorsDAO() {};

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
        public ArrayList<Float> getGeneratorUpgradesCosts(String generatorName) throws PersistanceException {
            try {
                ArrayList<Float> upgradesCost = new ArrayList<>();
                    String query =  "SELECT * FROM generators WHERE Name_Generator  = '" + generatorName + "';";
                    ResultSet rs = SQLConnector.getInstance().selectQuery(query);
                if (!rs.next()) {
                    return null;
                }
                upgradesCost.add(rs.getFloat("Silver_Upgrade_Cost"));
                upgradesCost.add(rs.getFloat("Gold_Upgrade_Cost"));
                upgradesCost.add(rs.getFloat("Diamond_Upgrade_Cost"));
                return upgradesCost;
            } catch (SQLException e) {
                throw new PersistanceException("Couldn't find stats in the database");
            }
        }
}
