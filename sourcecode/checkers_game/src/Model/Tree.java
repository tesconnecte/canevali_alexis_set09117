/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author alexi_000
 * @param <T>
 */
public class Tree<T> {
    
    private T data = null;
    private ArrayList<Tree> children = new ArrayList<>();
    private Tree parent = null;

    public Tree(T data) {
        this.data = data;
    }

    public void addChild(Tree child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(T data) {
        Tree<T> newChild = new Tree<>(data);
        newChild.setParent(this);
        children.add(newChild);
    }
    
    public void removeFirstChild(){
        if(this.getChildren().size()>0){
            this.getChildren().remove(0);
        }
    }

    public void addChildren(ArrayList<Tree> children) {
        for(Tree t : children) {
            t.setParent(this);
        }
        this.children.addAll(children);
    }

    public ArrayList<Tree> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setParent(Tree parent) {
        this.parent = parent;
    }

    public Tree getParent() {
        return parent;
    }
    
    public ArrayList<ArrayList> getLongestTreePath(){
        ArrayList<ArrayList> paths = new ArrayList<ArrayList>();
        Tree currentTree = this;
        ArrayList<T> currentPath = new ArrayList<T>();
        while(currentTree!=null){
            if(currentTree==this){
                currentPath = new ArrayList<T>();
            }
            currentPath.add((T)currentTree.getData());
            
            if(currentTree.getChildren().size()>0){
                currentTree=(Tree)currentTree.getChildren().get(0);
            } else {
                currentTree=currentTree.getParent();
                if(currentTree!=null){
                    currentTree.removeFirstChild();
                    currentTree=this;
                }
                paths.add(currentPath);
            }           
            
        }
        
        ArrayList<ArrayList> result = new ArrayList<ArrayList>();
        ArrayList<T> emptyArrayList = new ArrayList<>();
        result.add(emptyArrayList);        
        
        for(ArrayList currentArray : paths){
            if(currentArray.size()>result.get(0).size()){
                result.removeAll(result);
                result.add(currentArray);
            }else if(currentArray.size()==result.get(0).size()){
                result.add(currentArray);
            }
        }
        
        return result;        
        
        }
    
 
    
    public void drawTree(){
        if(this.getData() instanceof Check){
            Check currentCheck = (Check)this.getData();
            System.out.println("Line : "+(currentCheck.getLineNumber()+1)+" | Colomn "+(currentCheck.getColomnNumber()+1));
        } else {
            System.out.println(this.getData());

        }
        ArrayList<Tree> children = this.getChildren();
        for(Tree currentChild : children){
            currentChild.drawTree();
        }
        
    }
    
}
