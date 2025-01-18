package main;

import entity.Player;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Tile calculation
    final int actualTileSize = 16;
    final int scale = 3;
    public final int tileSize = scale * actualTileSize;

    // Screen width and height calculation
    final int maxCols = 16;
    final int maxRows = 12;
    final int width = tileSize * maxCols;
    final int height = tileSize * maxRows;

    // Technical stuff
    int fps = 60;
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread; // The thread the game will run on

    // Player default stuff
    Player player = new Player(this, keyHandler);

    // A generic constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        // The game loop (accumulator/delta method)

        // How it works is that when draw interval of time passes, only then is our game updated
        double drawInterval = 1000000000.0/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0; int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: "+drawCount);
                drawCount = 0; timer = 0;
            }
        }
    }
    public void update() {
        player.update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }
}
