package Presentation.Views;

import Business.Entities.EntityGame;
import Presentation.Controllers.ControllerGameList;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class GameListView extends JPanel{
    public static final String NEW_GAME = "NEWGAME";
    public static final String LOG_OUT = "LOGOUT";
    public static final String START = "START";
    public static final String COPY = "COPY";
    public static final String DELETE = "DELETE";
    public static final String CANCEL = "CANCEL";
    public static final String CONFIRM = "CONFIRM_DELETE";

    private final HashMap<String, JButton> buttons = new HashMap<>();
    private static Font coffeeClickerFont;
    private static int width;
    private static int height;
    private List<EntityGame> games;
    private final CoffeeClickerApp app;
    private static HashMap<String, BackgroundPanel> panelsGames = new HashMap<>();
    private Image backgroundImage;

    private JPanel centerPanel;
    private JPanel buttonPanel;
    private JLabel txtTitle;
    private JPanel backGroundScrollPanel;
    private JButton newGameButton;
    private JPanel panelConfirmation;
    private JButton confirmButton;
    private JButton cancelButton;
    private static JPanel internPanel;

    public GameListView(CoffeeClickerApp app){
        this.app = app;
        width = 1350;
        height = 1080;
        coffeeClickerFont = MainMenuView.loadCustomFont();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        panelConfirmation = new JPanel(new GridBagLayout());
        panelConfirmation.setLayout(new BoxLayout(panelConfirmation, BoxLayout.X_AXIS));
        confirmButton = newButtonRed("CONFIRM", CONFIRM);
        buttons.put(CONFIRM, confirmButton);
        cancelButton = newButton("CANCEL", CANCEL);
        buttons.put(CANCEL, cancelButton);
        panelConfirmation.add(confirmButton);
        panelConfirmation.add(cancelButton);
        panelConfirmation.setVisible(false);

        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setSize(new Dimension(510,398));


        Image backgroundImage = new ImageIcon("imgs/back_ground_scroll.png")
                .getImage().getScaledInstance(100, 115, Image.SCALE_DEFAULT);
        backGroundScrollPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Escalado al tamaño del panel
            }
        };
        backGroundScrollPanel.setMaximumSize(new Dimension(700, 450));
        backGroundScrollPanel.setOpaque(false);

        txtTitle = newText("SELECT GAME", 50, false);
        txtTitle.setForeground(new Color(127, 51, 0));
        txtTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        newGameButton = newButton("NEW GAME", NEW_GAME);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setMaximumSize(new Dimension(1350, 100));
        //buttonPanel.setBorder(new LineBorder(Color.PINK, 5));
        JButton logOutButton = newButton("LOGOUT", LOG_OUT);
        buttonPanel.add(logOutButton);

    }

    private JScrollPane configureScrollPanel(JScrollPane scrollPane){
        internPanel = new JPanel();
        internPanel.setLayout(new BoxLayout(internPanel, BoxLayout.Y_AXIS));
        internPanel.setOpaque(false);
        internPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        for(EntityGame e : games){
            if(e.getFinished() == 0) {
                JPanel aux = newButtonGame(e.getName());
                aux.setAlignmentX(Component.CENTER_ALIGNMENT);
                internPanel.add(Box.createVerticalStrut(20));
                internPanel.add(aux);
            }
        }

        scrollPane = new JScrollPane(internPanel);
        scrollPane.setBorder(null); // Sin borde adicional
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(700, 440));
        scrollPane.setMaximumSize(new Dimension(700, 440));
        scrollPane.setMinimumSize(new Dimension(700, 440));
        scrollPane.getVerticalScrollBar().setUnitIncrement(4);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        return scrollPane;
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

        ControllerGameList.MouseListener(button, buttonHoverIcon, buttonIcon);
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

        ControllerGameList.MouseListener(button, buttonHoverIcon, buttonIcon);
        button.setActionCommand(actionCommand);

        buttons.put(actionCommand, button);

        return button;
    }

    private JPanel newButtonGame(String text){
        ImageIcon startIcon = new ImageIcon(new ImageIcon("imgs/start.png")
                .getImage().getScaledInstance(50, 80, Image.SCALE_DEFAULT));
        ImageIcon startPressedIcon = new ImageIcon(new ImageIcon("imgs/start_pressed.png")
                .getImage().getScaledInstance(50, 80, Image.SCALE_DEFAULT));

        ImageIcon copyIcon = new ImageIcon(new ImageIcon("imgs/copy.png")
                .getImage().getScaledInstance(60, 70, Image.SCALE_DEFAULT));
        ImageIcon copyPressedIcon = new ImageIcon(new ImageIcon("imgs/copy_pressed.png")
                .getImage().getScaledInstance(60, 70, Image.SCALE_DEFAULT));

        ImageIcon deleteIcon = new ImageIcon(new ImageIcon("imgs/delete.png")
                .getImage().getScaledInstance(65, 75, Image.SCALE_DEFAULT));
        ImageIcon deletePressedIcon = new ImageIcon(new ImageIcon("imgs/delete_pressed.png")
                .getImage().getScaledInstance(65, 75, Image.SCALE_DEFAULT));

        backgroundImage = new ImageIcon("imgs/button_game.png")
                .getImage().getScaledInstance(600, 115, Image.SCALE_DEFAULT);

        BackgroundPanel panelGame = new BackgroundPanel();
        panelGame.setBackgroundImage(backgroundImage);
        panelGame.setLayout(new BoxLayout(panelGame, BoxLayout.X_AXIS));
        panelGame.setMaximumSize(new Dimension(600, 140));
        panelGame.setPreferredSize(new Dimension(600, 140));
        panelGame.setOpaque(false);

        panelsGames.put(text, panelGame);

        JPanel txtPanel = new JPanel();
        txtPanel.setLayout(new BoxLayout(txtPanel, BoxLayout.X_AXIS));
        txtPanel.setMaximumSize(new Dimension(278, 50));
        txtPanel.setPreferredSize(new Dimension(278, 50));
        txtPanel.setOpaque(false);
        JLabel nameGame;
        if(text.length() >= 12){
            nameGame = newText(text, 25, true);
        }else {
            nameGame = newText(text, 25, false);
        }
        nameGame.setForeground(new Color(127, 51, 0));
        txtPanel.add(nameGame);

        JButton start = new JButton(startIcon);
        ControllerGameList.MouseListener(start, startPressedIcon, startIcon);
        start.setBorderPainted(false);
        start.setContentAreaFilled(false);
        start.setFocusPainted(false);
        start.setOpaque(false);
        start.setActionCommand(START + "_" + text);
        buttons.put(START + "_" + text, start);

        JButton copy = new JButton(copyIcon);
        ControllerGameList.MouseListener(copy, copyPressedIcon, copyIcon);
        copy.setBorderPainted(false);
        copy.setContentAreaFilled(false);
        copy.setFocusPainted(false);
        copy.setOpaque(false);
        copy.setActionCommand(COPY + "_" + text);
        buttons.put(COPY + "_" + text, copy);

        JButton delete = new JButton(deleteIcon);
        ControllerGameList.MouseListener(delete, deletePressedIcon, deleteIcon);
        delete.setBorderPainted(false);
        delete.setContentAreaFilled(false);
        delete.setFocusPainted(false);
        delete.setOpaque(false);
        delete.setActionCommand(DELETE + "_" + text);
        buttons.put(DELETE + "_" + text, delete);

        panelGame.add(Box.createHorizontalStrut(20));
        panelGame.add(start);
        panelGame.add(Box.createHorizontalStrut(5));
        panelGame.add(txtPanel);
        panelGame.add(Box.createHorizontalStrut(15));
        panelGame.add(copy);
        panelGame.add(Box.createHorizontalStrut(5));
        panelGame.add(delete);

        return panelGame;
    }

    public void setController(ControllerGameList controller_gameList){
        games = controller_gameList.getGamesByUser();
        start();
        for (JButton button : buttons.values()) {
            button.addActionListener(controller_gameList);
        }
    }

    private void start(){
        JScrollPane scrollPane = null;
        scrollPane = configureScrollPanel(scrollPane);
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(txtTitle);
        centerPanel.add(Box.createVerticalStrut(20));
        backGroundScrollPanel.add(scrollPane);
        centerPanel.add(backGroundScrollPanel);
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(newGameButton);
        centerPanel.add(panelConfirmation);

        add(centerPanel, BorderLayout.CENTER);
    }

//    public static void updateCenterPanel() {
//        internPanel.revalidate();
//        internPanel.repaint();
//    }

    public CoffeeClickerApp getApp() {
        return app;
    }

    public JButton getnewGameButton(){
        return newGameButton;
    }

    public JPanel getPanelConfirmation() {
        return panelConfirmation;
    }

    public static void deleteGameSelectedView(String name) {
        Component aux = internPanel.getComponent(0);
        for(Component c: internPanel.getComponents()){
            if(c instanceof JPanel){
                if(c == panelsGames.get(name)){
                    internPanel.remove(aux);
                    internPanel.remove(c);
                }
            }
            aux = c;
        }

    }

    public void clearGameSelected(){
        Image backgroundImageRest = new ImageIcon("imgs/button_game.png")
                .getImage().getScaledInstance(600, 115, Image.SCALE_DEFAULT);
        for(BackgroundPanel p: panelsGames.values()) {
            p.setBackgroundImage(backgroundImageRest);
            for(Component c: p.getComponents()){
                if(c instanceof JButton){
                    if (((JButton) c).getActionCommand().contains(START)){
                        ImageIcon newIcon = new ImageIcon(new ImageIcon("imgs/start.png")
                                .getImage().getScaledInstance(50, 80, Image.SCALE_DEFAULT));
                        ImageIcon startPressedIcon = new ImageIcon(new ImageIcon("imgs/start_pressed.png")
                                .getImage().getScaledInstance(50, 80, Image.SCALE_DEFAULT));
                        ((JButton)c).setIcon(newIcon);
                        ControllerGameList.MouseListener(((JButton)c), startPressedIcon, newIcon);
                    }else if(((JButton) c).getActionCommand().contains(COPY)){
                        ImageIcon newIcon = new ImageIcon(new ImageIcon("imgs/copy.png")
                                .getImage().getScaledInstance(60, 70, Image.SCALE_DEFAULT));
                        ImageIcon copyPressedIcon = new ImageIcon(new ImageIcon("imgs/copy_pressed.png")
                                .getImage().getScaledInstance(60, 70, Image.SCALE_DEFAULT));
                        ((JButton)c).setIcon(newIcon);
                        ControllerGameList.MouseListener(((JButton)c), copyPressedIcon, newIcon);
                    }else if(((JButton) c).getActionCommand().contains(DELETE)){
                        ImageIcon newIcon = new ImageIcon(new ImageIcon("imgs/delete.png")
                                .getImage().getScaledInstance(65, 75, Image.SCALE_DEFAULT));
                        ImageIcon deletePressedIcon = new ImageIcon(new ImageIcon("imgs/delete_pressed.png")
                                .getImage().getScaledInstance(65, 75, Image.SCALE_DEFAULT));
                        ((JButton)c).setIcon(newIcon);
                        ControllerGameList.MouseListener(((JButton)c), deletePressedIcon, newIcon);
                    }
                }else if(c instanceof JPanel){
                    for (Component component : ((JPanel)c).getComponents()) {
                        if (component instanceof JLabel) {
                            component.setForeground(new Color(127, 51, 0));
                        }
                    }
                }
            }
        }
    }

    public void paintGameSelected(String name){
        backgroundImage = new ImageIcon("imgs/game_selected.png")
                .getImage().getScaledInstance(600, 115, Image.SCALE_DEFAULT);
        Image backgroundImageRest = new ImageIcon("imgs/button_game.png")
                .getImage().getScaledInstance(600, 115, Image.SCALE_DEFAULT);
        for(BackgroundPanel p: panelsGames.values()){
            if(p == panelsGames.get(name)){
                p.setBackgroundImage(backgroundImage);
                for(Component c: p.getComponents()){
                    if(c instanceof JButton){
                        if (((JButton) c).getActionCommand().contains(START)){
                            ImageIcon newIcon = new ImageIcon(new ImageIcon("imgs/play_bttn_white.png")
                                    .getImage().getScaledInstance(50, 80, Image.SCALE_DEFAULT));
                            ((JButton)c).setIcon(newIcon);
                            ControllerGameList.MouseListener(((JButton)c), newIcon, newIcon);
                        }else if(((JButton) c).getActionCommand().contains(COPY)){
                            ImageIcon newIcon = new ImageIcon(new ImageIcon("imgs/copy_white.png")
                                    .getImage().getScaledInstance(60, 70, Image.SCALE_DEFAULT));
                            ((JButton)c).setIcon(newIcon);
                            ControllerGameList.MouseListener(((JButton)c), newIcon, newIcon);
                        }else if(((JButton) c).getActionCommand().contains(DELETE)){
                            ImageIcon newIcon = new ImageIcon(new ImageIcon("imgs/delete_white.png")
                                    .getImage().getScaledInstance(65, 75, Image.SCALE_DEFAULT));
                            ((JButton)c).setIcon(newIcon);
                            ControllerGameList.MouseListener(((JButton)c), newIcon, newIcon);
                        }
                    }else if(c instanceof JPanel){
                        for (Component component : ((JPanel)c).getComponents()) {
                            if (component instanceof JLabel) {
                                component.setForeground(Color.WHITE);
                            }
                        }
                    }
                }
            }else{
                p.setBackgroundImage(backgroundImageRest);
                for(Component c: p.getComponents()){
                    if(c instanceof JButton){
                        if (((JButton) c).getActionCommand().contains(START)){
                            ImageIcon newIcon = new ImageIcon(new ImageIcon("imgs/start.png")
                                    .getImage().getScaledInstance(50, 80, Image.SCALE_DEFAULT));
                            ImageIcon startPressedIcon = new ImageIcon(new ImageIcon("imgs/start_pressed.png")
                                    .getImage().getScaledInstance(50, 80, Image.SCALE_DEFAULT));
                            ((JButton)c).setIcon(newIcon);
                            ControllerGameList.MouseListener(((JButton)c), startPressedIcon, newIcon);
                        }else if(((JButton) c).getActionCommand().contains(COPY)){
                            ImageIcon newIcon = new ImageIcon(new ImageIcon("imgs/copy.png")
                                    .getImage().getScaledInstance(60, 70, Image.SCALE_DEFAULT));
                            ImageIcon copyPressedIcon = new ImageIcon(new ImageIcon("imgs/copy_pressed.png")
                                    .getImage().getScaledInstance(60, 70, Image.SCALE_DEFAULT));
                            ((JButton)c).setIcon(newIcon);
                            ControllerGameList.MouseListener(((JButton)c), copyPressedIcon, newIcon);
                        }else if(((JButton) c).getActionCommand().contains(DELETE)){
                            ImageIcon newIcon = new ImageIcon(new ImageIcon("imgs/delete.png")
                                    .getImage().getScaledInstance(65, 75, Image.SCALE_DEFAULT));
                            ImageIcon deletePressedIcon = new ImageIcon(new ImageIcon("imgs/delete_pressed.png")
                                    .getImage().getScaledInstance(65, 75, Image.SCALE_DEFAULT));
                            ((JButton)c).setIcon(newIcon);
                            ControllerGameList.MouseListener(((JButton)c), deletePressedIcon, newIcon);
                        }
                    }else if(c instanceof JPanel){
                        for (Component component : ((JPanel)c).getComponents()) {
                            if (component instanceof JLabel) {
                                component.setForeground(new Color(127, 51, 0));
                            }
                        }
                    }
                }
            }
        }
    }

    private class BackgroundPanel extends JPanel {
        private Image bg;

        public void setBackgroundImage(Image image) {
            this.bg = image;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bg != null) {
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public void setComponentInterPanel(String name){
        JPanel aux = newButtonGame(name);
        aux.setAlignmentX(Component.CENTER_ALIGNMENT);
        internPanel.add(Box.createVerticalStrut(20));
        internPanel.add(aux);
    }
}
