package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    GamePanel gp;
    public int worldX, worldY;
    public int x,y;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2,dead;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle solidArea;
    public int solidAreaDefaultX,solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean isDead = false;
    public boolean visible = true; // default true so nothing else breaks

    public Entity(GamePanel gp) {
        this.gp = gp;

        // HITBOX
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 16;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image;

        if (isDead) {
            image = dead;
        } else {
            image = null;
            switch(direction) {
                case "up":
                    image = (spriteNumber == 1) ? up1 : up2;
                    break;
                case "down":
                    image = (spriteNumber == 1) ? down1 : down2;
                    break;
                case "left":
                    image = (spriteNumber == 1) ? left1 : left2;
                    break;
                case "right":
                    image = (spriteNumber == 1) ? right1 : right2;
                    break;
            }
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
