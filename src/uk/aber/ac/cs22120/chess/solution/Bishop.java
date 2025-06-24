package uk.aber.ac.cs22120.chess.solution;

public class Bishop extends Piece {
    public Bishop(int x,int y,WhiteOrBlack whiteOrBlack) {
        this.setRank(Rank.BISHOP);
        this.setPos(x,y);
        this.setWhiteOrBlack(whiteOrBlack);
    }
    @Override
    public  boolean[][] generateMoves(Board board){
        int[][] vectors = {{1,1},{1,-1},{-1,1},{-1,-1}};
        return super.generateMovesMany(this,board,vectors);
    }
}