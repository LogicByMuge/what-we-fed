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
    }

    public void getDialogue() {
        startDialogue(this,dialogueIndex);
    }
}
