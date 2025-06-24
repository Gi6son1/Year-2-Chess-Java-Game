package uk.aber.ac.cs22120.chess.solution;

public class Knight extends Piece{
    public Knight(int x,int y,WhiteOrBlack whiteOrBlack) {
        this.setRank(Rank.KNIGHT);
        this.setPos(x,y);
        this.setWhiteOrBlack(whiteOrBlack);
    }
    public  boolean[][] generatemoves(Board board){
        boolean[][] validMoves=new boolean[8][8];
        int[][] vectors ={{-2,-1},{-1,-2},{1,-2},{2,-1},{2,1},{1,2},{-1,2},{-2,1}};
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
        return validMoves;
    }
}