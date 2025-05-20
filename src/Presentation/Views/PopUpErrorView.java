package Presentation.Views;

import javax.swing.*;
import java.awt.*;

public class PopUpErrorView extends JDialog {

    private JLabel messageLabel;
    private JLabel iconLabel;
    private JPanel centerPanel;
    private Font coffeeClickerFont;

    public PopUpErrorView(Frame parent, String errorMessage, ImageIcon icon) {
        super(parent, "Error", true);
        initComponents(errorMessage, icon);
    }

    private void initComponents(String errorMessage, ImageIcon icon) {
        // Redimensionar imagen si es necesario
        coffeeClickerFont = MainMenuView.loadCustomFont();

        Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        // Crear panel central con BoxLayout en Y
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Crear etiquetas
        messageLabel = new JLabel(errorMessage);
        messageLabel.setForeground(Color.RED);
        if (coffeeClickerFont != null) {
            messageLabel.setFont(coffeeClickerFont.deriveFont(18));
        }
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // centrado

        iconLabel = new JLabel(resizedIcon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // centrado

        // Añadir componentes con espacio entre ellos
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(iconLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(messageLabel);

        // Añadir panel al contenido
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        setIconImage(icon.getImage());
        // Tamaño y posicionamiento
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    public static void showErrorPopup(Component parent, String message, ImageIcon icon) {
        Frame frame = JOptionPane.getFrameForComponent(parent);
        PopUpErrorView dialog = new PopUpErrorView(frame, message, icon);
        dialog.setVisible(true);
    }
}
