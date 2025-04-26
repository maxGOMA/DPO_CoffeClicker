package Persistance;

//DAO_USERS - Persistance
//Guardaremos en estructuras "USER": user_name, password, email

import Business.Entities.EntityGame;


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
     * @param name Nombre del juego a buscar en la base de datos.
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
     * @param ID_game El identificador único del juego que se desea eliminar.
     */
    public void deleteGame(int ID_game);

    /**
     * Elimina un juego del sistema según su identificador único.
     *
     * @param name El nombre del juego que se desea eliminar.
     * @param userName El nombre del jugador al que pertenece el juego.
     */
    public void deleteGame(String name, String userName);

    /**
     * Obtiene el identificador de una partida desde la base de datos.
     *
     * @param name El nombre del juego que se desea eliminar.
     * @param userName El nombre del jugador al que pertenece el juego.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    int getIdGame(String name, String userName) throws PersistanceException;

    /**
     * Obtiene el nombre de usuario asociado a una partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return El nombre de usuario como una cadena de texto.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    String getUsername(int ID_game) throws PersistanceException;

    /**
     * Obtiene la cantidad de cafés en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de cafés como un valor de tipo {@code Double}.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    Double getNumCoffees(int ID_game) throws PersistanceException;

    /**
     * Obtiene la cantidad de mejoras Deluxe en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Deluxe como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    int getDeluxe(int ID_game) throws PersistanceException;

    /**
     * Obtiene la cantidad de mejoras Supreme en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Supreme como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    int getSupreme(int ID_game) throws PersistanceException;

    /**
     * Obtiene la cantidad de mejoras Gold en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Gold como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    int getUpgradeGold(int ID_game) throws PersistanceException;

    /**
     * Obtiene la cantidad de mejoras Deluxe en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Deluxe como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    int getUpgradeDeluxe(int ID_game) throws PersistanceException;


    /**
     * Obtiene la cantidad de mejoras Supreme en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Supreme como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    int getUpgradeSupreme(int ID_game) throws PersistanceException;

    /**
     * Obtiene la cantidad de mejoras Clicker en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Número de mejoras Clicker como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    int getUpgradeClicker(int ID_game) throws PersistanceException;

    /**
     * Obtiene la cantidad de oro en la partida.
     *
     * @param ID_game Identificador único de la partida.
     * @return Cantidad de oro como un entero.
     * @throws PersistanceException Si ocurre un error al acceder a la base de datos.
     */
    int getGold(int ID_game) throws PersistanceException;

    String getName(int ID_game) throws PersistanceException;
}





