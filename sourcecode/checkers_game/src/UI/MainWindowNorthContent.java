/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author alexi
 */public class MainWindowNorthContent extends JPanel {
    
    public MainWindowNorthContent() {
        setBorder(null);
        JLabel gameTitle = new JLabel("The international Draught Game");
        gameTitle.setFont(new Font(gameTitle.getName(),Font.PLAIN,20));
        this.add(gameTitle);
    }
    
}
