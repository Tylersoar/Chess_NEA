package com.company.pieces;

import com.company.common.file;

import java.util.LinkedList;

public class knight extends piece{

    public knight(file xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }
}
