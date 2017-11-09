package Model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;
import java.util.HashMap;

public class Man extends Piece implements Serializable{

    /**
    * 2 possible directions :
    *  - 0 : From top of the gameboard to the bottom
    *  - 1 : From bottom of the gameboard to the top
    */

    private int destination;

    public Man(Check position, String color, int destination) {
        super(position, color);
        this.destination=destination;
    }

    public int getDestination() {
		return this.destination;
	}

    public void setDestination(int destination) {
		this.destination = destination;
	}
        
    @Override
    public Tree<Check> getRifleMove(Tree<Check> possibilities,Gameboard gameboard){
       // System.out.println("\nROOT of check line "+(possibilities.getData().getLineNumber()+1)+" and colomn "+(possibilities.getData().getColomnNumber()+1));
            Gameboard copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
            Gameboard originalGameboard = possibilities.getData().getGameboard();
            Check loopCheck;
            Tree<Check> loopTree = possibilities;
            while(loopTree.getParent()!=null){
                loopTree = loopTree.getParent();
                loopCheck = (Check)loopTree.getData();
                originalGameboard = loopCheck.getGameboard();
                
            }
            
            Check previousCheckOnOldGameboard = (Check)possibilities.getData();
            Check previousCheckOnNewGameboard = copyGameboard.getCheckByLineColomn(previousCheckOnOldGameboard.getLineNumber(), previousCheckOnOldGameboard.getColomnNumber());
            Check currentCheck = copyGameboard.getCheckByLineColomn(possibilities.getData().getLineNumber(), possibilities.getData().getColomnNumber());
            Man currentMan;
            if(possibilities.getParent()!=null){
                previousCheckOnOldGameboard = (Check)possibilities.getParent().getData();
                previousCheckOnNewGameboard = copyGameboard.getCheckByLineColomn(previousCheckOnOldGameboard.getLineNumber(), previousCheckOnOldGameboard.getColomnNumber());
                /*On the new copied gameboard, we copy the Man to its new position*/
                previousCheckOnNewGameboard.getcheckPiece().setPosition(currentCheck);
                currentCheck.setcheckPiece(previousCheckOnNewGameboard.getcheckPiece());
                previousCheckOnNewGameboard.setcheckPiece(null);
                currentMan = (Man)currentCheck.getcheckPiece();
            }else {//1st case, Man does not need to be moved
                currentMan = (Man)currentCheck.getcheckPiece();
            }
            HashMap<Check,Gameboard> nextPossibleMoves = new HashMap<Check,Gameboard>();
            //ArrayList<Check> nextPossibleMoves = new ArrayList<Check>();
     
            nextPossibleMoves.putAll(currentMan.getFrontCaptureMove(copyGameboard));
            nextPossibleMoves.putAll(currentMan.getBackCaptureMove(copyGameboard));
            Tree<Check> newChildTree;
            
            for(Check currentPossibleNextCheck : nextPossibleMoves.keySet()){
                //System.out.println("CHILD of check line "+(currentCheck.getLineNumber()+1)+" and colomn "+(currentCheck.getColomnNumber()+1));

                //if((possiblePreviousPosition.isEmpty())||(!possiblePreviousPosition.contains(currentCheck))){
                    newChildTree = new Tree<Check>(originalGameboard.getCheckByLineColomn(currentPossibleNextCheck.getLineNumber(), currentPossibleNextCheck.getColomnNumber()));
                    possibilities.addChild(newChildTree);
                //}           
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
        
    public ArrayList<Check> getFrontMove(){
           int positionLine = this.getPosition().getLineNumber();
           int positionColomn = this.getPosition().getColomnNumber();
           Gameboard gameboard = this.getPosition().getGameboard();
           int nbGameboardLines = (gameboard.getNbLines()-1);//Minus 1 to match array storage which starts at 0
           int nbGameboardColomns = (gameboard.getNbColomns()-1);//Minus 1 to match array storage which starts at 0
           int dest = this.getDestination();
           Check currentOption;
           Check backwardCaptureOption;
           ArrayList<Check> possibleMoves = new ArrayList<Check>();

           switch (dest){
               case 0://Case 0 : For pieces from gameboard's top to bottom
                      if(positionLine<nbGameboardLines){
                          if(positionColomn>0){
                                  currentOption = gameboard.getCheckByLineColomn((positionLine+1),(positionColomn-1));
                                  if(!currentOption.isOccupied()){
                                      possibleMoves.add(currentOption);
                                  }
                          }             
                          if(positionColomn<nbGameboardColomns){
                                  currentOption = gameboard.getCheckByLineColomn((positionLine+1),(positionColomn+1));
                                  if(!currentOption.isOccupied()){
                                      possibleMoves.add(currentOption);
                                  } 
                          }
                      }
                    break;

               case 1://Case 1 : From gameboard's bottom to top
                      if(positionLine>0){
                          if(positionColomn>0){
                                  currentOption = gameboard.getCheckByLineColomn((positionLine-1),(positionColomn-1));
                                  if(!currentOption.isOccupied()){
                                      possibleMoves.add(currentOption);
                                  }
                          }

                          if(positionColomn<nbGameboardColomns){
                                  currentOption = gameboard.getCheckByLineColomn((positionLine-1),(positionColomn+1));
                                  if(!currentOption.isOccupied()){
                                      possibleMoves.add(currentOption);
                                  }
                          }
                      }
                      break;       

           }
           return possibleMoves;            
        }
        
    public HashMap<Check,Gameboard> getFrontCaptureMove(Gameboard gameboard){            
            int positionLine = this.getPosition().getLineNumber();
            int positionColomn = this.getPosition().getColomnNumber();
            int nbGameboardLines = (gameboard.getNbLines()-1);//Minus 1 to match array storage which starts at 0
            int nbGameboardColomns = (gameboard.getNbColomns()-1);//Minus 1 to match array storage which starts at 0
            int dest = this.getDestination();
            Check middleOption;
            Check captureOption;
            HashMap<Check,Gameboard> possibleMoves = new HashMap<>();
            Gameboard copyGameboard;
            
            switch(dest){
            case 0:
                if(positionLine<(nbGameboardLines-1)){
                    if(positionColomn>1){
                        middleOption = gameboard.getCheckByLineColomn((positionLine+1),(positionColomn-1));
                        captureOption = gameboard.getCheckByLineColomn((positionLine+2),(positionColomn-2));
                        if((middleOption.isOccupied())&&(!middleOption.getcheckPiece().getColor().equals(this.getColor()))&&(!captureOption.isOccupied())){                     
                            copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
                            copyGameboard.getCheckByLineColomn(middleOption.getLineNumber(), middleOption.getColomnNumber()).getcheckPiece().disapear();
                            possibleMoves.put(captureOption, copyGameboard);
                        }
                    }             
                    if(positionColomn<(nbGameboardColomns-1)){
                        middleOption = gameboard.getCheckByLineColomn((positionLine+1),(positionColomn+1));
                        captureOption = gameboard.getCheckByLineColomn((positionLine+2),(positionColomn+2));
                        if((middleOption.isOccupied())&&(!middleOption.getcheckPiece().getColor().equals(this.getColor()))&&(!captureOption.isOccupied())){
                            copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
                            copyGameboard.getCheckByLineColomn(middleOption.getLineNumber(), middleOption.getColomnNumber()).getcheckPiece().disapear();
                            possibleMoves.put(captureOption, copyGameboard);
                        }
                    }
                }
            break;            
            case 1:
                if(positionLine>1){
                    if(positionColomn>1){
                            middleOption = gameboard.getCheckByLineColomn((positionLine-1),(positionColomn-1));
                            captureOption = gameboard.getCheckByLineColomn((positionLine-2),(positionColomn-2));
                            if((middleOption.isOccupied())&&(!middleOption.getcheckPiece().getColor().equals(this.getColor()))&&(!captureOption.isOccupied())){
                                copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
                                copyGameboard.getCheckByLineColomn(middleOption.getLineNumber(), middleOption.getColomnNumber()).getcheckPiece().disapear();
                                possibleMoves.put(captureOption, copyGameboard);                                
                            }
                    }             
                    if(positionColomn<(nbGameboardColomns-1)){
                            middleOption = gameboard.getCheckByLineColomn((positionLine-1),(positionColomn+1));
                            captureOption = gameboard.getCheckByLineColomn((positionLine-2),(positionColomn+2));
                            if((middleOption.isOccupied())&&(!middleOption.getcheckPiece().getColor().equals(this.getColor()))&&(!captureOption.isOccupied())){
                                copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
                                copyGameboard.getCheckByLineColomn(middleOption.getLineNumber(), middleOption.getColomnNumber()).getcheckPiece().disapear();
                                possibleMoves.put(captureOption, copyGameboard);
                            }
                    }
                }
            break;
            }
        return possibleMoves;
        }
        
    public HashMap<Check,Gameboard> getBackCaptureMove(Gameboard gameboard){
            int positionLine = this.getPosition().getLineNumber();
            int positionColomn = this.getPosition().getColomnNumber();
            int nbGameboardLines = (gameboard.getNbLines()-1);//Minus 1 to match array storage which starts at 0
            int nbGameboardColomns = (gameboard.getNbColomns()-1);//Minus 1 to match array storage which starts at 0
            int dest = this.getDestination();
            Check middleOption;
            Check backwardCaptureOption;
            HashMap<Check,Gameboard> possibleMoves = new HashMap<>();
            Gameboard copyGameboard;
            //ArrayList<Check> possibleMoves = new ArrayList<Check>();
            
            switch(dest){
            case 0:
                if(positionLine>1){
                    if(positionColomn>1){
                        middleOption = gameboard.getCheckByLineColomn((positionLine-1),(positionColomn-1));
                        backwardCaptureOption = gameboard.getCheckByLineColomn((positionLine-2),(positionColomn-2));
                        if((middleOption.isOccupied())&&(!middleOption.getcheckPiece().getColor().equals(this.getColor()))&&(!backwardCaptureOption.isOccupied())){
                            copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
                            copyGameboard.getCheckByLineColomn(middleOption.getLineNumber(), middleOption.getColomnNumber()).getcheckPiece().disapear();
                            possibleMoves.put(backwardCaptureOption, copyGameboard);
                        }
                    }             
                    if(positionColomn<(nbGameboardColomns-1)){
                        middleOption = gameboard.getCheckByLineColomn((positionLine-1),(positionColomn+1));
                        backwardCaptureOption = gameboard.getCheckByLineColomn((positionLine-2),(positionColomn+2));
                        if((middleOption.isOccupied())&&(!middleOption.getcheckPiece().getColor().equals(this.getColor()))&&(!backwardCaptureOption.isOccupied())){
                            copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
                            copyGameboard.getCheckByLineColomn(middleOption.getLineNumber(), middleOption.getColomnNumber()).getcheckPiece().disapear();
                            possibleMoves.put(backwardCaptureOption, copyGameboard);
                        }
                    }
                }
            break;            
            case 1:
                if(positionLine<(nbGameboardLines-1)){
                    if(positionColomn>1){
                            middleOption = gameboard.getCheckByLineColomn((positionLine+1),(positionColomn-1));
                            backwardCaptureOption = gameboard.getCheckByLineColomn((positionLine+2),(positionColomn-2));                            
                            if((middleOption.isOccupied())&&(!middleOption.getcheckPiece().getColor().equals(this.getColor()))&&(!backwardCaptureOption.isOccupied())){
                                copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
                                copyGameboard.getCheckByLineColomn(middleOption.getLineNumber(), middleOption.getColomnNumber()).getcheckPiece().disapear();
                                possibleMoves.put(backwardCaptureOption, copyGameboard);
                            }
                    }             
                    if(positionColomn<(nbGameboardColomns-1)){
                            middleOption = gameboard.getCheckByLineColomn((positionLine+1),(positionColomn+1));
                            backwardCaptureOption = gameboard.getCheckByLineColomn((positionLine+2),(positionColomn+2));
                            if((middleOption.isOccupied())&&(!middleOption.getcheckPiece().getColor().equals(this.getColor()))&&(!backwardCaptureOption.isOccupied())){
                                copyGameboard = (Gameboard)DeepCopy.copy(gameboard);
                                copyGameboard.getCheckByLineColomn(middleOption.getLineNumber(), middleOption.getColomnNumber()).getcheckPiece().disapear();
                                possibleMoves.put(backwardCaptureOption, copyGameboard);
                            }
                    }
                }
            break;
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
         *      - 0: Simple move
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
            captureMoves.addAll(this.getFrontCaptureMove(gameboardCopy).keySet());
            captureMoves.addAll(this.getBackCaptureMove(gameboardCopy).keySet());
            if(!captureMoves.isEmpty()){
                for(Check currentCheck : captureMoves){
                    newMoveToAdd = new ArrayList<Check>();
                    newMoveToAdd.add(this.getPosition().getGameboard().getCheckByLineColomn(currentCheck.getLineNumber(), currentCheck.getColomnNumber()));
                    results.put(newMoveToAdd,1);
                }
            }else{
                simpleMoves.addAll(this.getFrontMove());
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
        int departureLine = this.getPosition().getLineNumber();
        int departureColomn = this.getPosition().getColomnNumber();
        int arrivalLine = arrival.getLineNumber();
        int arrivalColomn = arrival.getColomnNumber();
        Gameboard gameboard = this.getPosition().getGameboard();
        int dest = this.getDestination();
        
        if(departureLine-arrivalLine>1){//case if White takes an adversary piece 
            int rightOrLeft  = ((arrivalColomn-departureColomn)/2);//Result will be 1 or -1 depending if it's right or left
            if((rightOrLeft==-1)||(rightOrLeft==1)){
                Check adversaryCheck = gameboard.getCheckByLineColomn((departureLine-1),(departureColomn+rightOrLeft));
                Piece adversaryPiece = adversaryCheck.getcheckPiece();
                adversaryPiece.die();
                adversaryPiece = null;
            }
        }
        
        if(departureLine-arrivalLine<-1){//case if Blacks takes an adversary piece 
            int rightOrLeft  = ((arrivalColomn-departureColomn)/2);//Result will be 1 or -1 depending if it's right or left
            if((rightOrLeft==-1)||(rightOrLeft==1)){
                Check adversaryCheck = gameboard.getCheckByLineColomn((departureLine+1),(departureColomn+rightOrLeft));
                Piece adversaryPiece = adversaryCheck.getcheckPiece();
                adversaryPiece.die();
                adversaryPiece = null;
            }            
        }
        
        if((dest==0)&&(arrivalLine==(gameboard.getNbLines()-1))){
            this.toKing(arrival);
        } else if((dest==1)&&(arrivalLine==0)){
            this.toKing(arrival);
        } else {
            this.getPosition().setcheckPiece(null);
            this.setPosition(arrival);
            arrival.setcheckPiece(this);
        }
    }
    
    @Override
    public void riffleMove(ArrayList<Check> path) {
        for(Check currentCheck : path){
            this.move(currentCheck);
        }
    }
    
    public void toKing(Check arrivalCheck){
        King upgradedMan = new King(arrivalCheck,this.getColor());
        upgradedMan.setOwner(this.getOwner());
        this.getOwner().addPiece(upgradedMan);
        this.die();
        arrivalCheck.setcheckPiece(upgradedMan);        
    }


        
        

}