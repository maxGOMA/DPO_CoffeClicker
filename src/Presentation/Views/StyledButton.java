package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class StyledButton extends JPanel {
    private Image bgNormal, bgHover;
    private JLabel iconLabel;
    private JLabel textLabel;
    private final List<ActionListener> listeners = new ArrayList<>();
    private String command;

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Point p = getMousePosition();
        Image bg = (p != null && contains(p)) ? bgHover : bgNormal;
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }

    public void setText(String newText) {
        textLabel.setText(newText);
        repaint();
    }

    private Image loadImage(String path) {
        return new ImageIcon(path).getImage();
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }
}
