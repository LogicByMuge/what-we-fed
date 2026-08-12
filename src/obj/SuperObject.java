package obj;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = true;
    public int x,y;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    GamePanel gp;
    public String dialogue[][] = new String[20][20];
    public int dialogueIndex = 0;

    public int dialogueSet = 0;

    public boolean alreadyAte = false;

    public void getDialogue() {};

    public void startDialogue(SuperObject obj, int setNum) {
        gp.gameState = gp.dialogueState;
        gp.ui.sObj = obj;
        dialogueSet = setNum;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
