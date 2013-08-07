package ru.kartashov.treeanalyzer;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Shoff
 * Date: 08.06.13
 * Time: 22:58
 */
public class ComparatorNodeTableModel implements TableModel {

    ArrayList<String[]> table;
    private static String[] head = new String[]{"name", "oldValue", "newValue"};

    public ComparatorNodeTableModel(SingleNodeTableModel oldTreeNodeModel, SingleNodeTableModel newTreeNodeModel){
        table = new ArrayList<String[]>();
        for (int i = 0; i < oldTreeNodeModel.getRowCount(); i++){
            if (!oldTreeNodeModel.getRow(i)[1].equals(newTreeNodeModel.getRow(i)[1])){
                table.add(new String[]{oldTreeNodeModel.getRow(i)[0], oldTreeNodeModel.getRow(i)[1],
                     newTreeNodeModel.getRow(i)[1]});
            }
        }
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

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

    public void addTableModelListener(TableModelListener l) {}

    public void removeTableModelListener(TableModelListener l) {}}
