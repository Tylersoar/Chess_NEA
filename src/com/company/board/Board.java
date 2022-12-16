package com.company.board;
import com.company.GUI;
import com.company.common.location;
import com.company.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.LinkedList;

public class Board extends JPanel {
    public static LinkedList<piece> ps = new LinkedList<>();
    public static piece selectedPiece=null;
    private static LinkedList<location> position = new LinkedList<>();
    public static int selectedPieceOriginalXP;
    public static int selectedPieceOriginalYP;
    public static boolean whiteTurn = true;
    public static boolean pieceMoved = false;


    static int timerW,timerB;

    public static boolean successfulLogin = false;

    public static void main(String[] args) {

        GUI loginMenu = new GUI();
        loginMenu.GUI();

        while(!successfulLogin){

            System.out.println(" "); // not sure why this works, but you absolutely must keep this loop in
        }

        Board();
    }

    public static void Board(){

        timerW = 600;
        timerB = 600;
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setBounds(10, 10, 512, 582); //creates window and sets the boundaries

        for(int x = 0; x < 8; x++) { //loops through both x and y-axis and uses boolean
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
                g.drawImage(ps.get(i).image,(ps.get(i).column)*64,ps.get(i).row*64,null);
            }
            g.setFont(new Font("Arial", Font.PLAIN, 20)); //sets font for timer
            g.setColor(Color.BLACK);
            String secondValue;
            if((timerW%60)<10){
                secondValue = "0"+(timerW%60);
            }
            else{
                secondValue = ""+(timerW%60);
            }
            g.drawString(("White" + (timerW / 60) + ":" + secondValue),10,560);  //draws the exact time and uses constructor to display minutes and seconds
            if((timerB%60)<10){
                secondValue = "0"+(timerB%60);
            }
            else{
                secondValue = ""+(timerB%60);
            }
            g.drawString(("Black" + (timerB / 60) + ":" + secondValue),280,560);
        }};


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
                //System.out.println((getPiece(e.getX(),e.getY()).isWhite?"White":"Black")+getPiece(e.getX(),e.getY()).name);
                selectedPiece=getPiece(e.getX(),e.getY());
                selectedPieceOriginalXP = e.getX()/64;
                selectedPieceOriginalYP = e.getY()/64;
                //System.out.println(e.getX() + " " + e.getY());
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedPiece.isWhite == whiteTurn){ //checks if selected piece iswhite
                    selectedPiece.move(e.getX()/64,e.getY()/64);
                    if((e.getX()/64 == selectedPieceOriginalXP) && (e.getY()/64 == selectedPieceOriginalYP)){
                    }
                    else if(pieceMoved){
                        whiteTurn = !whiteTurn;  //if the piece moved is white "whites turn" will be printed allowing all white pieces to move
                        if(whiteTurn){
                            System.out.println("It is white turn");
                        } else{
                            System.out.println("It is black turn");  //because each piece has the iswhite boolean if its true all white pieces can move and if iswhite is false black can move.
                        }
                    }
                }
                frame.repaint();
                selectedPiece = null;
                pieceMoved = false;
//                for (int i = 0; i < ps.size(); i++) {
//                    piece tempPiece;
//                    tempPiece = ps.get(i);
//
//                    if (tempPiece.name.equals("king")){
//                        tempPiece.check();
//                    }
//
//                }
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

        while (true) {
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

    public static piece getPieceC (int testingX, int testingY){
        for (piece p: ps){
            if (p.column== testingX &&p.row== testingY){
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
