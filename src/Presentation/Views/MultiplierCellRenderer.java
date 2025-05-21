package Presentation.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

class MultiplierCellRenderer extends DefaultTableCellRenderer {

    private final Font font = new Font("CoffeeClicker", Font.PLAIN, 8);
    private final Color bgDefault = Color.decode("#cccccc");
    private final Color borderColor = Color.decode("#492a23");

    // Mapa de nombre de generador → multiplicador aplicado ("x2", "x4", "x8")
    private final Map<String, String> generatorMultipliers = new HashMap<>();

    public void setMultiplier(String generatorName, String multiplier) {
        generatorMultipliers.put(generatorName, multiplier);
    }

    public void clearMultipliers() {
        generatorMultipliers.clear();
    }

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
