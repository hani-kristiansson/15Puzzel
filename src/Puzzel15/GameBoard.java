package Puzzel15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameBoard extends JFrame {

    JPanel gamePanel = new JPanel();
    JPanel menuPanel = new JPanel();

    final String[][] correctSolution =
            {{"1", "2", "3", "4"},
                    {"5", "6", "7", "8"},
                    {"9", "10", "11", "12"},
                    {"13", "14", "15", ""}};

    String[][] numbers =
            {{"1", "2", "3", "4"},
                    {"5", "6", "7", "8"},
                    {"9", "10", "11", "12"},
                    {"13", "14", "15", ""}};

    JButton[][] buttons = new JButton[4][4];

    public GameBoard() {

        this.setTitle("15 Puzzel");

        setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        gamePanel.setLayout(new GridLayout(4, 4));
        menuPanel.setLayout(new GridLayout(1, 2));

        //Game Panel Buttons
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.lightGray);
                buttons[i][j].setForeground(Color.white);
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 20));

                buttons[i][j].setText(numbers[i][j]);

                int finalI = i;
                int finalJ = j;

                buttons[i][j].addActionListener(ae -> buttonPushed(finalI, finalJ));
                //ae = ActionEvent e
                // actionPerformed(ActionEvent e) {buttonPushed(finalI,j);}

                gamePanel.add(buttons[i][j]);
            }
        }

        JButton startButton = new JButton("New game");
        startButton.setBackground(Color.blue);
        startButton.setForeground(Color.white);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(startButton);

        // click game start button to shuffle numbers
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shuffleNumbers();
            }
        });

        JButton hintButton = new JButton("Easy version");
        hintButton.setBackground(Color.black);
        hintButton.setForeground(Color.white);
        hintButton.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(hintButton);

        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shuffleNumbersEasy();
            }
        });

        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void buttonPushed(int row, int column) {
        System.out.println("Button clicked!" + row + " " + column);

        if (buttons[row][column].getText().isBlank()) {
            System.out.println("You pushed empty button");

        } else if (row - 1 >= 0 && buttons[row - 1][column].getText().isBlank()) {
            System.out.println("Above was empty, can move");
            buttons[row - 1][column].setText(buttons[row][column].getText());
            buttons[row][column].setText("");

        } else if (column - 1 >= 0 && buttons[row][column - 1].getText().isBlank()) {
            System.out.println("Left was empty, can move");
            buttons[row][column - 1].setText(buttons[row][column].getText());
            buttons[row][column].setText("");

        } else if (column + 1 <= 3 && buttons[row][column + 1].getText().isBlank()) {
            System.out.println("Right was empty, can move");
            buttons[row][column + 1].setText(buttons[row][column].getText());
            buttons[row][column].setText("");

        } else if (row + 1 <= 3 && buttons[row + 1][column].getText().isBlank()) {
            System.out.println("Below was empty, can move");
            buttons[row + 1][column].setText(buttons[row][column].getText());
            buttons[row][column].setText("");
        }
        isGameOver();
    }

    private void shuffleNumbers() {

        Random random = new Random();

        for (int i = 0; i < 200; i++) {
            int row1 = random.nextInt(4); //0 till 3
            int column1 = random.nextInt(4);
            int row2 = random.nextInt(4);
            int column2 = random.nextInt(4);

            String temp = numbers[row1][column1];
            numbers[row1][column1] = numbers[row2][column2];
            numbers[row2][column2] = temp;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setText(numbers[i][j]);
            }
        }
    }

    private void shuffleNumbersEasy() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                numbers[i][j] = correctSolution[i][j];
            }
        }

        Random random = new Random();
        //Start the shuffling from empty square
        int emptyRow = 3, emptyColumn = 3;

        for (int i = 0; i < 10; i++) {
            int direction = random.nextInt(4);

            if (direction == 0) {
                // if direction is 0 move up
                int newEmptyRow = emptyRow - 1;
                int newEmptyColumn = emptyColumn;
                if (newEmptyRow >= 0) {
                    //Move number in above into empty position
                    numbers[emptyRow][emptyColumn] = numbers[newEmptyRow][newEmptyColumn];
                    //Should be the empty position now
                    numbers[newEmptyRow][newEmptyColumn] = "";
                    emptyRow = newEmptyRow;
                }
            } else if (direction == 1) {
                // if direction is 1 move right
                int newEmptyX = emptyRow;
                int newEmptyY = emptyColumn + 1;
                if (newEmptyY <= 3) {
                    //Move number in above into empty position
                    numbers[emptyRow][emptyColumn] = numbers[newEmptyX][newEmptyY];
                    //Should be the empty position now
                    numbers[newEmptyX][newEmptyY] = "";
                    emptyColumn = newEmptyY;
                }

            } else if (direction == 2) {
                // if direction is 2 move down
                int newEmptyX = emptyRow + 1;
                int newEmptyY = emptyColumn;
                if (newEmptyX <= 3) {
                    //Move number in above into empty position
                    numbers[emptyRow][emptyColumn] = numbers[newEmptyX][newEmptyY];
                    //Should be the empty position now
                    numbers[newEmptyX][newEmptyY] = "";
                    emptyRow = newEmptyX;
                }

            } else {
                // else direction must be 3 and we move left
                int newEmptyX = emptyRow;
                int newEmptyY = emptyColumn - 1;
                if (newEmptyY >= 0) {
                    //Move number in above into empty position
                    numbers[emptyRow][emptyColumn] = numbers[newEmptyX][newEmptyY];
                    //Should be the empty position now
                    numbers[newEmptyX][newEmptyY] = "";
                    emptyColumn = newEmptyY;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setText(numbers[i][j]);
            }
        }
    }

    private void isGameOver() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!buttons[i][j].getText().equals(correctSolution[i][j])) {
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "You win!");
    }
}
