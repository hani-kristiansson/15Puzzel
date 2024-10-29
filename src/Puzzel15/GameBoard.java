package Puzzel15;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JFrame {

    JPanel gamePanel = new JPanel();
    JPanel menuPanel = new JPanel();

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
                gamePanel.add(buttons[i][j]);
            }
        }

        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.white);
        startButton.setFont(new Font("Arial", Font.BOLD, 25));
        menuPanel.add(startButton);

        JButton hintButton = new JButton("Hint pls");
        hintButton.setBackground(Color.red);
        hintButton.setForeground(Color.white);
        hintButton.setFont(new Font("Arial", Font.BOLD, 25));
        menuPanel.add(hintButton);

        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


}
