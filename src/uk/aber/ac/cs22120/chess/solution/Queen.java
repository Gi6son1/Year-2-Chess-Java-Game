package uk.aber.ac.cs22120.chess.solution;

public class Queen extends Piece {
    public Queen(int x,int y,WhiteOrBlack whiteOrBlack) {
        this.setRank(Rank.QUEEN);
        this.setPos(x,y);
        this.setWhiteOrBlack(whiteOrBlack);

    }
    @Override
    public  boolean[][] generateMoves(Board board){
        int[][] vectors = {{1,1},{1,-1},{-1,1},{-1,-1},{0,1},{1,0},{-1,0},{0,-1}};
        return super.generateMovesMany(this,board,vectors);
    }
}