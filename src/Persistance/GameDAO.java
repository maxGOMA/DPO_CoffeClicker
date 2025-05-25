package Persistance;

import Business.Entities.EntityGame;
import Business.Entities.EntityUser;
import Persistance.sql.SQLConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * GameDAO defines the contract for accessing and modifying game-related data
 * in the persistence layer. It includes methods for loading, saving, deleting,
 * and updating game entries from the database.
 */
public interface GameDAO {

    /**
     * Carga la información de una partida desde la base de datos.
     *
     * @param ID_game Identificador único de la partida.
     * @return Un objeto {@link EntityGame} con los datos de la partida.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    EntityGame loadInfoGame(int ID_game) throws PersistanceException;

    /**
     * Carga la información de un juego desde la base de datos utilizando su nombre.
     *
     * @param name     Nombre del juego a buscar en la base de datos.
     * @param userName Nombre del usuario al que pertenece el juego.
     * @return Un objeto {@link EntityGame} con los datos del juego.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    EntityGame loadInfoGame(String name, String userName) throws PersistanceException;

    /**
     * Inserta la información de un juego en la base de datos.
     *
     * @param game Objeto de tipo EntityGame que contiene la información del juego a insertar.
     * @throws PersistanceException Si ocurre un error al ejecutar la consulta en la base de datos.
     */
    void setInfoGame(EntityGame game) throws PersistanceException;

    /**
     * Elimina un juego del sistema según su identificador único.
     *
     * @param IDgame El identificador único del juego que se desea eliminar.
     */
    void deleteGame(int IDgame) throws PersistanceException;

    /**
     * Elimina un juego del sistema según su identificador único.
     *
     * @param name     El nombre del juego que se desea eliminar.
     * @param userName El nombre del jugador al que pertenece el juego.
     */
    void deleteGame(String name, String userName) throws PersistanceException;

    void deleteAllGamesByUser(String username) throws PersistanceException;

    /**
     * Obtiene el identificador de una partida desde la base de datos.
     *
     * @param name     El nombre del juego que se desea eliminar.
     * @param userName El nombre del jugador al que pertenece el juego.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    int getIdGame(String name, String userName) throws PersistanceException;

    /**
     * Obtiene la cantidad de mejoras Clicker en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Clicker como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    int getUpgradeClicker(int ID_game) throws PersistanceException;

    /**
     * Retrieves the name of the game using its ID.
     * @param ID_game the ID of the game
     * @return the name of the game
     * @throws PersistanceException if a database access error occurs
     */
    String getName(int ID_game) throws PersistanceException;

    /**
     * Marks a game as finished in the database.
     * @param userName the user who owns the game
     * @param name the name of the game to mark as finished
     * @throws PersistanceException if a database access error occurs
     */
    void setFinished(String userName, String name) throws PersistanceException;

    /**
     * Retrieves a list of games associated with the specified user.
     * @param user the username to query
     * @return a list of {@link EntityGame} instances
     * @throws PersistanceException if a database access error occurs
     */
    List<EntityGame> getGamesByUser(String user) throws PersistanceException;

    /**
     * Retrieves the names of finished games for a specific user.
     * @param user the username to query
     * @return a list of finished game names
     * @throws PersistanceException if a database access error occurs
     */
    ArrayList<String> getUserFinishedGameNames(String user) throws PersistanceException;

    /**
     * Retrieves the IDs of finished games for a specific user.
     * @param user the username to query
     * @return a list of finished game IDs
     * @throws PersistanceException if a database access error occurs
     */
    ArrayList<Integer> getUserFinishedGameIDs(String user) throws PersistanceException;

    /**
     * Updates an existing game's data in the database.
     * @param game the game entity containing updated information
     * @throws PersistanceException if a database access error occurs
     */
    void updateGame(EntityGame game) throws PersistanceException;
}






