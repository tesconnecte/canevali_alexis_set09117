package Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class King extends Piece implements Serializable{

    public King(Check position, String color) {
        super(position, color);
    }
    
    @Override
    public Tree<Check> getRifleMove(Tree<Check> possibilities,Gameboard gameboard){
       // System.out.println("\nROOT of check line "+(possibilities.getData().getLineNumber()+1)+" and colomn "+(possibilities.getData().getColomnNumber()+1));
            Gameboard copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
            Gameboard originalGameboard = possibilities.getData().getGameboard();
            Check loopCheck;
            Tree<Check> loopTree = possibilities;
            while(loopTree.getParent()!=null){
                loopCheck = (Check)possibilities.getParent().getData();
                originalGameboard = loopCheck.getGameboard();
                loopTree = loopTree.getParent();
            }
            
            Check previousCheckOnOldGameboard = (Check)possibilities.getData();
            Check previousCheckOnNewGameboard = copyGameboard.getCheckByLineColomn(previousCheckOnOldGameboard.getLineNumber(), previousCheckOnOldGameboard.getColomnNumber());
            Check currentCheck = copyGameboard.getCheckByLineColomn(possibilities.getData().getLineNumber(), possibilities.getData().getColomnNumber());
            King currentKing;
            if(possibilities.getParent()!=null){
                previousCheckOnOldGameboard = (Check)possibilities.getParent().getData();
                previousCheckOnNewGameboard = copyGameboard.getCheckByLineColomn(previousCheckOnOldGameboard.getLineNumber(), previousCheckOnOldGameboard.getColomnNumber());
                /*On the new copied gameboard, we copy the King to its new position*/
                previousCheckOnNewGameboard.getcheckPiece().setPosition(currentCheck);
                currentCheck.setcheckPiece(previousCheckOnNewGameboard.getcheckPiece());
                previousCheckOnNewGameboard.setcheckPiece(null);
                currentKing = (King)currentCheck.getcheckPiece();
            }else {//1st case, King does not need to be moved
                currentKing = (King)currentCheck.getcheckPiece();
            }
            
            HashMap<Check,Gameboard> nextPossibleMoves = new HashMap<Check,Gameboard>();
            nextPossibleMoves.putAll(currentKing.getKingCapture(copyGameboard));
            Tree<Check> newChildTree;
            
            
            
            for(Check currentPossibleNextCheck : nextPossibleMoves.keySet()){
                    newChildTree = new Tree<Check>(originalGameboard.getCheckByLineColomn(currentPossibleNextCheck.getLineNumber(), currentPossibleNextCheck.getColomnNumber()));
                    possibilities.addChild(newChildTree);         
            }
            
            Check keyCheck;
            Check treeCheck;
            for(Tree currentTree : possibilities.getChildren()){
                treeCheck = (Check)currentTree.getData();
                keyCheck = copyGameboard.getCheckByLineColomn(treeCheck.getLineNumber(), treeCheck.getColomnNumber());
                getRifleMove(currentTree,nextPossibleMoves.get(keyCheck));
            }
            
            return possibilities;
    }
    
    public HashMap<Check,Gameboard> getKingCapture(Gameboard gameboard){
        int positionLine = this.getPosition().getLineNumber();
        int positionColomn = this.getPosition().getColomnNumber();
        int nbGameboardLines = (gameboard.getNbLines()-1);//Minus 1 to match array storage which starts at 0
        int nbGameboardColomns = (gameboard.getNbColomns()-1);//Minus 1 to match array storage which starts at 0
        Check currentOption;
        Piece adversaryPiece;
        HashMap<Check,Gameboard> possibleCaptures = new HashMap<Check,Gameboard>();
        Gameboard copyGameboard;
        boolean isFree;

        /*Diagonal 1*/
        while(((positionLine-1)>=0)&&((positionColomn-1)>=0)){
            currentOption = gameboard.getCheckByLineColomn((positionLine-1), (positionColomn-1));
            if(currentOption.isOccupied()){
                adversaryPiece = currentOption.getcheckPiece();
                positionLine--;
                positionColomn--;
                if(!adversaryPiece.getColor().equals(this.getColor())){
                    copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
                    copyGameboard.getCheckByLineColomn(currentOption.getLineNumber(), currentOption.getColomnNumber()).getcheckPiece().disapear();
                    isFree  = true;
                    while((isFree)&&((positionLine-1)>=0)&&((positionColomn-1)>=0)){
                        currentOption = gameboard.getCheckByLineColomn((positionLine-1), (positionColomn-1));
                        if(currentOption.isOccupied()){
                            isFree=false;
                        }else{
                            possibleCaptures.put(currentOption, copyGameboard);

                        }
                        positionLine--;
                        positionColomn--;
                    }
                    break;
                }
            }
            positionLine--;
            positionColomn--;
        }
        
        positionLine = this.getPosition().getLineNumber();
        positionColomn = this.getPosition().getColomnNumber();
        
        /*Diagonal 2*/
        while(((positionLine-1)>=0)&&((positionColomn+1)<=nbGameboardColomns)){
            currentOption = gameboard.getCheckByLineColomn((positionLine-1), (positionColomn+1));
            if(currentOption.isOccupied()){
                adversaryPiece = currentOption.getcheckPiece();                
                positionLine--;
                positionColomn++;
                if(!adversaryPiece.getColor().equals(this.getColor())){
                    copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
                    copyGameboard.getCheckByLineColomn(currentOption.getLineNumber(), currentOption.getColomnNumber()).getcheckPiece().disapear();
                    isFree  = true;                    
                    while((isFree)&&((positionLine-1)>=0)&&((positionColomn+1)<=nbGameboardColomns)){
                        currentOption = gameboard.getCheckByLineColomn((positionLine-1),(positionColomn+1));
                        if(currentOption.isOccupied()){
                            isFree=false;
                        }else{
                            possibleCaptures.put(currentOption, copyGameboard);
                        }
                        positionLine--;
                        positionColomn++;
                    }
                    break;
                }
            }
            positionLine--;
            positionColomn++;
        }
        
        positionLine = this.getPosition().getLineNumber();
        positionColomn = this.getPosition().getColomnNumber();
        
        /*Diagonal 3*/
        while(((positionLine+1)<=nbGameboardLines)&&((positionColomn-1)>=0)){
            currentOption = gameboard.getCheckByLineColomn((positionLine+1), (positionColomn-1));
            if(currentOption.isOccupied()){
                adversaryPiece = currentOption.getcheckPiece();              
                positionLine++;
                positionColomn--;
                if(!adversaryPiece.getColor().equals(this.getColor())){
                    copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
                    copyGameboard.getCheckByLineColomn(currentOption.getLineNumber(), currentOption.getColomnNumber()).getcheckPiece().disapear();
                    isFree  = true;                    
                    while((isFree)&&((positionLine+1)<=nbGameboardLines)&&((positionColomn-1)>=0)){
                        currentOption = gameboard.getCheckByLineColomn((positionLine+1),(positionColomn-1));
                        if(currentOption.isOccupied()){
                            isFree=false;
                        }else{
                            possibleCaptures.put(currentOption, copyGameboard);
                        }
                        positionLine++;
                        positionColomn--;
                    }
                    break;
                }
            }
            positionLine++;
            positionColomn--;
        }
        
        isFree = true;
        positionLine = this.getPosition().getLineNumber();
        positionColomn = this.getPosition().getColomnNumber();
        
        /*Diagonal 4*/
        while((isFree)&&((positionLine+1)<=nbGameboardLines)&&((positionColomn+1)<=nbGameboardColomns)){
            currentOption = gameboard.getCheckByLineColomn((positionLine+1), (positionColomn+1));
            if(currentOption.isOccupied()){
                adversaryPiece = currentOption.getcheckPiece();          
                positionLine++;
                positionColomn++;
                if(!adversaryPiece.getColor().equals(this.getColor())){
                    copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
                    copyGameboard.getCheckByLineColomn(currentOption.getLineNumber(), currentOption.getColomnNumber()).getcheckPiece().disapear();
                    isFree  = true;                   
                    while((isFree)&&((positionLine+1)<=nbGameboardLines)&&((positionColomn+1)<=nbGameboardColomns)){
                        currentOption = gameboard.getCheckByLineColomn((positionLine+1),(positionColomn+1));
                        if(currentOption.isOccupied()){
                            isFree=false;
                        }else{
                            possibleCaptures.put(currentOption, copyGameboard);
                        }
                        positionLine++;
                        positionColomn++;
                    }
                    break;
                }
            }
            positionLine++;
            positionColomn++;
        }       

        return possibleCaptures;
    }
    
    public ArrayList<Check> getKingMove(){
        int positionLine = this.getPosition().getLineNumber();
        int positionColomn = this.getPosition().getColomnNumber();
        Gameboard gameboard = this.getPosition().getGameboard();
        int nbGameboardLines = (gameboard.getNbLines()-1);//Minus 1 to match array storage which starts at 0
        int nbGameboardColomns = (gameboard.getNbColomns()-1);//Minus 1 to match array storage which starts at 0
        Check currentOption;
        boolean isFree  = true;
        ArrayList<Check> possibleMoves = new ArrayList<Check>();

        /*Diagonal 1*/
        while((isFree)&&((positionLine-1)>=0)&&((positionColomn-1)>=0)){
            currentOption = gameboard.getCheckByLineColomn((positionLine-1), (positionColomn-1));
            if(currentOption.isOccupied()){
                isFree = false;
            } else {
                possibleMoves.add(currentOption);
            }
            positionLine--;
            positionColomn--;
        }
        
        isFree = true;
        positionLine = this.getPosition().getLineNumber();
        positionColomn = this.getPosition().getColomnNumber();
        
        /*Diagonal 2*/
        while((isFree)&&((positionLine-1)>=0)&&((positionColomn+1)<=nbGameboardColomns)){
            currentOption = gameboard.getCheckByLineColomn((positionLine-1), (positionColomn+1));
            if(currentOption.isOccupied()){
                isFree = false;
            } else {
                possibleMoves.add(currentOption);
            }
            positionLine--;
            positionColomn++;
        }
        
        isFree = true;
        positionLine = this.getPosition().getLineNumber();
        positionColomn = this.getPosition().getColomnNumber();
        
        /*Diagonal 3*/
        while((isFree)&&((positionLine+1)<=nbGameboardLines)&&((positionColomn-1)>=0)){
            currentOption = gameboard.getCheckByLineColomn((positionLine+1), (positionColomn-1));
            if(currentOption.isOccupied()){
                isFree = false;
            } else {
                possibleMoves.add(currentOption);
            }
            positionLine++;
            positionColomn--;
        }
        
        isFree = true;
        positionLine = this.getPosition().getLineNumber();
        positionColomn = this.getPosition().getColomnNumber();
        
        /*Diagonal 4*/
        while((isFree)&&((positionLine+1)<=nbGameboardLines)&&((positionColomn+1)<=nbGameboardColomns)){
            currentOption = gameboard.getCheckByLineColomn((positionLine+1), (positionColomn+1));
            if(currentOption.isOccupied()){
                isFree = false;
            } else {
                possibleMoves.add(currentOption);
            }
            positionLine++;
            positionColomn++;
        }       

        return possibleMoves;
    }
    
    @Override
    public HashMap<ArrayList,Integer> getPossibleMoves() {
        /**
         *We return a HashMap<ArrayList,Integer>
         * The ArrayList corresponds to a set of possible checks :
         *      - For a simple move or a simple capture it will only be one check
         *      - For a riffle, it includes all the necessary Checks to realize it
         * Integer corresponds to the move type :
         *      - 0: Simple move or simple capture move
         *      - 1: Simple capture move
         *      - 2: Riffle move
         * 
         * If a riffle is possible, The player has no other choice to do it
         * If multiple riffle are possible, The player must move its piece which can realize the longest riffle
         * If there are no riffle possible, Then we check if there are capture possible, If it is, the player has no other choice to do it
         * If there are no riffle or simple capture possible, we check if there are simple moves possible. 
         */
        HashMap<ArrayList,Integer> results = new HashMap<ArrayList,Integer>();
        
        Tree<Check> rootCurrentPosition = new Tree<Check>(this.getPosition());
        Tree<Check> riffle;
        riffle = this.getRifleMove(rootCurrentPosition,this.getPosition().getGameboard());
        ArrayList<ArrayList> longestRiffle = riffle.getLongestTreePath();
        ArrayList<Check> captureMoves = new ArrayList<Check>();
        ArrayList<Check> simpleMoves = new ArrayList<Check>();
        ArrayList<Check> newMoveToAdd;
        Gameboard gameboardCopy;
        
        if((!longestRiffle.isEmpty())&&(longestRiffle.get(0)).size()>2){// Must be > 2 to be consider as a riffle, else where it's a simple capture
            for(ArrayList currentRifle : longestRiffle){
                    results.put(currentRifle, 2);             
            }
        } else {            
            gameboardCopy = (Gameboard)DeepCopy.copy(this.getPosition().getGameboard());            
            captureMoves.addAll(this.getKingCapture(gameboardCopy).keySet());
            if(!captureMoves.isEmpty()){
                for(Check currentCheck : captureMoves){
                    newMoveToAdd = new ArrayList<Check>();
                    newMoveToAdd.add(currentCheck);
                    results.put(newMoveToAdd,1);
                }
            }else{
                simpleMoves.addAll(this.getKingMove());
                for(Check currentCheck : simpleMoves){
                    newMoveToAdd = new ArrayList<Check>();
                    newMoveToAdd.add(currentCheck);
                    results.put(newMoveToAdd,0);
                }                
            }           
            
        }      
        return results;
    }        

    @Override
    public void move(Check arrival) {
        Gameboard gameboard = this.getPosition().getGameboard();
        int currentLine = this.getPosition().getLineNumber();
        int currentColomn = this.getPosition().getColomnNumber();
        int limitLine = arrival.getLineNumber();
        int limitColomn = arrival.getColomnNumber();
        Check currentOption;
        int offsetLine = limitLine-currentLine;
        int offsetColomn = limitColomn-currentColomn;
        int diagonal;
        
        if((offsetLine>0)&&(offsetColomn>0)){
            diagonal = 4;
        } else if((offsetLine>0)&&(offsetColomn<0)){
            diagonal = 3;
        } else if ((offsetLine<0)&&(offsetColomn>0)){
            diagonal = 2;
        } else {
            diagonal = 1;
        }
        
        
        switch(diagonal){
            case 1:
                while(((currentLine-1)>=limitLine)&&((currentColomn-1)>=limitColomn)){
                    currentOption = gameboard.getCheckByLineColomn((currentLine-1), (currentColomn-1));
                    if(currentOption.isOccupied()){
                        currentOption.getcheckPiece().die();
                    }
                    currentLine--;
                    currentColomn--;
                }
            break;   
            
            case 2:
                while(((currentLine-1)>=limitLine)&&((currentColomn+1)<=limitColomn)){
                    currentOption = gameboard.getCheckByLineColomn((currentLine-1), (currentColomn+1));
                    if(currentOption.isOccupied()){
                        currentOption.getcheckPiece().die();
                    }
                    currentLine--;
                    currentColomn++;
                }
            break;
            
            case 3:
                while(((currentLine+1)<=limitLine)&&((currentColomn-1)>=limitColomn)){
                    currentOption = gameboard.getCheckByLineColomn((currentLine+1), (currentColomn-1));
                    if(currentOption.isOccupied()){
                        currentOption.getcheckPiece().die();
                    }
                    currentLine++;
                    currentColomn--;
                }
            break;
            
            case 4:
                while(((currentLine+1)<=limitLine)&&((currentColomn+1)<=limitColomn)){
                    currentOption = gameboard.getCheckByLineColomn((currentLine+1), (currentColomn+1));
                    if(currentOption.isOccupied()){
                        currentOption.getcheckPiece().die();
                    }
                    currentLine++;
                    currentColomn++;
                }    
            break;
        }        
        this.getPosition().setcheckPiece(null);
        this.setPosition(arrival);
        arrival.setcheckPiece(this);
    }
    
    @Override
    public void riffleMove(ArrayList<Check> path) {
        for(Check currentCheck : path){
            this.move(currentCheck);
        }
    }
    
    public ArrayList<Check> getIntervalCheck(int diagonal,Check limit){
        int currentLine = this.getPosition().getLineNumber();
        int currentColomn = this.getPosition().getColomnNumber();
        int limitLine = limit.getLineNumber();
        int limitColomn = limit.getColomnNumber();
        Check currentOption;
        Gameboard gameboard = this.getPosition().getGameboard();
        ArrayList<Check> result = new ArrayList<>();
    
        switch(diagonal){
            case 1:
                while(((currentLine-1)>=limitLine)&&((currentColomn-1)>=limitColomn)){
                    currentOption = gameboard.getCheckByLineColomn((currentLine-1), (currentColomn-1));
                    result.add(currentOption);
                    currentLine--;
                    currentColomn--;
                }
            break;   
            
            case 2:
                while(((currentLine-1)>=limitLine)&&((currentColomn+1)<=limitColomn)){
                    currentOption = gameboard.getCheckByLineColomn((currentLine-1), (currentColomn+1));
                    result.add(currentOption);
                    currentLine--;
                    currentColomn++;
                }
            break;
            
            case 3:
                while(((currentLine+1)<=limitLine)&&((currentColomn-1)>=limitColomn)){
                    currentOption = gameboard.getCheckByLineColomn((currentLine+1), (currentColomn-1));
                    result.add(currentOption);
                    currentLine++;
                    currentColomn--;
                }
            break;
            
            case 4:
                while(((currentLine+1)<=limitLine)&&((currentColomn+1)<=limitColomn)){
                    currentOption = gameboard.getCheckByLineColomn((currentLine+1), (currentColomn+1));
                    result.add(currentOption);
                    currentLine++;
                    currentColomn++;
                }    
            break;
        }
        
        return result;
    }

    
}