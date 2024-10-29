package Puzzel15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {

    JPanel gamePanel = new JPanel();
    JPanel menuPanel = new JPanel();

    String[][] numbers = {
            {"1", "2", "3", "4"},
            {"5", "6", "7", "8"},
            {"9", "10", "11", "12"},
            {"13", "14", "15", ""}
    };

    JButton[][] buttons = new JButton[4][4];

    public GameBoard() {

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
        startButton.setFont(new Font("Arial", Font.BOLD, 25));
        menuPanel.add(startButton);

        JButton hintButton = new JButton("Hint");
        hintButton.setBackground(Color.black);
        hintButton.setForeground(Color.white);
        hintButton.setFont(new Font("Arial", Font.BOLD, 25));
        menuPanel.add(hintButton);

        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void buttonPushed(int row, int column) {
        System.out.println("Button clicked!" + row + " " + column);

        if (buttons[row][column].getText().isBlank()) {
            System.out.println("You pushed empty button");

        } else if (row-1 >= 0 && buttons[row - 1][column].getText().isBlank()) {
            System.out.println("Above was empty, can move");
            buttons[row - 1][column].setText(buttons[row][column].getText());
            buttons[row][column].setText("");

        } else if (column-1 >= 0 && buttons[row][column - 1].getText().isBlank()) {
            System.out.println("Left was empty, can move");
            buttons[row][column - 1].setText(buttons[row][column].getText());
            buttons[row][column].setText("");

        } else if (column+1 <= 3 && buttons[row][column + 1].getText().isBlank()) {
            System.out.println("Right was empty, can move");
            buttons[row][column + 1].setText(buttons[row][column].getText());
            buttons[row][column].setText("");

        } else if (row+1 <= 3 && buttons[row + 1][column].getText().isBlank()) {
            System.out.println("Below was empty, can move");
            buttons[row + 1][column].setText(buttons[row][column].getText());
            buttons[row][column].setText("");
        }
    }
}
