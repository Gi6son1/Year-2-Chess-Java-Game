package uk.aber.ac.cs22120.chess.solution;

public class Main {
    public static void main(String[] args) {
        boarddemo();
    }
    public static void boarddemo(){
        int x ;
        int y ;
        Board board = new Board();
        StringBuilder builder = new StringBuilder();
        x=1;
        y=0;
        for (boolean[] row:board.getPiece(x,y).generateMoves(board)) {
            builder.append("[");

            for (boolean place:row) {
                if (place){
                    builder.append(true);
                    builder.append(" ");}
                else{
                    builder.append(false);
                }
                builder.append(",");
            }
            builder.delete(builder.length()-1,builder.length());
            builder.append("]");
            builder.append("\n");
        }
        System.out.println(board);
        System.out.println(builder);
    }
}