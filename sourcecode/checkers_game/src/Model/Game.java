/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexi_000
 */
public class Game implements Serializable{
    private Gameboard gameboard;
    private Player playerOne;
    private Player playerTwo;
    private LinkedList<Player> currentPlayer;
    private LinkedList<Game> gameUndoHistory;
    private LinkedList<Game> gameRedoHistory;
    boolean gameIsOver;

    public Game(Gameboard gameboard, Player playerOne, Player playerTwo) {
        this.gameboard = gameboard;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;        
        currentPlayer = new LinkedList<Player>();
        gameUndoHistory = new LinkedList<Game>();
        gameRedoHistory = new LinkedList<Game>();
        this.gameIsOver = false;
        ArrayList<Piece> whitePieces = gameboard.getPiecesByColor("white");
        ArrayList<Piece> blackPieces = gameboard.getPiecesByColor("black");
        
        for(Piece currentPiece: blackPieces){
            currentPiece.setOwner(playerOne);
            playerOne.addPiece(currentPiece);
        }
        
        for(Piece currentPiece: whitePieces){
            currentPiece.setOwner(playerTwo);
            playerTwo.addPiece(currentPiece);
        }
        
        currentPlayer.add((Player)DeepCopy.copy(playerOne));
    }
    
    public Game(){
        this.gameboard = gameboard;
        this.playerOne = null;
        this.playerTwo = null;        
        currentPlayer = new LinkedList<Player>();
        gameUndoHistory = new LinkedList<Game>();
        gameRedoHistory = new LinkedList<Game>();
        this.gameIsOver = false;
    }

    public Gameboard getGameboard() {
        return gameboard;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setGameboard(Gameboard gameboard) {
        this.gameboard = gameboard;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }   
    

    public Player getCurrentPlayer() {
        if(this.getPlayerOne().getName().equals(currentPlayer.getLast().getName())){
            return this.getPlayerOne();
        } else {
           return this.getPlayerTwo();
        }
        
    }
    
    public void saveGame() throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter gameSaveFile;
        try {            
            String gameInfos = "";
            gameInfos+="PLAYERONE=";
            if(this.getPlayerOne() instanceof Human){
                gameInfos+="PLAYER>HUMAN§";
            }else{
                gameInfos+="PLAYER>AI§";
            }
            gameInfos+="NAME:"+this.getPlayerOne().getName();
            
            gameInfos+="*PLAYERTWO=";
            if(this.getPlayerTwo() instanceof Human){
                gameInfos+="PLAYER>HUMAN§";
            }else{
                gameInfos+="PLAYER>AI§";
            }
            gameInfos+="NAME:"+this.getPlayerTwo().getName();
            if(this.getCurrentPlayer().getName().equals(this.getPlayerOne().getName())){
                gameInfos+="*CURRENTPLAYER=PLAYER:PLAYERONE";
            }else{
                gameInfos+="*CURRENTPLAYER=PLAYER:PLAYERTWO";
            }
            Piece currentPiece;
            for(int i=0;i<this.getGameboard().getNbLines();i++){
                for(int j=0;j<this.getGameboard().getNbColomns();j++){
                    gameInfos+="*CHECK=LINENUMBER:"+i+"|COLOMNNUMBER:"+j+"|PIECE";
                    if(this.getGameboard().getCheckByLineColomn(i, j).getcheckPiece()!=null){
                        currentPiece=this.getGameboard().getCheckByLineColomn(i, j).getcheckPiece();
                        if(currentPiece instanceof Man){
                            if(currentPiece.getOwner().getName().equals(this.getPlayerOne().getName())){
                                gameInfos+=">MAN§OWNER:PLAYERONE|COLOR:"+currentPiece.getColor()+"|DESTINATION:"+((Man) currentPiece).getDestination();
                            }else{
                                gameInfos+=">MAN§OWNER:PLAYERTWO|COLOR:"+currentPiece.getColor()+"|DESTINATION:"+((Man) currentPiece).getDestination();
                            }
                        }else{
                            if(currentPiece.getOwner().getName().equals(this.getPlayerOne().getName())){
                                gameInfos+=">KING§OWNER:PLAYERONE|COLOR:"+currentPiece.getColor();
                            }else{
                                gameInfos+=">KING§OWNER:PLAYERTWO|COLOR:"+currentPiece.getColor();
                            }
                        }
                    }else{
                         gameInfos+=":null";
                    }
                }
            }
            gameSaveFile = new PrintWriter("data\\gamesave.txt", "UTF-8");
            gameSaveFile.print(gameInfos);
            gameSaveFile.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void loadGame(){
        String FILENAME = "data\\gamesave.txt";
        String textFile = "";
        
        BufferedReader br = null;
	FileReader fr = null;
        
        try {
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                    textFile+=sCurrentLine;
            }
            
            String currentWord="";
            String[] objectsStrings = new String[102];
            int objectsStringsIndex = 0;
            
            for (char ch : textFile.toCharArray()){
                if(ch=='*'){
                    objectsStrings[objectsStringsIndex]=currentWord;
                    objectsStringsIndex++;
                    currentWord="";
                } else {
                    currentWord+=ch;
                }
            }
            
            currentWord="";
            Player filePlayerOne = null;
            Player filePlayerTwo = null;
            LinkedList<Player> fileCurrentPlayer = new LinkedList<Player>();
            Gameboard filegameboard = new Gameboard();
            Check currentCheck;
            filegameboard.emptyGameboard();
            String objectType = "";
            String referencedObjectType = "";
            String[] attributesNames = new String[5];
            String[] attributesValues = new String[5];
            int indexAttVal=0;
            String[] referencedattributesNames = new String[5];
            String[] referencedValues = new String[5];
            int referenceindexAttVal=0;
            Man fileMan = null;
            King fileKing = null;

            for(int i=0;i<102;i++){
                for (char cha : objectsStrings[i].toCharArray()){                    
                    if(cha=='='){
                        objectType=currentWord;                        
                        currentWord="";
                    }else if(cha=='|'){
                        if(referencedObjectType.equals("")){
                            attributesValues[indexAttVal]=currentWord;
                            indexAttVal++;
                        }else{
                            referencedValues[referenceindexAttVal]=currentWord;
                            referenceindexAttVal++;
                        }                        
                        currentWord="";
                    } else if(cha=='>'){
                        currentWord="";
                    } else if(cha=='§'){
                        referencedObjectType=currentWord;
                        currentWord="";
                    } else if(cha==':'){
                        if(referencedObjectType.equals("")){
                            attributesNames[indexAttVal]=currentWord;
                        }else{
                            referencedattributesNames[referenceindexAttVal]=currentWord;
                        }
                        currentWord="";
                    } else{
                        currentWord+=cha;                        
                    }
                }
                
                if(referencedObjectType.equals("")){
                    attributesValues[indexAttVal]=currentWord;
                    indexAttVal++;
                }else{
                    referencedValues[referenceindexAttVal]=currentWord;
                    referenceindexAttVal++;
                }
                        
                if(objectType.equals("PLAYERONE")){
                    if(referencedObjectType.equals("HUMAN")){
                        if(referencedattributesNames[0].equals("NAME")){
                            filePlayerOne= new Human(referencedValues[0]);
                        }else{
                            System.err.println("Error in game save reading : Expected attribute NAME, get "+referencedattributesNames[0]+" for PLAYERONE");
                        }
                    }else if(referencedObjectType.equals("AI")){
                        filePlayerOne= new AI(referencedValues[0]);
                    }else{
                        System.err.println("Error in game save reading : "+referencedObjectType+" is not a known player class");
                    }
                }else if(objectType.equals("PLAYERTWO")){
                    if(referencedObjectType.equals("HUMAN")){
                        if(referencedattributesNames[0].equals("NAME")){
                            filePlayerTwo= new Human(referencedValues[0]);
                        }else{
                            System.err.println("Error in game save reading : Expected attribute NAME, get "+referencedattributesNames[0]+" for PLAYERONE");
                        }
                    }else if(referencedObjectType.equals("AI")){
                        filePlayerTwo= new AI(referencedValues[0]);
                    }else{
                        System.err.println("Error in game save reading : "+referencedObjectType+" is not a known player class");
                    }            
                }else if(objectType.equals("CURRENTPLAYER")){
                    if(attributesNames[0].equals("PLAYER")){
                        if(attributesValues[0].equals("PLAYERONE")){
                            fileCurrentPlayer.add((Player)DeepCopy.copy(filePlayerOne));
                        }else if(attributesValues[0].equals("PLAYERTWO")){
                            fileCurrentPlayer.add((Player)DeepCopy.copy(filePlayerTwo));
                        }else{
                            System.err.println("Error in game save reading : Expected value PLAYERONE or PLAYERTWO, get "+attributesValues[0]+" for PLAYERONE");
                        }
                    }else{
                        System.err.println("Error in game save reading : Expected attribute PLAYER, get "+attributesNames[0]+" for PLAYERONE");
                    }
                }else if(objectType.equals("CHECK")){
                    if((attributesNames[0].equals("LINENUMBER"))&&(attributesNames[1].equals("COLOMNNUMBER"))){
                        currentCheck=filegameboard.getCheckByLineColomn(Integer.parseInt(attributesValues[0]), Integer.parseInt(attributesValues[1]));
                        if((attributesNames[2]!=null)&&(attributesNames[2].equals("PIECE"))){
                            if(!attributesValues[2].equals("null")){
                                System.err.println("Error in game save reading : Expected value null, get "+attributesNames[0]+" for CHECK");
                            }
                        }else if(referencedObjectType.equals("MAN")){
                            if((referencedattributesNames[0].equals("OWNER"))&&(referencedattributesNames[1].equals("COLOR"))&&(referencedattributesNames[2].equals("DESTINATION"))){
                                fileMan= new Man(currentCheck,referencedValues[1],Integer.parseInt(referencedValues[2]));
                                if(referencedValues[0].equals("PLAYERONE")){
                                    currentCheck.setcheckPiece(fileMan);
                                    fileMan.setOwner(filePlayerOne);
                                    filePlayerOne.addPiece(fileMan);
                                }else if(referencedValues[0].equals("PLAYERTWO")){
                                    currentCheck.setcheckPiece(fileMan);
                                    fileMan.setOwner(filePlayerTwo);
                                    filePlayerTwo.addPiece(fileMan);
                                }else{
                                    System.err.println("Error in game save reading : Expected value PLAYERONE or PLAYERTWO, get "+referencedValues[0]);
                                }
                            }else{
                                System.err.println("Error in game save reading : Expected attributes OWNER COLOR DESTINATION names, get "+referencedattributesNames[0]+", "+referencedattributesNames[1]+", "+referencedattributesNames[2]);
                            }
                        }else if(referencedObjectType.equals("KING")){
                            if((referencedattributesNames[0].equals("OWNER"))&&(referencedattributesNames[1].equals("COLOR"))){
                                fileKing= new King(currentCheck,referencedValues[1]);                                
                                if(referencedValues[0].equals("PLAYERONE")){
                                    currentCheck.setcheckPiece(fileKing);
                                    fileKing.setOwner(filePlayerOne);
                                    filePlayerOne.addPiece(fileKing);
                                }else if(referencedValues[0].equals("PLAYERTWO")){
                                    currentCheck.setcheckPiece(fileKing);
                                    fileKing.setOwner(filePlayerTwo);
                                    filePlayerTwo.addPiece(fileKing);
                                }else{
                                    System.err.println("Error in game save reading : Expected value PLAYERONE or PLAYERTWO, get "+referencedValues[0]+" for setting a check's piece owner");
                                }
                            }else{
                                System.err.println("Error in game save reading : Expected attributes OWNER COLOR names, get "+referencedattributesNames[0]+", "+referencedattributesNames[1]);
                            }
                        }else{
                            System.err.println("Error in game save reading : Expected object MAN or KING, get "+referencedObjectType);
                        }
                    }else{
                        System.err.println("Error in game save reading : Expected attribute PLAYER, get "+attributesNames[0]+" for PLAYERONE");
                    }                   
                }else{
                    System.err.println("Error in game save reading : "+objectType+" is not a known property");
                }
                currentWord="";
                objectType = "";
                referencedObjectType = "";
                attributesNames = new String[5];
                attributesValues = new String[5];
                indexAttVal=0;
                referencedattributesNames = new String[5];
                referencedValues = new String[5];
                referenceindexAttVal=0;
            }
            
            this.setCurrentPlayer(fileCurrentPlayer);
            this.setGameboard(filegameboard);
            this.setPlayerOne(filePlayerOne);
            this.setPlayerTwo(filePlayerTwo);         
            
            

            

	} catch (IOException e) {
            e.printStackTrace();
	} finally {
            try {
                if (br != null)
                    br.close();
                    if (fr != null)
                        fr.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }        
    }
    
    public void nextPlayer(){
        if(this.getCurrentPlayer().getName().equals(playerOne.getName())){
            currentPlayer.add(playerTwo);
        } else {
            currentPlayer.add(playerOne);
        }
    }
    
    public void addGameboardHistory(){
        gameUndoHistory.add((Game)DeepCopy.copy(this));
    }
    
    public void removeLastUndoElement(){
        this.gameUndoHistory.removeLast();
    }
    
    public void removeLastRedoElement(){
        this.gameRedoHistory.removeLast();
    }
    
    public Game getPreviousGame(){
        if(this.gameUndoHistory.peekLast()!=null){
            this.gameRedoHistory.add(this.gameUndoHistory.peekLast());
            return this.gameUndoHistory.peekLast();
        }else{
            return null;
        }
        
    }
    
    public Game getNextGame(){
        if(this.gameUndoHistory.peekLast()!=null){
            this.gameUndoHistory.add(this.gameRedoHistory.peekLast());
            return this.gameRedoHistory.peekLast();
        }else{
            return null;
        }
    }
    
    public LinkedList<Player> getCurrentListPlayer(){
        return this.currentPlayer;
    }

    public void setCurrentPlayer(LinkedList<Player> currentPlayer) {
        this.currentPlayer = currentPlayer;
    } 
   
    public boolean isGameIsOver() {
        return gameIsOver;
    }

    public void setGameIsOver(boolean gameIsOver) {
        this.gameIsOver = gameIsOver;
    }
    
}
