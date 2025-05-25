package Presentation.Views;

import Presentation.CoffeeClickerApp;
import Presentation.Controllers.ControllerGame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * GameView represents the main gameplay screen where the player can interact with
 * generators, upgrades, and buttons to produce and spend coffee resources.
 * It displays statistics, handles user actions, and visually presents the game's state.
 */
public class GameView extends JPanel {
    public static final String CLICKED_COFFEE_COMMAND = "CLICKED_COFFEE_COMMAND";
    public static final String BUY_BEANS_COMMAND = "BUY_BEANS_COMMAND";
    public static final String BUY_MAKER_COMMAND = "BUY_MAKER_COMMAND";
    public static final String BUY_TAKEAWAY_COMMAND = "BUY_TAKEAWAY_COMMAND";
    public static final String SETTINGS_COMMAND = "SETTINGS_COMMAND";
    public static final String STATS_COMMAND = "STATS_COMMAND";

    public static final String UPG_BEANS_COMMAND = "UPG_BEANS_COMMAND";
    public static final String UPG_MAKER_COMMAND = "UPG_MAKER_COMMAND";
    public static final String UPG_TAKEAWAY_COMMAND = "UPG_TAKEAWAY_COMMAND";
    public static final String CLICK_UPGRADE_COMMAND = "CLICK_UPGRADE_COMMAND";

    private JTable upgradesTable;
    private JTable generatorShopTable;
    private JTable purchasedgeneratorsInfoTable;


    private final double ASPECT_RATIO = 1085.0 / 802.0;
    private BackgroundPanel centralPanel;
    private JPanel iconPanel;

    private JButton coffeeButton;
    private JButton settingsButton;
    private JButton statsButton;
    private StyledButton clickButton;
    private JLabel cpsLabel;
    private JLabel totalCoffeeLabel;

    private ImageIcon cafeIcon;
    private ImageIcon cafeHoverIcon;

    private ImageIcon settings;
    private ImageIcon stats;

    private double totalNumCoffees;

    private HashMap<String, UpgradePanel> upgrades = new HashMap<>();
    private HashMap<String, GeneratorPanel> generators = new HashMap<>();

    private JPanel upgradesCardPanel;
    private boolean upgradesShowingCards = false;

    private JPanel generatorsCardPanel;
    private boolean generatorsShowingCards = false;

    private MultiplierCellRenderer cellRenderer;

    /**
     * Constructs and initializes the GameView UI layout.
     */
    public GameView() {
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
            public void mouseClicked(MouseEvent e) {
                //animatePop(); //comentado por un tema de ejecución, posible logg.
            }
        });

        coffeeButton.setActionCommand(CLICKED_COFFEE_COMMAND);

        JPanel coffeeButtonPanel = new JPanel(new BorderLayout());
        coffeeButtonPanel.setOpaque(false);
        coffeeButtonPanel.add(coffeeButton, BorderLayout.SOUTH);

        gbcLeft.gridy = 1;
        gbcLeft.weighty = 0.6;
        leftPanel.add(coffeeButtonPanel, gbcLeft);

        // 3. Extra styled button
        clickButton = new StyledButton(
                "imgs/click_icon.png",       // icono izquierdo
                "imgs/click_upgrade_box.png",           // fondo normal
                "imgs/click_upgrade_box_hover.png",     // fondo hover
                "125.00 | 2x",                          // texto del botón
                CLICK_UPGRADE_COMMAND                         // comando
        );


        gbcLeft.gridy = 2;
        gbcLeft.weighty = 0.05;
        gbcLeft.insets = new Insets(0, 5, 50, 5);  // Margen inferior más grande
        leftPanel.add(clickButton, gbcLeft);

        centralPanel.add(leftPanel);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 50, 5, 55);

        JPanel emptyPanel3 = new JPanel();
        emptyPanel3.setOpaque(false);
        gbc.gridy = 0;
        gbc.weighty = 0.5;
        rightPanel.add(emptyPanel3, gbc);

        cpsLabel = new JLabel("254.32M cfs/s", SwingConstants.CENTER);
        cpsLabel.setFont(new Font("CoffeeClicker", Font.PLAIN, 22));
        cpsLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        rightPanel.add(cpsLabel, gbc);

        JPanel emptyPanel4 = new JPanel();
        emptyPanel4.setOpaque(false);
        gbc.gridy = 2;
        gbc.weighty = 0.01;
        rightPanel.add(emptyPanel4, gbc);

        JPanel upgradesContainer = new JPanel(new BorderLayout());
        upgradesContainer.setOpaque(false);

        ImageIcon infoIcon = new ImageIcon(new ImageIcon("imgs/info.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        JButton upgradesInfoButton = new JButton(infoIcon);
        upgradesInfoButton.setBorderPainted(false);
        upgradesInfoButton.setContentAreaFilled(false);
        upgradesInfoButton.setFocusPainted(false);
        upgradesInfoButton.setPreferredSize(new Dimension(24, 24));

        String[] upgradeGenerators_Commands =  {UPG_BEANS_COMMAND, UPG_MAKER_COMMAND, UPG_TAKEAWAY_COMMAND};

        JPanel upgradesInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        upgradesInfoPanel.setOpaque(false);
        upgradesInfoPanel.add(upgradesInfoButton);

        JPanel upgradesPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        upgradesPanel.setOpaque(false);

        int arrayCommandIndex = 0;
        for (int i = 0; i < 9; i++) {
            String key = "upgrade" + i;
            if (i == 3) arrayCommandIndex = 1;
            if (i == 6 ) arrayCommandIndex = 2;
            UpgradePanel upgradeButton = new UpgradePanel(i, upgradeGenerators_Commands[arrayCommandIndex]);
            upgrades.put(key, upgradeButton);
            upgradesPanel.add(upgradeButton);
        }

        upgradesCardPanel = new JPanel();
        upgradesCardPanel.setOpaque(false);
        upgradesCardPanel.setLayout(new BorderLayout());

        JPanel upgradesTablesPanel = new JPanel();
        upgradesTablesPanel.setOpaque(false);
        upgradesTablesPanel.setLayout(new BorderLayout());

        cellRenderer = new MultiplierCellRenderer();
        String[] emptyCols = {""};
        String[][] emptyData = {new String[]{""}};

        Font tableFont = new Font("CoffeeClicker", Font.PLAIN, 8);
        Color bgColor = Color.decode("#cccccc");
        Color fgColor = Color.decode("#7c483c");

        upgradesTable = new NonEditableTable(emptyData, emptyCols);
        upgradesTable.setFont(tableFont);
        upgradesTable.setForeground(fgColor);
        upgradesTable.setBackground(bgColor);
        upgradesTable.setShowGrid(false);
        upgradesTable.setRowHeight(25);
        upgradesTable.setIntercellSpacing(new Dimension(0, 0));
        upgradesTable.setFocusable(false);

        JScrollPane scrollCombined = new JScrollPane(upgradesTable);
        scrollCombined.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCombined.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCombined.getViewport().setBackground(bgColor);

        upgradesTablesPanel.add(scrollCombined, BorderLayout.CENTER);

        upgradesCardPanel.removeAll();
        upgradesCardPanel.add(upgradesTablesPanel, BorderLayout.CENTER);

        JPanel upgradesStack = new JPanel(null);
        upgradesStack.setOpaque(false);
        upgradesStack.setLayout(new CardLayout());
        upgradesStack.add(upgradesPanel, "UPGRADES");
        upgradesStack.add(upgradesCardPanel, "CARDS");

        upgradesContainer.add(upgradesInfoPanel, BorderLayout.NORTH);
        upgradesContainer.add(upgradesStack, BorderLayout.CENTER);

        upgradesInfoButton.addActionListener(e -> {
            upgradesShowingCards = !upgradesShowingCards;
            CardLayout cl = (CardLayout)(upgradesStack.getLayout());
            cl.show(upgradesStack, upgradesShowingCards ? "CARDS" : "UPGRADES");
        });

        gbc.gridy = 3;
        gbc.weighty = 0.001;
        rightPanel.add(upgradesContainer, gbc);

        JPanel emptyPanel5 = new JPanel();
        emptyPanel5.setOpaque(false);
        gbc.gridy = 4;
        gbc.weighty = 0.01;
        rightPanel.add(emptyPanel5, gbc);

        JPanel generatorsContainer = new JPanel(new BorderLayout());
        generatorsContainer.setOpaque(false);

        JButton generatorsInfoButton = new JButton(infoIcon);
        generatorsInfoButton.setBorderPainted(false);
        generatorsInfoButton.setContentAreaFilled(false);
        generatorsInfoButton.setFocusPainted(false);
        generatorsInfoButton.setPreferredSize(new Dimension(24, 24));

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

            generators.put(keys[i], generatorPanel);
            generatorsPanel.add(generatorPanel);
        }

        generatorsCardPanel = new JPanel(new BorderLayout());
        generatorsCardPanel.setOpaque(false);
        JLabel genInfoText = new JLabel("<html><div style='text-align:center;'>Detailed Generator Info</div></html>", SwingConstants.CENTER);

        JPanel generatorsTablesPanel = new JPanel();
        generatorsTablesPanel.setOpaque(false);
        generatorsTablesPanel.setLayout(new GridLayout(2, 1, 5, 5));


        purchasedgeneratorsInfoTable = new NonEditableTable(emptyData, emptyCols);
        purchasedgeneratorsInfoTable.setFont(tableFont);
        purchasedgeneratorsInfoTable.setForeground(fgColor);
        purchasedgeneratorsInfoTable.setBackground(bgColor);
        purchasedgeneratorsInfoTable.setShowGrid(false);
        purchasedgeneratorsInfoTable.setRowHeight(25);
        purchasedgeneratorsInfoTable.setIntercellSpacing(new Dimension(0, 0));
        purchasedgeneratorsInfoTable.setFocusable(false);

        JScrollPane scrollPurchasedGenerators = new JScrollPane(purchasedgeneratorsInfoTable);
        scrollPurchasedGenerators.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPurchasedGenerators.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPurchasedGenerators.getViewport().setBackground(bgColor);
        generatorsTablesPanel.add(scrollPurchasedGenerators);

        generatorShopTable = new NonEditableTable(emptyData, emptyCols);
        generatorShopTable.setFont(tableFont);
        generatorShopTable.setForeground(fgColor);
        generatorShopTable.setBackground(bgColor);
        generatorShopTable.setShowGrid(false);
        generatorShopTable.setRowHeight(25);
        generatorShopTable.setIntercellSpacing(new Dimension(0, 0));
        generatorShopTable.setFocusable(false);

        JScrollPane scrollNextUpgrades = new JScrollPane(generatorShopTable);
        scrollNextUpgrades.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollNextUpgrades.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollNextUpgrades.getViewport().setBackground(bgColor);
        generatorsTablesPanel.add(scrollNextUpgrades);

        generatorsCardPanel.removeAll();
        generatorsCardPanel.add(generatorsTablesPanel, BorderLayout.CENTER);

        JPanel generatorsStack = new JPanel(null);
        generatorsStack.setOpaque(false);
        generatorsStack.setLayout(new CardLayout());
        generatorsStack.add(generatorsPanel, "GENERATORS");
        generatorsStack.add(generatorsCardPanel, "CARDS");

        generatorsContainer.add(generatorsInfoPanel, BorderLayout.NORTH);
        generatorsContainer.add(generatorsStack, BorderLayout.CENTER);

        generatorsInfoButton.addActionListener(e -> {
            generatorsShowingCards = !generatorsShowingCards;
            CardLayout cl = (CardLayout)(generatorsStack.getLayout());
            cl.show(generatorsStack, generatorsShowingCards ? "CARDS" : "GENERATORS");
        });

        gbc.gridy = 5;
        gbc.weighty = 0.4;
        rightPanel.add(generatorsContainer, gbc);

        JPanel emptyPanel2 = new JPanel();
        emptyPanel2.setOpaque(false);
        gbc.gridy = 6;
        gbc.weighty = 0.06;
        rightPanel.add(emptyPanel2, gbc);

        centralPanel.add(rightPanel);

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

        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        iconPanel.add(Box.createVerticalGlue());
        iconPanel.add(settingsButton);
        iconPanel.add(Box.createVerticalStrut(20));
        iconPanel.add(statsButton);
        iconPanel.add(Box.createVerticalGlue());

        add(iconPanel, BorderLayout.EAST);

        JPanel emptyPanel = new JPanel();
        emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
        emptyPanel.setPreferredSize(new Dimension(100, 120));
        emptyPanel.setOpaque(false);
        add(emptyPanel, BorderLayout.WEST);

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
    /**
     * Updates the label displaying the total number of coffees.
     * @param numCoffees the current coffee count
     */
    public void setTotalCoffeeLabel(double numCoffees) {
        totalNumCoffees = numCoffees;
        totalCoffeeLabel.setText(String.format("%.2f cfs", totalNumCoffees));
    }

    /**
     * Updates the current price label for a specific generator.
     *
     * @param generatorType the generator type
     * @param priceGenerator the price to display
     */
    public void setActualPriceGenerator(String generatorType, double priceGenerator) {
        GeneratorPanel generatorPanel = generators.get(generatorType);
        generatorPanel.updatePrice(String.valueOf(priceGenerator));
    }

    /**
     * Updates the number of generators shown for a specific type.
     *
     * @param generatorType the generator type (beans, coffee maker, TakeAway)
     * @param numGenerators the number of generators owned
     */
    public void setTotalNumberGenerators(String generatorType, int numGenerators) {
        GeneratorPanel generatorPanel = generators.get(generatorType);
        if (generatorPanel != null) {
            String text = "Lv." + numGenerators;
            generatorPanel.setLevel(text);
        }
    }

    /**
     * Updates the label showing coffees produced per second.
     *
     * @param coffeesPerSecond the coffees per second rate
     */
    public void setCoffeesPerSecondValue(double coffeesPerSecond) {
        cpsLabel.setText(String.format("%s cfs/s", formatPrice(coffeesPerSecond)));
    }

    public void incrementNumCoffees(double numCoffees) {
        totalNumCoffees += numCoffees;
        totalCoffeeLabel.setText(String.format("%s cfs", formatPrice(totalNumCoffees)));
    }

    public void setController(ControllerGame controller) {
        //Asocio listener boton cafe
        coffeeButton.addActionListener(controller);
        settingsButton.addActionListener(controller);
        statsButton.addActionListener(controller);
        clickButton.addActionListener(controller);
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

    private String getUpgradeGridIndex(int levelUpgrade, String generator) {
        int numUpgradesPerGenerator = 3;
        String key = "upgrade";
        switch (generator) {
            case "beans":
                return key + levelUpgrade;
            case "coffeeMaker":
                return key + (levelUpgrade + (numUpgradesPerGenerator * 1));
            case "TakeAway":
                return key + (levelUpgrade + numUpgradesPerGenerator * 2);
            default:
                return "update0";
        }
    }

    /**
     * Initializes the upgrade grid with upgrade levels and costs.
     * @param beansLevelUpgrade current level of the beans generator
     * @param makerLevelUpgrade current level of the coffee maker generator
     * @param takeAwayLevelUpgrade current level of the takeaway generator
     * @param beansUpgradeCosts list of costs for beans upgrades
     * @param makerUpgradeCosts list of costs for coffee maker upgrades
     * @param takeAwayUpgradeCosts list of costs for takeaway upgrades
     */
    public void initUpgradeGrid(int beansLevelUpgrade, int makerLevelUpgrade, int takeAwayLevelUpgrade, ArrayList<Float> beansUpgradeCosts, ArrayList<Float> makerUpgradeCosts, ArrayList<Float> takeAwayUpgradeCosts) {
        int mejorasPorTipo = 3;

        for (int i = 0; i < mejorasPorTipo; i++) {
            if (i < beansLevelUpgrade) {
                upgrades.get("upgrade" + i).markAsBought();
            } else if (i == beansLevelUpgrade){
                upgrades.get("upgrade" + i).unlock();
            } else {
                upgrades.get("upgrade" + i).lock();
            }
            upgrades.get("upgrade" + i).setUpgradePrice(beansUpgradeCosts.get(i));
        }

        for (int i = 0; i < mejorasPorTipo; i++) {
            int index = i + mejorasPorTipo;
            if (i < makerLevelUpgrade) {
                upgrades.get("upgrade" + index).markAsBought();
            } else if (i == makerLevelUpgrade){
                upgrades.get("upgrade" + index).unlock();
            } else {
                upgrades.get("upgrade" + index).lock();
            }
            upgrades.get("upgrade" + index).setUpgradePrice(makerUpgradeCosts.get(i));
        }

        for (int i = 0; i < mejorasPorTipo; i++) {
            int index = i + 2 * mejorasPorTipo;
            if (i < takeAwayLevelUpgrade) {
                upgrades.get("upgrade" + index).markAsBought();
            } else if (i == takeAwayLevelUpgrade){
                upgrades.get("upgrade" + index).unlock();
            } else {
                upgrades.get("upgrade" + index).lock();
            }
            upgrades.get("upgrade" + index).setUpgradePrice(takeAwayUpgradeCosts.get(i));
        }
    }

    /**
     * Updates the upgrade info table with the latest upgrade status.
     * @param beansLevelUpgrade current beans generator upgrade level
     * @param makerLevelUpgrade current coffee maker generator upgrade level
     * @param takeAwayLevelUpgrade current takeaway generator upgrade level
     * @param beansUpgradeCosts costs for each beans upgrade level
     * @param makerUpgradeCosts costs for each coffee maker upgrade level
     * @param takeAwayUpgradeCosts costs for each takeaway upgrade level
     */
    public void updateUpgradeInfoTable(int beansLevelUpgrade, int makerLevelUpgrade, int takeAwayLevelUpgrade, ArrayList<Float> beansUpgradeCosts, ArrayList<Float> makerUpgradeCosts, ArrayList<Float> takeAwayUpgradeCosts) {
        String[][] combinedUpgrades = {
                {"Coffee Beans", beansUpgradeCosts.get(0) + " cfs", "x2", (beansLevelUpgrade >= 1) ? "Bought" : "Available"},
                {"Coffee Maker", makerUpgradeCosts.get(0) + " cfs", "x2", (makerLevelUpgrade >= 1) ? "Bought" : "Available"},
                {"Take Away", takeAwayUpgradeCosts.get(0) + " cfs", "x2", (takeAwayLevelUpgrade >= 1) ? "Bought" : "Available"},
                {"Coffee Beans", beansUpgradeCosts.get(1) + " cfs", "x4", (beansLevelUpgrade >= 2) ? "Bought" : "Available"},
                {"Coffee Maker", makerUpgradeCosts.get(1) + " cfs", "x4", (makerLevelUpgrade >= 2) ? "Bought" : "Available"},
                {"Take Away", takeAwayUpgradeCosts.get(1) + " cfs", "x4", (takeAwayLevelUpgrade >= 2) ? "Bought" : "Available"},
                {"Coffee Beans", beansUpgradeCosts.get(2) + " cfs", "x8", (beansLevelUpgrade >= 3) ? "Bought" : "Available"},
                {"Coffee Maker", makerUpgradeCosts.get(2) + " cfs", "x8", (makerLevelUpgrade >= 3) ? "Bought" : "Available"},
                {"Take Away", takeAwayUpgradeCosts.get(2) + " cfs", "x8", (takeAwayLevelUpgrade >= 3) ? "Bought" : "Available"}
        };
        String[] upgradeCols = {"Generator", "Price", "Multiplier", "Status"};

        upgradesTable.setModel(new DefaultTableModel(combinedUpgrades, upgradeCols));

        // Aplicar el renderer personalizado para los multiplicadores
        for (int i = 0; i < upgradesTable.getColumnCount(); i++) {
            upgradesTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Header animado
        upgradesTable.getTableHeader().setDefaultRenderer(
                new AnimatedHeaderRenderer(upgradesTable.getTableHeader())
        );

        // Ajustar ancho primera columna
        upgradesTable.getColumnModel().getColumn(0).setPreferredWidth(120);
    }

    /**
     * Updates the shop table with current generator prices and production rates.
     * @param beansPriceGenerator price of beans generator
     * @param beansUnitProduction production rate of beans generator
     * @param makerPriceGenerator price of coffee maker generator
     * @param makerUnitProduction production rate of coffee maker generator
     * @param takeAwayPriceGenerator price of takeaway generator
     * @param takeAwayUnitProduction production rate of takeaway generator
     */
    public void updateGeneratorsShopTable (double beansPriceGenerator, String beansUnitProduction, double makerPriceGenerator, String makerUnitProduction, double takeAwayPriceGenerator, String takeAwayUnitProduction) {
        String[] generatorsShopCols = {
                "Name", "Cost", "Unit Production"
        };

        String[][] generatorShopInfo = { new String[]
                {"Coffee Beans", formatPrice(beansPriceGenerator) + " cfs", beansUnitProduction + ""},
                {"Coffee Maker", formatPrice(makerPriceGenerator) + " cfs", makerUnitProduction + ""},
                {"Take Away", formatPrice(takeAwayPriceGenerator) + " cfs", takeAwayUnitProduction + ""},
        };

        generatorShopTable.setModel(new DefaultTableModel(generatorShopInfo, generatorsShopCols));

        for (int i = 0; i < generatorShopTable.getColumnCount(); i++) {
            generatorShopTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }


        generatorShopTable.getTableHeader().setDefaultRenderer(
                new AnimatedHeaderRenderer(generatorShopTable.getTableHeader())
        );


        generatorShopTable.getColumnModel().getColumn(0).setPreferredWidth(140);
    }

    /**
     * Updates the generator info table with quantities and production stats.
     * @param beansQuantity  quantity of beans generators
     * @param makerQuantity quantity of coffee maker generators
     * @param takeAwayQuantity quantity of takeaway generators
     * @param beansUnitProduction unit production of beans
     * @param makerUnitProduction  unit production of coffee maker
     * @param takeAwayUnitProduction unit production of takeaway
     * @param beansTotalProduction total production of beans
     * @param makerTotalProduction total production of coffee maker
     * @param takeAwayTotalProduction total production of takeaway
     * @param beansGlobalProduction global production of beans
     * @param makerGlobalProduction global production of coffee maker
     * @param takeAwayGlobalProduction global production of takeaway
     */
    public void updateGeneratorsInfoTable(int beansQuantity, int makerQuantity, int takeAwayQuantity, String beansUnitProduction, String makerUnitProduction, String takeAwayUnitProduction, String beansTotalProduction, String makerTotalProduction, String takeAwayTotalProduction, String beansGlobalProduction, String makerGlobalProduction, String takeAwayGlobalProduction) {

        String[] generatorInfoTableCols = {
                "Name", "Quantity", "Unit Production", "Total Production", "Global Production %"
        };

        String[][] generatorInfoTableData = {
                {"Coffee Beans", beansQuantity + "", beansUnitProduction, beansTotalProduction, beansGlobalProduction},
                {"Coffee Maker", makerQuantity + "", makerUnitProduction, makerTotalProduction, makerGlobalProduction},
                {"Take Away", takeAwayQuantity + "", takeAwayUnitProduction, takeAwayTotalProduction, takeAwayGlobalProduction}
        };
        purchasedgeneratorsInfoTable.setModel(new DefaultTableModel(generatorInfoTableData, generatorInfoTableCols));

        for (int i = 0; i < purchasedgeneratorsInfoTable.getColumnCount(); i++) {
            purchasedgeneratorsInfoTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }


        purchasedgeneratorsInfoTable.getTableHeader().setDefaultRenderer(
                new AnimatedHeaderRenderer(purchasedgeneratorsInfoTable.getTableHeader())
        );


        purchasedgeneratorsInfoTable.getColumnModel().getColumn(0).setPreferredWidth(100);
    }

    public void updateClickerButton(double nextClickerUpgradeCost, double nextClickerMultiplicator) {
        clickButton.setText(formatPrice(nextClickerUpgradeCost) + " | x" + nextClickerMultiplicator);
    }

    /**
     * Blocks the clicker upgrade button visually and functionally.
     */
    public void blockClickerButton() {
        clickButton.setText("LEVEL MAX");
    }

    /**
     * Displays the selected upgrade visually as purchased.
     *
     * @param levelUpgrade the level being purchased
     * @param generator the type of generator being upgraded
     */
    public void buyUpgrade(int levelUpgrade, String generator) {
        upgrades.get(getUpgradeGridIndex(levelUpgrade,generator)).markAsBought();
    }

    /**
     * Unlocks the next available upgrade for a given generator.
     *
     * @param levelUpgrade the upgrade level to unlock
     * @param generator the type of generator to unlock upgrade for
     */
    public void unlockUpgrade(int levelUpgrade, String generator) {
        upgrades.get(getUpgradeGridIndex(levelUpgrade,generator)).unlock();
    }

}
