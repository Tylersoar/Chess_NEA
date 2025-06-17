package com.company.pieces;

import com.company.board.Board;

import java.util.LinkedList;

public class Knight extends piece {

    public Knight(int xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }

    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill) {
        if (xp < 8 && xp >= 0 && yp < 8 && yp >= 0) {

            int deltaX = Math.abs(Board.selectedPieceOriginalXP - xp);
            int deltaY = Math.abs(Board.selectedPieceOriginalYP - yp);

            // if knight moves in L-shape, 2 squares in one and 1 square perpendicular
            return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
        }
        return false;
    }
}
