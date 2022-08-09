package pieces;

import board.Board;

import javax.swing.*;
import java.util.LinkedList;

public class Knight extends Piece{
    public Knight(final byte coordinate,final boolean isWhite,final Board board){
        super(coordinate,isWhite, board);
        setIcon(new ImageIcon(isWhite?"images/WhiteKnight.png":"images/BlackKnight.png"));
    }
    private final byte[] CANDIDATE_MOVES = {-17,-15,-10,-6,6,10,15,17};
    @Override
    public void showMoves() {
        LinkedList<Move> moves=new LinkedList<>();
        for(byte currentCandidate: CANDIDATE_MOVES){
            if(isFirstColumnExclusion(currentCandidate) ||
                    isSecondColumnExclusion(currentCandidate) ||
                    isSeventhColumnExclusion(currentCandidate) ||
                    isEightColumnExclusion(currentCandidate))
                continue;
            byte targetSpot=(byte) (coordinate+currentCandidate);
            if(inBounds(targetSpot)&&(!board.isOccupied(targetSpot)||(board.getPiece(targetSpot).getIsWhite()!=this.isWhite)))
                moves.add(new Move(coordinate,targetSpot,board));
        }
        board.setMoves(moves);
    }

    private boolean isFirstColumnExclusion(byte currentCandidate){
        return inColumn((byte)1,coordinate) &&
                (currentCandidate== 6 || currentCandidate== 15 || currentCandidate== -17 || currentCandidate== -10);
    }

    private boolean isSecondColumnExclusion(int currentCandidate){
        return inColumn((byte)2, coordinate) &&
                (currentCandidate== 6 || currentCandidate== -10);
    }

    private boolean isSeventhColumnExclusion(int currentCandidate){
        return inColumn((byte) 7, coordinate) &&
                (currentCandidate== -6 || currentCandidate== 10);
    }

    private boolean isEightColumnExclusion(byte currentCandidate) {
        return inColumn((byte) 8,coordinate) &&
                (currentCandidate== -15 || currentCandidate== -6 || currentCandidate== 10 || currentCandidate== 17);
    }
}
