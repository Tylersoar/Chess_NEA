package com.company.pieces;

import com.company.board.Board;

import java.util.LinkedList;


public class rook extends piece {
    public static String direction = "";


    public rook(int xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }


    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill) {
        if (xp < 8 && xp >= 0 && yp < 8 && yp >= 0) {
            if (Board.selectedPieceOriginalYP == yp || Board.selectedPieceOriginalXP == xp) {

//                //need to implement piece detection so castle can't jump over pieces
//
//                //detects which way the castle is going
//
//                if (xp > Board.selectedPieceOriginalXP) {
//                    direction = "east";
//                }
//                if (xp < Board.selectedPieceOriginalXP) {
//                    direction = "west";
//                }
//                if (yp < Board.selectedPieceOriginalYP) {
//                    direction = "north";
//                }
//                if (yp > Board.selectedPieceOriginalYP) {
//                    direction = "south";
//                }
//
//                //rules to detect a piece on a certain axis
//
//                if (direction.equals("south")) {
//                    int spacesToMove = Math.abs(yp - Board.selectedPieceOriginalYP);
//                    for (int i = 1; i < spacesToMove; i++) {
//                        piece p = Board.getPiece(Board.selectedPieceOriginalXP, Board.selectedPieceOriginalYP + i);
//                        if (p != null) {
//                            return false;
//                        }
//
//                    }
//                }
//                if (direction.equals("north")) {
//                    int spacesToMove = Math.abs(yp - Board.selectedPieceOriginalYP);
//                    for (int i = 1; i < spacesToMove; i++) {
//                        piece p = Board.getPiece(Board.selectedPieceOriginalXP, Board.selectedPieceOriginalYP - i);
//                        if (p != null) {
//                            return false;
//                        }
//                    }
//                }
//                if (direction.equals("east")) {
//                    int spacesToMove = Math.abs(xp - Board.selectedPieceOriginalXP);
//                    for (int i = 1; i < spacesToMove; i++) {
//                        piece p = Board.getPiece(Board.selectedPieceOriginalXP + i, Board.selectedPieceOriginalYP);
//                        if (p != null) {
//                            return false;
//                        }
//                    }
//                }
//                if (direction.equals("west")) {
//                    int spacesToMove = Math.abs(xp - Board.selectedPieceOriginalXP);
//                    for (int i = 1; i < spacesToMove; i++) {
//                        piece p = Board.getPiece(Board.selectedPieceOriginalXP - i, Board.selectedPieceOriginalYP);
//                        if (p != null) {
//                            return false;
//                        }
//                    }
//
//                }
            }
        }
        return true;
    }

}












