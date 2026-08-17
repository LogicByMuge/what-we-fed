package obj;

import javax.imageio.ImageIO;

public class OBJ_fridge extends SuperObject{

    public OBJ_fridge() {
        name = "Fridge";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objs/fridgeBottom.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;

        dialogueSet = -1;
        setDialogue();
    }

    public void setDialogue() {
        dialogue[0][0] = "*Fridge Noises*";

        dialogue[1][0] = "World Hello";
    }

    public void getDialogue() {

        if (gp.player.days == 2) {
            dialogueSet = 1;
        } else {
            dialogueSet = 0;
        }

        startDialogue(this, dialogueSet);
    }

}
