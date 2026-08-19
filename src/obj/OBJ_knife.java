package obj;

import javax.imageio.ImageIO;

public class OBJ_knife extends SuperObject {

    public OBJ_knife() {
        name = "Knife";

        try {
            image = ImageIO.read(
                    getClass().getResourceAsStream("/objs/Knife.png")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        collision = false;

        dialogueSet = 0;
        setDialogue();
    }

    public void setDialogue() {

        dialogue[0][0] = "She grabs it.";
    }

    public void getDialogue() {
        startDialogue(this, dialogueSet);
    }
}