package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * UpgradePanel is a custom UI component used to display an upgrade button in the game view.
 * Each panel represents a specific upgrade type (silver, gold, diamond) and generator (beans, coffee maker, takeaway).
 * It supports interactive effects and visual states (locked, unlocked, bought).
 */
class UpgradePanel extends JPanel {
    private String type; // gold, silver, diamond
    private boolean bought = false;
    private boolean unlocked = true;
    private Float price;
    private int generatorIndex;

    private CardLayout cl;
    private JPanel centerPanel;

    private Image bgNormal, bgHover, bgBought, bgLocked, bgLockedUpgrade;

    private JLabel iconLabel;
    private JLabel priceLabel;
    private JLabel lockIconLabel;
    private Image lockIconImg;

    private String command;

    private final List<ActionListener> listeners = new ArrayList<>();

    /**
     * Constructs an UpgradePanel based on a unique upgrade index and command.
     * @param upgradeIndex the index (0–8) identifying the upgrade
     * @param command the action command to assign
     */
    public UpgradePanel(int upgradeIndex, String command) {
        this.command = command;

        switch (upgradeIndex) {
            case 0:
                type = "silver"; generatorIndex = 0; break;
            case 1:
                type = "gold"; generatorIndex = 0; break;
            case 2:
                type = "diamond"; generatorIndex = 0; break;
            case 3:
                type = "silver"; generatorIndex = 1; break;
            case 4:
                type = "gold"; generatorIndex = 1; break;
            case 5:
                type = "diamond"; generatorIndex = 1; break;
            case 6:
                type = "silver"; generatorIndex = 2; break;
            case 7:
                type = "gold"; generatorIndex = 2; break;
            case 8:
                type = "diamond"; generatorIndex = 2; break;
        }

        setLayout(new BorderLayout());
        setOpaque(false);

        bgNormal = loadImage("imgs/" + type + "_upgrade.png");
        bgHover = loadImage("imgs/" + type + "_upgrade_hover.png");
        bgBought = loadImage("imgs/" + type + "_upgrade_bought.png");
        bgLocked = loadImage("imgs/locked.png");
        bgLockedUpgrade = loadImage("imgs/locked_upgrade.png");

        ImageIcon generatorIcon;
        Image scaledIcon = null;

        switch (generatorIndex) {
            case 0:
                generatorIcon = new ImageIcon("imgs/bean.png");
                scaledIcon = generatorIcon.getImage().getScaledInstance(27, 32, Image.SCALE_SMOOTH);
                break;
            case 1:
                generatorIcon = new ImageIcon("imgs/cafetera.png");
                scaledIcon = generatorIcon.getImage().getScaledInstance(32, 31, Image.SCALE_SMOOTH);
                break;
            case 2:
                generatorIcon = new ImageIcon("imgs/take_away.png");
                scaledIcon = generatorIcon.getImage().getScaledInstance(26, 32, Image.SCALE_SMOOTH);
                break;
            default:
                generatorIcon = new ImageIcon("imgs/cafe.png");
                scaledIcon = generatorIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                break;
        }

        iconLabel = new JLabel(new ImageIcon(scaledIcon));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(14, 10, 14, 10));
        add(iconLabel, BorderLayout.WEST);

        priceLabel = new JLabel(formatPrice(0.0));
        priceLabel.setForeground(Color.BLACK);
        priceLabel.setFont(new Font("CoffeeClicker", Font.PLAIN, 8));
        priceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        lockIconImg = loadImage("imgs/lock.png");
        Image scaledLock = lockIconImg.getScaledInstance(31, 37, Image.SCALE_DEFAULT);
        lockIconLabel = new JLabel(new ImageIcon(scaledLock));
        lockIconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        cl = new CardLayout();
        centerPanel = new JPanel(cl);
        centerPanel.setOpaque(false);
        centerPanel.add(priceLabel, "PRICE");
        centerPanel.add(lockIconLabel, "LOCK");
        add(centerPanel, BorderLayout.CENTER);

        cl.show(centerPanel, "PRICE");


        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (unlocked && !bought) repaint();
            }

            public void mouseExited(MouseEvent e) {
                if (unlocked && !bought) repaint();
            }

            public void mouseClicked(MouseEvent e) {
                if (unlocked && !bought) {

                    ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, command);
                    for (ActionListener listener : listeners) {
                        listener.actionPerformed(event);
                    }
                }

            }
        });

        setPreferredSize(new Dimension(100, 60));
    }

    /**
     * Sets the upgrade panel to the 'bought' visual state.
     */
    public void markAsBought() {
        this.bought = true;
        this.unlocked = true;
        repaint();
    }

    /**
     * Sets the upgrade panel to a locked state.
     */
    public void lock() {
        this.unlocked = false;
        cl.show(centerPanel, "LOCK");
        repaint();
    }

    /**
     * Unlocks the upgrade so the user can purchase it.
     */
    public void unlock() {
        this.unlocked = true;
        cl.show(centerPanel, "PRICE");
        repaint();
    }

    /**
     * Sets the price of the upgrade.
     * @param price the price to display on the upgrade panel
     */
    public void setUpgradePrice(Float price) {
        this.price = price;
        priceLabel.setText(formatPrice(price));
    }

    /**
     * Paints the background and icons depending on the upgrade state.
     * @param g the graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image bg;
        if (!unlocked) {
            bg = bgLockedUpgrade;
        } else if (bought) {
            bg = bgBought;
        } else {
            Point p = getMousePosition();
            bg = (p != null && contains(p)) ? bgHover : bgNormal;
        }
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Loads and returns an image from a file path.
     * @param path the file path to the image
     * @return the loaded Image
     */
    private Image loadImage(String path) {
        return new ImageIcon(path).getImage();
    }

    /**
     * Formats the given float price into a readable string.
     * @param price the float value representing the price
     * @return a formatted price string
     */
    private String formatPrice(double price) {
        String[] suffixes = {"", "K", "M", "B", "T"};
        int index = 0;
        while (price >= 1000 && index < suffixes.length - 1) {
            price /= 1000.0;
            index++;
        }
        return String.format("%.2f%s", price, suffixes[index]);
    }

    /**
     * Adds an ActionListener to this upgrade panel.
     * This will be triggered when the panel is clicked.
     *
     * @param listener the ActionListener to attach
     */
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }
}
