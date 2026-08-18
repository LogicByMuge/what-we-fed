package obj;

import javax.imageio.ImageIO;

public class OBJ_door extends SuperObject{

    public OBJ_door() {
        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objs/door.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;

        setDialogue();
    }

    public void setDialogue() {
        dialogue[0][0] = "I shouldn't go out yet.";

        // Night Event
        dialogue[1][0] = "They're gone...";

        // Locked state
        dialogue[2][0] = "We still have food.";
        dialogue[2][1] = "Theres no reason to go out.";
    }

    public void getDialogue() {

        if (gp.player.days % 2 == 1) {
            dialogueSet = 0;
        } else {
            dialogueSet = 2;
        }

        startDialogue(this, dialogueSet);
    }

    public void getCompleteDialogue() {
        dialogueIndex = 0;
        forceStartDialogue(this, 1);
    }

}