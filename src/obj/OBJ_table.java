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

        setDialogue();
    }

    public void setDialogue() {
        dialogue[0] = "You ate";
    }

    public void getDialogue() {
        if (dialogueIndex >= dialogue.length || dialogue[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;
    }

}
