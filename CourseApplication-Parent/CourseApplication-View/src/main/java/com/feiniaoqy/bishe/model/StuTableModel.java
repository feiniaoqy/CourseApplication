package com.feiniaoqy.bishe.model;

import entity.Student;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by asus on 2016/5/8.
 */
public class StuTableModel extends AbstractTableModel {
    private String[] columnNames;
    private ArrayList<Object[]> list;

    public StuTableModel(String[] columnNames, ArrayList<Object[]> list){
        this.columnNames = columnNames;
        this.list = list;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return list.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return list.get(row)[col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
       /* if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                    + " to " + value
                    + " (an instance of "
                    + value.getClass() + ")");
        }*/

        list.get(row)[col] = value.toString();
        fireTableCellUpdated(row, col);

       /* if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }*/
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + list.get(i)[j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}