package com.company.pieces;

import com.company.board.Board;

import java.util.LinkedList;

public class bishop extends piece {

    public bishop(int xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }

    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill) {

        if (xp < 8 && xp >= 0 && yp < 8 && yp >= 0) {

            //need to use for loop to enable piece to move infinitely sideways
            for (int i = 0; i < 8; i++) {
                if (Board.selectedPieceOriginalYP - yp == i && (Board.selectedPieceOriginalXP - xp == i || xp - Board.selectedPieceOriginalXP == i)) {
                    return true;
                }

                if (yp - Board.selectedPieceOriginalYP == i && (Board.selectedPieceOriginalXP - xp == i || xp - Board.selectedPieceOriginalXP == i)) {
                    return true;
                }
            }
        }
        return false;
    }
}





