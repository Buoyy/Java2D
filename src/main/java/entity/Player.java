package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaults();
        getPlayerImage(); 
    }

    public void setDefaults() {
        x = 100;
        y = 100;
        speed = 3;
        direction = "down"; 
    }

    public void getPlayerImage() {
     // Try to load in the player's sprites. 
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/boy_right_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
     // This is a double check to stop the animations from playing even when no keys are pressed.
        if (keyH.down || keyH.left || keyH.up || keyH.right) {
         // Remember, (0, 0) corresponds to the top left corner. y increases downwards and x increases towards right.
            if (keyH.up) {
                direction = "up";
                y -= speed;
            } else if (keyH.left) {
                direction = "left";
                x -= speed;
            } else if (keyH.down) {
                direction = "down";
                y += speed;
            } else {
                direction = "right";
                x += speed;
            }
            spriteCounter++;
            if (spriteCounter > 10) { // every 10 draw intervals
                if (spriteNum == 1) spriteNum = 2;
                else if (spriteNum == 2) spriteNum = 1;
                spriteCounter = 0; // Reset after completing one cycle of the animation
            }
        }
    }

    public void draw(Graphics2D g2) {
     // Switch expression seems much more cool than normal switch statement
        BufferedImage image = switch (direction) {
            case "up" -> (spriteNum == 1) ? up1 : up2;
            case "down" -> (spriteNum == 1) ? down1 : down2;
            case "left" -> (spriteNum == 1) ? left1 : left2;
            case "right" -> (spriteNum == 1) ? right1 : right2;
            default -> null;
        };
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
