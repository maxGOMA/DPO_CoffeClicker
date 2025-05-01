package Presentation.Views;

import Presentation.Controllers.ControllerRegister;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class RegisterView extends JPanel {
    public static final String REGISTER_COMMAND = "REGISTER_COMMAND";
    public static final String BACK_COMMAND = "BACK_COMMAND";
    private final CoffeeClickerApp app;
    private final HashMap<String, JTextField> textFields = new HashMap<>();
    private static final HashMap<String, JLabel> errorLabels = new HashMap<>();
    private final HashMap<String, JButton> buttons = new HashMap<>();
    private Font coffeeClickerFont;


    public RegisterView(CoffeeClickerApp app) {
        this.app = app;
        setLayout(new BorderLayout());

        coffeeClickerFont = MainMenuView.loadCustomFont();

        ImageIcon headerIcon = new ImageIcon(new ImageIcon("imgs/header3.png")
                .getImage().getScaledInstance(382, 85, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(headerIcon);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel centerPanel = new JPanel(new GridBagLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(mainPanel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        mainPanel.add(label);
        mainPanel.add(Box.createVerticalStrut(25));
        JPanel askUserPanel = askInfo("USER");
        askUserPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(askUserPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        JPanel askEmailPanel = askInfo("EMAIL");
        askEmailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(askEmailPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        JPanel askPasswordPanel = askInfo("PASSWORD");
        askPasswordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(askPasswordPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        JPanel askConfirmationPanel = askInfo("CONFIRM PASSWORD");
        askConfirmationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(askConfirmationPanel);
        mainPanel.add(Box.createVerticalStrut(25));
        buttonPanel.add(newButton("BACK", BACK_COMMAND));
        buttonPanel.add(newButton("REGISTER", REGISTER_COMMAND));

        mainPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    public JPanel askInfo(String text) {
        JPanel infoBox = new JPanel();
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
        infoBox.setMaximumSize(new Dimension(308, 90));
        infoBox.setPreferredSize(new Dimension(308, 90));
        infoBox.setMinimumSize(new Dimension(308, 90));
        infoBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(text + ":");
        if (coffeeClickerFont != null) {
            label.setFont(coffeeClickerFont.deriveFont(12f));
        }
        label.setForeground(Color.black);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel textBox = addTextbox(text);

        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        if (coffeeClickerFont != null) {
            errorLabel.setFont(coffeeClickerFont.deriveFont(8f));
        }
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorLabel.setVerticalAlignment(SwingConstants.TOP);
        errorLabel.setMaximumSize(new Dimension(300, 25));
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
                .getImage().getScaledInstance(308, 37, Image.SCALE_DEFAULT));

        BackgroundPanel panel = new BackgroundPanel(textbox_img);
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(308, 37));
        panel.setMaximumSize(new Dimension(308, 37));
        panel.setOpaque(false);

        JTextField textField = new JTextField(21);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setForeground(new Color(127, 51, 0));
        if (coffeeClickerFont != null) {
            textField.setFont(coffeeClickerFont.deriveFont(12f));
        }
        textField.setPreferredSize(new Dimension(300, 35));

        textFields.put(text, textField);

        panel.add(textField);
        frame.add(panel);
        frame.setOpaque(false);
        frame.setPreferredSize(new Dimension(308, 37));

        frame.setAlignmentX(Component.LEFT_ALIGNMENT);
        frame.setMaximumSize(new Dimension(308, 43));
        frame.setMinimumSize(new Dimension(308, 43));

        return frame;
    }

    private JButton newButton(String text, String actionCommand) {
        ImageIcon buttonIcon = new ImageIcon(new ImageIcon("imgs/button.png")
                .getImage().getScaledInstance(159, 51, Image.SCALE_DEFAULT));

        ImageIcon buttonHoverIcon = new ImageIcon(new ImageIcon("imgs/button_selected.png")
                .getImage().getScaledInstance(159, 51, Image.SCALE_DEFAULT));

        JButton button = new JButton(text, buttonIcon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setFont(new Font("CoffeeClicker", Font.PLAIN, 18));
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

    public void clearFields() {
        for (JTextField field : textFields.values()) {
            field.setText(""); // Borra el texto de cada campo
        }
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

    public void setController(ControllerRegister controller) {
        for (JButton button : buttons.values()) {
            button.addActionListener(controller);
        }
    }

    public HashMap<String, JTextField> getTextFields() {
        return textFields;
    }

    public CoffeeClickerApp getApp() {
        return app;
    }
}