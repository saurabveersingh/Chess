package board;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

public class Board extends JFrame{
    private static final byte BOX_SIZE=80;
    private static final Color WHITE=new Color(205, 185, 136);
    private static final Color BLACK=new Color(84, 65, 11);
    private static final boolean TILE_COLOR[]=new boolean[]{true,false,true,false,true,false,true,false,
                                                            false,true,false,true,false,true,false,true,
                                                            true,false,true,false,true,false,true,false,
                                                            false,true,false,true,false,true,false,true,
                                                            true,false,true,false,true,false,true,false,
                                                            false,true,false,true,false,true,false,true,
                                                            true,false,true,false,true,false,true,false,
                                                            false,true,false,true,false,true,false,true};
    private boolean turn=true;
    private static boolean[] isOccupied;
    private HashMap<Byte,Piece> pieces;
    private LinkedList<Move> moves;
    public Board(){
        isOccupied=new boolean[64];
        setSize(655,675);
        setTitle("MyChess");
        setDefaultCloseOperation(3); // same as f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(new ImageIcon("images/icon.jpg").getImage());
        JLayeredPane lp=new JLayeredPane();
        add(lp);
        setLayout(null);
        setNewGame();
        setVisible(true);
    }
    private void setNewGame(){
        pieces=new HashMap<>();
        for(byte i=0;i<64;i++)
        {
            JLabel l=new JLabel();
            l.setBackground(TILE_COLOR[i]?WHITE:BLACK);
            l.setBounds(BOX_SIZE*(i%8),BOX_SIZE*(i/8), BOX_SIZE,BOX_SIZE);
            l.setOpaque(true);
            add(l);
            if(i<16||i>=48){
                Piece p;
                if(i==0||i==7||i==56||i==63)
                    p = new Rook(i, i < 16, this);
                else if(i==1||i==6||i==57||i==62)
                    p=new Knight(i,i<16,this);
                else if(i==2||i==5||i==58||i==61)
                    p=new Bishop(i,i<16,this);
                else if(i==3||i==59)
                    p=new Queen(i,i<16,this);
                else if(i==4||i==60)
                    p=new King(i,i<16,this);
                else
                    p=new Pawn(i,i<16,this);
                isOccupied[i]=true;
                pieces.put(i,p);
                p.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(e.getSource()==p){
                            removeMoves();
                            if(turn==p.getIsWhite())
                                p.showMoves();
                        }
                    }
                });
                add(p);
            }
        }
    }
    public boolean getTurn(){
        return turn;
    }

    public Piece getPiece(byte coordinate){
        return pieces.get(coordinate);
    }
    public void setMoves(LinkedList<Move> moves) {
        removeMoves();
        this.moves=moves;
        if(moves==null) return;
        for(Move m:moves) {
            m.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource()==m){
                        movePiece(m.getFrom(),m.getTo());
                    }
                }
            });
            add(m,2);
        }
    }
    public void removeMoves(){
        if(moves==null)
            return;
        for(Move m:moves){
            m.setVisible(false);
            remove(m);
        }
    }
    public void movePiece(byte from, byte to){
        if(isOccupied(to)) {
            Piece p=pieces.get(to);
            if(p.getIsKing()) endGame();
            p.setVisible(false);
            remove(p);
        }
        Piece p=pieces.get(from);
        if(p.getIsPawn()&&((p.getIsWhite()&&p.inRow((byte) 7,from))||(!p.getIsWhite()&&p.inRow((byte) 2,from)))) {
            p.setVisible(false);
            remove(p);
            p = new Queen(from, p.getIsWhite(), this);
            add(p);
        }
        isOccupied[from]=false;
        isOccupied[to]=true;
        p.setBounds(BOX_SIZE*(to%8),BOX_SIZE*(to/8), BOX_SIZE,BOX_SIZE);
        p.setBackground(TILE_COLOR[to]?WHITE:BLACK);
        p.setCoordinate(to);
        pieces.put(to,p);
        pieces.remove(from);
        removeMoves();
        turn=!turn;
    }

    public void endGame(){
        JButton end= new JButton((turn?"White":"Black")+" Wins");
        end.setFont(new Font("Serif", Font.PLAIN, 30));
        end.setBounds(200,250,250,100);
        end.setBackground(WHITE);
        for(byte i:pieces.keySet())
            pieces.get(i).setEnabled(false);
        add(end,4);
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==end)
                    System.exit(0);
            }
        });
    }
    public static boolean isOccupied(int coordinate){
        return isOccupied[coordinate];
    }
    public static boolean TILE_COLOR(byte coordinate){ return TILE_COLOR[coordinate]; }
    public static Color getWHITE(){
        return WHITE;
    }
    public static Color getBLACK() { return BLACK; }
    public static byte BOX_SIZE() { return BOX_SIZE; }
}
