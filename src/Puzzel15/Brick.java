package Puzzel15;

import javax.swing.*;
import java.awt.*;

public class Brick extends JFrame {

    int row;
    int col;
    int number;
    JButton button;

    public Brick(int row, int col, int number, JButton button) {
        this.row = row;
        this.col = col;
        this.number = number;
        this.button = button;
    }
    public boolean isMoveable(int emptyRow, int emptyCol) {

        int rowMinusOne = Math.abs(emptyRow - row);
        int colMinusOne = Math.abs(emptyCol - col);
        return (rowMinusOne == 1 && colMinusOne == 0 || rowMinusOne == 0 && colMinusOne == 1);

    }





    public static void EmptySpot( Brick[] bricks,int emptySpot) {

        int emptyRow = bricks[emptySpot].row;
        int emptyCol = bricks[emptySpot].col;

        //Check Top
        try {
            int topRow = (bricks[emptySpot].row) -1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        //Check Bottom

        //Check Right

        //Check Left


    }
    static public void Pressable(JButton button) {

        button.setBackground(Color.BLUE);
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }
}
