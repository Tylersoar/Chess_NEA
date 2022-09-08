package com.company.board;
import com.company.common.location;
import com.company.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.LinkedList;

public class Board {
    public static LinkedList<piece> ps = new LinkedList<>();
    public static piece selectedPiece=null;
    private static LinkedList<location> position = new LinkedList<>();
    public static int selectedPieceOriginalXP;
    public static int selectedPieceOriginalYP;

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setBounds(10, 10, 512, 512); //creates window and sets the boundaries

        for(int x = 0; x < 8; x++) { //loops through both x and y-axis and uses boolean
            for (int y = 0; y < 8; y++) {//to set square to black of white depending on the boolean
                //Sets the background colours of the board

                boolean piecePlaced = checkStartLocations(x, y);
                System.out.println("drawing originals");
            }
        }

        JPanel pn = new JPanel() {





            @Override
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
                        g.drawImage(ps.get(i).image,(ps.get(i).column)*64,ps.get(i).row*64,null);
                    }

            }
        };


        frame.add(pn);
        frame.setDefaultCloseOperation(3); //enables you to close the tab
        frame.setVisible(true);

        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedPiece!=null){
                    selectedPiece.x=e.getX()-32;
                    selectedPiece.y=e.getY()-32;
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
                System.out.println((getPiece(e.getX(),e.getY()).isWhite?"White":"Black")+getPiece(e.getX(),e.getY()).name);
                selectedPiece=getPiece(e.getX(),e.getY());
                selectedPieceOriginalXP = e.getX()/64;
                selectedPieceOriginalYP = e.getY()/64;
                System.out.println(e.getX() + " " + e.getY());
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                selectedPiece.move(e.getX()/64,e.getY()/64);
                frame.repaint();
                selectedPiece = null;
            }



            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {


            }
        });

    }
    public static piece getPiece (int testingX, int testingY){
        int xp=testingX/64;
        int yp=testingY/64;
        for (piece p: ps){
            if (p.column==xp&&p.row==yp){
                return p;
            }
        }
        return null;


    }

    private static boolean checkStartLocations(int x, int y) {
        //file.values()[x] this bit sets the A,B,C.... bit of the chess piece using the file enums
        location tempLocation = new location(x, y);
        boolean piecePlaced = false;

        //Checks if piece is in the right starting location, if not, will not make a new piece
        //if (x==1 y ==1){ DO a series of if statements for each of your chess pieces, if they are in the right location, set a piece and add it to list



        if (x == 0 && y == 7) { //adds all pieces on board to linked list
            piece wRook = new rook(tempLocation.getColumn(), tempLocation.getRow(), true, "rook", ps, "/com/company/res/white/rook.png");
            tempLocation.setPicture(new JLabel(wRook.getFileLocation())); //CHECK
            tempLocation.setPieces(wRook);
            piecePlaced = true;
        }
        if (x == 7 && y == 7) {
            piece wRook = new rook(tempLocation.getColumn(), tempLocation.getRow(), true, "rook", ps, "/com/company/res/white/rook.png");
            tempLocation.setPicture(new JLabel(wRook.getFileLocation())); //CHECK
            tempLocation.setPieces(wRook);
            piecePlaced = true;
        }
        if (x == 1 && y == 7) {
            piece wKnight = new knight(tempLocation.getColumn(), tempLocation.getRow(), true, "knight", ps, "/com/company/res/white/knight.png");
            tempLocation.setPicture(new JLabel(wKnight.getFileLocation())); //CHECK
            tempLocation.setPieces(wKnight);
            piecePlaced = true;
        }
        if (x == 6 && y == 7) {
            piece wKnight = new knight(tempLocation.getColumn(), tempLocation.getRow(), true, "knight", ps, "/com/company/res/white/knight.png");
            tempLocation.setPicture(new JLabel(wKnight.getFileLocation())); //CHECK
            tempLocation.setPieces(wKnight);
            piecePlaced = true;
        }
        if (x == 2 && y == 7) {
            piece wBishop = new bishop(tempLocation.getColumn(), tempLocation.getRow(), true, "bishop", ps, "/com/company/res/white/bishop.png");
            tempLocation.setPicture(new JLabel(wBishop.getFileLocation())); //CHECK
            tempLocation.setPieces(wBishop);
            piecePlaced = true;
        }
        if (x == 5 && y == 7) {
            piece wBishop = new bishop(tempLocation.getColumn(), tempLocation.getRow(), true, "bishop", ps, "/com/company/res/white/bishop.png");
            tempLocation.setPicture(new JLabel(wBishop.getFileLocation())); //CHECK
            tempLocation.setPieces(wBishop);
            piecePlaced = true;
        }
        if (x == 4 && y == 7) {
            piece wKing = new king(tempLocation.getColumn(), tempLocation.getRow(), true, "king", ps, "/com/company/res/white/king.png");
            tempLocation.setPieces(wKing);
            piecePlaced = true;
        }
        if (x == 3 && y == 7) {
            piece wQueen = new queen(tempLocation.getColumn(), tempLocation.getRow(), true, "queen", ps, "/com/company/res/white/queen.png");
            tempLocation.setPieces(wQueen);
            piecePlaced = true;
        }
        if ( y == 6) {
            piece wPawn = new pawn(tempLocation.getColumn(), tempLocation.getRow(), true, "pawn", ps, "/com/company/res/white/pawn.png");
            tempLocation.setPieces(wPawn);
            piecePlaced = true;
        }




        if (x == 0 && y == 0)  {
            piece bRook = new rook(tempLocation.getColumn(), tempLocation.getRow(), false, "rook", ps, "/com/company/res/black/rook.png");
            tempLocation.setPicture(new JLabel(bRook.getFileLocation())); //CHECK
            tempLocation.setPieces(bRook);
            piecePlaced = true;
        }
        if (x == 7 && y == 0) {
            piece bRook = new rook(tempLocation.getColumn(), tempLocation.getRow(), false, "rook", ps, "/com/company/res/black/rook.png");
            tempLocation.setPicture(new JLabel(bRook.getFileLocation())); //CHECK
            tempLocation.setPieces(bRook);
            piecePlaced = true;
        }
        if (x == 1 && y == 0) {
            piece bKnight = new knight(tempLocation.getColumn(), tempLocation.getRow(), false, "knight", ps, "/com/company/res/black/knight.png");
            tempLocation.setPicture(new JLabel(bKnight.getFileLocation())); //CHECK
            tempLocation.setPieces(bKnight);
            piecePlaced = true;
        }
        if (x == 6 && y == 0) {
            piece bKnight = new knight(tempLocation.getColumn(), tempLocation.getRow(), false, "knight", ps, "/com/company/res/black/knight.png");
            tempLocation.setPicture(new JLabel(bKnight.getFileLocation())); //CHECK
            tempLocation.setPieces(bKnight);
            piecePlaced = true;
        }
        if (x == 2 && y == 0) {
            piece bBishop = new bishop(tempLocation.getColumn(), tempLocation.getRow(), false, "bishop", ps, "/com/company/res/black/bishop.png");
            tempLocation.setPicture(new JLabel(bBishop.getFileLocation())); //CHECK
            tempLocation.setPieces(bBishop);
            piecePlaced = true;
        }
        if (x == 5 && y == 0) {
            piece bBishop = new bishop(tempLocation.getColumn(), tempLocation.getRow(), false, "bishop", ps, "/com/company/res/black/bishop.png");
            tempLocation.setPicture(new JLabel(bBishop.getFileLocation())); //CHECK
            tempLocation.setPieces(bBishop);
            piecePlaced = true;

        }
        if (x == 4 && y == 0) {
            piece bKing = new king(tempLocation.getColumn(), tempLocation.getRow(), false, "king", ps, "/com/company/res/black/king.png");
            tempLocation.setPicture(new JLabel(bKing.getFileLocation())); //CHECK
            tempLocation.setPieces(bKing);
            piecePlaced = true;
        }
        if (x == 3 && y == 0) {
            piece bQueen = new queen(tempLocation.getColumn(), tempLocation.getRow(), false, "queen", ps, "/com/company/res/black/queen.png");
            tempLocation.setPicture(new JLabel(bQueen.getFileLocation())); //CHECK
            tempLocation.setPieces(bQueen);
            piecePlaced = true;
        }
        if (y == 1) {
            piece bPawn = new pawn(tempLocation.getColumn(), tempLocation.getRow(), false, "pawn", ps, "/com/company/res/black/pawn.png");
            tempLocation.setPicture(new JLabel(bPawn.getFileLocation())); //CHECK
            tempLocation.setPieces(bPawn);
            piecePlaced = true;
        }




        position.add(tempLocation);
        return piecePlaced; //Returns true if we added a piece to the location square
    }


}
