package Persistance;

public interface GeneratorsDAO {
    int getGeneratorBaseCost(String generatorName) throws PersistanceException;

    float getGeneratorBaseProduction(String generatorName) throws PersistanceException;

    float getGeneratorProductionInterval(String generatorName) throws PersistanceException;

    float getGeneratorCostIncrease(String generatorName) throws PersistanceException;
}
