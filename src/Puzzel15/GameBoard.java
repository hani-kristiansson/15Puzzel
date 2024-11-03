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
    int tempClickCounter = 0;

    //korrekta lösning, spelaren behöver uppnå den ordning
    final String[][] correctSolution =
            {{"1", "2", "3", "4"},
                    {"5", "6", "7", "8"},
                    {"9", "10", "11", "12"},
                    {"13", "14", "15", ""}};

    // den lagrar aktuella tillståndet för pusslet
    // används för att blanda brickorna när ett nytt spel startas
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

                // variabler behövs för att kunna använda dem i lambdas
                // finalI & finalJ håller fast vid initial värde för i,j för varje knapp
                // så lambdauttrycket kan använda rätt värde när loopen går vidare
                int finalI = i;
                int finalJ = j;

                // varje knapp får en ActionListener som anropar method när knappen klickas
                buttons[i][j].addActionListener(ae -> buttonPushed(finalI, finalJ));
                /*
                ae = ActionEvent e
                actionPerformed(ActionEvent e) {buttonPushed(finalI,j);}
                 */

                gamePanel.add(buttons[i][j]);
            }
        }

        // new game knapp
        JButton startButton = new JButton("New game");
        startButton.setBackground(Color.blue);
        startButton.setForeground(Color.white);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(startButton);

       // shuffle nummer med shuffleNumbers() method
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shuffleNumbers();
            }
        });

        // easy version knapp
        JButton easyButton = new JButton("Easy version");
        easyButton.setBackground(Color.black);
        easyButton.setForeground(Color.white);
        easyButton.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(easyButton);

        // shuffle nummer med shuffleNumbersEasy() method
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shuffleNumbersEasy();
            }
        });

        //Info panel
        infoPanel.setLayout(new GridLayout(1, 2));
        infoPanel.setMaximumSize(new Dimension(400, 100));
        infoPanel.add(clickCountLabel);
        clickCountLabel.setFont(new Font("Arial", Font.BOLD, 20));

        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // method definierar vad som händer när en knapp klickas
    private void buttonPushed(int row, int column) {
        System.out.println("Button clicked!" + row + " " + column);

        // om den klickade knappen är tom, görs inget
        if (buttons[row][column].getText().isBlank()) {
            System.out.println("You pushed empty button");

            // en tom knapp flyttas siffran om det finns en tom knapp i närheten (upp, ner, vänster, höger)
            // klickräknaren ökas
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

        clickCountLabel.setText("click count: " + clickCounter);

        // när spelet är klart
        isGameFinished();

        infoPanel.repaint();
    }

    // blandas siffrorna slumpmässigt
    private void shuffleNumbers() {

        clickCounter = 0;
        clickCountLabel.setText("click count: " + clickCounter);
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

    // en lättare version av blandningen, som bara gör ett fåtal drag från lösningen
    private void shuffleNumbersEasy() {

        clickCounter = 0;
        clickCountLabel.setText("click count: " + clickCounter);
        tempClickCounter = clickCounter;

        // återställs alla knappar till ordningen i correctSolution
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                numbers[i][j] = correctSolution[i][j];
            }
        }

        Random random = new Random();
        int emptyRow = 3, emptyColumn = 3;

        // därefter görs endast 10 random drag från den tomma platsen
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
                int newEmptyRow = emptyRow;
                int newEmptyColumn = emptyColumn + 1;
                if (newEmptyColumn <= 3) {
                    //Move number in above into empty position
                    numbers[emptyRow][emptyColumn] = numbers[newEmptyRow][newEmptyColumn];
                    //Should be the empty position now
                    numbers[newEmptyRow][newEmptyColumn] = "";
                    emptyColumn = newEmptyColumn;

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

                }
            }
        }

        clickCounter = tempClickCounter;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setText(numbers[i][j]);
            }
        }
    }

    // kontrollerar om alla knappar är i samma ordning som i correctSolution
    private void isGameFinished() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!buttons[i][j].getText().equals(correctSolution[i][j])) {
                    return;
                }
            }
        }
        //Om alla knappar stämmer överens med lösningen
        JOptionPane.showMessageDialog(null, "You win!");
    }
}
