package Presentation.Views;

import Presentation.Controllers.ControllerGameList;
import Presentation.Controllers.ControllerNewGame;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class NewGameView extends JPanel {
    public static final String CANCEL = "CANCEL";
    public static final String START = "START";
    public static final String LOG_OUT = "LOGOUT";

    private final HashMap<String, JButton> buttons = new HashMap<>();
    private static Font coffeeClickerFont;
    private static final HashMap<String, JLabel> errorLabels = new HashMap<>();
    private final HashMap<String, JTextField> textFields= new HashMap<>();
    private JPanel centerPanel;
    private JLabel txtTitle;
    private JPanel panelConfirmation;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel buttonPanel;
    private String name;

    public NewGameView(String name){
        this.name = name;
        coffeeClickerFont = MainMenuView.loadCustomFont();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setSize(new Dimension(510,398));

        txtTitle = newText("NEW GAME", 50, false);
        txtTitle.setForeground(new Color(127, 51, 0));
        txtTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelConfirmation = new JPanel(new GridBagLayout());
        panelConfirmation.setLayout(new BoxLayout(panelConfirmation, BoxLayout.X_AXIS));
        confirmButton = newButton("START", START);
        buttons.put(START, confirmButton);
        cancelButton = newButton("CANCEL", CANCEL);
        buttons.put(CANCEL, cancelButton);
        panelConfirmation.add(confirmButton);
        panelConfirmation.add(cancelButton);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setMaximumSize(new Dimension(1350, 100));
        JButton logOutButton = newButton("LOGOUT", LOG_OUT);
        buttonPanel.add(logOutButton);

        JPanel askUserPanel = askInfo("ENTER GAME NAME");
        askUserPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalStrut(130));
        centerPanel.add(txtTitle);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(askUserPanel);
        if(name != null){
            JLabel textCopy = newText("This game will be created with the statistics from \"" + name + "\".", 12, false);
            textCopy.setForeground(new Color(0, 0, 255));
            textCopy.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(Box.createVerticalStrut(20));
            centerPanel.add(textCopy);
        }
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(panelConfirmation);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JLabel newText(String txt, float size, boolean rotate) {
        JLabel txtTitle = new JLabel(txt);

        if (coffeeClickerFont != null) {
            txtTitle.setFont(coffeeClickerFont.deriveFont(size));
        }

        if (rotate) {
            String fullText = "   " + txt + "   ";
            final String[] rotatingText = { fullText };
            Timer timer = ControllerGameList.timerListener(rotatingText, txtTitle);
            ControllerGameList.txtTitleListener(timer, txtTitle, txt, rotatingText, fullText);
        }

        return txtTitle;
    }

    private JButton newButton(String text, String actionCommand){
        ImageIcon buttonIcon = new ImageIcon(new ImageIcon("imgs/button.png")
                .getImage().getScaledInstance(250, 70, Image.SCALE_DEFAULT));

        ImageIcon buttonHoverIcon = new ImageIcon(new ImageIcon("imgs/button_selected.png")
                .getImage().getScaledInstance(250, 70, Image.SCALE_DEFAULT));

        JButton button = new JButton(text, buttonIcon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        if (coffeeClickerFont != null) {
            button.setFont(coffeeClickerFont.deriveFont(25f));
        }
        button.setBorderPainted(false);
        button.setForeground(new Color(107, 41, 0));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        ControllerGameList.mouseListener(button, buttonHoverIcon, buttonIcon);
        button.setActionCommand(actionCommand);

        buttons.put(actionCommand, button);

        return button;
    }

    public JPanel askInfo(String text) {
        JPanel infoBox = new JPanel();
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
        infoBox.setMaximumSize(new Dimension(612, 150));
        infoBox.setPreferredSize(new Dimension(612, 150));
        infoBox.setMinimumSize(new Dimension(612, 150));
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
            errorLabel.setFont(coffeeClickerFont.deriveFont(12f));
        }
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorLabel.setVerticalAlignment(SwingConstants.TOP);
        errorLabel.setMaximumSize(new Dimension(600, 50));
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
                .getImage().getScaledInstance(612, 74, Image.SCALE_DEFAULT));

        BackgroundPanel panel = new BackgroundPanel(textbox_img);
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(612, 74));
        panel.setMaximumSize(new Dimension(612, 74));
        panel.setOpaque(false);

        JTextField textField = new JTextField(21);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setForeground(new Color(127, 51, 0));
        if (coffeeClickerFont != null) {
            textField.setFont(coffeeClickerFont.deriveFont(24f));
        }
        textField.setPreferredSize(new Dimension(612, 74));

        textFields.put(text, textField);

        panel.add(textField);
        frame.add(panel);
        frame.setOpaque(false);
        frame.setPreferredSize(new Dimension(612, 84));

        frame.setAlignmentX(Component.LEFT_ALIGNMENT);
        frame.setMaximumSize(new Dimension(612, 84));
        frame.setMinimumSize(new Dimension(612, 84));

        return frame;
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

    public static void showError(String field, String message) {
        JLabel errorLabel = errorLabels.get(field);
        if (errorLabel != null) {
            errorLabel.setText("<html>" + message.replaceAll("\n", "<br>") + "</html>");
        }
    }

    public HashMap<String, JTextField> getTextFields(){
        return textFields;
    }

    public void setController(ControllerNewGame controller_newGame){
        for (JButton button : buttons.values()) {
            button.addActionListener(controller_newGame);
        }
    }

    public void setGameName(String name) {
        this.name = name;
    }
}
