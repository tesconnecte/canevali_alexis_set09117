/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Model.AI;
import Model.Human;
import Model.Player;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author alexi_000
 */
public class NewGameWithPlayers extends JFrame{
    private int mode;
    private JFrame newGameFrame;
    private JPanel newGameWindowContent;
    private JPanel newGameTitleWindowContent;
    private JPanel playerOnePanel;
    private JPanel playerTwoPanel;
    private JLabel gameTitle;
    private JLabel playerOneInstructions;
    private JTextField playerOneName;
    private JLabel playerOneInformations;
    private JLabel playerTwoInstructions;
    private JTextField playerTwoName;
    private JLabel playerTwoInformations;
    private JButton validate;
    private checkers_game.Checkers_game mainObject;
    
    public NewGameWithPlayers(int gameMode, checkers_game.Checkers_game mainObject) {
        this.mode = gameMode;
        this.newGameFrame = new JFrame();
        this.newGameWindowContent = new JPanel();
        this.newGameTitleWindowContent = new JPanel();
        this.newGameWindowContent.setLayout(new BoxLayout(newGameWindowContent, BoxLayout.PAGE_AXIS));
        this.newGameTitleWindowContent.setLayout(new BoxLayout(newGameTitleWindowContent, BoxLayout.LINE_AXIS));
        this.gameTitle = new JLabel("The international Draught Game");
        this.gameTitle.setFont(new Font(gameTitle.getName(),Font.PLAIN,40));
        this.gameTitle.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));        
        this.newGameWindowContent.add(newGameTitleWindowContent);
        this.mainObject=mainObject;
        
        this.playerOnePanel = new JPanel();
        this.playerOneInstructions = new JLabel("Enter 1st player's name : ");
        this.playerOneName = new JTextField(15);
        this.playerOneInformations = new JLabel("This player plays with the black pieces.");
        this.playerOnePanel.add(playerOneInstructions);
        this.playerOnePanel.add(playerOneName);
        this.playerOnePanel.add(playerOneInformations);
        this.newGameWindowContent.add(playerOnePanel);
        
        if(gameMode==2){
            this.playerTwoPanel = new JPanel();
            this.playerTwoInstructions = new JLabel("Enter 2nd player's name : ");
            this.playerTwoName = new JTextField(15);
            this.playerTwoInformations = new JLabel("This player plays with the white pieces.");
            this.playerTwoPanel.add(playerTwoInstructions);
            this.playerTwoPanel.add(playerTwoName);
            this.playerTwoPanel.add(playerTwoInformations);
            this.newGameWindowContent.add(playerTwoPanel);
        }        
        
        this.validate = new JButton("Ok, Let's start playing !");        
        this.validate.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.validate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int gameMode = getGameMode();
                switch(gameMode){
                    case 1:
                        if((!playerOneName.getText().equals(""))&&(!playerOneName.getText().toLowerCase().trim().equals("computer"))){
                            Player playerOne = new Human(playerOneName.getText());
                            Player playerTwo = new AI();
                            mainObject.setPlayerOne(playerOne);
                            mainObject.setPlayerTwo(playerTwo);                           
                            
                        } else {
                            JOptionPane.showMessageDialog(newGameFrame, "Player 1 name's is not filled or is incorrect. Please fill this field.");
                        }
                    break;
                    
                    case 2:
                        if(!playerOneName.getText().equals("")){
                            if(!playerTwoName.getText().equals("")){
                                if(!playerTwoName.getText().toLowerCase().trim().equals(playerOneName.getText().toLowerCase().trim())){
                                    Player playerOne = new Human(playerOneName.getText());
                                    Player playerTwo = new Human(playerTwoName.getText());
                                    mainObject.setPlayerOne(playerOne);
                                    mainObject.setPlayerTwo(playerTwo);                                    
                                } else {
                                    JOptionPane.showMessageDialog(newGameFrame, "Player 1&2 have the same name ! Please enter different names.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(newGameFrame, "Player 2 name's is not filled. Please fill this field.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(newGameFrame, "Player 1 name's is not filled. Please fill this field.");
                        }
                    break;
                        
                }
                
            }
        });
        this.newGameWindowContent.add(validate);
        this.newGameTitleWindowContent.add(new JLabel(new ImageIcon("data\\draughtillustration.jpg")));
        this.newGameTitleWindowContent.add(gameTitle);
        this.newGameFrame.add(newGameWindowContent);
        this.newGameFrame.setMinimumSize(new Dimension(800, 500));
        this.newGameFrame.setVisible(true);
    }
                
    public int getGameMode() {
        return mode;
    }          
                
                
}

    
