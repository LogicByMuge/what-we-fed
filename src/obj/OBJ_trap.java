package obj;

import javax.imageio.ImageIO;

public class OBJ_trap extends SuperObject{

    public OBJ_trap() {
        name = "Trap";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objs/trap.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = false;

        setDialogue();
    }

    public void setDialogue() {
        dialogue[0][0] = "Theres something caught in it.";
    }

    public void getDialogue() {
        startDialogue(this,dialogueIndex);
    }
}
