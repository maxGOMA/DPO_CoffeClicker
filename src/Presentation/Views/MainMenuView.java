package Presentation.Views;

import Presentation.Controllers.ControllerMainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * MainMenuView is the initial screen shown to the user.
 * It provides navigation buttons to log in, register, or exit the application.
 * Each button is styled with a custom font and image, and all actions are linked to a controller.
 */
public class MainMenuView extends JPanel {
    public static final String VIEW_LOGIN = "VIEW_LOGIN";
    public static final String VIEW_REGISTER = "VIEW_REGISTER";
    public static final String VIEW_EXIT = "VIEW_EXIT";
    private final HashMap<String, JButton> buttons = new HashMap<>();
    private static Font coffeeClickerFont;

    /**
     * Constructs the main menu view, loading components, layout, fonts and buttons.
     */
    public MainMenuView() {
        setLayout(new BorderLayout());

        loadCustomFont();

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setSize(new Dimension(510,398));

        JLabel creditsLabel = new JLabel("Made by: Elena Balfagon Costa, Raul Corominas San Agustin, Max Gomez Manso, Alexia Julia Asin, Santiago Martinez Roques");
        if (coffeeClickerFont != null) {
            creditsLabel.setFont(coffeeClickerFont.deriveFont(8f));
        }
        creditsLabel.setForeground(Color.BLACK);
        creditsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.add(creditsLabel);
        topLeftPanel.setOpaque(false);
        add(topLeftPanel, BorderLayout.NORTH);

        JButton loginButton = newButton("LOGIN", VIEW_LOGIN);
        JButton registerButton = newButton("REGISTER", VIEW_REGISTER);
        JButton exitButton = newButton("EXIT", VIEW_EXIT);

        ImageIcon headerIcon = new ImageIcon(new ImageIcon("imgs/header3.png")
                .getImage().getScaledInstance(382, 85, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(headerIcon);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPane.add(Box.createVerticalStrut(25)); // Espaciado superior
        contentPane.add(label);
        contentPane.add(Box.createVerticalStrut(15)); // Espaciado entre header y botones
        contentPane.add(loginButton);
        contentPane.add(Box.createVerticalStrut(10)); // Espaciado entre botones
        contentPane.add(registerButton);
        contentPane.add(Box.createVerticalStrut(10)); // Espaciado entre botones
        contentPane.add(exitButton);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(contentPane);
        centerPanel.setOpaque(false); // Hace el fondo transparente

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Creates and returns a styled JButton with icon and hover effects.
     * @param text the display text of the button
     * @param actionCommand the command assigned to the button
     * @return the configured JButton instance
     */
    private JButton newButton(String text, String actionCommand){
        ImageIcon buttonIcon = new ImageIcon(new ImageIcon("imgs/button.png")
                .getImage().getScaledInstance(159, 51, Image.SCALE_DEFAULT));

        ImageIcon buttonHoverIcon = new ImageIcon(new ImageIcon("imgs/button_selected.png")
                .getImage().getScaledInstance(159, 51, Image.SCALE_DEFAULT));

        JButton button = new JButton(text, buttonIcon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        if (coffeeClickerFont != null) {
            button.setFont(coffeeClickerFont.deriveFont(18f));
        }
        button.setBorderPainted(false);
        button.setForeground(new Color(107, 41, 0));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(buttonHoverIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(buttonIcon);
            }
        });

        button.setActionCommand(actionCommand);

        buttons.put(actionCommand, button);

        return button;
    }

    /**
     * Loads the custom font from resources and registers it to the environment.
     * @return the loaded Font, or null if loading failed
     */
    public static Font loadCustomFont() {
        try {
            InputStream is = MainMenuView.class.getResourceAsStream("/res/font/CoffeeClicker.ttf");
            if (is != null) {
                coffeeClickerFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 12);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(coffeeClickerFont);
            } else {
                System.err.println("⚠ No se pudo cargar la fuente CoffeeClicker.ttf");
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return coffeeClickerFont;
    }

    /**
     * Assigns the controller to all buttons in the menu.
     * @param controller the ControllerMainMenu instance to assign
     */
    public void setController(ControllerMainMenu controller) {
        for (JButton button : buttons.values()) {
            button.addActionListener(controller);
        }
    }

}
