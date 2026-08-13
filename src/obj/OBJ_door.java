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
        dialogue[0][0] = "I shouldn't go out yet";
        dialogue[1][0] = "They're gone...";
    }

    public void getDialogue() {
        startDialogue(this,dialogueIndex);
    }

    public void getCompleteDialogue() {
        dialogueIndex = 0;
        forceStartDialogue(this, 1);
    }

    public void getKeys() {

    }
}