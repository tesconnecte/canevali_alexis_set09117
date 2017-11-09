package Model;
import java.io.Serializable;
import java.util.*;

public class AI extends Player implements Serializable{

    public AI() {
        super("Computer");
    }
    
    public AI(String name) {
        super(name);
    }
    
    public ArrayList<Piece> getPiecesMovableWithRules() {
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
        
        return movablePiece;
    }
    
    public HashMap<ArrayList,Piece> evaluateOptionsforPieces(){
            ArrayList<Piece> piecesList = this.getPiecesMovableWithRules();
            ArrayList<HashMap> bestOptions = new ArrayList<HashMap>();
            ArrayList<HashMap> otherOptions = new ArrayList<HashMap>();          
            HashMap<ArrayList,Piece> movesAndPiece;
            HashMap<ArrayList,Integer> possibleMoves;
            ArrayList<Check> pieceMoves;
            Iterator<ArrayList> it;
            boolean willDieAfterMove;
            Check destination;
            for(Piece currentPiece : piecesList){
                possibleMoves = currentPiece.getPossibleMoves();
                it = possibleMoves.keySet().iterator();                
                while(it.hasNext()){
                    pieceMoves = it.next();
                    destination = pieceMoves.get((pieceMoves.size()-1));
                    willDieAfterMove = destination.nearCheckOccupied(currentPiece.getColor());
                    movesAndPiece = new HashMap<ArrayList,Piece>();
                    movesAndPiece.put(pieceMoves, currentPiece);
                    if(!willDieAfterMove){
                        bestOptions.add(movesAndPiece);
                    }else{
                        otherOptions.add(movesAndPiece);
                    }                    
                }
            }
            
            if(!bestOptions.isEmpty()){
                return bestOptions.get((int) (Math.random()*(bestOptions.size()-1)));
            }else if(!otherOptions.isEmpty()){                
                return otherOptions.get((int) (Math.random()*(otherOptions.size()-1)));               
            }else {
                return null;
            }
    }
    
    public void playOnce(){
        System.out.println(this.getName()+"'s turn to play !");
        HashMap<ArrayList,Piece> pieceToMoveAndMoves = this.evaluateOptionsforPieces();
        if(pieceToMoveAndMoves!=null){
            Iterator<ArrayList> it = pieceToMoveAndMoves.keySet().iterator();
            ArrayList<Check> movesToDo = it.next();
            Piece pieceToMove = pieceToMoveAndMoves.get(movesToDo);
            if(movesToDo.size()>1){
                pieceToMove.riffleMove(movesToDo);
            } else {
                pieceToMove.move(movesToDo.get(0));    
            }                    
        } else {
            System.out.println(this.getName()+" cannot play !");
        }
    }
    
}