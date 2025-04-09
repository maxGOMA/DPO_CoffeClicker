package Presentation.Controllers;

import Business.GameManager;
import Persistance.PersistanceException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerCreationGame implements ActionListener {

    private GameManager gameManager;

    public ControllerCreationGame(){
        this.gameManager = new GameManager();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String name = null;

        if(command.equals("CREAR")){
            //MOSTRAR LO Q SEA
            try {
                gameManager.setGameToPersistance(name);
                //start game
            } catch (PersistanceException ex) {
                // ERROR
            }
        }
    }
}
