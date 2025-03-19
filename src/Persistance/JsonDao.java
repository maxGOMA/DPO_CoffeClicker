package Persistance;

//DAO_USERS - Persistance
//Guardaremos en estructuras "USER": user_name, password, email

import Business.Entities.EntityGame;


public interface JsonDao {
    /**
     * Carga la información de configuración desde el archivo JSON.
     */
    void loadInfo();

    /**
     * Obtiene el nombre de usuario de la configuración.
     *
     * @return el nombre de usuario como una cadena de texto.
     */
    String getUsername();

    /**
     * Obtiene la contraseña de la configuración.
     *
     * @return la contraseña como una cadena de texto.
     */
    String getPassword();

    /**
     * Obtiene el host de la base de datos desde la configuración.
     *
     * @return el host como una cadena de texto.
     */
    String getHost();

    /**
     * Obtiene el puerto de la base de datos desde la configuración.
     *
     * @return el puerto como un número entero.
     */
    int getPort();

    /**
     * Obtiene el nombre de la base de datos desde la configuración.
     *
     * @return el nombre de la base de datos como una cadena de texto.
     */
    String getDbname();
}




