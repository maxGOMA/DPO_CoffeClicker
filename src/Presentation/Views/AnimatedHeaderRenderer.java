package Presentation.Views;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom table header renderer that animates scrolling text for columns with long titles.
 * This renderer creates a smooth horizontal marquee effect by periodically updating the
 * displayed portion of the column title.
 */
public class AnimatedHeaderRenderer extends DefaultTableCellRenderer {
    private final Map<Integer, String> scrollingTexts = new HashMap<>();
    private final Map<Integer, Integer> offsets = new HashMap<>();
    private final JTableHeader header;
    private final Timer timer;

    /**
     * Constructs an animated header renderer with a scrolling effect.
     * @param header the table header component this renderer belongs to
     */
    public AnimatedHeaderRenderer(JTableHeader header) {
        this.header = header;

        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font("CoffeeClicker", Font.PLAIN, 8));
        setForeground(Color.decode("#492a23"));
        setBackground(Color.decode("#cccccc"));

        timer = new Timer(150, e -> {
            boolean changed = false;
            for (int col = 0; col < header.getColumnModel().getColumnCount(); col++) {
                String text = scrollingTexts.getOrDefault(col, "");
                if (text.length() > 0) {
                    int offset = offsets.getOrDefault(col, 0);
                    offset = (offset + 1) % text.length();
                    offsets.put(col, offset);
                    changed = true;
                }
            }
            if (changed) {
                header.repaint();
            }
        });
        timer.start();
    }

    /**
     * Returns the component used for drawing the header cell.
     * @param table the JTable that uses this header
     * @param value the value to assign to the cell
     * @param isSelected whether the cell is selected
     * @param hasFocus whether the cell has focus
     * @param row the row index (ignored for headers)
     * @param column the column index of the header
     * @return a component used for header rendering with animated text
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        String baseText = value.toString();
        int maxDisplayChars = 12;

        // Construimos el tren solo una vez por columna
        if (!scrollingTexts.containsKey(column)) {
            if (baseText.length() > maxDisplayChars) {
                String train = baseText + "     " + baseText + "     "; // espacio para suavidad
                scrollingTexts.put(column, train);
            } else {
                scrollingTexts.put(column, baseText); // no hay scroll
            }
        }

        String scrollText = scrollingTexts.get(column);
        int offset = offsets.getOrDefault(column, 0);

        String displayText;
        if (scrollText.length() > maxDisplayChars) {
            int end = offset + maxDisplayChars;
            if (end > scrollText.length()) {
                displayText = scrollText.substring(offset) + scrollText.substring(0, end - scrollText.length());
            } else {
                displayText = scrollText.substring(offset, end);
            }
        } else {
            displayText = scrollText;
        }

        JLabel label = new JLabel(displayText);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("CoffeeClicker", Font.PLAIN, 8));
        label.setForeground(Color.decode("#492a23"));
        label.setOpaque(true);
        label.setBackground(Color.decode("#cccccc"));

        label.setBorder(BorderFactory.createMatteBorder(0, 1, 2, 1, Color.decode("#492a23")));

        return label;
    }
}
