package ru.kartashov.treeanalyzer;

import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Shoff
 * Date: 08.06.13
 * Time: 22:44
 */
public class ComparatorTreeNode {

    private ArrayList<ComparatorTreeNode> nodes = new ArrayList<ComparatorTreeNode>();
    private SingleTreeNode oldNode, newNode;
    private ComparatorNodeTableModel comparatorNodeTableModel;
    private static Color removedColor = new Color(170, 0, 0);
    private static Color addedColor = new Color(0, 128, 20);
    private static Color modifiedColor = new Color(238,168,0);
    private Color color;

    public ComparatorTreeNode(SingleTreeNode oldNode, SingleTreeNode newNode){
        this.oldNode = oldNode;
        this.newNode = newNode;
        if (oldNode != null && newNode != null)
            comparatorNodeTableModel = new ComparatorNodeTableModel(oldNode.getNodeTableModel(), newNode.getNodeTableModel());
        if (oldNode == null)
            color = addedColor;
        else if (newNode == null)
            color = removedColor;
        else if (comparatorNodeTableModel.getRowCount() > 0)
            color = modifiedColor;
        else color = Color.black;
    }

    public void add(ComparatorTreeNode child){
        nodes.add(child);
    }

    public ComparatorTreeNode getChild(int index){
        return nodes.get(index);
    }

    @Override
    public String toString(){
        return oldNode != null? oldNode.toString():newNode.toString();
    }

    public int getChildrenCount(){
        return nodes.size();
    }

    public int getIndexOfChild(ComparatorTreeNode element){
        return nodes.indexOf(element);
    }

    public TableModel getTableModel(){
        return (comparatorNodeTableModel != null && comparatorNodeTableModel.getRowCount() != 0)? comparatorNodeTableModel :oldNode != null? oldNode.getNodeTableModel():newNode.getNodeTableModel();
    }

    public Color getColor(){
        return color;
    }

}
