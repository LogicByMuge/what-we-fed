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
        dialogue[0][0] = "You split the food";
        dialogue[0][1] = "You ate";

        dialogue[1][0] = "You already ate";

        dialogue[2][0] = "You split the food";
        dialogue[2][1] = "You ate";
        dialogue[2][2] = "Some new dialogue";
    }

    public void getDialogue() {

        if (!alreadyAte) {
            switch (gp.player.days) {
                case 2 -> dialogueSet = 2;
                default -> dialogueSet = 0;
            }
            alreadyAte = true;
        } else {
            dialogueSet = 1;
        }

        startDialogue(this, dialogueSet);
    }

}
