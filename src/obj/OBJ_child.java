package obj;

import javax.imageio.ImageIO;

public class OBJ_child extends SuperObject{

    public OBJ_child() {
        name = "Chair";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objs/chair2.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;

        dialogueSet = -1;
        setDialogue();
    }

    public void setDialogue() {
        dialogue[0][0] = "\"I'm hungry\"";
        dialogue[0][1] = "She always said that";

        dialogue[1][0] = "\"I'm scared\"";
        dialogue[1][1] = "...";
    }

    public void getDialogue() {
        startDialogue(this,dialogueSet);

        if(gp.player.days == 2 || dialogueSet == -1) {
            dialogueSet++;
        }


        if(dialogue[dialogueSet][0] == null) {
            dialogueSet = 0;
        }
    }
}
