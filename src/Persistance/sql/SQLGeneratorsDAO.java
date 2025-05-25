package Persistance.sql;

import Persistance.GeneratorsDAO;
import Persistance.PersistanceException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * SQLGeneratorsDAO is the SQL implementation of the {@link Persistance.GeneratorsDAO} interface.
 * It retrieves configuration data for each generator from the database, including cost,
 * production rates, upgrade costs, and scaling behavior.
 */
public class SQLGeneratorsDAO implements GeneratorsDAO {
    /**
     * Constructs a new SQLGeneratorsDAO.
     */
    public SQLGeneratorsDAO() {};

    /**
     * Retrieves the base cost of the specified generator from the database.
     *
     * @param generatorName the name of the generator
     * @return the base cost as an integer
     * @throws PersistanceException if data cannot be accessed
     */
    @Override
    public int getGeneratorBaseCost(String generatorName) throws PersistanceException {
        try {
            String query = "SELECT * FROM generators WHERE Name_Generator  = '" + generatorName + "';";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return  rs.getInt("Base_Cost");
        } catch (SQLException | PersistanceException  e) {
            throw new PersistanceException("Couldn't access generators information");
        }
    }

    /**
     * Retrieves the base coffee production value of the specified generator.
     * @param generatorName the name of the generator
     * @return the base production as a float
     * @throws PersistanceException if data cannot be accessed
     */
    @Override
    public float getGeneratorBaseProduction(String generatorName) throws PersistanceException {
        try {
            String query = "SELECT * FROM generators WHERE Name_Generator  = '" + generatorName + "';";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return  rs.getFloat("Base_Production");
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Couldn't access generators information");
        }
    }

    /**
     * Retrieves the time interval between productions for the specified generator.
     * @param generatorName the name of the generator
     * @return the interval in seconds as a float
     * @throws PersistanceException if data cannot be accessed
     */
    @Override
        public float getGeneratorProductionInterval(String generatorName) throws PersistanceException {
            try {
                String query = "SELECT * FROM generators WHERE Name_Generator  = '" + generatorName + "';";
                ResultSet rs = SQLConnector.getInstance().selectQuery(query);
                rs.next();
                return  rs.getFloat("Production_Interval");
            } catch (SQLException | PersistanceException e) {
                throw new PersistanceException("Couldn't access generators information");
            }
        }

    /**
     * Retrieves the cost increase multiplier applied when purchasing additional units of the generator.
     * @param generatorName the name of the generator
     * @return the cost multiplier as a float
     * @throws PersistanceException if data cannot be accessed
     */
    @Override
    public float getGeneratorCostIncrease(String generatorName) throws PersistanceException {
        try {
            String query = "SELECT * FROM generators WHERE Name_Generator  = '" + generatorName + "';";
            ResultSet rs = SQLConnector.getInstance().selectQuery(query);
            rs.next();
            return  rs.getFloat("Cost_Increase");
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Couldn't access generators information");
        }
    }

    /**
     * Retrieves the list of upgrade costs (silver, gold, diamond) for the specified generator.
     * @param generatorName the name of the generator
     * @return a list of upgrade costs in float format; null if not found
     * @throws PersistanceException if data cannot be accessed
     */
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
        } catch (SQLException | PersistanceException e) {
            throw new PersistanceException("Couldn't find stats in the database");
        }
    }
}
