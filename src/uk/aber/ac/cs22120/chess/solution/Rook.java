package uk.aber.ac.cs22120.chess.solution;

public class Rook extends Piece {
    public Rook(int x,int y,WhiteOrBlack whiteOrBlack) {
        this.setRank(Rank.ROOK);
        this.setPos(x,y);
        this.setWhiteOrBlack(whiteOrBlack);
        this.setHasMoved(false);
    }
    @Override
    public  boolean[][] generateMoves(Board board){
        int[][] vectors = {{0,1},{1,0},{-1,0},{0,-1}};
        return super.generateMovesMany(this,board,vectors);
    }
}
