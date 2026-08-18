package obj;

import javax.imageio.ImageIO;

public class OBJ_lockedDoor extends SuperObject{

    public OBJ_lockedDoor() {
        name = "Locked Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objs/lockedDoor.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;

        setDialogue();
    }

    public void setDialogue() {
        dialogue[0][0] = "Its locked.";
    }

    public void getDialogue() {
        startDialogue(this,dialogueIndex);
    }
}
