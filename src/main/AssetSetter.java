package main;

import obj.*;

public class AssetSetter {

    GamePanel gp;

    public OBJ_child child;
    public OBJ_table table;
    public OBJ_corridorDoor corridorDoor;
    public OBJ_trap trap;
    public OBJ_lockedDoor lockedDoor;
    public OBJ_shelf shelf;

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

        corridorDoor = new OBJ_corridorDoor();
        corridorDoor.x = 13 * gp.tileSize;
        corridorDoor.y = 4 * gp.tileSize;
        gp.obj[1][0] = corridorDoor;

        trap = new OBJ_trap();
        trap.x = 120;
        trap.y = 290;
        gp.obj[1][1] = trap;

        lockedDoor = new OBJ_lockedDoor();
        lockedDoor.x = 3 * gp.tileSize;
        lockedDoor.y = 4 * gp.tileSize;
        gp.obj[1][2] = lockedDoor;

        shelf = new OBJ_shelf();
        shelf.x = 1 * gp.tileSize;
        shelf.y = 200;
        gp.obj[1][3] = shelf;
    }

    public void updateObjects() {

        // NIGHT EVENT
        if (gp.nEvent.isNight) {

            gp.obj[0][0] = null;
            gp.obj[0][3] = null;

        } else {

            gp.obj[0][0] = table;
            gp.obj[0][3] = child;
        }

        // MAP 2 SECTION
        if(gp.map2Section == 1) {
            gp.obj[1][0] = corridorDoor;
            gp.obj[1][1] = trap;

            gp.obj[1][2] = null;
            gp.obj[1][3] = null;
        } else {
            gp.obj[1][0] = null;
            gp.obj[1][1] = null;

            gp.obj[1][2] = lockedDoor;
            gp.obj[1][3] = shelf;
        }
    }
}