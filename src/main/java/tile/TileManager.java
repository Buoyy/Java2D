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
    private Tile[] tiles; // all kinds of tiles (like water, grass, brick)
    private int[][] mapTileNum; // to load the number assigned to a tile from map (like 0 for brick)
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[4]; // Currently. we have 3 types of tiles. Just to be safe, we have 4 spaces for tiles. 
        mapTileNum = new int[gp.maxScreenCols][gp.maxScreenRows]; // the 2D array needs to be limited to the screen's dimensions. 
        
     // Next we load in our map data and tile data.
        loadMap();
        loadTileImages();
    }
    public void loadMap() {
        try {
         // Method: we load the map's input stream to read it from a reader. By a loop, we read a line and split the numbers into an array and pass the array's data into mapTileNum as (col, row) in coordinate form. 

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
     // Load in all images for different kinds of tiles
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
     // Method: we start from the 1st column and 1st row, and the top left corner of the map. Draw the corresponding image via it's tile number. Once the final column is reached, we move on to the next row. 

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
