package obj;

import javax.imageio.ImageIO;

public class OBJ_corridorDoor extends SuperObject{

    public OBJ_corridorDoor() {
        name = "Corridor Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objs/corridorDoor.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;

        setDialogue();
    }

    public void setDialogue() {
        dialogue[0][0] = "I should find food first.";
    }

    public void getDialogue() {
        startDialogue(this,dialogueIndex);
    }
}
