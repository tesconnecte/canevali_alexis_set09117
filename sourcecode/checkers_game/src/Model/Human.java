/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import UI.MainWindowContent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author alexi_000
 */
public class Human extends Player implements Serializable{

    public Human(String name) {
        super(name);
    }
    
    public void displayRiffleMove(ArrayList<Check> riffle){
        System.out.println("Riffle path: [Line,Colomn]  -->  [next Check Line, next Check Colomn]  -->  ...");
        int nbChecks = 1;
        for(Check currentCheck : riffle){
            if(nbChecks==riffle.size()){
                System.out.println("["+(currentCheck.getLineNumber()+1)+","+(currentCheck.getColomnNumber()+1)+"]\n--------------------------------------------");
            }else{
                System.out.print("["+(currentCheck.getLineNumber()+1)+","+(currentCheck.getColomnNumber()+1)+"]  -->  ");
            }
            nbChecks++;           
        }
    }
    
    public void displayMove(ArrayList<Check> move){
        System.out.println("next Check: Line "+(move.get(0).getLineNumber()+1)+", Colomn "+(move.get(0).getColomnNumber()+1));        
    }
    
    public ArrayList<Check> chooseNextMove(HashMap<ArrayList,Integer> possibleMoves){
        Scanner keyboardUSER = new Scanner(System.in);
        int choice=0;
        int count=1;
        boolean rightChoice=false;
        System.out.println("Choose the next Check :");
        for(ArrayList currentoption : possibleMoves.keySet()){            
            System.out.print("Option "+count+") ");
            if(possibleMoves.get(currentoption)==2){
                this.displayRiffleMove(currentoption);
            } else {
                this.displayMove(currentoption);
            }
            
            count++;
        }
        while(!rightChoice){
            System.out.println("Select an option above from 1 to "+(count-1));
            choice=(keyboardUSER.nextInt());
            if((choice>=1)&&(choice<count)){
                rightChoice=true;
            } else {
                System.out.println("Wrong choice try again between 1 and "+(count-1));
            }
        }
        
        Set<ArrayList> keys = possibleMoves.keySet();
        Iterator<ArrayList> it = keys.iterator();
        ArrayList<Check> result = new ArrayList<Check>();
        ArrayList<Check> arrayIterator;
        int currentIndex = 0;
        while(it.hasNext()){
            arrayIterator =  it.next();
            if(currentIndex==(choice-1)){
                result = arrayIterator;
            }
            currentIndex++;
        }   
        return result;
    }

    /*
    *
    * command line version of choosePieceToMove()
    public Piece choosePieceToMove() {
        Scanner keyboardUSER = new Scanner(System.in);
        int choice=0;
        int count=1;
        boolean rightChoice=false;
        ArrayList<Piece> movablePiece = new ArrayList<Piece>();
        HashMap<Piece,HashMap> movablepieceAndPossibleMoves = new HashMap<Piece,HashMap>();
        HashMap<Piece,HashMap> possibleRifflePiece = new HashMap<Piece,HashMap>();
        HashMap<Piece,HashMap> possibleCapturePiece = new HashMap<Piece,HashMap>();

        for(Piece currentoption : this.getPieces()){
            if(!currentoption.getPossibleMoves().isEmpty()){
                movablepieceAndPossibleMoves.put(currentoption, currentoption.getPossibleMoves()); 
            }           
        }
        
        Set<Piece> keyPieceSet = movablepieceAndPossibleMoves.keySet();
        Iterator<Piece> it = keyPieceSet.iterator();
        Piece currentPiece;
        HashMap<ArrayList,Integer> currentPossibleMove;
        while(it.hasNext()){
            currentPiece=it.next();
            currentPossibleMove = movablepieceAndPossibleMoves.get(currentPiece);
            if(currentPossibleMove.containsValue(2)){
                possibleRifflePiece.put(currentPiece, currentPossibleMove);
            }
            
            if(currentPossibleMove.containsValue(1)){
                possibleCapturePiece.put(currentPiece, currentPossibleMove);
            }
        }
        
        if(!possibleRifflePiece.isEmpty()){
            keyPieceSet = possibleRifflePiece.keySet();
            it = keyPieceSet.iterator();
            Set<ArrayList> arrayListMoveSet;
            Iterator<ArrayList> it2;
            ArrayList<Check> rifflePath;
            ArrayList<Check> longestRiffle = new ArrayList<Check>();
            Piece longestRifflePiece = null;
            while(it.hasNext()){
                currentPiece=it.next();
                currentPossibleMove = movablepieceAndPossibleMoves.get(currentPiece);
                arrayListMoveSet = currentPossibleMove.keySet();
                it2 = arrayListMoveSet.iterator();
                while(it2.hasNext()){
                    rifflePath=it2.next();
                    if(rifflePath.size()>longestRiffle.size()){
                        longestRiffle=rifflePath;
                        longestRifflePiece=currentPiece;
                    }                    
                }                
            }
            if(longestRifflePiece!=null){
                movablePiece.add(longestRifflePiece);
            }            
        } else if(!possibleCapturePiece.isEmpty()){
            movablePiece.addAll(possibleCapturePiece.keySet());
        } else {
            movablePiece.addAll(movablepieceAndPossibleMoves.keySet());
        }
        
        
        if(!movablePiece.isEmpty()){
            System.out.println("Choose the piece to move :");
            for(Piece choicePiece : movablePiece){
            System.out.println(count+") Line "+(choicePiece.getPosition().getLineNumber()+1)+" | Colomn "+(choicePiece.getPosition().getColomnNumber()+1));           
            count++;            
            }            
            
            while(!rightChoice){
                System.out.println("Select an option above from 1 to "+(count-1));
                choice=(keyboardUSER.nextInt());
                if((choice>=1)&&(choice<count)){
                    rightChoice=true;
                } else {
                    System.out.println("Wrong choice try again between 1 and "+(count-1));
                }
            }
            
            return(movablePiece.get((choice-1)));
        }else{
            return null;
        }
    }*/
    
    public ArrayList<Piece> choosePieceToMove() {
        Scanner keyboardUSER = new Scanner(System.in);
        int choice=0;
        int count=1;
        boolean rightChoice=false;
        ArrayList<Piece> movablePiece = new ArrayList<Piece>();
        HashMap<Piece,HashMap> movablepieceAndPossibleMoves = new HashMap<Piece,HashMap>();
        HashMap<Piece,HashMap> possibleRifflePiece = new HashMap<Piece,HashMap>();
        HashMap<Piece,HashMap> possibleCapturePiece = new HashMap<Piece,HashMap>();

        for(Piece currentoption : this.getPieces()){
            if(!currentoption.getPossibleMoves().isEmpty()){
                movablepieceAndPossibleMoves.put(currentoption, currentoption.getPossibleMoves()); 
            }           
        }
        
        Set<Piece> keyPieceSet = movablepieceAndPossibleMoves.keySet();
        Iterator<Piece> it = keyPieceSet.iterator();
        Piece currentPiece;
        HashMap<ArrayList,Integer> currentPossibleMove;
        while(it.hasNext()){
            currentPiece=it.next();
            currentPossibleMove = movablepieceAndPossibleMoves.get(currentPiece);
            if(currentPossibleMove.containsValue(2)){
                possibleRifflePiece.put(currentPiece, currentPossibleMove);
            }
            
            if(currentPossibleMove.containsValue(1)){
                possibleCapturePiece.put(currentPiece, currentPossibleMove);
            }
        }
        
        if(!possibleRifflePiece.isEmpty()){
            keyPieceSet = possibleRifflePiece.keySet();
            it = keyPieceSet.iterator();
            Set<ArrayList> arrayListMoveSet;
            Iterator<ArrayList> it2;
            ArrayList<Check> rifflePath;
            ArrayList<Check> longestRiffle = new ArrayList<Check>();
            Piece longestRifflePiece = null;
            while(it.hasNext()){
                currentPiece=it.next();
                currentPossibleMove = movablepieceAndPossibleMoves.get(currentPiece);
                arrayListMoveSet = currentPossibleMove.keySet();
                it2 = arrayListMoveSet.iterator();
                while(it2.hasNext()){
                    rifflePath=it2.next();
                    if(rifflePath.size()>longestRiffle.size()){
                        longestRiffle=rifflePath;
                        longestRifflePiece=currentPiece;
                    }                    
                }                
            }
            if(longestRifflePiece!=null){
                movablePiece.add(longestRifflePiece);
            }            
        } else if(!possibleCapturePiece.isEmpty()){
            movablePiece.addAll(possibleCapturePiece.keySet());
        } else {
            movablePiece.addAll(movablepieceAndPossibleMoves.keySet());
        }
        
        if(!movablePiece.isEmpty()){
            return movablePiece;
        } else {
            return null;
        }       
        
    }
    
    @Override
    public void playOnce(){
        /*System.out.println(this.getName()+"'s turn to play !");
        Piece pieceToMove = this.choosePieceToMove();
        if(pieceToMove!=null){
            ArrayList<Check> arrival = this.chooseNextMove(pieceToMove.getPossibleMoves());
            if(arrival.size()>1){
                pieceToMove.riffleMove(arrival);
            } else {
                pieceToMove.move(arrival.get(0));    
            }                    
        } else {
            System.out.println(this.getName()+" cannot play !");
        }*/
    }
    
}
