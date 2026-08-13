package main;

import obj.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_table();
        gp.obj[0].x = 7 * gp.tileSize;
        gp.obj[0].y = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_bedroom();
        gp.obj[1].x = 13 * gp.tileSize;
        gp.obj[1].y = 5 * gp.tileSize;

        gp.obj[2] = new OBJ_fridge();
        gp.obj[2].x = 11 * gp.tileSize;
        gp.obj[2].y = 4 * gp.tileSize;

        gp.obj[3] = new OBJ_child();
        gp.obj[3].x = 7 * gp.tileSize;
        gp.obj[3].y = 8 * gp.tileSize;

        gp.obj[4] = new OBJ_door();
        gp.obj[4].x = 2 * gp.tileSize;
        gp.obj[4].y = 11 * gp.tileSize;

    }
}
