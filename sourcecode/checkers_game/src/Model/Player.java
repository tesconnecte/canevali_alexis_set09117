package Model;

import java.util.*;
import java.io.Serializable;

public abstract class Player implements Serializable{
    
    protected ArrayList<Piece> pieces;
    protected String name;

    public Player(String name) {
        this.name = name;
        this.pieces = new ArrayList<Piece>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }
    
    public void addPiece(Piece piece){
        this.pieces.add(piece);
    }

    public void deletePiece(Piece piece){
        int index = this.getPieces().indexOf(piece);
        if(index!=-1){
           this.getPieces().remove(index);
        }
    }
    
    public abstract void playOnce();


	
	

}