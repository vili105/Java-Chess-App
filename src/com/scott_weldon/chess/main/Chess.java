/* Chess
 * Copyright (C) 2010-2014 Scott Weldon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.scott_weldon.chess.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import com.scott_weldon.chess.gamestate.Game;
import com.scott_weldon.chess.gamestate.Piece;
import com.scott_weldon.chess.manual_view.Polygons;

public class Chess extends JPanel {
  Game game;
  //OptionPanel p2;
  private int side;
  
  //Set up frame or applet. TODO: Applet doesn't work with object tag.
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    Chess panel = new Chess();
    frame.add(panel);
    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("Chess");
    frame.setVisible(true);
  }
  
  //TODO: For object tag, work in progress
//  public void init() {
//    try {
//      SwingUtilities.invokeAndWait(new Runnable() {
//        public void run() {
//          p1 = new Checkerboard();
//          add(p1);
//          p1.addMouseListener(new MoveListener());
//        }this.side
//      });
//    }
//    catch (Exception e) {
//      System.err.println("Fail!");
//    }
//  }

  //Create new checkerboard panel; add it and listener to frame.
  public Chess() {
    game = new Game();
    //p2 = new OptionPanel();
    
    this.addMouseListener(new MoveListener());
  }
  
  //If the mouse clicked on a square, process it.
  public void processClick(int xPoint, int yPoint) {
    int x = xPoint / side + 1; //Get square number for x, starting at 1
    int y = yPoint / side + 1; //Get square number for y, starting at 1
    boolean valid = false;
    
    if ((x > 8) || (y > 8)) {
      game.setCurrentX(0);
      game.setCurrentY(0);
      valid = true;
    }
    if ((x == game.getCurrentX()) && (y == game.getCurrentY())) {
      game.setCurrentX(0);
      game.setCurrentY(0);
      valid = true;
    }
    
    for (int i = 0; i < game.white.pieces.size(); i++) {
      if ((game.white.pieces.get(i).getX() == game.getCurrentX())
          && (game.white.pieces.get(i).getY() == game.getCurrentY())) {
        game.processMove(game.white.pieces.get(i), x, y, game.white);
        valid = true;
        break;
      }
    }
    for (int i = 0; i < game.black.pieces.size(); i++) {
      if ((game.black.pieces.get(i).getX() == game.getCurrentX())
          && (game.black.pieces.get(i).getY() == game.getCurrentY())) {
        game.processMove(game.black.pieces.get(i), x, y, game.black);
        valid = true;
        break;
      }
    }
    
    if (!valid) {
      game.setCurrentX(x);
      game.setCurrentY(y);
    }
    
    repaint();
  }
  
  //Paint the checkerboard and the pieces.
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    //Get the width of each square
    if (this.getWidth() > this.getHeight()) {
      side = this.getHeight() / 8;
    }
    else {
      side = this.getWidth() / 8;
    }
    
    //Draw squares
    boolean isWhite = true;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (isWhite) {
          g.setColor(Color.WHITE);
          isWhite = false;
        }
        else {
          g.setColor(new Color(20, 190, 20));
          isWhite = true;
        }
        g.fillRect((i * side), (j * side), side, side);
      }
      
      if (i % 2 == 0) {
        isWhite = false;
      }
      else {
        isWhite = true;
      }
    }
    
    if ((game.getCurrentX() != 0) && (game.getCurrentY() != 0)) {
      g.setColor(Color.RED);
      g.fillRect((game.getCurrentX() - 1) * side, (game.getCurrentY() - 1) * side,
          side, side);
    }
    
    //Make sure everything is the right size
    Piece.setWidth(side);

    int width = side;
    for (int i = 0; i < game.white.pieces.size(); i++) {
      Polygons.setPolygon(game.white.pieces.get(i), width,
                          game.white.pieces.get(i).getType());
    }
    for (int i = 0; i < game.black.pieces.size(); i++) {
      Polygons.setPolygon(game.black.pieces.get(i), width,
                          game.black.pieces.get(i).getType());
    }

    //Draw the pieces
    g.setColor(Color.BLACK);
    if (game.white.king.getStatus()) {
      g.fillPolygon(game.white.king.piece);
    }
    else {
      System.out.println("Black is the winner!");
      System.exit(0);
    }
    for (int i = 1; i < game.white.pieces.size(); i++) {
      if (game.white.pieces.get(i).getStatus()) {
        g.fillPolygon(game.white.pieces.get(i).piece);
      }
    }
    
    g.setColor(new Color(255, 0, 255));
    if (game.black.king.getStatus()) {
      g.fillPolygon(game.black.king.piece);
    }
    else {
      System.out.println("White is the winner!");
      System.exit(0);
    }
    for (int i = 1; i < game.black.pieces.size(); i++) {
      if (game.black.pieces.get(i).getStatus()) {
        g.fillPolygon(game.black.pieces.get(i).piece);
      }
    }
  }
  
  class MoveListener implements MouseListener{
    //Watch for mouse clicks.
    public void mouseClicked(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();
      processClick(x, y);
    }

    public void mouseEntered(MouseEvent arg0) {
      // Do nothing
    }

    public void mouseExited(MouseEvent arg0) {
      // Do nothing
    }

    public void mousePressed(MouseEvent arg0) {
      // Do nothing
    }

    public void mouseReleased(MouseEvent arg0) {
      // Do nothing
    }
  }
  
//  class OptionPanel extends JPanel {
//    public OptionPanel() {
//      JRadioButton black = new JRadioButton("Black");
//      this.add(black);
//    }
//  }
}
