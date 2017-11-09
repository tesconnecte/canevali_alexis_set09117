/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers_game;
import Model.*;
import UI.*;
import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author alexi_000
 */
public class Checkers_game {

    /**
     * @param args the command line arguments
     */
    
    private Player playerOne;
    private Player PlayerTwo;
    public boolean loadGame;

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return PlayerTwo;
    }

    public void setPlayerTwo(Player PlayerTwo) {
        this.PlayerTwo = PlayerTwo;
    }

    public boolean isLoadGame() {
        return loadGame;
    }

    public void setLoadGame(boolean loadGame) {
        this.loadGame = loadGame;
    }

    public Checkers_game() {
        this.playerOne = null;
        this.PlayerTwo = null;
        this.loadGame = false;
    }
    
    
    
    
    
    public static void main(String[] args) throws InterruptedException {
        Checkers_game mainObject = new Checkers_game();
        // TODO code application logic here
        System.out.println(" Checkers Game\n_________________________________\n");
        JFrame startGame = new FirstWindow(mainObject);
        while(((mainObject.getPlayerOne()==null)||(mainObject.getPlayerTwo()==null))&&(mainObject.isLoadGame()==false)){
            System.out.print("");
        }
        Gameboard mainGameboard = new Gameboard();
        Player playerOne = mainObject.getPlayerOne();
        Player playerTwo = mainObject.getPlayerTwo();
        Game game;
        if(!mainObject.isLoadGame()){
            game = new Game(mainGameboard,playerOne,playerTwo);
        }else{
            game = new Game();
            game.loadGame();
        }
         
        JFrame mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainWindowAllContent windowAllContent = new MainWindowAllContent();
        windowAllContent.setLayout(new BorderLayout());
        windowAllContent.add(new MainWindowNorthContent(),BorderLayout.NORTH);
        MainWindowContent mainWindowContent = new MainWindowContent(game);
        windowAllContent.add(mainWindowContent, BorderLayout.CENTER);
        windowAllContent.add(new MainWindowOtherContent(game), BorderLayout.EAST);
        mainWindow.add(windowAllContent);
        mainWindow.pack();
        mainWindow.setVisible(true);
        
        Player currentPlayer;
        Piece pieceToMove = null;
        Check currentCheck = null;
        HashMap<ArrayList,Integer> currentPossibleMoves = null;
        ArrayList<Check> currentMoves = null;
        
        while(!game.isGameIsOver()){
            mainGameboard=game.getGameboard();
            currentPlayer=game.getCurrentPlayer();
            
            

            for(Piece cuPiece: game.getPlayerOne().getPieces()){
                cuPiece.setPosition(mainGameboard.getCheckByLineColomn(cuPiece.getPosition().getLineNumber(), cuPiece.getPosition().getColomnNumber()));
            }
            
            for(Piece cuPiece: game.getPlayerTwo().getPieces()){
                cuPiece.setPosition(mainGameboard.getCheckByLineColomn(cuPiece.getPosition().getLineNumber(), cuPiece.getPosition().getColomnNumber()));
            }
            mainWindowContent.setBlackPieces(mainGameboard.getPiecesByColor("black"));
            mainWindowContent.setWhitePieces(mainGameboard.getPiecesByColor("white"));
            /*            
            */
            windowAllContent.refreshDisplay(game);
            JOptionPane.showMessageDialog(windowAllContent, "It is at "+ currentPlayer.getName()+" to play");
            if(currentPlayer instanceof Human){
                ArrayList<Piece> movablePiece = ((Human) currentPlayer).choosePieceToMove();
                mainWindowContent.showSelectablePiece(movablePiece);
                int[] coordonates;
                while(pieceToMove==null){
                    coordonates=mainWindowContent.chooseCheck();
                    currentCheck=mainGameboard.getCheckByLineColomn(coordonates[0], coordonates[1]);
                    if(currentCheck.getcheckPiece()!=null){
                        pieceToMove=currentCheck.getcheckPiece();
                        if(!movablePiece.contains(pieceToMove)){
                            pieceToMove=null;
                        }
                    }
                }
                //windowAllContent.refreshDisplay(game);
                mainWindowContent.greenOriginalColorFlash(pieceToMove.getPosition().getLineNumber(), pieceToMove.getPosition().getColomnNumber(), (pieceToMove instanceof King), pieceToMove.getColor());
                currentPossibleMoves=pieceToMove.getPossibleMoves();
                for(ArrayList<Check> currentoption : currentPossibleMoves.keySet()){
                    mainWindowContent.showSelectablePath(currentoption);                   
                }
                while(currentMoves==null){
                    coordonates=mainWindowContent.chooseCheck();
                    currentCheck=mainGameboard.getCheckByLineColomn(coordonates[0], coordonates[1]);
                    for(ArrayList<Check> currentoption : currentPossibleMoves.keySet()){
                        if(currentoption.contains(currentCheck)){
                            currentMoves=currentoption;
                        }                   
                    }                    
                }

                if(currentMoves.size()>1){
                    pieceToMove.riffleMove(currentMoves);
                } else {
                    pieceToMove.move(currentMoves.get(0));                    
                }
                pieceToMove = null;
                currentCheck = null;
                currentPossibleMoves = null;
                currentMoves = null;
            } else {
                currentPlayer.playOnce();
            }
            
            //game.addGameboardHistory();
            game.nextPlayer();
            
            if((game.getPlayerOne().getPieces().isEmpty())||(game.getPlayerTwo().getPieces().isEmpty())){
                game.setGameIsOver(true);
                String winner;
                if(game.getPlayerOne().getPieces().isEmpty()){
                    winner=game.getPlayerTwo().getName();
                }else{
                    winner=game.getPlayerOne().getName();
                }
                JOptionPane.showMessageDialog(null,winner+" has won !");               
            }
        }       
        

    }
}
