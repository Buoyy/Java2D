package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    private final GamePanel gp;
    private final Tile[] tiles; // all kinds of tiles (like water, grass, brick)
    private final int[][] mapTileNum; // to load the number assigned to a tile from map (like 0 for brick)
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[8]; // Currently. we have 3 types of tiles. Just to be safe, we have 4 spaces for tiles.
        mapTileNum = new int[gp.maxScreenCols][gp.maxScreenRows]; // the 2D array needs to be limited to the screen's dimensions. 
        
     // Next we load in our map data and tile data.
        loadMap();
        loadTileImages();
    }
    public void loadMap() {
        try {
         // Method: we load the map's input stream to read it from a reader. By a loop, we read a line and split the numbers into an array and pass the array's data into mapTileNum as (col, row) in coordinate form. 

            InputStream is = getClass().getResourceAsStream("/maps/test_map.txt");
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
            tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/brick.png")));
            
            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/dirty_grass.png")));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/dirty_grass_left.png")));

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/dirty_grass_left_end.png")));

            tiles[6] = new Tile();
            tiles[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/dirty_grass_right.png")));

            tiles[7] = new Tile();
            tiles[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/dirty_grass_right_end.png")));

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
