package Persistance;

import java.util.ArrayList;

public interface GeneratorsDAO {
    int getGeneratorBaseCost(String generatorName) throws PersistanceException;

    float getGeneratorBaseProduction(String generatorName) throws PersistanceException;

    float getGeneratorProductionInterval(String generatorName) throws PersistanceException;

    float getGeneratorCostIncrease(String generatorName) throws PersistanceException;

    ArrayList<Float> getGeneratorUpgradesCosts(String generatorName) throws PersistanceException;
}
