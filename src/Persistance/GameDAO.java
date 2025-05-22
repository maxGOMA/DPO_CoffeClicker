package Persistance;

//DAO_USERS - Persistance
//Guardaremos en estructuras "USER": user_name, password, email

import Business.Entities.EntityGame;
import Business.Entities.EntityUser;
import Persistance.sql.SQLConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
    void setInfoGame(EntityGame game);

    /**
     * Elimina un juego del sistema según su identificador único.
     *
     * @param IDgame El identificador único del juego que se desea eliminar.
     */
    public void deleteGame(int IDgame);

    /**
     * Elimina un juego del sistema según su identificador único.
     *
     * @param name     El nombre del juego que se desea eliminar.
     * @param userName El nombre del jugador al que pertenece el juego.
     */
    public void deleteGame(String name, String userName);

    void deleteAllGamesByUser(String username);

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

    String getName(int ID_game) throws PersistanceException;

    void setFinished(String userName, String name);

    List<EntityGame> getGamesByUser(String user) throws PersistanceException;

    ArrayList<String> getUserFinishedGameNames(String user) throws PersistanceException;

    ArrayList<Integer> getUserFinishedGameIDs(String user) throws PersistanceException;

    void updateGame(EntityGame game);
}






