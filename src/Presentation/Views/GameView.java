package Presentation.Views;

import Presentation.Controllers.ControllerGame;
import Presentation.Controllers.ControllerLogin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class GameView extends JPanel {
    public static final String CLICKED_COFFEE_COMMAND = "CLICKED_COFFEE_COMMAND";
    public static final String BUY_BEANS_COMMAND = "BUY_BEANS_COMMAND";
    public static final String BUY_MAKER_COMMAND = "BUY_MAKER_COMMAND";
    public static final String BUY_TAKEAWAY_COMMAND = "BUY_TAKEAWAY_COMMAND";
    public static final String SETTINGS_COMMAND = "SETTINGS_COMMAND";
    public static final String STATS_COMMAND = "STATS_COMMAND";

    private final CoffeeClickerApp app;

    private final double ASPECT_RATIO = 1085.0 / 802.0;
    private BackgroundPanel centralPanel;
    private JPanel iconPanel;

    private JButton coffeeButton;
    private JButton settingsButton;
    private JButton statsButton;
    private JLabel cpsLabel;
    private JLabel totalCoffeeLabel;

    private ImageIcon cafeIcon;
    private ImageIcon cafeHoverIcon;

    private ImageIcon settings;
    private ImageIcon stats;

    private double totalNumCoffees;

    private HashMap<String, UpgradePanel> upgrades = new HashMap<>();
    private HashMap<String, GeneratorPanel> generators = new HashMap<>();

    public GameView(CoffeeClickerApp app) {
        this.app = app;

        setPreferredSize(new Dimension(1200, 800));
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setOpaque(false);
        add(containerPanel, BorderLayout.CENTER);

        centralPanel = new BackgroundPanel("imgs/final_bkg.png");
        centralPanel.setLayout(new GridLayout(1,2));
        centralPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        containerPanel.add(centralPanel, new GridBagConstraints());

        // === LEFT PANEL ===
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0;
        gbcLeft.fill = GridBagConstraints.BOTH;
        gbcLeft.insets = new Insets(5, 5, 5, 5);

        // 1. Total (20%)
        totalNumCoffees = 0;
        totalCoffeeLabel = new JLabel(totalNumCoffees +" cfs", SwingConstants.CENTER);
        totalCoffeeLabel.setFont(new Font("CoffeeClicker", Font.PLAIN, 30));
        totalCoffeeLabel.setForeground(Color.WHITE);
        gbcLeft.gridy = 0;
        gbcLeft.weighty = 0.2;
        leftPanel.add(totalCoffeeLabel, gbcLeft);

        // 2. Coffee button (80%)
        cafeIcon = new ImageIcon(new ImageIcon("imgs/cafe.png").getImage().getScaledInstance(270, 257, Image.SCALE_DEFAULT));
        cafeHoverIcon = new ImageIcon(new ImageIcon("imgs/cafe_selected.png").getImage().getScaledInstance(270, 257, Image.SCALE_DEFAULT));
        coffeeButton = new JButton(cafeIcon);
        coffeeButton.setBorderPainted(false);
        coffeeButton.setContentAreaFilled(false);
        coffeeButton.setFocusPainted(false);
        coffeeButton.setPreferredSize(new Dimension(150, 150));

        coffeeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                coffeeButton.setIcon(cafeHoverIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                coffeeButton.setIcon(cafeIcon);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                animatePop();
            }
        });

        //TODO: RAUL HEMOS Añadido comand a coffeeButton!
        coffeeButton.setActionCommand(CLICKED_COFFEE_COMMAND);

        JPanel coffeeButtonPanel = new JPanel(new GridBagLayout());
        coffeeButtonPanel.setOpaque(false);
        coffeeButtonPanel.add(coffeeButton);
        gbcLeft.gridy = 1;
        gbcLeft.weighty = 0.8;
        leftPanel.add(coffeeButtonPanel, gbcLeft);

        centralPanel.add(leftPanel);

        // === RIGHT PANEL ===
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel emptyPanel3 = new JPanel();
        emptyPanel3.setOpaque(false);
        gbc.gridy = 0;
        gbc.weighty = 0.2;
        rightPanel.add(emptyPanel3, gbc);

        // 1. Cps (20%)
        cpsLabel = new JLabel("254.32M cfs/s", SwingConstants.CENTER);
        cpsLabel.setFont(new Font("CoffeeClicker", Font.PLAIN, 22));
        cpsLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.weighty = 0.06;
        rightPanel.add(cpsLabel, gbc);

        JPanel emptyPanel4 = new JPanel();
        emptyPanel4.setOpaque(false);
        gbc.gridy = 2;
        gbc.weighty = 0.06;
        rightPanel.add(emptyPanel4, gbc);

        // 2. 9 upgrade buttons (40%)
        // Panel contenedor para upgrades + info
        JPanel upgradesContainer = new JPanel(new BorderLayout());
        upgradesContainer.setOpaque(false);

        // Botón de información (i) para upgrades
        ImageIcon infoIcon = new ImageIcon(new ImageIcon("imgs/info.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        JButton upgradesInfoButton = new JButton(infoIcon);
        upgradesInfoButton.setBorderPainted(false);
        upgradesInfoButton.setContentAreaFilled(false);
        upgradesInfoButton.setFocusPainted(false);
        upgradesInfoButton.setPreferredSize(new Dimension(24, 24));
        upgradesInfoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Información sobre mejoras.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        // Panel para alinear el botón (i) a la izquierda
        JPanel upgradesInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        upgradesInfoPanel.setOpaque(false);
        upgradesInfoPanel.add(upgradesInfoButton);

        // Panel real de upgrades
        JPanel upgradesPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        upgradesPanel.setOpaque(false);
        for (int i = 1; i <= 9; i++) {
            String key = "upgrade" + i;
            UpgradePanel upgradeButton = new UpgradePanel(i);
            upgrades.put(key, upgradeButton);
            if(i > 3){
                upgradeButton.lock();
            }
            upgradesPanel.add(upgradeButton);
        }


        upgradesContainer.add(upgradesInfoPanel, BorderLayout.NORTH);
        upgradesContainer.add(upgradesPanel, BorderLayout.CENTER);

        // Añadir al layout principal
        gbc.gridy = 3;
        gbc.weighty = 0.22;
        rightPanel.add(upgradesContainer, gbc);

        JPanel emptyPanel5 = new JPanel();
        emptyPanel5.setOpaque(false);
        gbc.gridy = 4;
        gbc.weighty = 0.01;
        rightPanel.add(emptyPanel5, gbc);

        // 3. 3 generator buttons (40%)
        JPanel generatorsContainer = new JPanel(new BorderLayout());
        generatorsContainer.setOpaque(false);

        // Botón de información (i) para generadores
        JButton generatorsInfoButton = new JButton(infoIcon);
        generatorsInfoButton.setBorderPainted(false);
        generatorsInfoButton.setContentAreaFilled(false);
        generatorsInfoButton.setFocusPainted(false);
        generatorsInfoButton.setPreferredSize(new Dimension(24, 24));
        generatorsInfoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Información sobre generadores.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel generatorsInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        generatorsInfoPanel.setOpaque(false);
        generatorsInfoPanel.add(generatorsInfoButton);

        JPanel generatorsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        generatorsPanel.setOpaque(false);
        String[] names = {
                "<html><div align='center'>COFFEE<br>BEANS</div></html>",
                "<html><div align='center'>COFFEE<br>MAKER</div></html>",
                "<html><div align='center'>TAKE<br>AWAY</div></html>"
        };
        String[] keys = {"beans", "coffeeMaker", "TakeAway"};
        String[] buyGenerators_commands =  {BUY_BEANS_COMMAND, BUY_MAKER_COMMAND, BUY_TAKEAWAY_COMMAND};

        for (int i = 0; i < names.length; i++) {
            GeneratorPanel generatorPanel = new GeneratorPanel(keys[i], buyGenerators_commands[i], names[i]);
            generators.put(keys[i], generatorPanel);
            generatorsPanel.add(generatorPanel);

            // TODO añadir el comando del setActionCommand (lo de abajo no va)
            // generatorPanel.setActionCommand(buyGenerators_commands[i]);

            generators.put(keys[i], generatorPanel);
            generatorsPanel.add(generatorPanel);
        }

        generatorsContainer.add(generatorsInfoPanel, BorderLayout.NORTH);
        generatorsContainer.add(generatorsPanel, BorderLayout.CENTER);

        gbc.gridy = 5;
        gbc.weighty = 0.46;
        rightPanel.add(generatorsContainer, gbc);

        JPanel emptyPanel2 = new JPanel();
        emptyPanel2.setOpaque(false);
        gbc.gridy = 6;
        gbc.weighty = 0.06;
        rightPanel.add(emptyPanel2, gbc);

        centralPanel.add(rightPanel);

        // === ICON PANEL ===
        iconPanel = new JPanel();
        iconPanel.setPreferredSize(new Dimension(150,400));
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.Y_AXIS));
        iconPanel.setOpaque(false);

        settings = new ImageIcon(new ImageIcon("imgs/cog.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        settingsButton = new JButton(settings);
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setActionCommand(SETTINGS_COMMAND);

        stats = new ImageIcon(new ImageIcon("imgs/stats.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        statsButton = new JButton(stats);
        statsButton.setBorderPainted(false);
        statsButton.setContentAreaFilled(false);
        statsButton.setFocusPainted(false);
        statsButton.setActionCommand(STATS_COMMAND);

        // Centrado
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Espaciado vertical
        iconPanel.add(Box.createVerticalGlue());
        iconPanel.add(settingsButton);
        iconPanel.add(Box.createVerticalStrut(20));
        iconPanel.add(statsButton);
        iconPanel.add(Box.createVerticalGlue());

        add(iconPanel, BorderLayout.EAST);

        // === EMPTY PANEL ===
        JPanel emptyPanel = new JPanel();
        emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
        emptyPanel.setPreferredSize(new Dimension(100, 120));
        emptyPanel.setOpaque(false);
        add(emptyPanel, BorderLayout.WEST);

        // === Proportional scaling listener ===
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeCentralPanel(containerPanel);
            }
        });

        SwingUtilities.invokeLater(() -> resizeCentralPanel(containerPanel));
    }

    private void resizeCentralPanel(JPanel container) {
        int maxWidth = container.getWidth() - 100;
        int maxHeight = container.getHeight();

        int newWidth = maxWidth;
        int newHeight = (int) (newWidth / ASPECT_RATIO);

        if (newHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (int) (newHeight * ASPECT_RATIO);
        }

        centralPanel.setPreferredSize(new Dimension(newWidth, newHeight));
        centralPanel.revalidate();

        resizeInternalComponents();
    }

    private void resizeInternalComponents() {
        int width = centralPanel.getWidth();
        int height = centralPanel.getHeight();

        double scaleX = width / 1085.0;
        double scaleY = height / 802.0;
        double scale = Math.min(scaleX, scaleY);

        int pad = (int) (scale*25);
        centralPanel.setBorder(new EmptyBorder(pad,pad,pad,pad));

        // Coffee button
        int coffeeSize = (int) (270 * scale);
        coffeeButton.setPreferredSize(new Dimension(coffeeSize, coffeeSize));
        coffeeButton.setFont(coffeeButton.getFont().deriveFont((float) (64 * scale)));

        // CPS label
        cpsLabel.setFont(cpsLabel.getFont().deriveFont((float) (24 * scale)));

        // Total coffee label
        totalCoffeeLabel.setFont(totalCoffeeLabel.getFont().deriveFont((float) (32 * scale)));

        // Upgrade buttons
        for (UpgradePanel upgrade : upgrades.values()) {
            upgrade.setPreferredSize(new Dimension((int) (60 * scale), (int) (40 * scale)));
            upgrade.setFont(upgrade.getFont().deriveFont((float) (14 * scale)));
        }

        // Generator buttons
        for (GeneratorPanel gen : generators.values()) {
            gen.setPreferredSize(new Dimension((int) (125 * scale), (int) (81 * scale)));
            gen.resizeFont((float) (14 * scale));
        }

        settings = new ImageIcon(new ImageIcon("imgs/cog.png").getImage().getScaledInstance((int) (scale * 100), (int) (scale * 100), Image.SCALE_DEFAULT));
        settingsButton.setIcon(settings);
        stats = new ImageIcon(new ImageIcon("imgs/stats.png").getImage().getScaledInstance((int) (scale * 100), (int) (scale * 100), Image.SCALE_DEFAULT));
        statsButton.setIcon(stats);
        cafeIcon = new ImageIcon(new ImageIcon("imgs/cafe.png").getImage().getScaledInstance((int) (scale*270), (int) (scale*257), Image.SCALE_DEFAULT));
        cafeHoverIcon = new ImageIcon(new ImageIcon("imgs/cafe_selected.png").getImage().getScaledInstance((int) (scale*270), (int) (scale*257), Image.SCALE_DEFAULT));
        coffeeButton.setIcon(cafeIcon);

        Dimension iconButtonSize = new Dimension((int) (scale*100), (int) (scale*100));

        settingsButton.setPreferredSize(iconButtonSize);
        statsButton.setPreferredSize(iconButtonSize);

        centralPanel.revalidate();
        centralPanel.repaint();
    }

    private void animatePop() {
        final int frames = 6;
        final int delay  = 15;
        final double popFactor = 1.5;

        Dimension original = coffeeButton.getPreferredSize();
        int ow = original.width;
        int oh = original.height;

        Timer t = new Timer(delay, null);
        t.addActionListener(new ActionListener() {
            int step = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                double f;
                if (step < frames/2) {
                    f = 1 + (popFactor - 1) * (step+1) / (frames/2);
                } else {
                    f = popFactor - (popFactor - 1) * (step - frames/2 +1) / (frames/2);
                }

                coffeeButton.setPreferredSize(new Dimension(
                        (int)(ow * f), (int)(oh * f)));
                coffeeButton.revalidate();
                coffeeButton.repaint();

                if (++step >= frames) t.stop();
            }
        });
        t.start();
    }

    //TODO RAUL ESTO ES NUEVO
    public void setTotalCoffeeLabel(double numCoffees) {
        totalNumCoffees = numCoffees;
        totalCoffeeLabel.setText(String.format("%.2f cfs", totalNumCoffees));
    }

    //TODO implementar esto en un cartel o algo
    public void setActualPriceGenerator(String generatorType, double priceGenerator) {
        System.out.println("The new price for" + generatorType + ": " + priceGenerator);
        GeneratorPanel generatorPanel = generators.get(generatorType);
        generatorPanel.updatePrice(String.valueOf(priceGenerator));
    }

    public void setTotalNumberGenerators(String generatorType, int numGenerators) {
        GeneratorPanel generatorPanel = generators.get(generatorType);
        if (generatorPanel != null) {
            String text = "Lv." + numGenerators;
            generatorPanel.setLevel(text);
        }
    }

    public void setCoffeesPerSecondValue(double coffeesPerSecond) {
        cpsLabel.setText(String.format("%s cfs/s", formatPrice(coffeesPerSecond)));
    }

    //TODO MOSTRAR MENSAJE DE ERROR
    public void showErrorMessage(String text) {
        System.out.println(text);
    }

    //TODO RAUL ESTO ES NUEVO
    public void incrementNumCoffees(double numCoffees) {
        totalNumCoffees += numCoffees;
        totalCoffeeLabel.setText(String.format("%s cfs", formatPrice(totalNumCoffees)));
    }

    public void setController(ControllerGame controller) {
        //Asocio listener boton cafe
        coffeeButton.addActionListener(controller);
        settingsButton.addActionListener(controller);
        statsButton.addActionListener(controller);
        //Asocio listeners botones comprar generador.
        for (GeneratorPanel button : generators.values()) {
            button.addActionListener(controller);
        }
        for(UpgradePanel button : upgrades.values()){
            button.addActionListener(controller);
        }
    }

    private String formatPrice(double price) {
        String[] suffixes = {"", "K", "M", "B", "T"};
        int index = 0;
        while (price >= 1000 && index < suffixes.length - 1) {
            price /= 1000.0;
            index++;
        }
        return String.format("%.2f%s", price, suffixes[index]);
    }

    public CoffeeClickerApp getApp() {
        return app;
    }
}
