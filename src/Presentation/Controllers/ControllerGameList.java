package Presentation.Controllers;

import Business.BusinessException;
import Business.Entities.EntityGame;
import Business.GameManager;
import Business.UserManager;
import Persistance.GameDAO;
import Persistance.PersistanceException;
import Persistance.sql.SQLGameDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerGameList implements ActionListener {

    private GameDAO gDao;
    private GameManager gameManager;
    static String name;
    //variable de la view

    public ControllerGameList() {
        this.gDao = new SQLGameDAO();
        this.gameManager = new GameManager();
        name = null;
        //view CUANDO SE CREE
    }

    private String findName(String command){
        String name = null;
        Boolean guardar = false;
        for(int i = 0; i < command.length(); i++){
            if(guardar){
                name += command.charAt(i);
            }
            if(command.charAt(i) == '_'){
                guardar = true;
            }
        }
        return name;
    }

    //TODO: Hay que crear la View de GameSelectionView

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command.equals("CONFIRMAR_BORRAR")){ //Se VALIDA borrar la partida
            gDao.deleteGame(name);
            //view.getApp().showPanel("MainMenuView");
        }else if(command.equals("CANCELAR")){ //No se VALIDA borrar la partida
            // OCULTAR CONFIRMACIÓN
        }else if(command.equals("CREAR")){
            //MOSTRAR PANTALLA PARA CREAR
        }else if(command.contains("RESUME")){ // RESUME_nameGame -> crear funcion para separarlo
            //SE INICIA UNA PARTIDA N EL ESTADO EN LA QUE SE ENCONTRABA LA SELECIONADA
            name = findName(command);
        }else if(command.contains("BORRAR")){
            //MOSTRAR CONFIRMACIÓN
            name = findName(command);
        }
    }
}
