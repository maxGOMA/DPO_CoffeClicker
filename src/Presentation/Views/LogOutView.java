package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LogOutView extends JPanel {
    public static final String LOGOUT_COMMAND = "LOGOUT_COMMAND";
    public static final String DELETE_ACCOUNT_COMMAND = "DELETE_ACCOUNT_COMMAND";
    public static final String CONFIRMATION_COMMAND = "CONFIRMATION_COMMAND";
    private final CoffeeClickerApp app;
    private Font coffeeClickerFont;

    public LogOutView(CoffeeClickerApp app) {
        this.app = app;
        setLayout(new BorderLayout());

        coffeeClickerFont = MainMenuView.loadCustomFont();

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setSize(new Dimension(765,579));

        JButton deleteAccountButton = newButton("<html><div style='text-align:center;'>DELETE<br>ACCOUNT</div></html>");
        JButton logoutButton = newButton("LOGOUT");

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

    private JButton newButton(String text){
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

        return button;
    }

    public CoffeeClickerApp getApp(){
        return app;
    }
}
