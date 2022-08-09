package pieces;

import board.Board;

import javax.swing.*;
import java.util.LinkedList;

public class King extends Piece{
    public King(final byte coordinate,final boolean isWhite,final Board board){
        super(coordinate,isWhite, board);
        isKing=true;
        setIcon(new ImageIcon(isWhite?"images/WhiteKing.png":"images/BlackKing.png"));
    }

    private final byte[] CANDIDATE_MOVES = {-7,-8,-9,-1,1,7,8,9};
    @Override
    public void showMoves() {
        LinkedList<Move> moves=new LinkedList<>();
        for(byte currentCandidate: CANDIDATE_MOVES){
            if(isFirstColumnExclusion(currentCandidate) || isEightColumnExclusion(currentCandidate))
                continue;
            byte targetSpot=(byte) (coordinate+currentCandidate);
            if(inBounds(targetSpot)&&(!board.isOccupied(targetSpot)||(board.getPiece(targetSpot).getIsWhite()!=this.isWhite)))
                moves.add(new Move(coordinate,targetSpot,board));
        }
        board.setMoves(moves);
    }

    private boolean isFirstColumnExclusion(byte currentCandidate){
        return inColumn((byte)1,coordinate) &&
                (currentCandidate== -9 || currentCandidate== -1 || currentCandidate== 7);
    }

    private boolean isEightColumnExclusion(byte currentCandidate) {
        return inColumn((byte) 8,coordinate) &&
                (currentCandidate== -7 ||currentCandidate== 1 || currentCandidate== 9);
    }
}
