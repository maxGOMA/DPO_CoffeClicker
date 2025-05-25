package Presentation.Views;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JPanel that displays a scaled background image.
 * The image is automatically resized to preserve its aspect ratio
 * while fitting the available space within the panel.
 */
public class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    /**
     * Constructs a BackgroundPanel using an image path.
     * @param imagePath the path to the image file
     */
    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    /**
     * Constructs a BackgroundPanel using an ImageIcon directly.
     * @param image the ImageIcon to be used as the background
     */
    public BackgroundPanel(ImageIcon image) {
        backgroundImage = image.getImage();
    }

    /**
     * Custom paintComponent implementation that scales and centers the background image.
     * @param g the Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int imgWidth = backgroundImage.getWidth(this);
        int imgHeight = backgroundImage.getHeight(this);

        if (imgWidth > 0 && imgHeight > 0) {
            double aspectRatio = (double) imgWidth / imgHeight;
            int newWidth, newHeight;

            if (panelWidth / aspectRatio <= panelHeight) {
                newWidth = panelWidth;
                newHeight = (int) (panelWidth / aspectRatio);
            } else {
                newHeight = panelHeight;
                newWidth = (int) (panelHeight * aspectRatio);
            }

            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;
            g.drawImage(backgroundImage, x, y, newWidth, newHeight, this);
        }
    }
}
