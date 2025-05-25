package Persistance.impl;

import Persistance.JsonDao;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * JsonConfigDAO is the implementation of {@link JsonDao} that loads configuration data
 * (such as database credentials and connection info) from a local JSON file.
 * The configuration is expected to be stored in a file located at "data/config.json".
 */
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
     * Constructs a JsonConfigDAO instance and loads the configuration file.
     * @throws FileNotFoundException if the JSON file does not exist or cannot be opened
     */
    public JsonConfigDAO() throws FileNotFoundException {
        File file = new File(JSON_FILE_PATH); // Ruta al archivo JSON
        if (!file.exists()) {
            throw new FileNotFoundException("Error: The items.json file can’t be accessed.");
        } else {
            try {
                fr = new FileReader(file); // Lector para abrir el archivo
                JsonElement element = JsonParser.parseReader(fr); // Parsea el contenido del archivo
                array = element.getAsJsonArray(); // Convierte el contenido a un JsonArray
            } catch (Exception e) {
                throw new FileNotFoundException("ERROR: Cant open file: config.json");
            }
        }
    }

    /**
     * Loads the configuration values from the JSON file into memory.
     * Values include: username, password, host, port, and database name.
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

    /**
     * @return the configured username
     */
    public String getUsername() { return username; }

    /**
     * @return the configured password
     */
    public String getPassword() { return password; }

    /**
     * @return the configured host
     */
    public String getHost() { return host; }

    /**
     * @return the configured port
     */
    public int getPort() { return port; }

    /**
     * @return the configured database name
     */
    public String getDbname() { return dbname; }

}
