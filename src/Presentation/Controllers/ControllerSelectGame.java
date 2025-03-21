package Presentation.Controllers;

import Business.Entities.EntityGame;
import Business.UserManager;
import Persistance.GameDAO;
import Persistance.PersistanceException;
import Persistance.sql.SQLGameDAO;

public class ControllerSelectGame {

    private UserManager um;
    //variable de la view

    public ControllerSelectGame(UserManager um) {
        this.um = um;
        //view CUANDO SE CREE
    }

    //TODO: Hay que crear la View de GameSelectionView

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
    public Boolean createGame(String name) throws PersistanceException {
        GameDAO gDao = new SQLGameDAO();
        try{
            if(gDao.loadInfoGame(name) != null){
                return false;
                //Usar la showError()
            }else{
                gDao.setInfoGame(new EntityGame(name, 0, 0, 0, 0, 0, 0, 0 ,0.0, um.getUser().getUsername(), -1));
            }
        }catch(PersistanceException e){
            //ERROR: AL PILLAR INFO (PROBLEMA BASE DE DATOS)
            throw new PersistanceException(e.getMessage());
        }

        return true;
    }
}
