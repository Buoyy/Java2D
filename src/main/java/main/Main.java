package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame(); // The main window

        // Setting major settings
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Game");

        // Panel
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();

        // Centering and making window visible
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Starting
        gp.startGameThread();
    }
}