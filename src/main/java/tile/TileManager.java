package tile;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    private final GamePanel gp;
    private Tile[] tiles;
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[4];
        loadTileImages();
    }
    public void loadTileImages() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/brick.png"));
            
            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
        g2.drawImage(tiles[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tiles[1].image, gp.tileSize, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tiles[2].image, 2*gp.tileSize, 0, gp.tileSize, gp.tileSize, null);
    }
}
