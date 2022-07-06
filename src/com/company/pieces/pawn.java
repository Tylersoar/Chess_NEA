package com.company.pieces;

import com.company.common.file;

import java.util.LinkedList;

public class pawn extends piece{

    public pawn(file xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation, int x) {
        super(xp, yp, isWhite, n, ps, fileLocation,x);
    }
}

