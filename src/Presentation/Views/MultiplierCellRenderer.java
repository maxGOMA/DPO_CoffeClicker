package Presentation.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

/**
 * MultiplierCellRenderer is a custom table cell renderer for displaying multiplier-based
 * formatting in upgrade and generator tables.
 * Depending on the multiplier (e.g., x2, x4, x8), it adjusts the text color
 * to visually distinguish the strength of each upgrade.
 */
class MultiplierCellRenderer extends DefaultTableCellRenderer {

    private final Font font = new Font("CoffeeClicker", Font.PLAIN, 8);
    private final Color bgDefault = Color.decode("#cccccc");
    private final Color borderColor = Color.decode("#492a23");

    // Mapa de nombre de generador → multiplicador aplicado ("x2", "x4", "x8")
    private final Map<String, String> generatorMultipliers = new HashMap<>();

    /**
     * Sets the multiplier text to be associated with a specific generator name.
     * @param generatorName the name of the generator
     * @param multiplier the multiplier value (e.g., x2, x4, x8)
     */
    public void setMultiplier(String generatorName, String multiplier) {
        generatorMultipliers.put(generatorName, multiplier);
    }

    /**
     * Clears all stored multiplier mappings.
     */
    public void clearMultipliers() {
        generatorMultipliers.clear();
    }

    /**
     * Customizes the appearance of a table cell based on multiplier information.
     * @param table the JTable to render for
     * @param value the value to assign to the cell
     * @param isSelected whether the cell is selected
     * @param hasFocus whether the cell has focus
     * @param row the row index of the cell
     * @param column the column index of the cell
     * @return the customized component to be rendered
     */
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        setFont(font);
        setBackground(bgDefault);
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, borderColor));

        // Obtener nombre del generador (columna 0)
        String generatorName = table.getValueAt(row, 0).toString();
        String multiplier = generatorMultipliers.getOrDefault(generatorName, null);

        if (multiplier == null && table.getColumnCount() > 2) {
            multiplier = table.getValueAt(row, 2).toString();
        }

        switch (multiplier) {
            case "x2" -> setForeground(Color.decode("#444444"));   // gris
            case "x4" -> setForeground(Color.decode("#B8860B"));   // dorado
            case "x8" -> setForeground(Color.decode("#007FA3"));   // diamante
            default -> setForeground(Color.decode("#492a23"));     // texto normal
        }

        return this;
    }
}
