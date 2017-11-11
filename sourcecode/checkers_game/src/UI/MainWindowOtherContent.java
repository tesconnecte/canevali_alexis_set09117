/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Model.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author alexi
 */
public class MainWindowOtherContent extends JPanel {
    private JLabel infoPionOne;
    private JLabel infoPionTwo;
    private Game game;
    
    public MainWindowOtherContent(Game currentGame) {
        JPanel buttonsPanel = new JPanel();
        JPanel playerOnePanel = new JPanel();
        JPanel playerTwoPanel = new JPanel();
        
        game=currentGame;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        
        JLabel playerTwo = new JLabel(game.getPlayerTwo().getName());
        JLabel infoColorTwo = new JLabel("plays with white pieces");
        infoPionTwo = new JLabel("and have "+game.getPlayerTwo().getPieces().size()+" pieces left");
        
        JLabel playerOne = new JLabel(game.getPlayerOne().getName());
        JLabel infoColorOne = new JLabel("plays with black pieces");
        infoPionOne = new JLabel("and have "+game.getPlayerOne().getPieces().size()+" pieces left");
        
        JButton save = new JButton("Save the game");
        
        
        
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {               
            
                try {
                    game.saveGame();
                } catch (FileNotFoundException ex) {
                    System.err.println(ex);
                } catch (UnsupportedEncodingException ex) {
                    System.err.println(ex);
                }
            }
        });
        JButton undo = new JButton("Undo last move");
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game lastGame = game.getPreviousGame();                
                if(lastGame!=null){                    
                    game.removeLastUndoElement();
                    lastGame=game.getPreviousGame();
                    game.setCurrentPlayer(lastGame.getCurrentListPlayer());
                    game.setGameboard(lastGame.getGameboard());
                    game.setPlayerOne(lastGame.getPlayerOne());
                    game.setPlayerTwo(lastGame.getPlayerTwo());
                    game.removeLastUndoElement();                   
                }else{
                    System.err.println("UNDO returned null");
                }
            }
        });
        JButton redo = new JButton("Redo last move");
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game nextGame = game.getNextGame();
                if(nextGame!=null){
                    game.removeLastRedoElement();
                    nextGame = game.getNextGame();
                    game.setCurrentPlayer(nextGame.getCurrentListPlayer());
                    game.setGameboard(nextGame.getGameboard());
                    game.setPlayerOne(nextGame.getPlayerOne());
                    game.setPlayerTwo(nextGame.getPlayerTwo());
                    game.removeLastRedoElement();
                    
                }else{
                    System.err.println("REDO returned null");
                }            
            }
        });
        
        buttonsPanel.add(save);
        buttonsPanel.add(undo);
        buttonsPanel.add(redo);
        
        playerOnePanel.add(playerOne);
        playerOnePanel.add(infoColorOne);
        playerOnePanel.add(infoPionOne);
        
        playerTwoPanel.add(playerTwo);
        playerTwoPanel.add(infoColorTwo);
        playerTwoPanel.add(infoPionTwo);
        
        this.add(buttonsPanel);
        this.add(playerTwoPanel);
        this.add(playerOnePanel);
    }

    public Game getGame() {
        return game;
    }
    
    
    
    
    @Override
    public void paintComponent(Graphics g) {
        this.infoPionOne.setText("and have "+this.getGame().getPlayerOne().getPieces().size()+" pieces left");
        this.infoPionTwo.setText("and have "+this.getGame().getPlayerTwo().getPieces().size()+" pieces left");
    }
    
}
