package entity;

import main.GamePanel;

import javax.imageio.ImageIO;

public class NPC_Survivor extends  Entity{

    public NPC_Survivor(GamePanel gp) {
        super(gp);

        direction = "up";
        speed = 4;

        getNPCImage();
    }

    public void getNPCImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/npc/npc_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/npc/npc_up_2.png"));
            dead = ImageIO.read(getClass().getResourceAsStream("/npc/npc_dead.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
