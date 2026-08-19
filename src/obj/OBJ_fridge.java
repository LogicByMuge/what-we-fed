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
        // day 1
        dialogue[0][0] = "She counts what's left.";
        dialogue[0][1] = "Twice, out of habit.";

        // day 2
        dialogue[1][0] = "Enough for today.";
        dialogue[1][1] = "She doesn't check further than that.";

        // day 3
        dialogue[2][0] = "Barely enough for one more day.";
        dialogue[2][1] = "No deliveries anymore.";
        dialogue[2][2] = "No stores either.";

        // day 4
        dialogue[4][0] = "She counts without really looking.";
        dialogue[4][1] = "She already knows the number.";

        // day 5
        dialogue[5][0] = "One bowl.";
        dialogue[5][1] = "There was only ever going to be one.";

        // day 6
        dialogue[6][0] = "She opens it out of habit.";
        dialogue[6][1] = "Doesn't take anything out.";

        // After restock
        dialogue[3][0] = "That should do.";
    }

    public void getDialogue() {

        if (gp.player.days == 2) {
            dialogueSet = 1;
        } else if(gp.player.days == 3) {
            dialogueSet = 2;
        }  else if(gp.player.days == 4) {
            dialogueSet = 4;
        } else if(gp.player.days == 5) {
            dialogueSet = 5;
        } else if(gp.player.days == 6) {
            dialogueSet = 6;
        }
        else {
            dialogueSet = 0;
        }

        startDialogue(this, dialogueSet);
    }

    public void getDefaultDialogue() {
        dialogueSet = 3;
        startDialogue(this, dialogueSet);
    }

}
