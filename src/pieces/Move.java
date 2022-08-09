package pieces;

import board.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Move extends JButton {
    private byte from;
    private byte to;
    private Board board;
    protected Move(final byte from,final byte to,final Board board){
        this.from=from;
        this.to=to;
        this.board=board;
        byte boxSize=board.BOX_SIZE();
        setBounds(boxSize*(to%8),boxSize*(to/8), boxSize,boxSize);
        setBackground(board.TILE_COLOR(to)?board.getWHITE():board.getBLACK());
        setIcon(new ImageIcon("images/dot.png"));
    }

    public byte getFrom() {
        return from;
    }

    public byte getTo() {
        return to;
    }
}
