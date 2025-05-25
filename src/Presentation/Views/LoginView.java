package Presentation.Views;
import Presentation.Controllers.ControllerLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * LoginView represents the user interface panel where users can enter
 * their credentials to log into the game. It includes fields for user/email
 * and password input, as well as login and back buttons. It also supports
 * error display and interaction with a controller.
 */
public class LoginView extends JPanel {
    public static final String LOGIN_COMMAND = "LOGIN_COMMAND";
    public static final String BACK_COMMAND = "BACK_COMMAND";

    private final HashMap<String, JTextField> textFields= new HashMap<>();
    private static final HashMap<String, JLabel> errorLabels = new HashMap<>();
    private final HashMap<String, JButton> buttons = new HashMap<>();
    private Font coffeeClickerFont;

    /**
     * Constructs and initializes the login view layout and components.
     */
    public LoginView () {
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
        JPanel askPasswordPanel = askInfoPassword("PASSWORD");
        askPasswordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(askPasswordPanel);
        mainPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(newButton("BACK", BACK_COMMAND));
        buttonPanel.add(newButton("LOGIN", LOGIN_COMMAND));

        mainPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Creates and returns a JPanel for password input.
     * @param text the label of the input field
     * @return the assembled password input panel
     */
    public JPanel askInfoPassword(String text) {
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

        JPanel textBox = addTextboxPassword(text);

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

    /**
     * Creates and returns a JPanel for standard text input.
     * @param text the label of the input field
     * @return the assembled input panel
     */
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

    /**
     * Creates and returns a textbox component for regular text.
     * @param text the identifier key for the field
     * @return a JPanel containing the styled textbox
     */
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

    /**
     * Creates and returns a textbox component specifically for passwords.
     * @param text the identifier key for the password field
     * @return a JPanel containing the styled password textbox
     */
    public JPanel addTextboxPassword(String text) {
        JPanel frame = new JPanel();

        ImageIcon textbox_img = new ImageIcon(new ImageIcon("imgs/textBox.png")
                .getImage().getScaledInstance(308, 37, Image.SCALE_DEFAULT));

        BackgroundPanel panel = new BackgroundPanel(textbox_img);
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(308, 37));
        panel.setMaximumSize(new Dimension(308, 37));
        panel.setOpaque(false);

        // Usar JPasswordField en lugar de JTextField
        JPasswordField passwordField = new JPasswordField(21);
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setForeground(new Color(127, 51, 0));

        // Establecer el caracter a mostrar (por ejemplo, un punto)
        passwordField.setEchoChar('*');  // Cambiar el carácter a un punto
        if (coffeeClickerFont != null) {
            passwordField.setFont(coffeeClickerFont.deriveFont(12f));
        }
        passwordField.setPreferredSize(new Dimension(300, 35));

        textFields.put(text, passwordField);

        panel.add(passwordField);
        frame.add(panel);
        frame.setOpaque(false);
        frame.setPreferredSize(new Dimension(308, 37));

        frame.setAlignmentX(Component.LEFT_ALIGNMENT);
        frame.setMaximumSize(new Dimension(308, 43));
        frame.setMinimumSize(new Dimension(308, 43));

        return frame;
    }

    /**
     * Creates and configures a button with hover effects.
     * @param text the display text of the button
     * @param actionCommand the action command to be assigned
     * @return the configured JButton instance
     */
    private JButton newButton(String text, String actionCommand) {
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
     * Displays an error message beneath the field associated with the provided key.
     * @param field the identifier of the field
     * @param message the error message to display
     */
    public static void showError(String field, String message) {
        JLabel errorLabel = errorLabels.get(field);
        if (errorLabel != null) {
            errorLabel.setText("<html>" + message.replaceAll("\n", "<br>") + "</html>");
        }
    }

    /**
     * Clears all visible error messages in the form.
     */
    public void clearErrorMessages() {
        for (JLabel label : errorLabels.values()) {
            label.setText(" ");
        }
    }

    /**
     * Clears all user input fields.
     */
    public void clearFields() {
        for (JTextField field : textFields.values()) {
            field.setText("");
        }
    }

    /**
     * Binds a controller to all actionable components in this view.
     * @param controller the controller to set
     */
    public void setController(ControllerLogin controller) {
        for (JButton button : buttons.values()) {
            button.addActionListener(controller);
        }
    }

    /**
     * Provides access to the internal map of input fields.
     * @return a HashMap linking field keys to JTextField components
     */
    public HashMap<String, JTextField> getTextFields(){
        return textFields;
    }

}
