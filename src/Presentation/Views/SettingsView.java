package Presentation.Views;


import Presentation.Controllers.ControllerGameList;
import Presentation.Controllers.ControllerSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * SettingsView provides an interface for the user to manage session-related actions
 * such as saving the game, logging out, finishing the game, or returning to gameplay.
 * All buttons are styled according to the CoffeeClicker UI theme.
 */
public class SettingsView extends JPanel {
    public static final String LOGOUT_COMMAND = "LOGOUT_COMMAND";
    public static final String BACK_COMMAND = "BACK_COMMAND";
    public static final String FINISH_COMMAND = "FINISH_COMMAND";
    public static final String SAVE_COMMAND = "SAVE_COMMAND";
    private Font coffeeClickerFont;
    private final HashMap<String, JButton> buttons = new HashMap<>();

    /**
     * Constructs and initializes the settings view layout and components.
     */
    public SettingsView() {
        setLayout(new BorderLayout());

        coffeeClickerFont = MainMenuView.loadCustomFont();

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setSize(new Dimension(765,579));

        JButton saveButton = newButton("SAVE GAME", SAVE_COMMAND);
        JButton finishButton = newButtonRed("FINISH GAME", FINISH_COMMAND);
        JButton backButton = newButton("BACK", BACK_COMMAND);
        JButton logoutButton = newButton("LOGOUT", LOGOUT_COMMAND);

        ImageIcon headerIcon = new ImageIcon(new ImageIcon("imgs/header3.png")
                .getImage().getScaledInstance(382, 85, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(headerIcon);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPane.add(Box.createVerticalStrut(50)); // Espaciado superior
        contentPane.add(label);
        contentPane.add(Box.createVerticalStrut(30)); // Espaciado superior
        contentPane.add(saveButton);
        contentPane.add(Box.createVerticalStrut(30)); // Espaciado superior
        contentPane.add(logoutButton);
        contentPane.add(Box.createVerticalStrut(30)); // Espaciado entre header y botones
        contentPane.add(backButton);
        contentPane.add(Box.createVerticalStrut(30)); // Espaciado entre botones
        contentPane.add(finishButton);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(contentPane);
        centerPanel.setOpaque(false); // Hace el fondo transparente

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Creates a red-styled warning button typically used for critical actions.
     * @param text the button label
     * @param actionCommand the command associated with the button
     * @return the styled red JButton
     */
    private JButton newButtonRed(String text, String actionCommand){
        ImageIcon buttonIcon = new ImageIcon(new ImageIcon("imgs/warning_button.png")
                .getImage().getScaledInstance(257, 82, Image.SCALE_DEFAULT));

        ImageIcon buttonHoverIcon = new ImageIcon(new ImageIcon("imgs/warning_button_selected.png")
                .getImage().getScaledInstance(257, 82, Image.SCALE_DEFAULT));

        JButton button = new JButton(text, buttonIcon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        if (coffeeClickerFont != null) {
            button.setFont(coffeeClickerFont.deriveFont(18f));
        }
        button.setBorderPainted(false);
        button.setForeground(new Color(255, 255, 255));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        ControllerGameList.mouseListener(button, buttonHoverIcon, buttonIcon);
        button.setActionCommand(actionCommand);

        buttons.put(actionCommand, button);

        return button;
    }

    /**
     * Creates a standard styled button with hover effects.
     * @param text the button label
     * @param actionCommand the command associated with the button
     * @return the styled JButton
     */
    private JButton newButton(String text, String actionCommand){
        ImageIcon buttonIcon = new ImageIcon(new ImageIcon("imgs/button.png")
                .getImage().getScaledInstance(257, 82, Image.SCALE_DEFAULT));

        ImageIcon buttonHoverIcon = new ImageIcon(new ImageIcon("imgs/button_selected.png")
                .getImage().getScaledInstance(257, 82, Image.SCALE_DEFAULT));

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
     * Assigns a ControllerSettings instance to all actionable buttons.
     * @param controller_settings the controller handling settings logic
     */
    public void setController(ControllerSettings controller_settings){
        for (JButton button : buttons.values()) {
            button.addActionListener(controller_settings);
        }
    }
}
