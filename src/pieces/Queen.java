package pieces;

import board.Board;

import javax.swing.*;
import java.util.LinkedList;

public class Queen extends Piece{
    public Queen(final byte coordinate,final boolean isWhite,final Board board){
        super(coordinate,isWhite, board);
        setIcon(new ImageIcon(isWhite?"images/WhiteQueen.png":"images/BlackQueen.png"));
    }

    private final byte[] CANDIDATE_MOVES = {-9,-8,-7,-1,1,7,8,9};
    @Override
    public void showMoves() {
        LinkedList<Move> moves=new LinkedList<>();
        for(byte currentCandidate: CANDIDATE_MOVES){
            byte targetSpot= coordinate;
            while(!(isFirstColumnExclusion(currentCandidate,targetSpot)||
                    isEightColumnExclusion(currentCandidate,targetSpot))){
                targetSpot+=currentCandidate;
                if(!inBounds(targetSpot))
                    break;
                if(!board.isOccupied(targetSpot)){
                    moves.add(new Move(coordinate,targetSpot,board));
                }
                else {
                    if(board.getPiece(targetSpot).getIsWhite()!=this.isWhite){
                        moves.add(new Move(coordinate,targetSpot,board));
                    }
                    break;
                }
            }
        }
        board.setMoves(moves);
    }

    private boolean isFirstColumnExclusion(byte currentCandidate,byte prevSpot){
        return inColumn((byte)1,prevSpot) &&
                (currentCandidate== -9 || currentCandidate== -1 || currentCandidate== 7);
    }

    private boolean isEightColumnExclusion(byte currentCandidate,byte prevSpot) {
        return inColumn((byte) 8,prevSpot) &&
                (currentCandidate== -7 ||currentCandidate== 1 || currentCandidate== 9);
    }
}
