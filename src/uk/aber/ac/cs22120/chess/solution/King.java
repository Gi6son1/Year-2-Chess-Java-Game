package uk.aber.ac.cs22120.chess.solution;

import java.util.Arrays;

public class King extends Piece {
    public King(int x,int y,WhiteOrBlack whiteOrBlack) {
        this.setRank(Rank.KING);
        this.setPos(x, y);
        this.setWhiteOrBlack(whiteOrBlack);
        this.setHasMoved(false);
    }

    @Override
    public boolean[][] generateMoves(Board board) {
        boolean[][] validMoves =  new boolean[8][8];
        int[][] vectors = {{1,1},{1,-1},{-1,1},{-1,-1},{0,1},{1,0},{-1,0},{0,-1}};
        for (int[] vector : vectors) {
            int x=this.pos[0]+vector[0];
            int y=this.pos[1]+vector[1];
            if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {
                if (board.getPiece((x), (y)) == null) {
                    if (validPos(x,y,board)) {
                        validMoves[y][x] = true;
                    }
                }else {
                    if (board.getPiece((x), (y)).getWhiteOrBlack() != this.whiteOrBlack) {
                        if (validPos(x,y,board)) {
                            validMoves[y][x] = true;
                        }
                    }
                }
            }
        }
        int[] startLocBlackKing= {4,0};
        int[] startLocWhiteKing ={4,7};
        if((!this.getHasMoved())&&(Arrays.equals(this.getPos(),startLocWhiteKing)||Arrays.equals(this.getPos(),startLocBlackKing))){
            if(rightSideCastle(board)){
                validMoves[pos[1]][pos[0]+2]=true;
            }
            if(leftSideCastle(board)){
                validMoves[pos[1]][pos[0]-2]= true;
            }
        }
        return validMoves;
    }
    private boolean rightSideCastle(Board board){
        if (board.getPiece((pos[0]+3),(pos[1])) == null) {
            return false;
        }
        if(board.getPiece((pos[0]+2),(pos[1]))!=null ||board.getPiece((pos[0]+1),(pos[1]))!=null){
            return false;
        }
        if(board.getPiece((pos[0]+3),(pos[1])).getRank()==Rank.ROOK  && !board.getPiece(pos[0]+3,(pos[1])).getHasMoved()) {
            if(validPos((pos[0]+1),(pos[1]),board)){
                if(validPos((pos[0]+2),(pos[1]),board)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean leftSideCastle(Board board){
        if (board.getPiece((pos[0]-4),(pos[1])) == null) {
            return false;
        }
        if(board.getPiece((pos[0]-2),(pos[1]))!=null ||board.getPiece((pos[0]-1),(pos[1]))!=null||board.getPiece((pos[0]-3),(pos[1]))!=null){
            return false;
        }
        if(board.getPiece((pos[0]-4),(pos[1])).getRank()==Rank.ROOK  && !board.getPiece((pos[0]-4),pos[1]).getHasMoved()) {
            if(validPos((pos[0]-1),(pos[1]),board)){
                if(validPos((pos[0]-2),(pos[1]),board)){
                    return true;
                }
            }
        }
        return false;
    }
}
