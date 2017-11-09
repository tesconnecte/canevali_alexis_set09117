package Model;

import java.util.*;
import java.io.Serializable;

public class Gameboard implements Serializable{

	private int nbLines;
	private int nbColomns;
	private Check[][] gameboardChecks;

        public Gameboard() {
            this.nbColomns=10;
            this.nbLines=10;
            this.gameboardChecks = new Check[nbLines][nbColomns];
            Check currentCheck;
            for(int i=0;i<nbLines;i++){
                for(int j=0;j<nbColomns;j++){
                    currentCheck = new Check(i, j, this);
                    this.gameboardChecks[i][j]=(currentCheck);
                }
            }
        }
        
        public void emptyGameboard(){
            Check currentCheck;
            Piece currentPiece;
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    currentCheck=this.getCheckByLineColomn(i, j);
                    currentPiece=currentCheck.getcheckPiece();
                    if(currentPiece!=null){
                        currentPiece.die();
                        currentCheck.setcheckPiece(null);
                    }                    
                }
            }
        }
        
        

	public int getNbLines() {
		return this.nbLines;
	}

	public int getNbColomns() {
		return this.nbColomns;
	}

        public Check[][] getGameboardChecks() {
            return gameboardChecks;
        }
        
        public Check getCheckByLineColomn(int line,int colomn){            
                return this.getGameboardChecks()[line][colomn];            
        }
        
        public ArrayList<Piece> getPiecesByColor(String color){
            ArrayList<Piece> colorPieces = new ArrayList<Piece>();
            Check currentCheck;
            Piece currentPiece;
            for(int i=0;i<this.getNbLines();i++){
                for(int j=0;j<this.getNbColomns();j++){
                    currentCheck = this.getCheckByLineColomn(i, j);
                    if(currentCheck.isOccupied()){
                        currentPiece = currentCheck.getcheckPiece();
                        if(currentPiece.getColor().equals(color)){
                            colorPieces.add(currentPiece);
                        }                        
                    }
                }
            }
            return colorPieces;
        }
        
        

	public void drawGameboard(){            
            //System.out.println("Nombre de lignes :"+mainGameboard.getNbLines());
            //System.out.println("Nombre de colonnes :"+mainGameboard.getNbColomns());
            System.out.print("  |");
            Check[][] checksList = this.getGameboardChecks();            
            for(int i=0;i<10;i++){
                if(i==9){
                    System.out.println((i+1)+"|");
                } else {
                    System.out.print((i+1)+" |");
                }                
            }
            
            for(int i=0;i<this.getNbLines();i++){
                if(i<9){
                    System.out.print((i+1)+" ");
                } else {
                    System.out.print(i+1);
                }
                for(int j=0;j<this.getNbColomns();j++){                    
                    System.out.print("|");
                    if(checksList[i][j].isOccupied()==true){
                        if(checksList[i][j].getcheckPiece().getColor().equals("black")){
                            if(checksList[i][j].getcheckPiece() instanceof King){
                                System.out.print("KB");
                            }else {
                                System.out.print("mB");
                            }                            
                        } else {
                            if(checksList[i][j].getcheckPiece() instanceof King){
                                System.out.print("KW");
                            }else {
                                System.out.print("mW");
                            }   
                        }
                        //System.out.println("Case de la ligne "+(currentCheck.getLineNumber()+1)+" et de colonne "+(currentCheck.getColomnNumber()+1)+ " occupé par un pion "+currentCheck.getcheckPiece().getColor());
                    } else {                  
                        System.out.print("  ");
                    }                
                }
                System.out.println("|");
            }
	}

}