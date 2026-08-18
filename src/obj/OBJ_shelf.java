package obj;

import javax.imageio.ImageIO;

public class OBJ_shelf extends SuperObject{

    public OBJ_shelf() {
        name = "Shelf";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objs/shelf.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;

        setDialogue();
    }

    public void setDialogue() {
        dialogue[0][0] = "Someone else's cans, probably.";
        dialogue[0][1] = "She takes what's left.";

        dialogue[1][0] = "Emptier than last time..";
        dialogue[1][1] = "She takes what's left anyway.";

        dialogue[2][0] = "That should be enough.";
    }

    public void getDialogue() {

        if (gp.player.days >= 3) {
            dialogueSet = 1;
        } else {
            dialogueSet = 0;
        }

        startDialogue(this, dialogueSet);
    }

    public void getDefaultDialogue() {
        dialogueSet = 2;
        startDialogue(this, dialogueSet);
    }
}

