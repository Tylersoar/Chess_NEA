package com.company.board;

import com.company.GUI;
import com.company.common.Location;
import com.company.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

public class Board extends JPanel {
  public static LinkedList<Piece> ps = new LinkedList<>();
  public static Piece selectedPiece = null;
  private static LinkedList<Location> position = new LinkedList<>();
  public static int selectedPieceOriginalXP;
  public static int selectedPieceOriginalYP;
  public static boolean whiteTurn = true;
  public static boolean pieceMoved = false;

  public static boolean gameOver = false;
  static int timerW, timerB;

  public static boolean successfulLogin = false;

  public static void main(String[] args) {

    GUI loginMenu = new GUI();
    loginMenu.GUI();

    while (!successfulLogin) {

      System.out.println(" "); // not sure why this works, but you absolutely must keep this loop in
    }

    Board();
  }

  public static void Board() {

    timerW = 600;
    timerB = 600;
    JFrame frame = new JFrame();
    frame.setUndecorated(true);
    frame.setBounds(10, 10, 512, 582); //creates window and sets the boundaries

    for (int x = 0; x < 8; x++) { //loops through both x and y-axis and uses boolean
      for (int y = 0; y < 8; y++) {//to set square to black of white depending on the boolean
        //Sets the background colours of the board

        boolean piecePlaced = checkStartLocations(x, y);
        System.out.println("drawing originals");
      }
    }


    JPanel pn = new JPanel() {


      public void paint(Graphics g) {

        boolean white = true;
        for (int x = 0; x < 8; x++) { //loops through both x and y-axis and uses boolean
          for (int y = 0; y < 8; y++) {//to set square to black of white depending on the boolean
            //Sets the background colours of the board
            if (white) {
              g.setColor(new Color(232, 235, 239));
            } else {
              g.setColor(new Color(125, 135, 150));
            }
            g.fillRect(x * 64, y * 64, 64, 64);
            white = !white;

          }
          white = !white;
        }

        for (int i = 0; i < ps.size(); i++) {
          g.drawImage(ps.get(i).image, (ps.get(i).column) * 64, ps.get(i).row * 64, null);
        }
        g.setFont(new Font("Arial", Font.PLAIN, 20)); //sets font for timer
        g.setColor(Color.BLACK);
        String secondValue;
        if ((timerW % 60) < 10) {
          secondValue = "0" + (timerW % 60);
        } else {
          secondValue = "" + (timerW % 60);
        }
        g.drawString(("White" + (timerW / 60) + ":" + secondValue), 10, 560);  //draws the exact time and uses constructor to display minutes and seconds
        if ((timerB % 60) < 10) {
          secondValue = "0" + (timerB % 60);
        } else {
          secondValue = "" + (timerB % 60);
        }
        g.drawString(("Black" + (timerB / 60) + ":" + secondValue), 280, 560);
      }

    };


    frame.add(pn);
    frame.setDefaultCloseOperation(3); //enables you to close the tab
    frame.setVisible(true);

    frame.addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseDragged(MouseEvent e) {
        if (selectedPiece != null) {
          selectedPiece.x = e.getX() - 32;
          selectedPiece.y = e.getY() - 32;
          frame.repaint();
        }

      }

      @Override
      public void mouseMoved(MouseEvent e) {

      }
    });

    frame.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
      }

      @Override
      public void mousePressed(MouseEvent e) {
        selectedPiece = getPiece(e.getX(), e.getY());
        selectedPieceOriginalXP = e.getX() / 64;
        selectedPieceOriginalYP = e.getY() / 64;
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        if (gameOver || selectedPiece == null) {
          return;
        }
        if (selectedPiece.isWhite == whiteTurn) { //checks if selected piece iswhite
          selectedPiece.move(e.getX() / 64, e.getY() / 64);
          if ((e.getX() / 64 == selectedPieceOriginalXP) && (e.getY() / 64 == selectedPieceOriginalYP)) {
          } else if (pieceMoved) {
            whiteTurn = !whiteTurn;  // switch turns after a successful move
            if (whiteTurn) {
              System.out.println("It is white turn");
            } else {
              System.out.println("It is black turn");
            }
            updateGameState(); // check / checkmate / stalemate for the side to move
          }
        }
        frame.repaint();
        selectedPiece = null;
        pieceMoved = false;
      }

      @Override
      public void mouseEntered(MouseEvent e) {
      }

      @Override
      public void mouseExited(MouseEvent e) {
      }
    });

    double drawInterval = 1000000000 / 60; //0.01666 seconds

    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;

    while (!gameOver) {
      currentTime = System.nanoTime();
      timer += (currentTime - lastTime);  //game loop which keeps track of time
      lastTime = currentTime;
      frame.repaint();
      if (timer >= 1000000000) {
        if (whiteTurn) {
          timerW -= 1;
        } else {
          timerB -= 1;
        }
        //System.out.println("FPS:" + drawCount);
        timer = 0;
      }
      if (timerW == 0) {
        timerW = 0;
        break;
      }
      if (timerB == 0) {
        timerB = 0;
        break;

        //need to implement a play again button
      }
    }
  }

  public static Piece getPiece(int testingX, int testingY) {
    int xp = testingX / 64;
    int yp = testingY / 64;
    for (Piece p : ps) {
      if (p.column == xp && p.row == yp) {
        return p;
      }
    }
    return null;
  }

  public static Piece getPieceC(int testingX, int testingY) {
    for (Piece p : ps) {
      if (p.column == testingX && p.row == testingY) {
        return p;
      }
    }
    return null;
  }


  // -------------------------
  // Check / checkmate helpers
  // -------------------------

  public static void updateGameState() {
    if (gameOver) return;

    boolean sideToMove = whiteTurn; // after a move we flip turns, so this is the player who must respond
    boolean inCheck = isKingInCheck(sideToMove);

    if (inCheck && !hasAnyLegalMove(sideToMove)) {
      gameOver = true;
      String winner = sideToMove ? "Black" : "White";
      JOptionPane.showMessageDialog(null, "Checkmate! " + winner + " wins.");
      return;
    }

    if (!inCheck && !hasAnyLegalMove(sideToMove)) {
      gameOver = true;
      JOptionPane.showMessageDialog(null, "Stalemate!");
      return;
    }

    if (inCheck) {
      System.out.println("Check!");
    }
  }

  private static Piece findKing(boolean whiteKing) {
    for (Piece p : ps) {
      if (p != null && p.isWhite == whiteKing && "king".equals(p.name)) {
        return p;
      }
    }
    return null;
  }

  public static boolean isKingInCheck(boolean whiteKing) {
    Piece k = findKing(whiteKing);
    if (k == null) return false; // king missing (shouldn't happen)
    return isSquareAttacked(k.column, k.row, !whiteKing);
  }

  private static boolean isSquareAttacked(int col, int row, boolean byWhite) {
    int savedOX = selectedPieceOriginalXP;
    int savedOY = selectedPieceOriginalYP;

    for (Piece attacker : new LinkedList<>(ps)) {
      if (attacker == null || attacker.isWhite != byWhite) continue;

      // validMove() relies on Board.selectedPieceOriginalXP/YP as the origin
      selectedPieceOriginalXP = attacker.column;
      selectedPieceOriginalYP = attacker.row;

      // willKill=true is important for pawns (diagonal captures)
      if (attacker.validMove(col, row, attacker.isWhite, true)) {
        selectedPieceOriginalXP = savedOX;
        selectedPieceOriginalYP = savedOY;
        return true;
      }
    }

    selectedPieceOriginalXP = savedOX;
    selectedPieceOriginalYP = savedOY;
    return false;
  }

  private static boolean hasAnyLegalMove(boolean sideWhite) {
    for (Piece p : new LinkedList<>(ps)) {
      if (p == null || p.isWhite != sideWhite) continue;

      for (int x = 0; x < 8; x++) {
        for (int y = 0; y < 8; y++) {
          if (isLegalMove(p, x, y)) return true;
        }
      }
    }
    return false;
  }

  private static boolean isLegalMove(Piece p, int toX, int toY) {
    if (p == null) return false;

    Piece target = getPieceC(toX, toY);
    if (target != null && target.isWhite == p.isWhite) return false; // can't capture own piece
    if (target != null && "king".equals(target.name)) return false;   // never capture the king

    int fromX = p.column;
    int fromY = p.row;
    int fromPX = p.x;
    int fromPY = p.y;
    boolean fromFirst = p.firstMove;

    int savedOX = selectedPieceOriginalXP;
    int savedOY = selectedPieceOriginalYP;

    selectedPieceOriginalXP = fromX;
    selectedPieceOriginalYP = fromY;

    boolean willKill = (target != null);
    if (!p.validMove(toX, toY, p.isWhite, willKill)) {
      selectedPieceOriginalXP = savedOX;
      selectedPieceOriginalYP = savedOY;
      return false;
    }

    // ---- simulate ----
    if (target != null) ps.remove(target);

    p.column = toX;
    p.row = toY;
    p.x = toX * 64;
    p.y = toY * 64;

    boolean leavesKingInCheck = isKingInCheck(p.isWhite);

    // ---- restore ----
    p.column = fromX;
    p.row = fromY;
    p.x = fromPX;
    p.y = fromPY;
    p.firstMove = fromFirst;

    if (target != null) ps.add(target);

    selectedPieceOriginalXP = savedOX;
    selectedPieceOriginalYP = savedOY;

    return !leavesKingInCheck;
  }


  private static boolean checkStartLocations(int x, int y) {
    //file.values()[x] this bit sets the A,B,C.... bit of the chess piece using the file enums
    Location tempLocation = new Location(x, y);
    boolean piecePlaced = false;

    //Checks if piece is in the right starting location, if not, will not make a new piece
    //if (x==1 y ==1){ DO a series of if statements for each of your chess pieces, if they are in the right location, set a piece and add it to list


    if (x == 0 && y == 7) { //adds all pieces on board to linked list
      Piece wRook = new Rook(tempLocation.getColumn(), tempLocation.getRow(), true, "rook", ps, "/com/company/res/white/rook.png");
      tempLocation.setPicture(new JLabel(wRook.getFileLocation())); //CHECK
      tempLocation.setPieces(wRook);
      piecePlaced = true;
    }
    if (x == 7 && y == 7) {
      Piece wRook = new Rook(tempLocation.getColumn(), tempLocation.getRow(), true, "rook", ps, "/com/company/res/white/rook.png");
      tempLocation.setPicture(new JLabel(wRook.getFileLocation())); //CHECK
      tempLocation.setPieces(wRook);
      piecePlaced = true;
    }
    if (x == 1 && y == 7) {
      Piece wKnight = new Knight(tempLocation.getColumn(), tempLocation.getRow(), true, "knight", ps, "/com/company/res/white/knight.png");
      tempLocation.setPicture(new JLabel(wKnight.getFileLocation())); //CHECK
      tempLocation.setPieces(wKnight);
      piecePlaced = true;
    }
    if (x == 6 && y == 7) {
      Piece wKnight = new Knight(tempLocation.getColumn(), tempLocation.getRow(), true, "knight", ps, "/com/company/res/white/knight.png");
      tempLocation.setPicture(new JLabel(wKnight.getFileLocation())); //CHECK
      tempLocation.setPieces(wKnight);
      piecePlaced = true;
    }
    if (x == 2 && y == 7) {
      Piece wBishop = new Bishop(tempLocation.getColumn(), tempLocation.getRow(), true, "bishop", ps, "/com/company/res/white/bishop.png");
      tempLocation.setPicture(new JLabel(wBishop.getFileLocation())); //CHECK
      tempLocation.setPieces(wBishop);
      piecePlaced = true;
    }
    if (x == 5 && y == 7) {
      Piece wBishop = new Bishop(tempLocation.getColumn(), tempLocation.getRow(), true, "bishop", ps, "/com/company/res/white/bishop.png");
      tempLocation.setPicture(new JLabel(wBishop.getFileLocation())); //CHECK
      tempLocation.setPieces(wBishop);
      piecePlaced = true;
    }
    if (x == 4 && y == 7) {
      Piece wKing = new King(tempLocation.getColumn(), tempLocation.getRow(), true, "king", ps, "/com/company/res/white/king.png");
      tempLocation.setPieces(wKing);
      piecePlaced = true;
    }
    if (x == 3 && y == 7) {
      Piece wQueen = new Queen(tempLocation.getColumn(), tempLocation.getRow(), true, "queen", ps, "/com/company/res/white/queen.png");
      tempLocation.setPieces(wQueen);
      piecePlaced = true;
    }
    if (y == 6) {
      Piece wPawn = new Pawn(tempLocation.getColumn(), tempLocation.getRow(), true, "pawn", ps, "/com/company/res/white/pawn.png");
      tempLocation.setPieces(wPawn);
      piecePlaced = true;
    }


    if (x == 0 && y == 0) {
      Piece bRook = new Rook(tempLocation.getColumn(), tempLocation.getRow(), false, "rook", ps, "/com/company/res/black/rook.png");
      tempLocation.setPicture(new JLabel(bRook.getFileLocation())); //CHECK
      tempLocation.setPieces(bRook);
      piecePlaced = true;
    }
    if (x == 7 && y == 0) {
      Piece bRook = new Rook(tempLocation.getColumn(), tempLocation.getRow(), false, "rook", ps, "/com/company/res/black/rook.png");
      tempLocation.setPicture(new JLabel(bRook.getFileLocation())); //CHECK
      tempLocation.setPieces(bRook);
      piecePlaced = true;
    }
    if (x == 1 && y == 0) {
      Piece bKnight = new Knight(tempLocation.getColumn(), tempLocation.getRow(), false, "knight", ps, "/com/company/res/black/knight.png");
      tempLocation.setPicture(new JLabel(bKnight.getFileLocation())); //CHECK
      tempLocation.setPieces(bKnight);
      piecePlaced = true;
    }
    if (x == 6 && y == 0) {
      Piece bKnight = new Knight(tempLocation.getColumn(), tempLocation.getRow(), false, "knight", ps, "/com/company/res/black/knight.png");
      tempLocation.setPicture(new JLabel(bKnight.getFileLocation())); //CHECK
      tempLocation.setPieces(bKnight);
      piecePlaced = true;
    }
    if (x == 2 && y == 0) {
      Piece bBishop = new Bishop(tempLocation.getColumn(), tempLocation.getRow(), false, "bishop", ps, "/com/company/res/black/bishop.png");
      tempLocation.setPicture(new JLabel(bBishop.getFileLocation())); //CHECK
      tempLocation.setPieces(bBishop);
      piecePlaced = true;
    }
    if (x == 5 && y == 0) {
      Piece bBishop = new Bishop(tempLocation.getColumn(), tempLocation.getRow(), false, "bishop", ps, "/com/company/res/black/bishop.png");
      tempLocation.setPicture(new JLabel(bBishop.getFileLocation())); //CHECK
      tempLocation.setPieces(bBishop);
      piecePlaced = true;

    }
    if (x == 4 && y == 0) {
      Piece bKing = new King(tempLocation.getColumn(), tempLocation.getRow(), false, "king", ps, "/com/company/res/black/king.png");
      tempLocation.setPicture(new JLabel(bKing.getFileLocation())); //CHECK
      tempLocation.setPieces(bKing);
      piecePlaced = true;
    }
    if (x == 3 && y == 0) {
      Piece bQueen = new Queen(tempLocation.getColumn(), tempLocation.getRow(), false, "queen", ps, "/com/company/res/black/queen.png");
      tempLocation.setPicture(new JLabel(bQueen.getFileLocation())); //CHECK
      tempLocation.setPieces(bQueen);
      piecePlaced = true;
    }
    if (y == 1) {
      Piece bPawn = new Pawn(tempLocation.getColumn(), tempLocation.getRow(), false, "pawn", ps, "/com/company/res/black/pawn.png");
      tempLocation.setPicture(new JLabel(bPawn.getFileLocation())); //CHECK
      tempLocation.setPieces(bPawn);
      piecePlaced = true;
    }


    position.add(tempLocation);
    return piecePlaced; //Returns true if we added a piece to the location square
  }


}
