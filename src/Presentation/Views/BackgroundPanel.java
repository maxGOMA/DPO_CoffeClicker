package Presentation.Views;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    public BackgroundPanel(ImageIcon image) {
        backgroundImage = image.getImage();
    }

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
