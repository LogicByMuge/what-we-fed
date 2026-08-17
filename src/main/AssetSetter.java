package main;

import obj.*;

public class AssetSetter {

    GamePanel gp;

    public OBJ_child child;
    public OBJ_table table;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        table = new OBJ_table();
        table.x = 6 * gp.tileSize;
        table.y = 7 * gp.tileSize;
        gp.obj[0][0] = table;

        gp.obj[0][1] = new OBJ_bedroom();
        gp.obj[0][1].x = 550;
        gp.obj[0][1].y = 5 * gp.tileSize;

        gp.obj[0][2] = new OBJ_fridge();
        gp.obj[0][2].x = 4 * gp.tileSize;
        gp.obj[0][2].y = 4 * gp.tileSize;

        child = new OBJ_child();
        child.x = 6 * gp.tileSize;
        child.y = 8 * gp.tileSize;
        gp.obj[0][3] = child;

        gp.obj[0][4] = new OBJ_door();
        gp.obj[0][4].x = 3 * gp.tileSize;
        gp.obj[0][4].y = 565;
    }

    public void updateObjects() {

        if (gp.nEvent.isNight) {

            gp.obj[0][0] = null;
            gp.obj[0][3] = null;

        } else {

            gp.obj[0][0] = table;
            gp.obj[0][3] = child;
        }
    }
}