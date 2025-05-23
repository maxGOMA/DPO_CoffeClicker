import Business.BusinessException;
import Presentation.CoffeeClickerApp;
import Presentation.Views.PopUpErrorView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            CoffeeClickerApp app = new CoffeeClickerApp();
        }catch(BusinessException e){
            PopUpErrorView.showErrorPopup(null, e.getMessage(), new ImageIcon("imgs/warning.png"));
        }
    }
}