package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StyledButton is a custom UI component that behaves like a JButton but with enhanced
 * visuals. It includes icon + background styling and interactive mouse effects,
 * used primarily for in-game actions like upgrades.
 */
public class StyledButton extends JPanel {
    private Image bgNormal, bgHover;
    private JLabel iconLabel;
    private JLabel textLabel;
    private final List<ActionListener> listeners = new ArrayList<>();
    private String command;

    /**
     * Constructs a new StyledButton with icons, background images, label text, and command.
     * @param iconPath path to the left-side icon image
     * @param bgNormalPath path to the background image in normal state
     * @param bgHoverPath path to the background image in hover state
     * @param text the text to be displayed on the button
     * @param command the action command associated with this button
     */
    public StyledButton(String iconPath, String bgNormalPath, String bgHoverPath, String text, String command) {
        this.command = command;

        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(150, 90));

        // Load backgrounds
        bgNormal = loadImage(bgNormalPath);
        bgHover = loadImage(bgHoverPath);

        // Icon
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledIcon = icon.getImage().getScaledInstance(26, 38, Image.SCALE_SMOOTH);
        iconLabel = new JLabel(new ImageIcon(scaledIcon));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(14, 25, 14, 10));
        add(iconLabel, BorderLayout.WEST);

        // Text
        textLabel = new JLabel(text);
        textLabel.setForeground(Color.BLACK);
        textLabel.setFont(new Font("CoffeeClicker", Font.PLAIN, 12));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        add(textLabel, BorderLayout.CENTER);

        // Mouse interacciones
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { repaint(); }
            public void mouseExited(MouseEvent e) { repaint(); }
            public void mouseClicked(MouseEvent e) {
                ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, command);
                for (ActionListener listener : listeners) {
                    listener.actionPerformed(event);
                }
            }
        });
    }

    /**
     * Renders the background depending on whether the mouse is hovering.
     * @param g the Graphics context to draw on
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Point p = getMousePosition();
        Image bg = (p != null && contains(p)) ? bgHover : bgNormal;
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Updates the displayed text on the button.
     * @param newText the new label text
     */
    public void setText(String newText) {
        textLabel.setText(newText);
        repaint();
    }

    /**
     * Loads an image from a file path.
     * @param path the image file path
     * @return the loaded Image
     */
    private Image loadImage(String path) {
        return new ImageIcon(path).getImage();
    }

    /**
     * Adds an ActionListener to this StyledButton.
     * @param listener the ActionListener to be notified when the button is clicked
     */
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }
}
