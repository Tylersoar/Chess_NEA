package com.company.pieces;

import com.company.board.Board;

import java.util.LinkedList;

public class pawn extends piece{

    public pawn(int xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }

    //need to be able to only move diagonally when an enemy is there

    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill){
        if(xp < 8 && xp>=0 && yp<8 && yp>=0){
            if(isWhite){
                if(willKill){
                    if(Board.selectedPieceOriginalYP-yp == 1 && (Board.selectedPieceOriginalXP-xp == 1 || xp-Board.selectedPieceOriginalXP == 1)){
                        return true;
                    }
                }
                else{
                    if(firstMove){
                        if(Board.selectedPieceOriginalYP-yp == 2){
                            return true;
                        }
                    }
                    if(Board.selectedPieceOriginalYP-yp == 1){
                        return true;
                    }
                }
            }
            else{
                if(willKill){
                    if(yp-Board.selectedPieceOriginalYP == 1 && (Board.selectedPieceOriginalXP-xp == 1 || xp-Board.selectedPieceOriginalXP == 1)){
                        return true;
                    }
                }
                else{
                    if(firstMove){
                        if(yp-Board.selectedPieceOriginalYP == 2){
                            return true;
                        }
                    }
                    if(yp-Board.selectedPieceOriginalYP == 1){
                        return true;
                    }
                }
            }

        } return false;
    }
}

