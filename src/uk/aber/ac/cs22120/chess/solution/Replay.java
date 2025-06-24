package uk.aber.ac.cs22120.chess.solution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Replay {
    int position = 0;
    ArrayList<Piece[][]> boardstates= new ArrayList<>();

    /**
     * gets the board at every move in the game and saves them to an arraylist
     * @param filename the filename of the file in question
     */
    public void getfileboards(String filename) {
        String[] filecontence =  getContents(filename);
        Board board = new Board();
        for (String i : filecontence) {
            int startx = Integer.valueOf(i.substring(0, 1));
            int starty = Integer.valueOf(i.substring(1, 2));
            int endx = Integer.valueOf(i.substring(2, 3));
            int endy = Integer.valueOf(i.substring(3, 4)); //gets the first 4 chars as ints
            WhiteOrBlack whiteOrBlack = board.getPiece(startx,starty).getWhiteOrBlack();
            board.movePiece(startx, starty, endx, endy);
            if (i.length() != 4) { //if theres move that 4 chars get the fifth letter since its a promotion
                switch (i.substring(4, 5)) {
                    case "K" -> board.setPiece(Rank.KING, endx, endy, whiteOrBlack);
                    case "R" -> board.setPiece(Rank.ROOK, endx, endy, whiteOrBlack);
                    case "P" -> board.setPiece(Rank.PAWN, endx, endy, whiteOrBlack);
                    case "N" -> board.setPiece(Rank.KNIGHT, endx, endy, whiteOrBlack);
                    case "Q" -> board.setPiece(Rank.QUEEN, endx, endy, whiteOrBlack);
                    case "B" -> board.setPiece(Rank.BISHOP, endx, endy, whiteOrBlack);
                }
            }
            boardstates.add(board.getBoardCopy());
        }
    }

    /**
     * gets the contence as an array of moves that are made
     * @param filename
     * @return
     */
    public String[] getContents(String filename) {
        try {
            File file = new File(filename);
            file.createNewFile();
            Scanner scanner = new Scanner(file);
            List<String> filecontence = new ArrayList<>();
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                filecontence.add(scanner.next());
            }
            return filecontence.toArray(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * gets the current board state
     * @return returns array of pieces that correspond to the current turn
     */
    public Piece[][] getBoardstate() {
        return boardstates.get(position);
    }

    /**
     * gets the next turns board
     * @return returns array of pieces that correspond to the next turn
     */
    public Piece[][] getNext(){
        if (position!= getBoardstate().length-1){
            position++;
            return boardstates.get(position);
        }
        return boardstates.get(position);
    }
    /**
     * gets the next turns board
     * @return returns array of pieces that correspond to the previous turn
     */
    public Piece[][] getPrevious(){
        if (position!=0){
            position--;
            return boardstates.get(position);
        }
        return boardstates.get(position);
    }
}
