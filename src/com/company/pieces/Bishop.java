package com.company.pieces;

import java.util.LinkedList;

public class Bishop extends Piece {

    public Bishop(int xp, int yp, boolean isWhite, String n, LinkedList<Piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }

    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill) {
        return validMoveBishop(xp,yp,isWhite,willKill);
    }
}





