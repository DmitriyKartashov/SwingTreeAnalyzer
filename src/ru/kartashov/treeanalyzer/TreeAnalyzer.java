package ru.kartashov.treeanalyzer;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Shoff
 * Date: 01.06.13
 * Time: 20:20
 */
public class TreeAnalyzer {
    private ArrayList<SingleTreeModel> treeModels = new ArrayList<SingleTreeModel>();
    private JTree sceneTree = new JTree();
    private JTable rootTable = new JTable();
    private JFrame mainFrame = new JFrame();
    private JPanel comparatorPanel = new JPanel();
    private JCheckBox compareCheckBox;
    private JCheckBox lastTwoCheckBox;
    private JComboBox oldComboBox;
    private JComboBox newComboBox;
    private JScrollPane tableScrollPane;
    private static ArrayList<TreeAnalyzer> treeAnalyzers = new ArrayList<TreeAnalyzer>();
    private final ItemListener oldComboBoxItemListener;
    private final ItemListener newComboBoxItemListener;

    private void expandTree(){
        for (int i = 0; i < sceneTree.getRowCount(); i++) {
            sceneTree.expandRow(i);
        }
    }

    private TreeAnalyzer(){
        mainFrame.setBounds(300, 100, 700, 500);
        mainFrame.setVisible(true);
        JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainPanel.setSize(mainFrame.getSize());
        mainPanel.setVisible(true);
        sceneTree.setSize(mainFrame.getSize());
        sceneTree.setVisible(true);
        sceneTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                if (e.getNewLeadSelectionPath() == null)
                    return;
                Object node = e.getNewLeadSelectionPath().getLastPathComponent();
                if ((node instanceof SingleTreeNode))
                    rootTable.setModel(((SingleTreeNode) node).getNodeTableModel());
                if ((node instanceof ComparatorTreeNode))
                    rootTable.setModel(((ComparatorTreeNode) node).getTableModel());
                tableScrollPane.repaint();
            }
        });
        sceneTree.setCellRenderer(new TreeAnalyzerCellRenderer());
        mainFrame.add(mainPanel);
        BorderLayout rightPanelLayout = new BorderLayout();
        JPanel rightPanel = new JPanel(rightPanelLayout);
        JScrollPane treeScrollPane = new JScrollPane(sceneTree);
        treeScrollPane.setMinimumSize(new Dimension(150, 0));
        tableScrollPane = new JScrollPane(rootTable);
        tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(rightPanel);
        mainPanel.add(tableScrollPane);
        rightPanel.add(comparatorPanel, BorderLayout.NORTH);
        rightPanel.add(treeScrollPane, BorderLayout.CENTER);
        comparatorPanel.setMinimumSize(new Dimension(300, 60));
        comparatorPanel.setMaximumSize(new Dimension(300, 60));
        comparatorPanel.setLayout(new GridLayout(0, 2));
        compareCheckBox = new JCheckBox("compare");
        compareCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                boolean selected = compareCheckBox.isSelected();
                lastTwoCheckBox.setEnabled(selected);
                boolean lastTwoCheckBoxSelected = lastTwoCheckBox.isSelected();
                oldComboBox.setEnabled(!(lastTwoCheckBox.isEnabled() && lastTwoCheckBoxSelected));
                newComboBox.setEnabled(selected && !lastTwoCheckBoxSelected);
                if (compareCheckBox.isSelected()){
                    if (newComboBox.getSelectedItem() != null)
                        newComboBoxItemListener.itemStateChanged(new ItemEvent(compareCheckBox, 0, null, ItemEvent.SELECTED));
                }
                else
                    oldComboBoxItemListener.itemStateChanged(new ItemEvent(compareCheckBox, 0, null, ItemEvent.SELECTED));
                comparatorPanel.repaint();
            }
        });
        lastTwoCheckBox = new JCheckBox("last two");
        lastTwoCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                boolean lastTwoCheckBoxSelected = lastTwoCheckBox.isSelected();
                oldComboBox.setEnabled(!lastTwoCheckBoxSelected);
                newComboBox.setEnabled(!lastTwoCheckBoxSelected);
                if (lastTwoCheckBoxSelected){
                    ComparatorTreeModel comparatorTreeModel = new ComparatorTreeModel(treeModels.get(treeModels.size() - 2) ,
                         treeModels.get(treeModels.size() - 1));
                    sceneTree.setModel(comparatorTreeModel);
                    oldComboBox.setSelectedIndex(treeModels.size() - 2);
                    newComboBox.setSelectedIndex(treeModels.size() - 1);
                    expandTree();
                }
                comparatorPanel.repaint();
            }
        });
        oldComboBox = new JComboBox(new TreeAnalyzerComboBoxModel(treeModels));
        newComboBox = new JComboBox(new TreeAnalyzerComboBoxModel(treeModels));
        oldComboBoxItemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED)
                    return;
                sceneTree.clearSelection();
                if (newComboBox.isEnabled() && newComboBox.getSelectedItem() != null)
                    sceneTree.setModel(new ComparatorTreeModel((SingleTreeModel) oldComboBox.getSelectedItem(), (SingleTreeModel) newComboBox.getSelectedItem()));
                else
                    sceneTree.setModel((SingleTreeModel) oldComboBox.getSelectedItem());
                expandTree();
            }
        };
        oldComboBox.addItemListener(oldComboBoxItemListener);
        newComboBoxItemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED)
                    return;
                sceneTree.clearSelection();
                sceneTree.setModel(new ComparatorTreeModel((SingleTreeModel) oldComboBox.getSelectedItem(), (SingleTreeModel) newComboBox.getSelectedItem()));
                expandTree();
            }
        };
        newComboBox.addItemListener(newComboBoxItemListener);
        compareCheckBox.setEnabled(false);
        oldComboBox.setEnabled(false);
        newComboBox.setEnabled(false);
        lastTwoCheckBox.setEnabled(false);
        comparatorPanel.add(compareCheckBox);
        comparatorPanel.add(lastTwoCheckBox);
        comparatorPanel.add(oldComboBox);
        comparatorPanel.add(newComboBox);
        mainPanel.validate();
    }

    private void analyze(Component root){
        SingleTreeModel treeModel = new SingleTreeModel(root);
        treeModels.add(treeModel);
        if (treeModels.size() == 1){
            oldComboBox.setSelectedIndex(0);
        }else if (treeModels.size() == 2){
            oldComboBox.setSelectedIndex(-1);
            oldComboBox.setEnabled(true);
            compareCheckBox.setEnabled(true);
            compareCheckBox.setSelected(true);
            lastTwoCheckBox.setSelected(true);
        }
        if (lastTwoCheckBox.isEnabled() && lastTwoCheckBox.isSelected()){
            oldComboBox.setSelectedIndex(treeModels.size() - 2);
            newComboBox.setSelectedIndex(treeModels.size() - 1);
        }
        mainFrame.setTitle(treeModel.getRoot().toString() + " " + root.getName());
        comparatorPanel.repaint();
    }


    private Component getRoot(){
        return treeModels.get(0).getRoot().getComponent();
    }

    public static void doAnalyze(Component root){
        for (TreeAnalyzer treeAnalyzer: treeAnalyzers){
            if (treeAnalyzer.getRoot() == root){
                treeAnalyzer.analyze(root);
                return;
            }
        }
        TreeAnalyzer ta = new TreeAnalyzer();
        ta.analyze(root);
        treeAnalyzers.add(ta);
    }

    private static class TreeAnalyzerComboBoxModel implements ComboBoxModel {
        private SingleTreeModel selectedItem;
        private ArrayList<SingleTreeModel> items;
        protected EventListenerList listenerList = new EventListenerList();

        private TreeAnalyzerComboBoxModel(ArrayList<SingleTreeModel> items){
            this.items = items;
        }

        public void setSelectedItem(Object anItem) {
            selectedItem = (SingleTreeModel)anItem;
            Object[] listeners = listenerList.getListenerList();
            ListDataEvent e = null;

            for (int i = listeners.length - 2; i >= 0; i -= 2) {
                if (listeners[i] == ListDataListener.class) {
                    if (e == null) {
                        e = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, -1, -1);
                    }
                    ((ListDataListener)listeners[i+1]).contentsChanged(e);
                }
            }
        }

        public Object getSelectedItem() {
            return selectedItem;
        }

        public int getSize() {
            return items.size();
        }

        public Object getElementAt(int index) {
            return items.get(index);
        }

        public void addListDataListener(ListDataListener l) {
            listenerList.add(ListDataListener.class, l);
        }

        public void removeListDataListener(ListDataListener l) {}
    }
}
