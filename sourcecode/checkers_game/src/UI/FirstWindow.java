/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class FirstWindow extends JFrame{
    private checkers_game.Checkers_game mainObject;
    private JFrame firstFrame;
    private JPanel firstWindowContent;
    private JPanel firstTitleWindowContent;
    private JLabel gameTitle;
    private JButton loadGame;
    private JButton newGame;

    public FirstWindow(checkers_game.Checkers_game mainObject) {
        this.mainObject=mainObject;
        firstFrame = new JFrame();
        firstWindowContent = new JPanel();
        firstTitleWindowContent = new JPanel();
        firstWindowContent.setLayout(new BoxLayout(firstWindowContent, BoxLayout.PAGE_AXIS));
        firstTitleWindowContent.setLayout(new BoxLayout(firstTitleWindowContent, BoxLayout.LINE_AXIS));
        gameTitle = new JLabel("The international Draught Game");
        gameTitle.setFont(new Font(gameTitle.getName(),Font.PLAIN,40));
        gameTitle.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        loadGame = new JButton("Load a game");
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMainGameLoadGame();
            }
        });
        loadGame.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        newGame = new JButton("New game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWindowNewGame();
            }
        });
        newGame.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        firstTitleWindowContent.add(new JLabel(new ImageIcon("data\\draughtillustration.jpg")));
        firstTitleWindowContent.add(gameTitle);
        firstWindowContent.add(firstTitleWindowContent);
        firstWindowContent.add(newGame);
        firstWindowContent.add(loadGame);
        firstFrame.add(firstWindowContent);
        firstFrame.setMinimumSize(new Dimension(800, 400));
        firstFrame.setVisible(true);
    }
    
    public void openWindowNewGame(){
        JFrame nextWindow = new NewGameWindow(mainObject);
        firstFrame.dispose();
    }
    
    public void openMainGameLoadGame(){
       mainObject.setLoadGame(true);
    }
    
    
    
    
    
}
