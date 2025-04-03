package Presentation.Views;

import Presentation.Controllers.ControllerLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class LoginView extends JPanel {
    public static final String LOGIN_COMMAND = "LOGIN_COMMAND";
    public static final String BACK_COMMAND = "BACK_COMMAND";

    private final HashMap<String, JTextField> textFields = new HashMap<>();
    private static final HashMap<String, JLabel> errorLabels = new HashMap<>();
    private final HashMap<String, JButton> buttons = new HashMap<>();
    private final CoffeeClickerApp app;
    private Font coffeeClickerFont;

    public LoginView (CoffeeClickerApp app) {
        this.app = app;
        setLayout(new BorderLayout());

        coffeeClickerFont = MainMenuView.loadCustomFont();

        ImageIcon headerIcon = new ImageIcon(new ImageIcon("imgs/header3.png")
                .getImage().getScaledInstance(765, 171, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(headerIcon);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel centerPanel = new JPanel(new GridBagLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(mainPanel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        mainPanel.add(label);
        mainPanel.add(Box.createVerticalStrut(50));
        JPanel askUserPanel = askInfo("USER/EMAIL");
        askUserPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(askUserPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        JPanel askPasswordPanel = askInfo("PASSWORD");
        askPasswordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(askPasswordPanel);
        mainPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(newButton("BACK", BACK_COMMAND));
        buttonPanel.add(newButton("LOGIN", LOGIN_COMMAND));

        mainPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    public JPanel askInfo(String text) {
        JPanel infoBox = new JPanel();
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
        infoBox.setPreferredSize(new Dimension(617, 140));
        infoBox.setMinimumSize(new Dimension(617, 140));
        infoBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(text + ":");
        if (coffeeClickerFont != null) {
            label.setFont(coffeeClickerFont.deriveFont(24f));
        }
        label.setForeground(Color.black);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel textBox = addTextbox(text);

        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        if (coffeeClickerFont != null) {
            errorLabel.setFont(coffeeClickerFont.deriveFont(10f));
        }
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorLabel.setVerticalAlignment(SwingConstants.TOP);
        errorLabel.setMaximumSize(new Dimension(600, 20));
        errorLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

        errorLabels.put(text, errorLabel);

        infoBox.add(label);
        infoBox.add(textBox);
        infoBox.add(errorLabel);

        return infoBox;
    }

    public JPanel addTextbox(String text) {
        JPanel frame = new JPanel();

        ImageIcon textbox_img = new ImageIcon(new ImageIcon("imgs/textBox.png")
                .getImage().getScaledInstance(617, 75, Image.SCALE_SMOOTH));

        BackgroundPanel panel = new BackgroundPanel(textbox_img);
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(617, 75));
        panel.setMaximumSize(new Dimension(617, 75));
        panel.setOpaque(false);

        JTextField textField = new JTextField(20);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setForeground(new Color(127, 51, 0));
        if (coffeeClickerFont != null) {
            textField.setFont(coffeeClickerFont.deriveFont(24f));
        }
        textField.setPreferredSize(new Dimension(600, 70));

        textFields.put(text, textField);

        panel.add(textField);
        frame.add(panel);
        frame.setOpaque(false);
        frame.setPreferredSize(new Dimension(617, 75));

        frame.setAlignmentX(Component.LEFT_ALIGNMENT);
        frame.setMaximumSize(new Dimension(617, 85));
        frame.setMinimumSize(new Dimension(617, 85));

        return frame;
    }

    private JButton newButton(String text, String actionCommand) {
        ImageIcon buttonIcon = new ImageIcon(new ImageIcon("imgs/button.png")
                .getImage().getScaledInstance(319, 102, Image.SCALE_DEFAULT));

        ImageIcon buttonHoverIcon = new ImageIcon(new ImageIcon("imgs/button_selected.png")
                .getImage().getScaledInstance(319, 102, Image.SCALE_DEFAULT));

        JButton button = new JButton(text, buttonIcon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        if (coffeeClickerFont != null) {
            button.setFont(coffeeClickerFont.deriveFont(36f));
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

    public static void showError(String field, String message) {
        JLabel errorLabel = errorLabels.get(field);
        if (errorLabel != null) {
            errorLabel.setText("<html>" + message.replaceAll("\n", "<br>") + "</html>");
        }
    }

    public void clearErrorMessages() {
        for (JLabel label : errorLabels.values()) {
            label.setText(" ");
        }
    }

    public void clearFields() {
        for (JTextField field : textFields.values()) {
            field.setText("");
        }
    }

    public void setController(ControllerLogin controller) {
        for (JButton button : buttons.values()) {
            button.addActionListener(controller);
        }
    }

    public HashMap<String, JTextField> getTextFields(){
        return textFields;
    }

    public CoffeeClickerApp getApp() {
        return app;
    }
}
