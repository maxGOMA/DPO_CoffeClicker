package Persistance;

import java.util.ArrayList;

/**
 * GeneratorsDAO defines the contract for retrieving configuration data related to generators
 * in the persistence layer. This includes base values, upgrade costs, and scaling behavior.
 */
public interface GeneratorsDAO {

    /**
     * Retrieves the base cost of a specific generator.
     * @param generatorName the name of the generator
     * @return the base cost as an integer
     * @throws PersistanceException if a persistence error occurs
     */
    int getGeneratorBaseCost(String generatorName) throws PersistanceException;

    /**
     * Retrieves the base production value of a specific generator.
     * @param generatorName the name of the generator
     * @return the base production as a float
     * @throws PersistanceException if a persistence error occurs
     */
    float getGeneratorBaseProduction(String generatorName) throws PersistanceException;

    /**
     * Retrieves the production interval for a specific generator.
     * @param generatorName the name of the generator
     * @return the time interval between productions, in seconds
     * @throws PersistanceException if a persistence error occurs
     */
    float getGeneratorProductionInterval(String generatorName) throws PersistanceException;

    /**
     * Retrieves the cost increase multiplier for a generator after each purchase.
     * @param generatorName the name of the generator
     * @return the cost increase multiplier as a float
     * @throws PersistanceException if a persistence error occurs
     */
    float getGeneratorCostIncrease(String generatorName) throws PersistanceException;

    /**
     * Retrieves the upgrade cost progression for a specific generator.
     * @param generatorName the name of the generator
     * @return a list of upgrade costs
     * @throws PersistanceException if a persistence error occurs
     */
    ArrayList<Float> getGeneratorUpgradesCosts(String generatorName) throws PersistanceException;
}
