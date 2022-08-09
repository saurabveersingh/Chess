package pieces;

import board.Board;

import javax.swing.*;

public abstract class Piece extends JButton{
    Board board;
    byte coordinate;
    boolean isWhite;
    boolean isKing;
    boolean isPawn;

    Piece(final byte coordinate, final boolean isWhite, Board board) {
        this.board = board;
        this.isWhite = isWhite;
        isKing=false;
        isPawn=false;
        setCoordinate(coordinate);
        setBackground(board.TILE_COLOR(coordinate) ? board.getWHITE() : board.getBLACK());

    }



    public void setCoordinate(final byte coordinate) {
        this.coordinate = coordinate;
        byte boxSize = board.BOX_SIZE();
        setBounds(boxSize * (coordinate % 8), boxSize * (coordinate / 8), boxSize, boxSize);
    }

    public int getCoordinate() {
        return coordinate;
    }

    public boolean getIsWhite() {
        return isWhite;
    }

    public boolean getIsKing() {
        return isKing;
    }

    public boolean getIsPawn() {return isPawn;}

    protected boolean inBounds(byte coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }

    public boolean inColumn(byte column, byte coordinate) {
        return coordinate % 8 == column - 1;
    }

    public boolean inRow(byte row, byte coordinate) {
        return coordinate / 8 == row - 1;
    }

    public abstract void showMoves();
}
