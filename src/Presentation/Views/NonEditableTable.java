package Presentation.Views;

import javax.swing.*;

class NonEditableTable extends JTable {
    public NonEditableTable(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
