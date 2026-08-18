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
        // day 1
        dialogue[0][0] = "\"Eat,\" she says.";
        dialogue[0][2] = "Molly doesn't answer.";
        dialogue[0][2] = "She never really does.";

        // day 2
        dialogue[1][0] = "\"Eat,\" she says.";
        dialogue[1][1] = "Same as always.";

        // def
        dialogue[2][0] = ". . .";

        // re
        dialogue[3][0] = "\"I'm back,\" she says.";

        // day 3
        dialogue[4][0] =  "\"Eat,\" she says.";
        dialogue[4][1] = "Molly always wanted to be just like her.";
        dialogue[4][2] = "She used to say it all the time.";

        // day 4
        dialogue[5][0] = "Molly used to ask a hundred questions a day.";
        dialogue[5][1] = "It's quiet now.";
        dialogue[5][2] = "Mara's gotten used to the quiet.";

        // day 5
        dialogue[6][0] = "She goes to feed her, like always.";
        dialogue[6][1] = "Her hand stops.";
        dialogue[6][2] = "Molly hasn't moved.";
        dialogue[6][3] = "She's cold.";
        dialogue[6][4] = "She's been cold for awhile now.";
    }

    public void getDialogue() {
        if(!gp.player.restocked) {
            if(gp.player.days == 1) {
                dialogueSet = 0;
            }
            else if (gp.player.days == 2) {
                dialogueSet = 1;
            } else if (gp.player.days == 3) {
                dialogueSet = 4;
            } else if (gp.player.days == 4) {
                dialogueSet = 5;
            } else if (gp.player.days == 5) {
                dialogueSet = 6;
            } else {
                dialogueSet = 2;
            }
        } else {
            dialogueSet = 3;
        }


        startDialogue(this, dialogueSet);
    }

    public void getDefaultDialogue() {
        dialogueSet = 2;
        startDialogue(this, dialogueSet);
    }


}
