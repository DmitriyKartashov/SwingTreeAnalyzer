package ru.kartashov.treeanalyzer;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Shoff
 * Date: 02.06.13
 * Time: 0:51
 */
public class SingleNodeTableModel implements TableModel {

    private ArrayList<String[]> table;
    private static String[] head = new String[]{"name", "value"};

    private void filAllFields(Component root, Class<?> clazz){
        for(Field f: clazz.getDeclaredFields()){
            try {
                if (Modifier.isStatic(f.getModifiers()))
                    continue;
                f.setAccessible(true);
                String[] row = new String[2];
                row[0] = f.getName();
                Object value = f.get(root);

                row[1] = (value != null)?value.toString():"null";
                table.add(row);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (!clazz.equals(Object.class))
            filAllFields(root, clazz.getSuperclass());
    }

    public SingleNodeTableModel(Component component){
        table = new ArrayList<String[]>();
        filAllFields(component, component.getClass());
    }

    public int getRowCount() {
        return table.size();
    }

    public int getColumnCount() {
        return head.length;
    }

    public String getColumnName(int columnIndex) {
        return head[columnIndex];
    }

    public Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return table.get(rowIndex)[columnIndex];
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    public void addTableModelListener(TableModelListener l) {}

    public void removeTableModelListener(TableModelListener l) {}

    public String[] getRow(int index) {
        return table.get(index);
    }
}
