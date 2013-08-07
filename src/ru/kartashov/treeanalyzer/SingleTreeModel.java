package ru.kartashov.treeanalyzer;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Shoff
 * Date: 02.06.13
 * Time: 0:06
 */

public class SingleTreeModel implements TreeModel {

    private SingleTreeNode root;
    private String name;

    private static void addNodes(Component component, SingleTreeNode node){
        if (!(component instanceof Container))
            return;
        Container container = (Container)component;
        for (Component comp: container.getComponents()){
            SingleTreeNode tn = new SingleTreeNode(comp);
            node.add(tn);
            addNodes(comp, tn);
        }
    }

    public SingleTreeModel(Component root){
        name = String.valueOf(System.nanoTime());
        this.root = new SingleTreeNode(root);
        addNodes(root, this.root);
    }

    public SingleTreeNode getRoot() {
        return root;
    }

    public SingleTreeNode getChild(Object parent, int index) {
        if (!(parent instanceof SingleTreeNode))
            throw new IllegalArgumentException("Illegal parent");
        return ((SingleTreeNode)parent).getChild(index);
    }

    public int getChildCount(Object parent) {
        if (!(parent instanceof SingleTreeNode))
            throw new IllegalArgumentException("Illegal parent");
        return ((SingleTreeNode)parent).getChildrenCount();
    }

    public boolean isLeaf(Object node) {
        if (!(node instanceof SingleTreeNode))
            throw new IllegalArgumentException("Illegal node");
        return ((SingleTreeNode)node).getChildrenCount() == 0;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    public int getIndexOfChild(Object parent, Object child) {
        if (!(parent instanceof SingleTreeNode))
            throw new IllegalArgumentException("Illegal parent");
        if (!(child instanceof SingleTreeNode))
            throw new IllegalArgumentException("Illegal child");
        return ((SingleTreeNode)parent).getIndexOfChild(((SingleTreeNode)child));  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addTreeModelListener(TreeModelListener l) {
    }

    public void removeTreeModelListener(TreeModelListener l) {
    }

    @Override
    public String toString() {
        return name;
    }
}
