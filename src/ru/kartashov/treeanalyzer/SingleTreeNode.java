package ru.kartashov.treeanalyzer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Shoff
 * Date: 02.06.13
 * Time: 0:08
 */
public class SingleTreeNode {

    private Component component;
    private String name;
    private ArrayList<SingleTreeNode> nodes;
    private SingleNodeTableModel nodeTableModel;

    public Component getComponent() {
        return component;
    }

    public SingleTreeNode(Component component) {
        this.component = component;
        String compClassName = component.getClass().getName();
        this.name = compClassName.substring(compClassName.lastIndexOf(".") + 1);
        nodes = new ArrayList<SingleTreeNode>();
        nodeTableModel = new SingleNodeTableModel(component);
    }

    public void add(SingleTreeNode child){
        nodes.add(child);
    }

    public SingleTreeNode getChild(int index){
        return nodes.get(index);
    }

    @Override
    public String toString(){
        return name;
    }

    public int getChildrenCount(){
        return nodes.size();
    }

    public int getIndexOfChild(SingleTreeNode element){
        return nodes.indexOf(element);
    }

    public SingleNodeTableModel getNodeTableModel(){
        return nodeTableModel;
    }

}
