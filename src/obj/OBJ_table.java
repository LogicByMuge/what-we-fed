package obj;

import javax.imageio.ImageIO;

public class OBJ_table extends SuperObject{


    public OBJ_table() {
        name = "Table";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objs/table.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;

        dialogueSet = -1;
        setDialogue();
    }

    public void setDialogue() {

        // day 1
        dialogue[0][0] = "She sets two bowls down.";
        dialogue[0][1] = "Same as always.";

        dialogue[1][0] = "There's nothing left to do here.";

        // day 2
        dialogue[2][0] = "Same bowls.";
        dialogue[2][1] = "Same spot.";
        dialogue[2][2] = "It's quieter outside than it used to be.";
        dialogue[2][3] = "She's not sure if that's better or worse.";

        // day 3
        dialogue[3][0] = "Molly used to talk through the whole meal before\n" +
                "it happened.";
        dialogue[3][1] = "Mara misses the noise.";

        // day 4
        dialogue[4][0] = "That night was supposed to be just the two of them.";
        dialogue[4][1] = "Watching the sky.";
        dialogue[4][2] = "It was her idea.";

        // day 5
        dialogue[5][0] = "She doesn't set out two bowls today.";
        dialogue[5][1] = "She's not sure when she'll be able to again.";

        // day 6
        dialogue[6][0] = "One bowl today.";
        dialogue[6][1] = "She sets it down and just looks at it for awhile.";
    }

    public void getDialogue() {

        if (!alreadyAte) {
            switch (gp.player.days) {
                case 2 -> dialogueSet = 2;
                case 3 -> dialogueSet = 3;
                case 4 -> dialogueSet = 4;
                case 5 -> dialogueSet = 5;
                case 6 -> dialogueSet = 6;
                default -> dialogueSet = 0;
            }
            alreadyAte = true;
        } else {
            dialogueSet = 1;
        }

        startDialogue(this, dialogueSet);
    }

}
