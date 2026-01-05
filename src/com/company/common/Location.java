package com.company.common;

import java.util.Objects;
import javax.swing.JLabel;
import com.company.pieces.Piece;

public class Location {
    private final int column;
    private final Integer row;
    private JLabel picture = null;
    private Piece Pieces = null;



    public Location(Integer File, Integer rank) {
        this.column = File;
        this.row = rank;
    }


    public Integer getColumn() {
        return column;
    } //returns column for the location of a piece

    public Integer getRow() {
        return row;
    } //returns row for the location of a piece

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return column == location.column && Objects.equals(row, location.row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    public JLabel getPicture() {
        return picture;
    }

    public void setPicture(JLabel picture) {
        this.picture = picture;
    }

    public Piece getPieces() {
        return Pieces;
    }

    public void setPieces(Piece pieces) {
        Pieces = pieces;
    }
}
