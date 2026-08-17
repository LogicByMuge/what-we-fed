package main;

import entity.Entity;
import obj.SuperObject;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeft = entity.x + entity.solidArea.x;
        int entityRight = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTop = entity.y + entity.solidArea.y;
        int entityBottom = entity.y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeft / gp.tileSize;
        int entityRightCol = entityRight / gp.tileSize;
        int entityTopRow = entityTop / gp.tileSize;
        int entityBottomRow = entityBottom / gp.tileSize;

        int tileNum1;
        int tileNum2;

        switch (entity.direction) {

            case "up":

                entityTopRow =
                        (entityTop - entity.speed) / gp.tileSize;

                // Top boundary
                if (entityTopRow < 0) {
                    entity.collisionOn = true;
                    return;
                }

                tileNum1 =
                        getTileNum(entityLeftCol, entityTopRow);

                tileNum2 =
                        getTileNum(entityRightCol, entityTopRow);

                break;


            case "down":

                entityBottomRow =
                        (entityBottom + entity.speed) / gp.tileSize;

                // Bottom boundary
                if (entityBottomRow >= gp.maxScreenRow) {
                    entity.collisionOn = true;
                    return;
                }

                tileNum1 =
                        getTileNum(entityLeftCol, entityBottomRow);

                tileNum2 =
                        getTileNum(entityRightCol, entityBottomRow);

                break;


            case "left":

                entityLeftCol =
                        (entityLeft - entity.speed) / gp.tileSize;

                // Don't allow player to leave the left side
                if (entityLeftCol < 0) {
                    entity.collisionOn = true;
                    return;
                }

                tileNum1 =
                        getTileNum(entityLeftCol, entityTopRow);

                tileNum2 =
                        getTileNum(entityLeftCol, entityBottomRow);

                break;


            case "right":

                entityRightCol =
                        (entityRight + entity.speed) / gp.tileSize;

                // Don't allow player to leave the right side
                if (entityRightCol >= gp.maxScreenCol) {
                    entity.collisionOn = true;
                    return;
                }

                tileNum1 =
                        getTileNum(entityRightCol, entityTopRow);

                tileNum2 =
                        getTileNum(entityRightCol, entityBottomRow);

                break;


            default:
                return;
        }


        // Check the actual tile collision
        if (gp.tileM.tile[tileNum1].collision ||
                gp.tileM.tile[tileNum2].collision) {

            entity.collisionOn = true;
        }
    }


    // Get the tile number from the current map
    private int getTileNum(int col, int row) {

        if (gp.currentMap == 0) {

            return gp.tileM.mapTileNum[col][row];

        } else {

            /*
             * Map 2 is displayed as 16-tile sections.
             *
             * Right section = map columns 9-24
             * Left section  = map columns 0-15
             */

            int mapCol;

            if (gp.map2Section == 1) {

                // RIGHT SIDE
                mapCol = col + 9;

            } else {

                // LEFT SIDE
                mapCol = col;
            }

            // Prevent invalid map coordinates
            if (mapCol < 0) {
                mapCol = 0;
            }

            if (mapCol >= gp.tileM.map2Cols) {
                mapCol = gp.tileM.map2Cols - 1;
            }

            return gp.tileM.mapTileNum2[mapCol][row];
        }
    }


    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0;
             i < gp.obj[gp.currentMap].length;
             i++) {

            if (gp.obj[gp.currentMap][i] != null) {

                // Entity's solid area
                entity.solidArea.x =
                        entity.x + entity.solidArea.x;

                entity.solidArea.y =
                        entity.y + entity.solidArea.y;


                // Object's solid area
                gp.obj[gp.currentMap][i].solidArea.x =
                        gp.obj[gp.currentMap][i].x +
                                gp.obj[gp.currentMap][i].solidArea.x;

                gp.obj[gp.currentMap][i].solidArea.y =
                        gp.obj[gp.currentMap][i].y +
                                gp.obj[gp.currentMap][i].solidArea.y;


                switch (entity.direction) {

                    case "up":

                        entity.solidArea.y -= entity.speed;

                        if (entity.solidArea.intersects(
                                gp.obj[gp.currentMap][i].solidArea)) {

                            entity.collisionOn = true;

                            if (player) {
                                index = i;
                            }
                        }

                        break;


                    case "down":

                        entity.solidArea.y += entity.speed;

                        if (entity.solidArea.intersects(
                                gp.obj[gp.currentMap][i].solidArea)) {

                            entity.collisionOn = true;

                            if (player) {
                                index = i;
                            }
                        }

                        break;


                    case "left":

                        entity.solidArea.x -= entity.speed;

                        if (entity.solidArea.intersects(
                                gp.obj[gp.currentMap][i].solidArea)) {

                            entity.collisionOn = true;

                            if (player) {
                                index = i;
                            }
                        }

                        break;


                    case "right":

                        entity.solidArea.x += entity.speed;

                        if (entity.solidArea.intersects(
                                gp.obj[gp.currentMap][i].solidArea)) {

                            entity.collisionOn = true;

                            if (player) {
                                index = i;
                            }
                        }

                        break;
                }


                // Reset entity hitbox
                entity.solidArea.x =
                        entity.solidAreaDefaultX;

                entity.solidArea.y =
                        entity.solidAreaDefaultY;


                // Reset object hitbox
                gp.obj[gp.currentMap][i].solidArea.x =
                        gp.obj[gp.currentMap][i].solidAreaDefaultX;

                gp.obj[gp.currentMap][i].solidArea.y =
                        gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }

        return index;
    }


    public int checkObjectInFront(
            Entity entity,
            SuperObject[] targets) {

        int index = 999;

        int checkDistance = gp.tileSize;


        // Entity hitbox
        entity.solidArea.x =
                entity.x + entity.solidArea.x;

        entity.solidArea.y =
                entity.y + entity.solidArea.y;


        switch (entity.direction) {

            case "up":
                entity.solidArea.y -= checkDistance;
                break;

            case "down":
                entity.solidArea.y += checkDistance;
                break;

            case "left":
                entity.solidArea.x -= checkDistance;
                break;

            case "right":
                entity.solidArea.x += checkDistance;
                break;
        }


        for (int i = 0; i < targets.length; i++) {

            if (targets[i] != null) {

                targets[i].solidArea.x =
                        targets[i].x +
                                targets[i].solidArea.x;

                targets[i].solidArea.y =
                        targets[i].y +
                                targets[i].solidArea.y;


                if (entity.solidArea.intersects(
                        targets[i].solidArea)) {

                    index = i;
                }


                // Reset object hitbox
                targets[i].solidArea.x =
                        targets[i].solidAreaDefaultX;

                targets[i].solidArea.y =
                        targets[i].solidAreaDefaultY;
            }
        }


        // Reset entity hitbox
        entity.solidArea.x =
                entity.solidAreaDefaultX;

        entity.solidArea.y =
                entity.solidAreaDefaultY;


        return index;
    }
}