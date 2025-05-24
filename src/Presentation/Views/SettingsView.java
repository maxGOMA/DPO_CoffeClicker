package Presentation.Views;


import Presentation.Controllers.ControllerGameList;
import Presentation.Controllers.ControllerSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class SettingsView extends JPanel {
    public static final String LOGOUT_COMMAND = "LOGOUT_COMMAND";
    public static final String BACK_COMMAND = "BACK_COMMAND";
    public static final String FINISH_COMMAND = "FINISH_COMMAND";
    public static final String SAVE_COMMAND = "SAVE_COMMAND";
    private Font coffeeClickerFont;
    private final HashMap<String, JButton> buttons = new HashMap<>();

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

        ControllerGameList.MouseListener(button, buttonHoverIcon, buttonIcon);
        button.setActionCommand(actionCommand);

        buttons.put(actionCommand, button);

        return button;
    }

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

    public void setController(ControllerSettings controller_settings){
        for (JButton button : buttons.values()) {
            button.addActionListener(controller_settings);
        }
    }
}
