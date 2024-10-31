package Puzzel15;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class GameMain extends JFrame {

    JPanel masterPanel = new JPanel();
    static JPanel gameBoard = new JPanel();
    JPanel topPanel = new JPanel();

    JButton startButton = new JButton("Start");
    JButton resetButton = new JButton("Reset");

    //Add how many bricks u want
    static Brick[] bricks = new Brick[16];
    static int emptyRow = 0;
    static int emptyCol = 0;

    public GameMain() {
        add(masterPanel);
        masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
        masterPanel.add(topPanel);
        masterPanel.add(gameBoard);

        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(startButton);
        topPanel.add(resetButton);
        startButton.setPreferredSize(new Dimension(200,80));
        resetButton.setPreferredSize(new Dimension(200,80));
        masterPanel.setBackground(Color.LIGHT_GRAY);
        masterPanel.setMaximumSize(new Dimension(550,650));
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setMaximumSize(new Dimension(550,100));
        gameBoard.setBackground(Color.PINK);
        gameBoard.setMaximumSize(new Dimension(500,500));
        gameBoard.setLayout(new GridLayout(4,4));


        // Example numbers for each brick, the randomizes them
        java.util.List<Integer> numbers = new ArrayList<>(java.util.List.of( 1, 2, 3,4,5,6,7,8,9,10,11,12,13,14,15,0));
        // Collections.shuffle(numbers);


        //Loops for amont of bricks and creates new objects
        for (int i = 0; i < 16; i++) {
            int row = i / 4;       //Calculates the row
            int col = i % 4;       //Calculates the col
            int number = numbers.get(i);   //Gets a number from the randomised list

            JButton button = new JButton(String.valueOf(number));
            button.setPreferredSize(new Dimension(10,10));
            gameBoard.add(button);
            bricks[i] = new Brick(row, col, number, button);

            if (number == 0){  //Empty tile
                button.setBackground(Color.LIGHT_GRAY);
                emptyRow = row;
                emptyCol = col;
                button.setText("");
                System.out.println("row: " + row + " col: " + col);
            }else {
                button.setBackground(Color.PINK);
            }
            button.addActionListener(new MyActionListener(bricks[i]));

        }



        setVisible(true);
        setSize(550,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    //Inner Class
    static class MyActionListener  implements ActionListener {
        private Brick brick;


        public MyActionListener(Brick brick) {
            this.brick = brick;

        }
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Button pressed");
// Check if the clicked brick is adjacent to the empty spot
            if (brick.isMoveable(emptyRow, emptyCol)) {
                // Index of empty and clicked bricks in the array
                int emptyIndex = emptyRow * 4 + emptyCol;
                int clickedIndex = brick.row * 4 + brick.col;

                // Update the empty button with the clicked brick's properties
                bricks[emptyIndex].button.setText(brick.button.getText());
                bricks[emptyIndex].button.setBackground(Color.PINK);

                // Clear the clicked button (new empty spot) visually
                brick.button.setText("");
                brick.button.setBackground(Color.LIGHT_GRAY);


                // Update rows and columns of the bricks in the array
                bricks[clickedIndex].row = brick.row;
                bricks[clickedIndex].col = brick.col;
                bricks[emptyIndex].row = emptyRow;
                bricks[emptyIndex].col = emptyCol;

                // Update the emptyRow and emptyCol to the new empty spot location
                emptyRow = brick.row;
                emptyCol = brick.col;

                // Repaint to ensure UI is updated correctly
                gameBoard.revalidate();
                gameBoard.repaint();
            }

            java.util.List<Integer> numbersInOrder = new ArrayList<>(List.of(0, 1, 2, 3,4,5,6,7,8,9,10,11,12,13,14,15));

            boolean won = true;
            for (int i = 1; i < 16; i++) {
                String text = bricks[i].button.getText();
                int brickNumber = Integer.parseInt(text);
                if (brickNumber != i) { // Check if current position matches the order
                    won = false;
                    break; // Stop checking further, as the sequence is incorrect
                }
            }

            if (won) {
                System.out.println("You won");
            }


        }
    }


    public static void main(String[] args) {
        GameMain game = new GameMain();
    }
}

