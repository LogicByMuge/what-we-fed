package obj;

import javax.imageio.ImageIO;

public class OBJ_trap extends SuperObject {

    public OBJ_trap() {
        name = "Trap";

        try {
            image = ImageIO.read(
                    getClass().getResourceAsStream("/objs/trap.png")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        collision = false;

        dialogueSet = 0;
        setDialogue();
    }

    public void setDialogue() {

        dialogue[0][0] = "Something's caught in it.";
        dialogue[0][1] = "Not what she was hoping for.";
        dialogue[0][2] = "Not what she was afraid of either.";

        dialogue[1][0] = "Still there.";
        dialogue[1][1] = "She doesn't look this time either.";
    }

    public void getDialogue() {

        if (gp.player.days >= 3) {
            dialogueSet = 1;
        } else {
            dialogueSet = 0;
        }

        startDialogue(this, dialogueSet);
    }
}