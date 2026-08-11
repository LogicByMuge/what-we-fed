package main;

import obj.OBJ_table;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_table();
        gp.obj[0].x = 7 * gp.tileSize;
        gp.obj[0].y =  7 * gp.tileSize;
    }
}
