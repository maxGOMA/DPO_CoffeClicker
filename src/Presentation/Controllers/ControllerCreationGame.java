package Presentation.Controllers;

import Business.BusinessException;
import Business.GameManager;
import Persistance.PersistanceException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerCreationGame implements ActionListener {
    private GameManager gameManager;

    public ControllerCreationGame (GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String name = null;

        if(command.equals("CREAR")){
            //MOSTRAR LO Q SEA
            try {
                if (gameManager.gameNameAlreadyRegisteredByUser(name)) {
                    //mostrar mensaje de nombre ya utilitzado
                } else {
                    //Le he cambiado el nombre de setGameToPersistance a createNewGame
                    gameManager.createNewGame(name);
                }
            } catch (BusinessException ex) {
                // Mostrar mensaje de error de acceso a la base de datos
            }
        }
    }
}
