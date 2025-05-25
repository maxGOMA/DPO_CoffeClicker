package Presentation.Views;

import javax.swing.*;
import java.awt.*;

/**
 * PopUpErrorView is a custom dialog that displays an error message along with an image icon.
 * It is used to show blocking error alerts in a modal window centered on the screen.
 */
public class PopUpErrorView extends JDialog {

    private JLabel messageLabel;
    private JLabel iconLabel;
    private JPanel centerPanel;
    private Font coffeeClickerFont;

    /**
     * Constructs and initializes the error popup dialog.
     * @param parent the parent frame of the dialog
     * @param errorMessage the error message to display
     * @param icon the icon to display alongside the message
     */
    public PopUpErrorView(Frame parent, String errorMessage, ImageIcon icon) {
        super(parent, "Error", true);
        initComponents(errorMessage, icon);
    }

    /**
     * Initializes the layout and styling of the popup content.
     * @param errorMessage the error message to show
     * @param icon the icon to be shown
     */
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

    /**
     * Static utility method to show the error popup on screen.
     * @param parent the parent component for context
     * @param message the error message to display
     * @param icon the image icon to use
     */
    public static void showErrorPopup(Component parent, String message, ImageIcon icon) {
        Frame frame = JOptionPane.getFrameForComponent(parent);
        PopUpErrorView dialog = new PopUpErrorView(frame, message, icon);
        dialog.setVisible(true);
    }
}
