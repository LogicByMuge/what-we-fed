package obj;

import javax.imageio.ImageIO;

public class OBJ_fridge extends SuperObject{

    public OBJ_fridge() {
        name = "Fridge";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objs/fridgeBottom.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;

        setDialogue();
    }

    public void setDialogue() {
        dialogue[0] = "Hello World";
    }

    public void getDialogue() {
        if (dialogueIndex >= dialogue.length || dialogue[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;
    }

}
