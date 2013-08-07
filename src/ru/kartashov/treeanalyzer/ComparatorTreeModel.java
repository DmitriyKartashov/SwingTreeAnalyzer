package ru.kartashov.treeanalyzer;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Created with IntelliJ IDEA.
 * User: Shoff
 * Date: 08.06.13
 * Time: 22:40
 */
public class ComparatorTreeModel implements TreeModel {

    private ComparatorTreeNode root;

    private static void fillNode(ComparatorTreeNode node, SingleTreeNode oldNode, SingleTreeNode newNode){
        if (oldNode == null){
            for (int i = 0; i < newNode.getChildrenCount(); i++){
                ComparatorTreeNode newChild = new ComparatorTreeNode(null, newNode.getChild(i));
                fillNode(newChild, null, newNode.getChild(i));
                node.add(newChild);
            }
            return;
        }
        if (newNode == null){
            for (int i = 0; i < oldNode.getChildrenCount(); i++){
                ComparatorTreeNode newChild = new ComparatorTreeNode(oldNode.getChild(i), null);
                fillNode(newChild, oldNode.getChild(i), null);
                node.add(newChild);
            }
            return;
        }
        for (int i = 0; i < oldNode.getChildrenCount(); i++){
            SingleTreeNode relativeNode = null;
            for (int j = 0; j < newNode.getChildrenCount();j++){
                if (oldNode.getChild(i).getComponent() == newNode.getChild(j).getComponent()){
                    relativeNode = newNode.getChild(j);
                    break;
                }
            }
            ComparatorTreeNode newChild = new ComparatorTreeNode(oldNode.getChild(i), relativeNode);
            fillNode(newChild, oldNode.getChild(i), relativeNode);
            node.add(newChild);
        }
        for (int i = 0; i < newNode.getChildrenCount(); i++){
            SingleTreeNode relativeNode = null;
            for (int j = 0; j < oldNode.getChildrenCount();j++){
                if (newNode.getChild(i).getComponent() == oldNode.getChild(j).getComponent()){
                    relativeNode = oldNode.getChild(j);
                    break;
                }
            }
            if (relativeNode == null){
                ComparatorTreeNode newChild = new ComparatorTreeNode(relativeNode, newNode.getChild(i));
                fillNode(newChild, relativeNode, newNode.getChild(i));
                node.add(newChild);
            }
        }

    }

    public ComparatorTreeModel(SingleTreeModel oldModel, SingleTreeModel newModel){
        root = new ComparatorTreeNode(oldModel.getRoot(), newModel.getRoot());
        fillNode(root, oldModel.getRoot(), newModel.getRoot());
    }

    public ComparatorTreeNode getRoot() {
        return root;
    }

    public ComparatorTreeNode getChild(Object parent, int index) {
        if (!(parent instanceof ComparatorTreeNode))
            throw new IllegalArgumentException("Illegal parent");
        return ((ComparatorTreeNode)parent).getChild(index);
    }

    public int getChildCount(Object parent) {
        if (!(parent instanceof ComparatorTreeNode))
            throw new IllegalArgumentException("Illegal parent");
        return ((ComparatorTreeNode)parent).getChildrenCount();
    }

    public boolean isLeaf(Object node) {
        if (!(node instanceof ComparatorTreeNode))
            throw new IllegalArgumentException("Illegal node");
        return ((ComparatorTreeNode)node).getChildrenCount() == 0;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    public int getIndexOfChild(Object parent, Object child) {
        if (!(parent instanceof ComparatorTreeNode))
            throw new IllegalArgumentException("Illegal parent");
        if (!(child instanceof ComparatorTreeNode))
            throw new IllegalArgumentException("Illegal child");
        return ((ComparatorTreeNode)parent).getIndexOfChild(((ComparatorTreeNode)child));
    }

    public void addTreeModelListener(TreeModelListener l) {
    }

    public void removeTreeModelListener(TreeModelListener l) {
    }
}
