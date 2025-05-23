package Presentation.Views;

import Presentation.Controllers.ControllerStatistics;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GraphView extends JPanel {
    public static final String BACK_COMMAND = "BACK_COMMAND";
    public static final String GAME_SELECTED = "GAME_SELECTED";
    public static final String USER_SELECTED = "USER_SELECTED";

    private Font coffeeClickerFont;
    private ArrayList<Double> stats;

    //JcomboBoxs
    private ArrayList<String> usernames;
    private ArrayList<String> finishedGames;

    private JComboBox<String> userSelectionComboBox;
    private JComboBox<String> gameSelectionComboBox;

    private JButton backButton;

    private JLabel errorLabel;
    private JLabel gameSelectionLabel;
    private JLabel userSelectionLabel;

    public GraphView () {
        setLayout(new BorderLayout());
        coffeeClickerFont = MainMenuView.loadCustomFont();
        stats = new ArrayList<>();

        usernames = new ArrayList<>();
        finishedGames = new ArrayList<>();

        JPanel backGroundPanel = new JPanel(new BorderLayout());
        ImageIcon buttonIcon = new ImageIcon(new ImageIcon("imgs/button.png")
                .getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH));

        backButton = new JButton("BACK", buttonIcon);
        backButton.setHorizontalTextPosition(JButton.CENTER);
        backButton.setVerticalTextPosition(JButton.CENTER);
        if (coffeeClickerFont != null) {
            backButton.setFont(coffeeClickerFont.deriveFont(16f));
        }
        backButton.setBorderPainted(false);
        backButton.setForeground(new Color(107, 41, 0));
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.setActionCommand(BACK_COMMAND);

        //Panel de las jComboBox
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(150, 50, 150, 50));

        JLabel titleLabel = new JLabel("Game stats");
        titleLabel.setFont(coffeeClickerFont.deriveFont(30f));
        titleLabel.setForeground(new Color(127, 51, 0));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        userSelectionLabel = new JLabel("Select User");
        userSelectionLabel.setFont(coffeeClickerFont);
        userSelectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        errorLabel = new JLabel ("Error text");
        errorLabel.setFont(coffeeClickerFont.deriveFont(7f));
        errorLabel.setForeground(Color.RED);

        userSelectionComboBox = new JComboBox<>();
        userSelectionComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        userSelectionComboBox.setBackground(new Color(248, 249, 251));
        userSelectionComboBox.setMaximumSize(new Dimension(300, 30));
        userSelectionComboBox.setActionCommand(USER_SELECTED);

        gameSelectionLabel = new JLabel("Select Game");
        gameSelectionLabel.setFont(coffeeClickerFont);
        gameSelectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        gameSelectionComboBox = new JComboBox<>();
        gameSelectionComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        gameSelectionComboBox.setBackground(new Color(248, 249, 251));
        gameSelectionComboBox.setMaximumSize(new Dimension(300, 30));
        gameSelectionComboBox.setActionCommand(GAME_SELECTED);

        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        selectionPanel.add(titleLabel);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        selectionPanel.add(userSelectionLabel);
        userSelectionLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));
        selectionPanel.add(userSelectionComboBox);
        selectionPanel.add(errorLabel);
        errorLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 1, 0));
        errorLabel.setVisible(false);
        selectionPanel.add(gameSelectionLabel);
        gameSelectionLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 5, 0));
        gameSelectionLabel.setVisible(false);
        selectionPanel.add(gameSelectionComboBox);
        backButton.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0)); // Ajusta el margen superior aquí
        selectionPanel.add(backButton);
        gameSelectionComboBox.setVisible(false);
        backGroundPanel.add(selectionPanel, BorderLayout.WEST);

        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintGraph((Graphics2D) g, getWidth(), getHeight()); // llamada al método de pintado real
            }
        };
        graphPanel.setBackground(Color.WHITE); // o cualquier fondo que quieras
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backGroundPanel.add(graphPanel, BorderLayout.CENTER);

        add(backGroundPanel, BorderLayout.CENTER);
    }

    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
        userSelectionComboBox.removeAllItems();
        for (String username : usernames) {
            userSelectionComboBox.addItem(username);
        }
    }

    public void setFinishedGames(ArrayList<String> finishedGames) {
        this.finishedGames = finishedGames;
        gameSelectionComboBox.removeAllItems();
        for (String finishedGame : finishedGames) {
            gameSelectionComboBox.addItem(finishedGame);
        }
    }


    public void showGameSelectionComboBox() {
        errorLabel.setVisible(false);
        gameSelectionLabel.setVisible(true);
        gameSelectionComboBox.setVisible(true);
    }

    public void showNoUsersRegisteredMessage() {
        errorLabel.setText("No registered users found!");
        errorLabel.setVisible(true);
        gameSelectionLabel.setVisible(false);
        gameSelectionComboBox.setVisible(false);
    }

    public void showNoFinishedGamesForSelectedUserMessage() {
        errorLabel.setText("This user does not have finished games!");
        errorLabel.setVisible(true);
        gameSelectionLabel.setVisible(false);
        gameSelectionComboBox.setVisible(false);
        updateStats(new ArrayList<>()); //Para que aparezca loading...
    }


    public void updateStats(ArrayList<Double> newStats) {
        stats = newStats;
        repaint();
    }

    protected void paintGraph(Graphics2D g, int w, int h) {
        super.paintComponent(g);
        Graphics2D g2 = g;

        int width = w;
        int height = h;
        int margin = 50;

        g2.setColor(new Color(248, 249, 251)); //Fondo del grafico.
        g2.fillRect(0, 0, width, height);

        if (stats == null || stats.isEmpty()) {
            g2.setColor(Color.GRAY);
            g2.setFont(coffeeClickerFont.deriveFont(20f));

            String message = "Loading...";
            int x = (w - 2*margin) / 2;
            int y = (h) / 2;

            g2.drawString(message, x, y);
            return; //No printar nada si no hay datos..

        }

        //Escalado de la funcion
        double yMax = Double.MIN_VALUE;
        double xMax = stats.size();

        double yMin = Double.MAX_VALUE;
        double xMin = 1;

        for (Double stat : stats) {
            double y = stat;
            if (y < yMin) yMin = y;
            if (y > yMax) yMax = y;
        }

        double xScale = (width - 2 * margin) / (xMax - xMin);
        double yScale = (height - 2 * margin) / (yMax - yMin);

        //Dibujo lineas de ejes
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(margin, height - margin, width - margin, height - margin);
        g2.drawLine(margin, height - margin, margin, margin);

        g2.setFont(coffeeClickerFont.deriveFont(13f));
        g2.drawString("Time (minutes)", (width - 2 * margin) / 2, height - 10);
        g2.drawString("Num coffees (units)", margin, margin - 10);

        //Dibuja marcas del eje X e Y
        g2.setFont(coffeeClickerFont.deriveFont(7f));
        int partitions = 20;
        for (int i = 0; i <= partitions; i++) {
            int x = margin + (i * (width - 2 * margin)) / partitions;
            String xLabel = String.format("%.1f", xMin + (i * (xMax - xMin)) / partitions);
            g2.drawLine(x, height - margin - 5, x, height - margin + 5);
            g2.drawString(xLabel, x - 10, height - margin + 20);

            int y = height - margin - (i * (height - 2 * margin)) / partitions;
            String yLabel = String.format("%.1f", yMin + (i * (yMax - yMin)) / partitions);
            g2.drawLine(margin - 5, y, margin + 5, y);
            g2.drawString(yLabel, margin - 35, y + 5);
        }


        int previousX = -1;
        int previousY = -1;

        //Dibujo lineas que unen los puntos
        g2.setColor(new Color(170, 85, 34));
        g2.setStroke(new BasicStroke(3));
        for (int i = 0; i < stats.size(); i++) {
            int x = margin + 5 + (int) (((i + 1)- xMin) * xScale);
            int y = height - margin - (int) ((stats.get(i) - yMin) * yScale);

            if (previousX != -1 && previousY != -1) {
                g2.drawLine(previousX, previousY, x, y);
            }

            previousX = x;
            previousY = y;
        }

        //Dibujo puntos
        g2.setColor(new Color(120, 51, 0));
        for (int i = 0; i < stats.size(); i++) {
            int x = margin + 5 + (int) (((i + 1)- xMin) * xScale);
            int y = height - margin - (int) ((stats.get(i) - yMin) * yScale);

            g2.fillOval(x - 3, y - 3, 8, 8);
        }
    }

    public void setController(ControllerStatistics controller) {
        userSelectionComboBox.addActionListener(controller);
        gameSelectionComboBox.addActionListener(controller);
        backButton.addActionListener(controller);
    }


}

