package Persistance.impl;

import Persistance.JsonDao;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonConfigDAO implements JsonDao {
    private String username;
    private String password;
    private String host;
    private int port;
    private String dbname;

    private JsonArray array; // Almacena los datos JSON cargados del archivo
    private FileReader fr;

    private static final String JSON_FILE_PATH = "data/config.json";

    /**
     * Constructor de la clase JsonConfigDAO.
     * Carga el archivo JSON de configuración y lo convierte en un JsonArray.
     *
     * @throws FileNotFoundException si el archivo JSON no se encuentra o no se puede abrir.
     */
    public JsonConfigDAO() throws FileNotFoundException {
        File file = new File(JSON_FILE_PATH); // Ruta al archivo JSON
        if (!file.exists()) {
            // Lanza una excepción si el archivo no existe
            throw new FileNotFoundException("Error: The items.json file can’t be accessed.");
        } else {
            try {
                fr = new FileReader(file); // Lector para abrir el archivo
                JsonElement element = JsonParser.parseReader(fr); // Parsea el contenido del archivo
                array = element.getAsJsonArray(); // Convierte el contenido a un JsonArray
            } catch (Exception e) {
                // Muestra un mensaje de error si ocurre una excepción
                throw new FileNotFoundException("ERROR: Cant open file: config.json");
            }
        }
    }

    /**
     * Método que carga la información de configuración desde el archivo JSON.
     * Extrae los valores de usuario, contraseña, host, puerto y nombre de la base de datos.
     */
    @Override
    public void loadInfo(){
        JsonElement jsonElement = array.get(0);
        this.username = jsonElement.getAsJsonObject().get("username").getAsString();
        this.password = jsonElement.getAsJsonObject().get("password").getAsString();
        this.host = jsonElement.getAsJsonObject().get("host").getAsString();
        this.port = jsonElement.getAsJsonObject().get("port").getAsInt();
        this.dbname = jsonElement.getAsJsonObject().get("dbname").getAsString();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getHost() { return host; }
    public int getPort() { return port; }
    public String getDbname() { return dbname; }

}
