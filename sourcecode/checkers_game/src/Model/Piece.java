package Model;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;

public abstract class Piece implements Serializable{

	protected Check position;
	protected Player owner;
	protected String color;

        public Player getOwner() {
            return owner;
        }

        public void setOwner(Player owner) {
            this.owner = owner;
        }     
        

        public Piece(Check position, String color) {
            this.position = position;
            this.color = color;
        }

        public Check getPosition() {
            return position;
        }

        public void setPosition(Check position) {
            this.position = position;
        }  

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}
        
        public abstract HashMap<ArrayList,Integer> getPossibleMoves();
        
        public abstract Tree<Check> getRifleMove(Tree<Check> possibilities,Gameboard gameboard);

	public abstract void move(Check arrival);
        
        public abstract void riffleMove(ArrayList<Check> path);
        
        public void disapear(){
            this.getPosition().setcheckPiece(null);        
            this.setPosition(null);
        }
        
        public void die(){
            if(this.getOwner()!=null){
                this.getOwner().deletePiece(this);
            }            
            this.setOwner(null);
            if(this.getPosition()!=null){
                this.getPosition().setcheckPiece(null);
            }                    
            this.setPosition(null);
    }

}