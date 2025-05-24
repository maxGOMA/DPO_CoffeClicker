package Presentation.Views;

import Presentation.Controllers.ControllerConfirmation;
import Presentation.Controllers.ControllerGameList;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ConfirmationView extends JPanel {
    public static final String CONFIRM = "CONFIRM_DELETE";
    public static final String CANCEL = "CANCEL";
    private Font coffeeClickerFont;
    private final HashMap<String, JButton> buttons = new HashMap<>();

    private JPanel contentPane;
    private JPanel panelConfirmation;
    private JLabel textCopy;
    private JLabel text;
    private JLabel label;

    public ConfirmationView() {
        setLayout(new BorderLayout());

        coffeeClickerFont = MainMenuView.loadCustomFont();

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setSize(new Dimension(765,579));

        panelConfirmation = new JPanel(new GridBagLayout());
        panelConfirmation.setLayout(new BoxLayout(panelConfirmation, BoxLayout.X_AXIS));
        JButton confirmButton = newButtonRed("CONFIRM", CONFIRM);
        JButton cancelButton = newButton("CANCEL", CANCEL);
        panelConfirmation.add(confirmButton);
        panelConfirmation.add(cancelButton);

        textCopy = newText("ARE YOU SURE?", 30, false);
        textCopy.setForeground(new Color(229, 15, 49));
        textCopy.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon headerIcon = new ImageIcon(new ImageIcon("imgs/header3.png")
                .getImage().getScaledInstance(450, 100, Image.SCALE_DEFAULT));
        label = new JLabel(headerIcon);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        text = newText("", 15, false);
        text.setForeground(new Color(229, 15, 49));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPane.add(Box.createVerticalStrut(30)); // Espaciado superior
        contentPane.add(label);
        contentPane.add(Box.createVerticalStrut(30));
        contentPane.add(textCopy);
        contentPane.add(Box.createVerticalStrut(30));
        contentPane.add(text);
        contentPane.add(Box.createVerticalStrut(30)); // Espaciado entre header y botones
        contentPane.add(panelConfirmation);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(contentPane);
        centerPanel.setOpaque(false); // Hace el fondo transparente

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
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

    private JButton newButtonRed(String text, String actionCommand){
        ImageIcon buttonIcon = new ImageIcon(new ImageIcon("imgs/warning_button.png")
                .getImage().getScaledInstance(250, 70, Image.SCALE_DEFAULT));

        ImageIcon buttonHoverIcon = new ImageIcon(new ImageIcon("imgs/warning_button_selected.png")
                .getImage().getScaledInstance(250, 70, Image.SCALE_DEFAULT));

        JButton button = new JButton(text, buttonIcon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        if (coffeeClickerFont != null) {
            button.setFont(coffeeClickerFont.deriveFont(25f));
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


    public void setController(ControllerConfirmation controller_confirmation){
        for (JButton button : buttons.values()) {
            button.addActionListener(controller_confirmation);
        }
    }

    public void setMessage(String txt){
        if(txt.contains("account")){
            JLabel text2 = newText("All information related to this user will be permanently removed.", 15, false);
            text2.setForeground(new Color(229, 15, 49));
            text2.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPane.removeAll();
            contentPane.add(Box.createVerticalStrut(30)); // Espaciado superior
            contentPane.add(label);
            contentPane.add(Box.createVerticalStrut(30));
            contentPane.add(textCopy);
            contentPane.add(Box.createVerticalStrut(30));
            contentPane.add(text);
            contentPane.add(Box.createVerticalStrut(30));
            contentPane.add(text2);
            contentPane.add(Box.createVerticalStrut(30)); // Espaciado entre header y botones
            contentPane.add(panelConfirmation);
            contentPane.revalidate();
            contentPane.repaint();
        }else{
            contentPane.removeAll();
            contentPane.add(Box.createVerticalStrut(30)); // Espaciado superior
            contentPane.add(label);
            contentPane.add(Box.createVerticalStrut(30));
            contentPane.add(textCopy);
            contentPane.add(Box.createVerticalStrut(30));
            contentPane.add(text);
            contentPane.add(Box.createVerticalStrut(30)); // Espaciado entre header y botones
            contentPane.add(panelConfirmation);
            contentPane.revalidate();
            contentPane.repaint();
        }
        JLabel label = (JLabel) contentPane.getComponent(5);  // Obtener el JLabel
        label.setText(txt);
    }

    public JPanel getPanel(){
        return contentPane;
    }
}
