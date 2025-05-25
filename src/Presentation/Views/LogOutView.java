package Presentation.Views;

import Presentation.Controllers.ControllerLogOut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * LogOutView is the view that presents options to either log out from the session
 * or delete the current user account. It provides visual components styled with
 * CoffeeClicker's theme and binds button actions to a controller.
 */
public class LogOutView extends JPanel {
    public static final String LOGOUT_COMMAND = "LOGOUT_COMMAND";
    public static final String DELETE_ACCOUNT_COMMAND = "DELETE_ACCOUNT_COMMAND";
    public static final String CONFIRMATION_COMMAND = "CONFIRMATION_COMMAND";
    private Font coffeeClickerFont;
    private final HashMap<String, JButton> buttons = new HashMap<>();

    /**
     * Constructs the logout view and initializes UI components, layout and event mapping.
     */
    public LogOutView() {
        setLayout(new BorderLayout());

        coffeeClickerFont = MainMenuView.loadCustomFont();

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setSize(new Dimension(765,579));

        JButton deleteAccountButton = newButton("<html><div style='text-align:center;'>DELETE<br>ACCOUNT</div></html>", DELETE_ACCOUNT_COMMAND);
        JButton logoutButton = newButton("LOGOUT", LOGOUT_COMMAND);

        ImageIcon headerIcon = new ImageIcon(new ImageIcon("imgs/header3.png")
                .getImage().getScaledInstance(382, 85, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(headerIcon);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPane.add(Box.createVerticalStrut(50)); // Espaciado superior
        contentPane.add(label);
        contentPane.add(Box.createVerticalStrut(30)); // Espaciado entre header y botones
        contentPane.add(deleteAccountButton);
        contentPane.add(Box.createVerticalStrut(20)); // Espaciado entre botones
        contentPane.add(logoutButton);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(contentPane);
        centerPanel.setOpaque(false); // Hace el fondo transparente

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Creates a new button styled according to the application's theme.
     * @param text the button label
     * @param actionCommand the action command to associate with the button
     * @return the constructed and styled JButton
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
     * Binds the given controller to all actionable buttons in the view.
     * @param controller_logOut the logout controller to register
     */
    public void setController(ControllerLogOut controller_logOut){
        for (JButton button : buttons.values()) {
            button.addActionListener(controller_logOut);
        }
    }
}
