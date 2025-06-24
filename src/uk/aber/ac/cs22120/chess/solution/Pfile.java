package uk.aber.ac.cs22120.chess.solution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pfile {
    private WhiteOrBlack currentturn;
    public Pfile(){
        String defultfilename = "pFile";
        int itter=1;
        File file = new File(defultfilename + ".csv");
        while(file.isFile()) {
                defultfilename = defultfilename + itter;
                itter++;
                file = new File(defultfilename + ".csv");
        }
        pFilename = defultfilename+".csv";
    }
    public String pFilename;

    /**
     * gets the final state of the board after all the actions within the file for the defult file name
     * @return the final state of the board after all the actions within the file
     */
    public Board getfileboard() {
        return getfileboard(pFilename);
    }

    /**
     * gets whose turn it is after the operation of getfileboard
     * @return white or black
     */
    public WhiteOrBlack getCurrentturn() {
        return currentturn;
    }
    /**
     * gets the final state of the board after all the actions within the file
     * @return the final state of the board after all the actions within the file
     */
    public Board getfileboard(String filename) {
        pFilename = filename;
        String[] filecontence =  getContents(filename);
        Board board = new Board();
        for (String i : filecontence) {
            int startx = Integer.valueOf(i.substring(0, 1));
            int starty = Integer.valueOf(i.substring(1, 2));
            int endx = Integer.valueOf(i.substring(2, 3));
            int endy = Integer.valueOf(i.substring(3, 4));
            WhiteOrBlack whiteOrBlack= board.getPiece(startx,starty).getWhiteOrBlack();
            board.movePiece(startx, starty, endx, endy);
            if (i.length() != 4) {
                switch (i.substring(4, 5)) {
                    case "K" -> board.setPiece(Rank.KING,endx,endy,whiteOrBlack);
                    case "R" -> board.setPiece(Rank.ROOK,endx,endy,whiteOrBlack);
                    case "P" -> board.setPiece(Rank.PAWN,endx,endy,whiteOrBlack);
                    case "N" -> board.setPiece(Rank.KNIGHT,endx,endy,whiteOrBlack);
                    case "Q" -> board.setPiece(Rank.QUEEN,endx,endy,whiteOrBlack);
                    case "B" -> board.setPiece(Rank.BISHOP,endx,endy,whiteOrBlack);
                }
            }
        }
        if (filecontence.length % 2 == 0) {
            currentturn = WhiteOrBlack.BLACK;
        } else {
            currentturn = WhiteOrBlack.WHITE;
        }
        return board;
    }

    /**
     * gets the file contents
     * @return string array of all the moves made
     */
    public String[] getContents() {
        return getContents(pFilename);
    }
    public String[] getContents(String filename) {
        this.pFilename = filename;
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
     * savesd the move made with the perams bellow
     * @param startx start x cordinate of the piece
     * @param starty start y cordinate of the piece
     * @param endx end x cordinate of the piece
     * @param endy end y cordinate of the piece
     */
    public void saveMove(int startx, int starty, int endx, int endy) {
        try {
            String toappend;
            File file = new File(pFilename);
            file.createNewFile();
            FileWriter writer = new FileWriter(file,true);
            if (file.length()==0) {
                toappend = "" + startx + starty + endx + endy;
                writer.append(toappend);
                writer.close();
            }else {
                toappend = "," + startx + starty + endx + endy;
                writer.append(toappend);
                writer.close();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * saves a promotion
     * @param startx start x cordinate of the piece
     * @param starty start y cordinate of the piece
     * @param endx end x cordinate of the piece
     * @param endy end y cordinate of the piece
     * @param rank the type of piece
     */
    public void saveMove(int startx, int starty, int endx, int endy,Rank rank){
        String rankid = null;
        switch (rank) {
            case ROOK -> rankid= "R";
            case PAWN -> rankid = "P";
            case BISHOP -> rankid = "B";
            case KING -> rankid = "K";
            case KNIGHT -> rankid = "N";
            case QUEEN -> rankid = "Q";
        }
        try {
            String toappend;
            File file = new File(pFilename);
            file.createNewFile();
            FileWriter writer = new FileWriter(file,true);
            if (file.length()==0) {
                toappend = "" + startx + starty + endx + endy+rankid;
                writer.append(toappend);
                writer.close();
            }else {
                toappend = "," + startx + starty + endx + endy;
                writer.append(toappend);
                writer.close();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}