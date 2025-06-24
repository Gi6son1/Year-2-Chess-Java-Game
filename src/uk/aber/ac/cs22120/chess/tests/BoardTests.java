package uk.aber.ac.cs22120.chess.tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.aber.ac.cs22120.chess.solution.*;


public class BoardTests {

    @Test
    public void testNewBoard(){
        Piece[][] testArray = new Piece[8][8];
        //manually create a board of pieces
        //for every single item in that board, make sure that they are equal
        for (int i = 0; i<8; i++){
            testArray[1][i]= new Pawn(i,1, WhiteOrBlack.BLACK);
            testArray[6][i]= new Pawn(i,6,WhiteOrBlack.WHITE);
        }
        testArray[0][0]= new Rook(0, 0, WhiteOrBlack.BLACK);
        testArray[0][7]= new Rook(7, 0, WhiteOrBlack.BLACK);
        testArray[0][1]= new Knight(1, 0, WhiteOrBlack.BLACK);
        testArray[0][6]= new Knight(6, 0, WhiteOrBlack.BLACK);
        testArray[0][2]= new Bishop(2, 0, WhiteOrBlack.BLACK);
        testArray[0][5]= new Bishop(5, 0, WhiteOrBlack.BLACK);
        testArray[0][3]= new Queen(3, 0, WhiteOrBlack.BLACK);
        testArray[0][4]= new King(4, 0, WhiteOrBlack.BLACK);

        testArray[7][0]= new Rook(0, 7, WhiteOrBlack.WHITE);
        testArray[7][7]= new Rook(7, 7, WhiteOrBlack.WHITE);
        testArray[7][1]= new Knight(1, 7, WhiteOrBlack.WHITE);
        testArray[7][6]= new Knight(6, 7, WhiteOrBlack.WHITE);
        testArray[7][2]= new Bishop(2, 7, WhiteOrBlack.WHITE);
        testArray[7][5]= new Bishop(5, 7, WhiteOrBlack.WHITE);
        testArray[7][3]= new Queen(3, 7, WhiteOrBlack.WHITE);
        testArray[7][4]= new King(4, 7, WhiteOrBlack.WHITE);

        Board controlBoard = new Board(testArray);
        Board testBoard = new Board();

        Assertions.assertArrayEquals(controlBoard.getBoard(), testBoard.getBoard());

    }
}
