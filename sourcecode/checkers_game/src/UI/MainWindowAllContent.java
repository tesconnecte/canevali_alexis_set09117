/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Model.Game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author alexi
 */
public class MainWindowAllContent extends JPanel {
    
    public MainWindowAllContent() {
        setBorder(BorderFactory.createLineBorder(Color.black));        
    }
    
    public void refreshDisplay(Game game){
        game.getGameboard().drawGameboard();
        this.getParent().getComponent(0).repaint();
        this.getComponent(2).repaint();
    }
    
    
    
}
