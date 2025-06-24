package uk.aber.ac.cs22120.chess.solution;

import java.util.Arrays;
public class Board {
    Piece[][] board;

    /**
     * Constructor for board class with a custom board state
     * @param board This is the board state that we want it to start with its an array of piece objects
     */
    public Board(Piece[][] board) {
        this.board = board;
    }

    /**
     * constructor for board class makes a default board state
     */
    public Board() {
        board= new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            board[1][i]= new Pawn(i,1,WhiteOrBlack.BLACK);
            board[6][i]= new Pawn(i,6,WhiteOrBlack.WHITE);
        }
        board[0]= new Piece[]{new Rook(0, 0, WhiteOrBlack.BLACK), new Knight(1, 0, WhiteOrBlack.BLACK),new Bishop(2, 0, WhiteOrBlack.BLACK),new Queen(3, 0, WhiteOrBlack.BLACK), new King(4, 0, WhiteOrBlack.BLACK),new Bishop(5, 0, WhiteOrBlack.BLACK), new Knight(6, 0, WhiteOrBlack.BLACK),new Rook(7, 0, WhiteOrBlack.BLACK)};
        board[7]= new Piece[]{new Rook(0, 7, WhiteOrBlack.WHITE), new Knight(1, 7, WhiteOrBlack.WHITE),new Bishop(2, 7, WhiteOrBlack.WHITE),new Queen(3, 7, WhiteOrBlack.WHITE), new King(4, 7, WhiteOrBlack.WHITE),new Bishop(5, 7, WhiteOrBlack.WHITE), new Knight(6, 7, WhiteOrBlack.WHITE),new Rook(7, 7, WhiteOrBlack.WHITE)};
    }

    /**
     * sets a specific square on the board to be a new piece
     * @param rank the type of piece
     * @param x x coordinate that the new piece will be at
     * @param y y coordinate that the new piece will be at
     * @param whiteOrBlack the colour of the piece
     */
    public void setPiece(Rank rank,int x,int y,WhiteOrBlack whiteOrBlack) {
        switch (rank) {
            case ROOK -> board[y][x] = new Rook(x, y, whiteOrBlack);
            case PAWN -> board[y][x] = new Pawn(x, y, whiteOrBlack);
            case BISHOP -> board[y][x] = new Bishop(x, y, whiteOrBlack);
            case KING -> board[y][x] = new King(x, y, whiteOrBlack);
            case KNIGHT -> board[y][x] = new Knight(x, y, whiteOrBlack);
            case QUEEN -> board[y][x] = new Queen(x, y, whiteOrBlack);
            default -> throw new IllegalStateException("Unexpected value: " + rank);
        }
    }

    /**
     * getter for a piece at a certain coordinate
     * @param x x coordinate
     * @param y y coordinate
     * @return
     */
    public Piece getPiece(int x, int y) {
        return  board[y][x];
    }

    /**
     * getter for the 2d array of pieces
     * @return board as a 2d array of pieces
     */
    public Piece[][] getBoard() {
        return board;
    }

    /**
     * sets the board to be a certain array of pieces
     * @param board 2d array of pieces
     */
    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    /**
     * code to move piece at cordiante startx,starty to endx,endy
     * @param startx start x cordinate of the piece
     * @param starty start y cordinate of the piece
     * @param endx end x cordinate of the piece
     * @param endy end y cordinate of the piece
     */
    public void movePiece(int startx,int starty,int endx,int endy){
        if(startx >8 || starty >8 || endx>8 || endy >8||startx <0 || starty <0|| endx<0 || endy <0){
            return;
        }
        if(board[starty][startx] ==null){
            return;
        }
        if(!this.getPiece(startx,starty).generateMoves(this)[endy][endx]){
            return;
        }
        switch (board[starty][startx].getRank()) {
            case KING -> {
                if (startx - endx == 2) { //if its queen side castling
                    board[endy][endx] = board[starty][startx];
                    board[endy][endx].setHasMoved(true);
                    board[endy][endx].setPos(endx, endy);
                    board[starty][startx] = null;
                    board[starty][startx - 4].setHasMoved(true);
                    board[endy][endx + 1] = board[starty][startx - 4];
                    board[starty][startx - 4] = null; //logic to get the left side rook since that rook is 4 away
                } else if (startx - endx == -2) {//if its king side castling
                    board[endy][endx] = board[starty][startx];
                    board[endy][endx].setHasMoved(true);
                    board[endy][endx].setPos(endx, endy);
                    board[starty][startx] = null;
                    board[starty][startx + 3].setHasMoved(true);
                    board[endy][endx - 1] = board[starty][startx + 3];
                    board[starty][startx + 3] = null; //logic to get the right side rook since that rook is 3 away
                } else {
                    defaultMove(startx, starty, endx, endy); // else just move it like any other piece
                }
            }
            case PAWN -> {
                int fwd;
                if (board[starty][startx].getWhiteOrBlack() == WhiteOrBlack.WHITE) { // gets the forward direction for said pawn
                    fwd = -1;
                } else {
                    fwd = 1;
                }
                if (startx != endx && board[endy][endx] == null) {//does en passant
                    board[endy][endx] = board[starty][startx];
                    board[endy][endx].setHasMoved(true);
                    board[endy][endx].setPos(endx, endy);
                    board[starty - fwd][startx] = null;
                    board[starty][startx] = null;

                } else if (endy - starty == 2 * fwd) { //does double move
                    board[endy][endx] = board[starty][startx];
                    board[endy][endx].setHasMoved(true);
                    board[endy][endx].setHasDoubleMoved(true);
                    board[endy][endx].setPos(endx, endy);
                    board[starty][startx] = null;
                } else {
                    defaultMove(startx, starty, endx, endy);//else move it like a normal piece
                }
            }
            default -> defaultMove(startx, starty, endx, endy);// normal piece movement
        }

    }

    /**
     * the defult move preformed by every piece when it is not doing a special case
     * @param startx start x cordinate of the piece
     * @param starty start y cordinate of the piece
     * @param endx end x cordinate of the piece
     * @param endy end y cordinate of the piece
     */
    private void defaultMove(int startx, int starty, int endx, int endy){
        board[endy][endx] = board[starty][startx];
        board[endy][endx].setHasMoved(true);
        board[endy][endx].setPos(endx,endy);
        board[starty][startx] = null;
    }

    /**
     * checks for a check on a certain colored king on any array of pieces (used in generate moves)
     * @param whiteOrBlack
     * @param boardCopy
     * @return
     */
    public boolean checkForcheck(WhiteOrBlack whiteOrBlack, Piece[][] boardCopy) {
        Piece king = null;
        int kingx= 0;
        int kingy = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = boardCopy[i][j];
                if (piece != null) {
                    if (piece.getRank() == Rank.KING && piece.getWhiteOrBlack() == whiteOrBlack) {
                        king = piece;
                        kingx= j;
                        kingy= i;
                        break;
                    }
                }
            }
            if (king != null) {
                break;
            }
        }
        int[][] vectors = {{0,1}, {1,0}, {-1,0}, {0,-1}};
        for (int[] vector : vectors) {
            int x = kingx;
            int y = kingy;
            boolean pass = false;
            x=x+vector[0];
            y=y+vector[1];
            Piece workingPiece;
            if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {
                workingPiece= boardCopy[y][x];

            if(workingPiece!=null){
                pass=true;
                if (workingPiece.getWhiteOrBlack()!=whiteOrBlack){
                    Rank rank = workingPiece.getRank();
                    if (rank==Rank.ROOK||rank==Rank.QUEEN||rank==Rank.KING){
                        return true;
                    }
                }
            }
            }
            while (!pass) {
                x = x + vector[0];
                y = y + vector[1];
                if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {
                workingPiece = boardCopy[y][x];
                if (workingPiece != null) {
                    pass = true;
                    if (workingPiece.getWhiteOrBlack()!=whiteOrBlack) {
                        Rank rank = workingPiece.getRank();
                        if (rank == Rank.ROOK || rank == Rank.QUEEN) {
                            return true;
                        }
                    }
                }
            }else{
                    pass=true;
                }
            }
        }
        vectors = new int[][]{{1,1},{1,-1},{-1,1},{-1,-1}};
        for (int[] vector : vectors) {
            int x = kingx;
            int y = kingy;
            boolean pass = false;
            x=x+vector[0];
            y=y+vector[1];
            Piece workingPiece;
            if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {
                workingPiece = boardCopy[y][x];
                if (workingPiece != null) {
                    pass = true;
                    if (workingPiece.getWhiteOrBlack()!=whiteOrBlack) {
                        Rank rank = workingPiece.getRank();
                        if (rank == Rank.BISHOP || rank == Rank.QUEEN || rank == Rank.KING) {
                            return true;
                        }
                        if (rank == Rank.PAWN) {
                            int fwd;
                            if (whiteOrBlack == WhiteOrBlack.WHITE) {
                                fwd = -1;
                            } else {
                                fwd = 1;
                            }
                            if (y - fwd == king.getPos()[1]) {
                                return true;
                            }
                        }
                    }
                }
            }
            while (!pass) {
                x = x + vector[0];
                y = y + vector[1];
                if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {
                    workingPiece = boardCopy[y][x];
                    if (workingPiece != null) {
                        pass = true;
                        if (workingPiece.getWhiteOrBlack()!=whiteOrBlack) {
                            Rank rank = workingPiece.getRank();
                            if (rank == Rank.BISHOP || rank == Rank.QUEEN) {
                                return true;
                            }
                        }
                    }
                }
                else{
                    pass=true;
                }
            }
        }
        return false;
    }

    /**
     * checks if the specified coulor is in checkmate
     * @param whiteOrBlack specified coulor
     * @return
     */
    public boolean checkForMate(WhiteOrBlack whiteOrBlack){
        boolean[][] falsesample = new boolean[8][8];
        for (Piece[] pieces:board) {
            for (Piece piece:pieces) {
                if (piece != null) {
                    if (piece.getWhiteOrBlack()==whiteOrBlack) {
                        if (!(Arrays.deepEquals(piece.generateMoves(this), falsesample))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * returns a deep coppy of the 2d array of pieces
     * @return
     */
    protected Piece[][] getBoardCopy(){//this function is so we get a discrete copy of board and arnt working on the original
        Piece[][] tmpBoard =new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tmpBoard[i][j]=board[i][j];
            }
        }
        return tmpBoard;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            stringBuilder.append("\n");
            stringBuilder.append("[");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    stringBuilder.append("null");
                }else {
                    stringBuilder.append(board[i][j].toString());
                }
                stringBuilder.append(",");
            }
            stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());

            stringBuilder.append("]");
        }
        return stringBuilder.toString();
    }
}