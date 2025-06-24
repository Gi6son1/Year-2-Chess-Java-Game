package uk.aber.ac.cs22120.chess.solution;

import java.util.Arrays;
import java.util.Objects;

public abstract class Piece {
    protected int[] pos;
    protected WhiteOrBlack whiteOrBlack;
    protected Rank rank;
    boolean hasMoved;
    boolean hasDoubleMoved;

    /**
     * returns the moves the piece can make by defult an array of false
     * @param board
     * @return
     */
    public  boolean[][] generateMoves(Board board){
        return new boolean[8][8];
    }

    /**
     * gets if the piece has moved
     * @return
     */
    public boolean getHasMoved() {
        return hasMoved;
    }

    /**
     * sets whether the piece has moved
     * @param hasMoved
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * gets whether a piece has just double moved (used for pawns)
     * @return boolean of whether they have or not
     */
    public boolean getHasDoubleMoved() {
        return hasDoubleMoved;
    }

    /**
     * sets whether a piece has just double moved (used for pawns)
     * @param hasDoubleMoved whether they have double moved
     */
    public void setHasDoubleMoved(boolean hasDoubleMoved) {
        this.hasDoubleMoved = hasDoubleMoved;
    }

    /**
     * getter for the pieces position
     * @return
     */
    public int[] getPos() {
        return pos;
    }

    /**
     * generates whether the pieces position on the board is valid
     * @param newx where the piece is trying to get to x cordinate
     * @param newy where the piece is trying to get to y cordinate
     * @param board the board
     * @return
     */
    public boolean validPos(int newx  ,int newy,Board board){
        boolean validpos;
        Piece[][] tmpBoard = (board.getBoardCopy());
        tmpBoard[newy][newx] = tmpBoard[this.pos[1]][this.pos[0]];
        tmpBoard[this.pos[1]][this.pos[0]] = null;
        validpos = (!(board.checkForcheck(this.whiteOrBlack, tmpBoard))) ;
        return validpos;
    }

    /**
     * setter for the position useing x and y instead of an array of ints
     * @param x the x cord of the piece
     * @param y the y cord of the piece
     */
    public void setPos(int x ,int y) {
        int[] pos = {x,y};
        this.pos = pos;
    }
    public WhiteOrBlack getWhiteOrBlack() {
        return whiteOrBlack;
    }

    /**
     * setter for the piece coulor
     * @param whiteOrBlack
     */
    public void setWhiteOrBlack(WhiteOrBlack whiteOrBlack) {
        this.whiteOrBlack = whiteOrBlack;
    }

    /**
     * getter for the pieces rank
     * @return pieces type
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * sets the pieces rank
     * @param rank pieces type that we want to set it to
     */
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    /**
     * gets the moves a piece can move given a set of movement vectors (directions it can move)
     * @param piece the piece that we are referring to
     * @param board the board we are looking at
     * @param vectors the movement vectors
     * @return a 2d array of true values where the piece can move and false where it cant
     */
    public  boolean[][] generateMovesMany(Piece piece, Board board, int[][] vectors){//generic generate moves that is used by the rook bishop and queen
        boolean[][] validmoves =new boolean[8][8];
        boolean checkOnBoard=false;
        if((board.checkForcheck(piece.whiteOrBlack, board.getBoardCopy()))){
                 checkOnBoard= true;
        }
        for (int [] vector : vectors ) {
            int x = piece.pos[0];
            int y = piece.pos[1];
            boolean pass = false;// whether the loop should stop
            boolean first = true;// whether it's the first time the loop is being run
            while (!pass) {
                x = x + vector[0];
                y = y + vector[1];//get the next coordinates in that movement vector
                if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {//checking if it is withing the board
                    if (board.getPiece((x), (y)) == null) {
                        if (first) {
                            if (validPos(x,y,board)) {//if making that move wouldn't put it in check
                                validmoves[y][x] = true;
                            }
                        } else {
                            if (validmoves[y - vector[1]][x - vector[0]]) { // if the last move was true checked in that direction
                                validmoves[y][x] = true;
                            } else {
                                pass = true;
                            }
                        }
                    } else {
                        if (board.getPiece((x), (y)).getWhiteOrBlack() != piece.whiteOrBlack) { // if it is a checking a piece it could potentially take
                            if (first) {
                                if (validPos(x,y,board)) {
                                    validmoves[y][x] = true;
                                    pass = true;
                                } else {
                                    pass = true;
                                }
                            } else {
                                if (validmoves[y - vector[1]][x - vector[0]]) {
                                    validmoves[y][x] = true;
                                }
                                pass = true;
                            }
                        } else {
                            pass = true;
                        }
                    }
                } else {
                    pass = true;
                }
                if(!checkOnBoard){ //if not in check then after first move do different
                    first =false;
                }
            }
        }
        return validmoves;
    }
    @Override
    /**
     * basic to string
     */
    public String toString() {
        return Arrays.toString(pos) +
                 whiteOrBlack + rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Are they the same object?
        if (o == null || getClass() != o.getClass()) return false; // Are they the same class?
        Piece piece = (Piece) o;  // Do the cast to Piece
        if (this.getWhiteOrBlack() != piece.getWhiteOrBlack()) return false; //same colour?
        // Now just check the locations
        return Arrays.equals(getPos(), piece.getPos()); // Another way of checking equality. Also checks for nulls
    }
}