package obj;

import javax.imageio.ImageIO;

public class OBJ_bedroom extends SuperObject{

    public OBJ_bedroom() {
        name = "Bedroom Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objs/bedroomDoor.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;

        setDialogue();
    }

    public void setDialogue() {

        dialogue[0][0] = "I should feed us before I sleep";

        // Night Event
        dialogue[1][0] = "You had a bad dream";

        //
        dialogue[2][0] = "I need to restock our food supplies.";
    }

    public void getDialogue() {

        if(gp.player.hasEaten == false) {
            dialogueSet = 0;
        }
        if(gp.player.hasEaten && gp.player.restocked == false) {
            dialogueSet = 2;
        }

        startDialogue(this, dialogueSet);
    }


    public void getGameOverDialogue() {
        dialogueIndex = 0;
        forceStartDialogue(this, 1);
    }
}
