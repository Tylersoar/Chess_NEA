package com.company.pieces;

import com.company.board.Board;

import java.util.LinkedList;

public class King extends Piece {

    public King(int xp, int yp, boolean isWhite, String n, LinkedList<Piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }

    /**
     * King can move 1 square in any direction BUT cannot move onto a square
     * that is attacked by an opponent piece (i.e., cannot move into check).
     */
    @Override
    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill) {
        // Board bounds
        if (xp < 0 || xp >= 8 || yp < 0 || yp >= 8) {
            return false;
        }

        // Must be at most 1 square away from original king position
        int fromX = Board.selectedPieceOriginalXP;
        int fromY = Board.selectedPieceOriginalYP;

        int dx = Math.abs(xp - fromX);
        int dy = Math.abs(yp - fromY);
        if (dx > 1 || dy > 1 || (dx == 0 && dy == 0)) {
            return false;
        }

        // Cannot capture your own piece
        Piece target = Board.getPieceC(xp, yp);
        if (target != null && target.isWhite == this.isWhite) {
            return false;
        }

        // Cannot move into check
        return !wouldSquareBeAttackedAfterMove(xp, yp, target);
    }

    /**
     * Simulate the king moving to (toX,toY) (and capturing target if present)
     * then check whether any enemy piece attacks that square.
     */
    private boolean wouldSquareBeAttackedAfterMove(int toX, int toY, Piece capturedTarget) {
        // Save current state
        int oldCol = this.column;
        int oldRow = this.row;
        int oldX = this.x;
        int oldY = this.y;

        int savedSelX = Board.selectedPieceOriginalXP;
        int savedSelY = Board.selectedPieceOriginalYP;

        boolean removed = false;

        try {
            // Apply simulated move
            if (capturedTarget != null) {
                removed = Board.ps.remove(capturedTarget);
            }

            this.column = toX;
            this.row = toY;
            this.x = toX * 64;
            this.y = toY * 64;

            // Check attacks from all enemy pieces
            for (Piece p : Board.ps) {
                if (p == null) continue;
                if (p.isWhite == this.isWhite) continue; // only enemies

                // Set origin for that piece because your validMove() methods depend on these
                Board.selectedPieceOriginalXP = p.column;
                Board.selectedPieceOriginalYP = p.row;

                boolean willKill = "pawn".equals(p.name); // pawns only attack diagonally when willKill==true

                if (p.validMove(toX, toY, p.isWhite, willKill)) {
                    return true; // attacked
                }
            }

            return false;

        } finally {
            // Restore state no matter what
            if (removed) {
                Board.ps.add(capturedTarget);
            }
            this.column = oldCol;
            this.row = oldRow;
            this.x = oldX;
            this.y = oldY;

            Board.selectedPieceOriginalXP = savedSelX;
            Board.selectedPieceOriginalYP = savedSelY;
        }
    }
}
