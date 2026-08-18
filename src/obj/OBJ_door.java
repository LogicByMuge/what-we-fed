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
        dialogue[0][0] = "Not yet.";
        dialogue[0][1] = "Eat first.";

        // Night Event
        dialogue[1][0] = "They're gone...";

        // Locked state
        dialogue[2][0] = "No need today.";

        dialogue[3][0] = "Not much food left.";
        dialogue[3][1] = "She'll need to go out again soon.";
    }

    public void getDialogue() {

        if (gp.player.days % 2 == 1 && gp.player.days != 5) {
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

    public void getDefaultDialogue() {
        dialogueSet = 3;
        startDialogue(this, dialogueSet);
    }

}