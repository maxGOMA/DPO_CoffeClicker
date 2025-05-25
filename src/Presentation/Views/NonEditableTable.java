package Presentation.Views;

import javax.swing.*;

/**
 * NonEditableTable is a specialized JTable used for display-only purposes in the UI.
 * It disables cell editing while preserving normal JTable rendering and behavior.
 */
class NonEditableTable extends JTable {

    /**
     * Constructs a NonEditableTable with the given data and column headers.
     * @param data the 2D Object array representing table row data
     * @param columnNames the array of column names
     */
    public NonEditableTable(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    /**
     * Overrides the default cell editability behavior.
     * Always returns false to prevent editing of any table cell.
     * @param row the row index
     * @param column the column index
     * @return false to make all cells non-editable
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
