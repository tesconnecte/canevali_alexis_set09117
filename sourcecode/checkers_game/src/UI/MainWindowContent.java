/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Model.Check;
import Model.Game;
import Model.King;
import Model.Piece;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.w3c.dom.events.MouseEvent;
import sun.print.resources.serviceui;

/**
 *
 * @author alexi
 */
public class MainWindowContent extends JPanel implements MouseListener{
    private ArrayList<Piece> blackPieces;
    private ArrayList<Piece> whitePieces;
    private Rectangle2D[][] graphicalChecks;
    private int playerInputCheckLine;
    private int playerInputCheckColomn;
    int size = 900;
    int offset = 15;
    int subLenght = size / 10;

    public MainWindowContent(Game game) {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.graphicalChecks = new Rectangle2D[10][10];
        this.blackPieces = game.getPlayerOne().getPieces();
        this.whitePieces = game.getPlayerTwo().getPieces();
        this.playerInputCheckLine=-1;
        this.playerInputCheckColomn=-1;        
        this.addMouseListener(this);
       
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size + offset * 2, size + offset * 2);
    }

    public void setBlackPieces(ArrayList<Piece> blackPieces) {
        this.blackPieces = blackPieces;
    }

    public void setWhitePieces(ArrayList<Piece> whitePieces) {
        this.whitePieces = whitePieces;
    }
    
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int totalLenght = size;
        
        // Paint the board

        //g.setColor(Color.BLUE);
        //g.fillRect(offset, offset, (totalLenght+50), (totalLenght+50));
       // g.setColor(Color.DARK_GRAY);
        Rectangle2D check;
        Graphics2D g2d = (Graphics2D) g;
        //g.drawRect(offset, offset, totalLenght, totalLenght);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                int ope = x + (y & 1);
                if ((ope & 1) != 0) {
                    g.setColor(Color.DARK_GRAY);
                    check = new Rectangle2D.Double(x * subLenght + offset, y * subLenght + offset, subLenght, subLenght);
                    //g.fillRect(x * subLenght + offset, y * subLenght + offset, subLenght, subLenght);
                    
                    graphicalChecks[y][x]=(check);
                    g2d.fill(check);
                }else{
                    g.setColor(Color.WHITE);
                    check = new Rectangle2D.Double(x * subLenght + offset, y * subLenght + offset, subLenght, subLenght);
                    
                    graphicalChecks[y][x]=(check);
                    g2d.fill(check);
                    
                }
                //g.drawRect(x * subLenght + offset, y * subLenght + offset, subLenght, subLenght);
                
            }
        }

        for (int i=0;i<blackPieces.size();i++) {
            g.setColor(Color.BLACK);
            g.fillOval(blackPieces.get(i).getPosition().getColomnNumber() * subLenght + (offset*2) , blackPieces.get(i).getPosition().getLineNumber() * subLenght + (offset*2) , 60, 60);
            
            if (blackPieces.get(i) instanceof King) {
                g.setColor(Color.RED);
                g.fillOval(blackPieces.get(i).getPosition().getColomnNumber() * subLenght + (offset*2) + 13, blackPieces.get(i).getPosition().getLineNumber() * subLenght + (offset*2) +13 , 35, 35);
                g.setColor(Color.BLACK);
                g.fillOval(blackPieces.get(i).getPosition().getColomnNumber() * subLenght + (offset*2) + 18, blackPieces.get(i).getPosition().getLineNumber() * subLenght + (offset*2) +18 , 25, 25);
            }
        }
        
        for (int i=0;i<whitePieces.size();i++) {
            g.setColor(Color.WHITE);
            g.fillOval(whitePieces.get(i).getPosition().getColomnNumber() * subLenght + (offset*2) , whitePieces.get(i).getPosition().getLineNumber() * subLenght + (offset*2) , 60, 60);

            if (whitePieces.get(i) instanceof King) {
                g.setColor(Color.RED);
                g.fillOval(whitePieces.get(i).getPosition().getColomnNumber() * subLenght + (offset*2) + 13, whitePieces.get(i).getPosition().getLineNumber() * subLenght + (offset*2) +13 , 35, 35);
                g.setColor(Color.WHITE);
                g.fillOval(whitePieces.get(i).getPosition().getColomnNumber() * subLenght + (offset*2) + 18, whitePieces.get(i).getPosition().getLineNumber() * subLenght + (offset*2) +18 , 25, 25);
            }
        }
    }
    
    public void yellowOriginalColorFlash(Rectangle2D rectangle, int lineNumber, int colomnNumber, boolean isKing, Color color){
        Graphics g = this.getGraphics();
        Graphics2D g2d = (Graphics2D) g;        
        g.setColor(Color.YELLOW);
        rectangle.setRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        g2d.fill(rectangle);
        g.setColor(color);
        g.fillOval(colomnNumber * subLenght + (offset*2) , lineNumber * subLenght + (offset*2) , 60, 60);
        if(isKing==true){
            g.setColor(Color.RED);
            g.fillOval(colomnNumber * subLenght + (offset*2) + 13, lineNumber * subLenght + (offset*2) +13 , 35, 35);
            g.setColor(color);
            g.fillOval(colomnNumber * subLenght + (offset*2) + 18, lineNumber * subLenght + (offset*2) +18 , 25, 25);
        }
        
    }
    
    public void greenOriginalColorFlash( int lineNumber, int colomnNumber, boolean isKing, String color){
        Color paintColor;
        if(color.equals("black")){
            paintColor=Color.BLACK;
        }else{
            paintColor=Color.WHITE;
        }
        Rectangle2D currentRec=graphicalChecks[lineNumber][colomnNumber];
        Graphics g = this.getGraphics();
        Graphics2D g2d = (Graphics2D) g;        
        g.setColor(Color.GREEN);
        currentRec.setRect(currentRec.getX(), currentRec.getY(), currentRec.getWidth(), currentRec.getHeight());
        g2d.fill(currentRec);
        g.setColor(paintColor);
        g.fillOval(colomnNumber * subLenght + (offset*2) , lineNumber * subLenght + (offset*2) , 60, 60);
        if(isKing==true){
            g.setColor(Color.RED);
            g.fillOval(colomnNumber * subLenght + (offset*2) + 13, lineNumber * subLenght + (offset*2) +13 , 35, 35);
            g.setColor(paintColor);
            g.fillOval(colomnNumber * subLenght + (offset*2) + 18, lineNumber * subLenght + (offset*2) +18 , 25, 25);
        }
        
    }
    
    public void blueOriginalColorFlash(Rectangle2D rectangle, int lineNumber, int colomnNumber){
        Graphics g = this.getGraphics();
        Graphics2D g2d = (Graphics2D) g;        
        g.setColor(Color.CYAN);
        rectangle.setRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        g2d.fill(rectangle);
        
        
    }
    
    public void showSelectablePiece(ArrayList<Piece> possibilities){
        Check currentCheck;
        int currentLine;
        int currentColomn;
        boolean isKing;
        Color color;
        Rectangle2D currentRec ;
        for(int i=0;i<possibilities.size();i++){
            currentCheck=possibilities.get(i).getPosition();
            currentLine=currentCheck.getLineNumber();
            currentColomn=currentCheck.getColomnNumber();
            currentRec=graphicalChecks[currentLine][currentColomn];
            if(possibilities.get(i)instanceof King){
                isKing=true;
            }else{
                isKing=false;
            }
            
            if(possibilities.get(i).getColor().equals("black")){
                color=Color.BLACK;
            }else{
                 color=Color.WHITE;
            }
            this.yellowOriginalColorFlash(currentRec,currentLine,currentColomn,isKing,color);           
        }        
    }
    
    public void showSelectablePath(ArrayList<Check> possibilities){
        Check currentCheck;
        int currentLine;
        int currentColomn;
        Rectangle2D currentRec ;
        for(int i=0;i<possibilities.size();i++){
            currentCheck=possibilities.get(i);
            currentLine=currentCheck.getLineNumber();
            currentColomn=currentCheck.getColomnNumber();
            currentRec=graphicalChecks[currentLine][currentColomn];
            this.blueOriginalColorFlash(currentRec,currentLine,currentColomn);           
        }        
    }
    
    public int[] chooseCheck() throws InterruptedException{
        while((playerInputCheckLine==-1)&&(playerInputCheckColomn==-1)){            
            System.out.print("");
        }              
        int[] result = {playerInputCheckLine,playerInputCheckColomn};
        playerInputCheckLine=-1;
        playerInputCheckColomn=-1;
        return result;
        
    }

    public ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

    public ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getButton() == 1) {
            int checkLine = -1;
            int checkColomn = -1;
            Rectangle2D currentRectangle;
            for(int i = 0; i<graphicalChecks.length;i++){
                for(int j=0; j<graphicalChecks.length;j++){
                    currentRectangle = graphicalChecks[i][j];
                    if(currentRectangle.contains(e.getX(), e.getY())){
                    checkLine=i;
                    checkColomn= j;    
                    }
                }              
            }
            if((checkLine!=-1)&&(checkColomn!=-1)){
                playerInputCheckLine=checkLine;
                playerInputCheckColomn=checkColomn;
                //return result;
                //JOptionPane.showMessageDialog(null,"Clicked check line "+(checkLine+1)+ " colomn "+(checkColomn+1)); 
            }else{
                //return null;
            }
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
    }
}
