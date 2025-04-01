package Presentation.Views;

import Presentation.Controllers.ControllerMainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class MainMenuView extends JPanel {
    public static final String VIEW_LOGIN = "VIEW_LOGIN";
    public static final String VIEW_REGISTER = "VIEW_REGISTER";
    public static final String VIEW_LOGOUT = "VIEW_LOGOUT";
    public static final String VIEW_EXIT = "VIEW_EXIT";
    private final CoffeeClickerApp app;
    private final HashMap<String, JButton> buttons = new HashMap<>();

    public MainMenuView(CoffeeClickerApp app) {
        this.app = app;
        setLayout(new BorderLayout());

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setSize(new Dimension(765,579));

        JLabel creditsLabel = new JLabel("Made by: Elena Balfagon Costa, Raul Corominas San Agustin, Max Gomez Manso, Alexia Julia Asin, Santiago Martinez Roques");
        creditsLabel.setFont(new Font("CoffeeClicker", Font.PLAIN, 8));
        creditsLabel.setForeground(Color.BLACK);
        creditsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.add(creditsLabel);
        topLeftPanel.setOpaque(false);
        add(topLeftPanel, BorderLayout.NORTH);

        JButton loginButton = newButton("LOGIN", VIEW_LOGIN);
        JButton registerButton = newButton("REGISTER", VIEW_REGISTER);
        JButton logoutButton = newButton("LOGOUT", VIEW_LOGOUT);
        JButton exitButton = newButton("EXIT", VIEW_EXIT);

        ImageIcon headerIcon = new ImageIcon(new ImageIcon("imgs/header3.png")
                .getImage().getScaledInstance(765, 171, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(headerIcon);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPane.add(Box.createVerticalStrut(50)); // Espaciado superior
        contentPane.add(label);
        contentPane.add(Box.createVerticalStrut(30)); // Espaciado entre header y botones
        contentPane.add(loginButton);
        contentPane.add(Box.createVerticalStrut(20)); // Espaciado entre botones
        contentPane.add(registerButton);
        contentPane.add(Box.createVerticalStrut(20)); // Espaciado entre botones
        contentPane.add(logoutButton);
        contentPane.add(Box.createVerticalStrut(20)); // Espaciado entre botones
        contentPane.add(exitButton);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(contentPane);
        centerPanel.setOpaque(false); // Hace el fondo transparente

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton newButton(String text, String actionCommand){
        ImageIcon buttonIcon = new ImageIcon(new ImageIcon("imgs/button.png")
                .getImage().getScaledInstance(319, 102, Image.SCALE_DEFAULT));

        ImageIcon buttonHoverIcon = new ImageIcon(new ImageIcon("imgs/button_selected.png")
                .getImage().getScaledInstance(319, 102, Image.SCALE_DEFAULT));

        JButton button = new JButton(text, buttonIcon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setFont(new Font("CoffeeClicker", Font.PLAIN, 36));
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

    public void setController(ControllerMainMenu controller) {
        for (JButton button : buttons.values()) {
            button.addActionListener(controller);
        }
    }

    public CoffeeClickerApp getApp(){
        return app;
    }
}
