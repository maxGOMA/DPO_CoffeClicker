package Business;

import Business.Entities.EntityGame;
import Persistance.GameDAO;
import Persistance.PersistanceException;
import Persistance.sql.SQLGameDAO;


public class GameManager {
    private static EntityGame entityGame;
    private final GameDAO gameDAO;
    private UserManager um;

    public GameManager() {
        entityGame = null;
        this.gameDAO = new SQLGameDAO();
        this.um = um;
    }
    
    public void getGameFromPersistance(String name) throws PersistanceException {
        try{
            entityGame = gameDAO.loadInfoGame(name);
        }
        catch (PersistanceException e){
            throw new PersistanceException(e.getMessage());
        }

    }

    /**
     * Crea un nuevo juego con el nombre proporcionado si aún no existe uno con ese nombre.
     *
     * Este método verifica si ya existe un juego con el nombre especificado.
     * Si ya existe, retorna {@code false}. Si no existe, crea un nuevo objeto
     * {@link EntityGame} con valores iniciales predeterminados y lo guarda usando el {@link GameDAO}.
     *
     * @param name el nombre del juego que se desea crear.
     * @return {@code true} si el juego fue creado exitosamente, {@code false} si ya existe un juego con ese nombre.
     * @throws PersistanceException si ocurre un error al acceder a la base de datos durante la operación.
     */
    public void setGameToPersistance(String name) throws PersistanceException {
        try{
            if(gameDAO.loadInfoGame(name) != null){
                //Usar la showError()
            }else{
                gameDAO.setInfoGame(new EntityGame(name, 0, 0, 0, 0, 0, 0, 0 ,0.0, um.getUser().getUsername(), -1));
            }
        }catch(PersistanceException e){
            //ERROR: AL PILLAR INFO (PROBLEMA BASE DE DATOS)
            throw new PersistanceException(e.getMessage());
        }
    }

    public double IncrementCoffee() {
        return entityGame.IncrementCoffee();
    }
}