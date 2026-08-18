package obj;

import javax.imageio.ImageIO;

public class OBJ_shelf extends SuperObject{

    public OBJ_shelf() {
        name = "Shelf";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objs/shelf.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;

        setDialogue();
    }

    public void setDialogue() {
        dialogue[0][0] = "WOA food.";
        dialogue[0][1] = "wala pa akong maisip na dialogue";
    }

    public void getDialogue() {
        startDialogue(this,dialogueIndex);
    }
}
