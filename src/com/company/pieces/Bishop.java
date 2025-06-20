package com.company.pieces;

import com.company.board.Board;

import java.util.LinkedList;

public class Bishop extends piece {

    public Bishop(int xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }

    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill) {
        return validMoveBishop(xp,yp,isWhite,willKill);
    }
}





