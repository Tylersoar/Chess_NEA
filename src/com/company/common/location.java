package com.company.common;

import java.util.Objects;
import javax.swing.JLabel;
import com.company.pieces.piece;

public class location {
    private final file column;
    private final Integer row;
    //private file column;
    //private Integer row;
    private JLabel picture = null;
    private piece Pieces = null;



    public location( file File, Integer rank) {
        column = File;
        this.row = rank;
    }


    public file getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        location location = (location) o;
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

    public piece getPieces() {
        return Pieces;
    }

    public void setPieces(piece pieces) {
        Pieces = pieces;
    }
}
