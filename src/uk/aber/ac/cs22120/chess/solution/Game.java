package uk.aber.ac.cs22120.chess.solution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Game {
    //jfx object
    Board board;
    Piece SelectedPiece;
    WhiteOrBlack currentTurn;
    Pfile pfile;
    Replay replay;

    /**
     * saves the current game as a replay meant for when games are over
     * @param newfilename
     */
    public void saveReplay(String newfilename){
        try {
            String[] movesMade = pfile.getContents();
            File file = new File(newfilename);
            FileWriter writer = new FileWriter(file,true);
            StringBuilder towrite = new StringBuilder();
            for (String moves:movesMade) {
                towrite.append(moves);
            }
            writer.append(towrite);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *initialise a new game from scratch
     */
    public void newGame(){
        this.board = new Board();
        this.currentTurn = WhiteOrBlack.WHITE;
        this.pfile = new Pfile();
    }

    /**
     * opens an existing game
     * @param filename the filename of the pfile
     */
    public void continueGame(String filename){
        this.pfile = new Pfile();
        board = pfile.getfileboard(filename);
        currentTurn= pfile.getCurrentturn();
    }

    /**
     * gets the board
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     * runs all the logic for a turn (to be used by the ui program)
     * @param startx start x cordinate of the piece
     * @param starty start y cordinate of the piece
     * @param endx end x cordinate of the piece
     * @param endy end y cordinate of the piece
     */
    public void makeMove(int startx,int starty,int endx,int endy){
        if(startx >8 || starty >8 || endx>8 || endy >8||startx <0 || starty <0|| endx<0 || endy <0) {
        }else{
            if(board.getPiece(startx,starty).generateMoves(board)[endy][endx]){
                board.movePiece(startx, starty, endx, endy);
                pfile.saveMove(startx, starty, endx, endy);
                }
            }
    }

    /**
     * code to be run in the occasion of a pawn being promoted
     * @param startx start x cordinate of the piece
     * @param starty start y cordinate of the piece
     * @param endx end x cordinate of the piece
     * @param endy end y cordinate of the piece
     * @param rank the type of piece
     */
    public void promote(int startx,int starty,int endx,int endy,Rank rank){
        WhiteOrBlack whiteOrBlack = board.getPiece(startx,starty).getWhiteOrBlack();
        board.movePiece(startx,starty,endx,endy);
        board.setPiece(rank,endx,endy,whiteOrBlack);
        pfile.saveMove(startx,starty,endx,endy,rank);
    }
}