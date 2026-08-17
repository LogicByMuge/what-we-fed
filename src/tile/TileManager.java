package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;
    public int[][] mapTileNum2;

    public int map1Cols;
    public int map2Cols;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[24];

        map1Cols = gp.maxScreenCol;
        mapTileNum = new int[map1Cols][gp.maxScreenRow];

        map2Cols = gp.maxScreenCol + 9;
        mapTileNum2 = new int[map2Cols][gp.maxScreenRow];

        getTileImage();

        loadMap("/maps/map01.txt", 0);
        loadMap("/maps/map02.txt", 1);
    }

    public void getTileImage() {
        setup(0,"floor",false);
        setup(1,"empty",true);
        setup(2,"tableNight",true);
        setup(3,"chair",false);
        setup(4,"chairNight",true);
        setup(5,"wall",true);
        setup(6,"seperator",true);
        setup(7,"seperator2",true);
        setup(8,"seperatorBottom",true);
        setup(9,"seperator2Bottom",true);
        setup(10,"wallBottom",true);
        setup(11,"fridgeTop",true);
        setup(12,"sink",true);
        setup(13,"pantry",true);
        setup(14,"stove",true);
        setup(15,"trashcan",true);
        setup(16,"chair3",false);
        setup(17,"chair4",false);
        setup(18,"corridorTiles",false);
        setup(19,"corridorWall",true);
    }

    public void setup(int index, String name, boolean collision) {
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + name + ".png"));
            tile[index].collision = collision;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadMap(String filePath, int map) {
        int screenCol = (map == 0) ? map1Cols : map2Cols;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(filePath)))) {

            int row = 0;
            while (row < gp.maxScreenRow) {
                String line = br.readLine();
                if (line == null) break;
                String[] numbers = line.split(" ");

                for (int col = 0; col < screenCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    if (map == 0) {
                        mapTileNum[col][row] = num;
                    } else {
                        mapTileNum2[col][row] = num;
                    }
                }
                row++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2) {

        if (gp.currentMap == 0) {

            for (int row = 0; row < gp.maxScreenRow; row++) {
                for (int col = 0; col < map1Cols; col++) {

                    int num = mapTileNum[col][row];

                    g2.drawImage(
                            tile[num].image,
                            col * gp.tileSize,
                            row * gp.tileSize,
                            gp.tileSize,
                            gp.tileSize,
                            null
                    );
                }
            }

        } else {

            int startCol;

            if (gp.map2Section == 0) {
                startCol = 0;   // columns 0–15
            } else {
                startCol = 9;   // columns 9–24
            }

            for (int row = 0; row < gp.maxScreenRow; row++) {
                for (int col = 0; col < gp.maxScreenCol; col++) {

                    int mapCol = startCol + col;

                    int num = mapTileNum2[mapCol][row];

                    g2.drawImage(
                            tile[num].image,
                            col * gp.tileSize,
                            row * gp.tileSize,
                            gp.tileSize,
                            gp.tileSize,
                            null
                    );
                }
            }
        }
    }
}
