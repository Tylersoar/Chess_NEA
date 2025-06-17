package com.company.pieces;

import com.company.board.Board;

import java.util.LinkedList;

public class King extends piece {

    public King(int xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }

    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill) {
        if (xp < 8 && xp >= 0 && yp < 8 && yp >= 0) {
            // Check if the move is within one square of the king
            for (int i = Board.selectedPieceOriginalXP - 1; i <= Board.selectedPieceOriginalXP + 1; i++) {
                for (int j = Board.selectedPieceOriginalYP - 1; j <= Board.selectedPieceOriginalYP + 1; j++) {
                    if (xp == i && yp == j) {
                        // Don't allow king to move into check
                        if (wouldBeInCheck(xp, yp, isWhite)) {
                            return false;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Check if the king would be in check at a given position
    public boolean wouldBeInCheck(int xp, int yp, boolean isWhite) {
        // Temporarily store original position
        int originalX = this.column;
        int originalY = this.row;

        // Temporarily move king to new position
        this.column = xp;
        this.row = yp;

        boolean inCheck = false;

        // Check if any enemy piece can attack this position
        for (piece p : Board.ps) {
            if (p != this && p.isWhite != isWhite) {
                if (p.validMove(xp, yp, p.isWhite, true)) {
                    inCheck = true;
                    break;
                }
            }
        }

        // Restore original position
        this.column = originalX;
        this.row = originalY;

        return inCheck;
    }

    // Check if the king is currently in check
    public boolean isInCheck() {
        for (piece p : Board.ps) {
            if (p != this && p.isWhite != this.isWhite) {
                if (p.validMove(this.column, this.row, p.isWhite, true)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Check if the king is in checkmate
    public boolean isInCheckmate() {
        if (!isInCheck()) {
            return false; // Not in check, so can't be checkmate
        }

        // Try all possible moves for all pieces of the same color
        for (piece p : Board.ps) {
            if (p.isWhite == this.isWhite) {
                // Try all possible squares
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        if (canMoveToEscapeCheck(p, x, y)) {
                            return false; // Found a valid move, not checkmate
                        }
                    }
                }
            }
        }
        return true; // No valid moves found, it's checkmate
    }

    // Helper method to check if a piece can move to escape check
    private boolean canMoveToEscapeCheck(piece p, int newX, int newY) {
        // Store original positions
        int originalX = p.column;
        int originalY = p.row;
        piece capturedPiece = Board.getPieceC(newX, newY);

        // Check if the move is valid for this piece
        boolean willKill = capturedPiece != null && capturedPiece.isWhite != p.isWhite;
        boolean canMove = capturedPiece == null || willKill;

        if (!canMove || !p.validMove(newX, newY, p.isWhite, willKill)) {
            return false;
        }

        // Temporarily make the move
        if (capturedPiece != null) {
            Board.ps.remove(capturedPiece);
        }
        p.column = newX;
        p.row = newY;

        // Check if king is still in check after this move
        boolean stillInCheck = this.isInCheck();

        // Restore the board state
        p.column = originalX;
        p.row = originalY;
        if (capturedPiece != null) {
            Board.ps.add(capturedPiece);
        }

        return !stillInCheck;
    }

    // Check if the game is in stalemate
    public boolean isInStalemate() {
        if (isInCheck()) {
            return false; // In check, so can't be stalemate
        }

        // Check if any piece of the same color has a valid move
        for (piece p : Board.ps) {
            if (p.isWhite == this.isWhite) {
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        if (canMoveToEscapeCheck(p, x, y)) {
                            return false; // Found a valid move, not stalemate
                        }
                    }
                }
            }
        }
        return true; // No valid moves and not in check = stalemate
    }
}