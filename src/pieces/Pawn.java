package pieces;

import board.Board;

import javax.swing.*;
import java.util.LinkedList;

public class Pawn extends Piece{
    public Pawn(final byte coordinate,final boolean isWhite,final Board board){
        super(coordinate,isWhite, board);
        setIcon(new ImageIcon(isWhite?"images/WhitePawn.png":"images/BlackPawn.png"));
        isPawn=true;
    }
    private final byte[] CANDIDATE_MOVES = {7,8,9};
    @Override
    public void showMoves() {
        LinkedList<Move> moves=new LinkedList<>();
        for(byte currentCandidate: CANDIDATE_MOVES){
            byte targetSpot=(byte) (coordinate+(currentCandidate*(isWhite?1:-1)));
            if(!inBounds(targetSpot))
                continue;
            if(currentCandidate==8&&!board.isOccupied(targetSpot))
            {
                moves.add(new Move(coordinate,targetSpot,board));
                if(isWhite&&inRow((byte)2, coordinate)
                        ||!isWhite&&inRow((byte)7,coordinate))
                {
                    targetSpot=(byte) (targetSpot+8*(this.isWhite?1:-1));
                    if(!board.isOccupied(targetSpot))
                        moves.add(new Move(coordinate,targetSpot,board));
                }
            }
            if(board.isOccupied(targetSpot)
                    &&board.getPiece(targetSpot).isWhite!=this.isWhite
                    &&(currentCandidate==7
                    &&!(inColumn((byte) 1, coordinate)&&!isWhite)
                    &&!(inColumn((byte)8, coordinate)&&isWhite)
                    ||(currentCandidate==9
                    &&!(inColumn((byte)1, coordinate)&&isWhite)
                    &&!(inColumn((byte)8, coordinate)&&!isWhite))))
                moves.add(new Move(coordinate,targetSpot,board));
        }
        board.setMoves(moves);
    }
}
