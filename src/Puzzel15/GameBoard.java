package Puzzel15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameBoard extends JFrame {

    JPanel gamePanel = new JPanel();
    JPanel menuPanel = new JPanel();
    JPanel infoPanel = new JPanel();
    JLabel clickCountLabel = new JLabel("click count");


    int clickCounter = 0;

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
        add(infoPanel, BorderLayout.SOUTH);

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

        JButton easyButton = new JButton("Easy version");
        easyButton.setBackground(Color.black);
        easyButton.setForeground(Color.white);
        easyButton.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(easyButton);

        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shuffleNumbersEasy();
            }
        });

        //Info panel
        infoPanel.setLayout(new GridLayout(1, 2));
        infoPanel.setMaximumSize(new Dimension(400, 100));
        JLabel timer = new JLabel("timer");
        infoPanel.add(timer);
        infoPanel.add(clickCountLabel);
        clickCountLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timer.setFont(new Font("Arial", Font.BOLD, 20));




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
            clickCounter++;

        } else if (column - 1 >= 0 && buttons[row][column - 1].getText().isBlank()) {
            System.out.println("Left was empty, can move");
            buttons[row][column - 1].setText(buttons[row][column].getText());
            buttons[row][column].setText("");
            clickCounter++;


        } else if (column + 1 <= 3 && buttons[row][column + 1].getText().isBlank()) {
            System.out.println("Right was empty, can move");
            buttons[row][column + 1].setText(buttons[row][column].getText());
            buttons[row][column].setText("");
            clickCounter++;


        } else if (row + 1 <= 3 && buttons[row + 1][column].getText().isBlank()) {
            System.out.println("Below was empty, can move");
            buttons[row + 1][column].setText(buttons[row][column].getText());
            buttons[row][column].setText("");
            clickCounter++;

        }
        isGameFinished();
        clickCountLabel.setText("click count: " + clickCounter);

        infoPanel.repaint();

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
                    clickCounter++;
                }
            } else if (direction == 1) {
                // if direction is 1 move right
                int newEmptyRow = emptyRow;
                int newEmptyColumn = emptyColumn + 1;
                if (newEmptyColumn <= 3) {
                    //Move number in above into empty position
                    numbers[emptyRow][emptyColumn] = numbers[newEmptyRow][newEmptyColumn];
                    //Should be the empty position now
                    numbers[newEmptyRow][newEmptyColumn] = "";
                    emptyColumn = newEmptyColumn;
                    clickCounter++;
                }

            } else if (direction == 2) {
                // if direction is 2 move down
                int newEmptyRow = emptyRow + 1;
                int newEmptyColumn = emptyColumn;
                if (newEmptyRow <= 3) {
                    //Move number in above into empty position
                    numbers[emptyRow][emptyColumn] = numbers[newEmptyRow][newEmptyColumn];
                    //Should be the empty position now
                    numbers[newEmptyRow][newEmptyColumn] = "";
                    emptyRow = newEmptyRow;
                    clickCounter++;
                }

            } else {
                // else direction must be 3 and we move left
                int newEmptyRow = emptyRow;
                int newEmptyColumn = emptyColumn - 1;
                if (newEmptyColumn >= 0) {
                    //Move number in above into empty position
                    numbers[emptyRow][emptyColumn] = numbers[newEmptyRow][newEmptyColumn];
                    //Should be the empty position now
                    numbers[newEmptyRow][newEmptyColumn] = "";
                    emptyColumn = newEmptyColumn;
                    clickCounter++;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setText(numbers[i][j]);
            }
        }
    }

    private void isGameFinished() {

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
