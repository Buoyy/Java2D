package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    private final GamePanel gp;
    private Tile[] tiles;
    private int[][] mapTileNum;
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[4];
        mapTileNum = new int[gp.maxScreenCols][gp.maxScreenRows];
        loadMap();
        loadTileImages();
    }
    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/maps/testmap.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0; int row = 0;
            while (col < gp.maxScreenCols && row < gp.maxScreenRows) {
                String line = br.readLine();
                while (col < gp.maxScreenCols) {
                    String[] nums = line.split(" ");
                    int num = Integer.parseInt(nums[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxScreenCols) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        int col = 0; int row = 0;
        int x = 0; int y = 0;
        while (col < gp.maxScreenCols && row < gp.maxScreenRows) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tiles[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++; x += gp.tileSize;
            if (col == gp.maxScreenCols) {
                col = 0; x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
