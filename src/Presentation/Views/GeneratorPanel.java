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
 * A custom JPanel used to display a generator in the GameView.
 * Includes generator name, level, icon, price overlay, and handles user interaction.
 */
public class GeneratorPanel extends JPanel {
    private final String command;
    private final JLabel nameLabel;
    private final JLabel levelLabel;
    private final JLabel imageLabel;
    private final JLabel overlayTextLabel;
    private final JLabel priceCardLabel;
    private final List<ActionListener> listeners = new ArrayList<>();

    /**
     * Constructs a new GeneratorPanel for the specified generator.
     * @param generatorName the internal key or type of the generator
     * @param command the action command associated with this panel
     * @param text the display name to show on the panel
     */
    public GeneratorPanel(String generatorName, String command, String text) {
        this.command = command;
        setLayout(new GridBagLayout());
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        nameLabel = new JLabel(text, SwingConstants.CENTER);
        nameLabel.setFont(new Font("CoffeeClicker", Font.PLAIN, 12));
        nameLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 4, 0);
        add(nameLabel, gbc);

        levelLabel = new JLabel("LV.1", SwingConstants.CENTER);
        levelLabel.setFont(new Font("CoffeeClicker", Font.PLAIN, 12));
        levelLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.insets = new Insets(2, 0, 4, 0);
        add(levelLabel, gbc);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new OverlayLayout(imagePanel));
        imagePanel.setOpaque(false);
        imagePanel.setPreferredSize(new Dimension(106, 115));

        imageLabel = new JLabel();
        if (generatorName.contains("beans")) {
            imageLabel.setIcon(loadImageIcon("imgs/bean.png", 75, 90));
        } else if (generatorName.contains("coffeeMaker")) {
            imageLabel.setIcon(loadImageIcon("imgs/cafetera.png", 92, 90));
        } else if (generatorName.contains("TakeAway")) {
            imageLabel.setIcon(loadImageIcon("imgs/take_away.png", 72, 90));
        }
        imageLabel.setAlignmentX(0.5f);
        imageLabel.setAlignmentY(1.0f);
        imageLabel.setOpaque(false);

        priceCardLabel = new JLabel();
        ImageIcon resizedCard = loadImageIcon("imgs/price_card.png", 92, 40);
        priceCardLabel.setIcon(resizedCard);
        priceCardLabel.setAlignmentX(0.55f);
        priceCardLabel.setAlignmentY(0.65f);
        priceCardLabel.setOpaque(false);

        overlayTextLabel = new JLabel("400", SwingConstants.CENTER);
        overlayTextLabel.setFont(new Font("CoffeeClicker", Font.PLAIN, 10));
        overlayTextLabel.setForeground(new Color(102, 51, 0));
        overlayTextLabel.setAlignmentX(0.6f);
        overlayTextLabel.setAlignmentY(0.9f); // Subido para centrarse mejor
        overlayTextLabel.setOpaque(false);

        imagePanel.add(overlayTextLabel);
        imagePanel.add(priceCardLabel);
        imagePanel.add(imageLabel);

        gbc.gridy = 2;
        gbc.insets = new Insets(4, 0, 0, 0);
        add(imagePanel, gbc);

        revalidate();
        repaint();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, command);
                for (ActionListener listener : listeners) {
                    listener.actionPerformed(event);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon hoverCard = loadImageIcon("imgs/price_card_hover.png", 92, 40);
                priceCardLabel.setIcon(hoverCard);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                priceCardLabel.setIcon(resizedCard);
            }
        });
    }

    /**
     * Loads and scales an image icon from a given path.
     * @param path the image file path
     * @param width the desired width
     * @param height the desired height
     * @return the scaled ImageIcon
     */
    private ImageIcon loadImageIcon(String path, int width, int height) {
        ImageIcon img = new ImageIcon(path);
        return new ImageIcon(img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    /**
     * Sets the name text shown on the panel.
     * @param name the new name string to display
     */
    public void setName(String name) {
        nameLabel.setText(name);
    }

    /**
     * Sets the level text shown on the panel.
     * @param levelText the new level string to display
     */
    public void setLevel(String levelText) {
        levelLabel.setText(levelText);
    }

    /**
     * Updates the price overlay label text.
     * @param text the new price string to display
     */
    public void updatePrice(String text) {
        try {
            double value = Double.parseDouble(text);
            overlayTextLabel.setText(formatPrice(value));
        } catch (NumberFormatException e) {
            overlayTextLabel.setText(text);
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

    /**
     * Adds an ActionListener to this panel, allowing it to handle click events.
     * @param listener the ActionListener to add
     */
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }
}
