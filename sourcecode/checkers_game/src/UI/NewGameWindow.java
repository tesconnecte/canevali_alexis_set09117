/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Model.AI;
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
import javax.swing.JPanel;

/**
 *
 * @author alexi_000
 */
public class NewGameWindow extends JFrame{
    private checkers_game.Checkers_game mainObject;
    private JFrame newGameFrame;
    private JPanel newGameWindowContent;
    private JPanel newGameTitleWindowContent;
    private JLabel gameTitle;
    private JLabel chooseGameMode;
    private JButton gameMode1;
    private JButton gameMode2;
    private JButton gameMode3;

    public NewGameWindow(checkers_game.Checkers_game mainObject) {
        this.mainObject=mainObject;
        newGameFrame = new JFrame();
        newGameWindowContent = new JPanel();
        newGameTitleWindowContent = new JPanel();
        newGameWindowContent.setLayout(new BoxLayout(newGameWindowContent, BoxLayout.PAGE_AXIS));
        newGameTitleWindowContent.setLayout(new BoxLayout(newGameTitleWindowContent, BoxLayout.LINE_AXIS));
        gameTitle = new JLabel("The international Draught Game");
        gameTitle.setFont(new Font(gameTitle.getName(),Font.PLAIN,40));
        gameTitle.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        gameMode1 = new JButton("Player vs Player");        
        gameMode1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        gameMode1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame nextWindow = new NewGameWithPlayers(2,mainObject);
                newGameFrame.dispose();
            }
        });        
        gameMode2 = new JButton("Player vs Computer");
        gameMode2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        gameMode2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame nextWindow = new NewGameWithPlayers(1,mainObject);
                newGameFrame.dispose();
            }
        });
        gameMode3 = new JButton("Computer vs Computer");        
        gameMode3.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        gameMode3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player playerOne = new AI();
                Player playerTwo = new AI("Computer2");
                mainObject.setPlayerOne(playerOne);
                mainObject.setPlayerTwo(playerTwo);               
            }
        });
        newGameTitleWindowContent.add(new JLabel(new ImageIcon("data\\draughtillustration.jpg")));
        newGameTitleWindowContent.add(gameTitle);
        newGameWindowContent.add(newGameTitleWindowContent);
        newGameWindowContent.add(gameMode1);
        newGameWindowContent.add(gameMode2);
        newGameWindowContent.add(gameMode3);
        newGameFrame.add(newGameWindowContent);
        newGameFrame.setMinimumSize(new Dimension(800, 400));
        newGameFrame.setVisible(true);
    }
    
    
}
