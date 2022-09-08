package com.company.pieces;

import com.company.board.Board;

import java.util.LinkedList;

public class king extends piece{

    public king(int xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }

    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill) {
        if (xp < 8 && xp >= 0 && yp < 8 && yp >= 0) {
            for (int i = Board.selectedPieceOriginalXP -1; i <= Board.selectedPieceOriginalXP+1; i++) {
                for (int j = Board.selectedPieceOriginalYP-1; j <= Board.selectedPieceOriginalYP+1; j++) {
                    if(xp == i && yp == j){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

