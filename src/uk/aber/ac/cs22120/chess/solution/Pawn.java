package uk.aber.ac.cs22120.chess.solution;

public class Pawn extends Piece {
    private int fwd;
    public Pawn(int x,int y,WhiteOrBlack whiteOrBlack) {
        this.setRank(Rank.PAWN);

        this.setPos(x,y);
        this.setWhiteOrBlack(whiteOrBlack);
        this.setHasMoved(false);
        this.setHasDoubleMoved(false);
        if (whiteOrBlack==WhiteOrBlack.WHITE){
            fwd =-1;
        }else{
            fwd =1;
        }
    }

    @Override
    public boolean[][] generateMoves(Board board) {
        boolean [][] validmoves= new boolean[8][8];
        int x = pos[0];
        int y = pos[1];
        if (y+fwd <=7 && y+fwd>=0){
        if (board.getPiece((x), (y+fwd)) == null){
                validmoves[y+fwd][x] = validPos(x,y+fwd,board);
        }}
        if(x+1<=7){
        if ((board.getPiece((x+1), (y+fwd)) != null) && (board.getPiece((x+1), (y+fwd)).getWhiteOrBlack() !=this.whiteOrBlack) ){
                validmoves[y+fwd][x+1] = validPos(x+1,y+fwd,board);
        }}
        if (x-1>=0){
        if (board.getPiece((x-1), (y+fwd)) != null && (board.getPiece((x-1), (y+fwd)).getWhiteOrBlack() !=this.whiteOrBlack)){
                validmoves[y+fwd][x-1] =validPos(x-1,y+fwd,board);
        }}
        if (!this.hasMoved){
            if ((board.getPiece((x), (y+fwd)) == null)&&(board.getPiece((x), (y+(2*fwd))) == null)){
                validmoves[y+(2*fwd)][x] = validPos(x,(y+(2*fwd)),board);
            }
        }
        if (enpassentR(board)){
            Piece[][] tmpBoard = (board.getBoardCopy());
            tmpBoard[y+fwd][x+1] = tmpBoard[this.pos[1]][this.pos[0]];
            tmpBoard[this.pos[1]][this.pos[0]] = null;
            tmpBoard[this.pos[1]][this.pos[0]+1]=null;
            if (!(board.checkForcheck(this.whiteOrBlack, tmpBoard))) {
                validmoves[y+fwd][x+1] = true;
            }

        }
        if (enpassentL(board)){
            Piece[][] tmpBoard = (board.getBoardCopy());
            tmpBoard[y+fwd][x-1] = tmpBoard[this.pos[1]][this.pos[0]];
            tmpBoard[this.pos[1]][this.pos[0]] = null;
            tmpBoard[this.pos[1]][this.pos[0]-1]=null;
            if (!(board.checkForcheck(this.whiteOrBlack, tmpBoard))) {
                validmoves[y+fwd][x-1] = true;
            }
        }
        return validmoves;
    }
    private boolean enpassentR(Board board) {
        if (pos[0] +1 <=7){
        if (board.getPiece((pos[0] + 1), (pos[1])) != null) {
            return (board.getPiece((pos[0] + 1), (pos[1])).getRank() == Rank.PAWN && board.getPiece((pos[0] + 1), (pos[1])).getHasDoubleMoved() == true && board.getPiece((pos[0] + 1), (pos[1])).getWhiteOrBlack() != this.whiteOrBlack);
        }else {return false;}}
        else {return false;}
    }

    private boolean enpassentL(Board board) {
        if (pos[0] - 1 >=0){
        if (board.getPiece((pos[0] - 1), (pos[1])) != null) {
            return (board.getPiece((pos[0] - 1), (pos[1])).getRank() == Rank.PAWN && board.getPiece((pos[0] - 1), (pos[1])).getHasDoubleMoved() == true && board.getPiece((pos[0] - 1), (pos[1])).getWhiteOrBlack() != this.whiteOrBlack);
        }else {return false;}}
        else {return false;}
    }
}
