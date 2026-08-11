package main;

import java.awt.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_35;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    int secondCounter = 0;
    public boolean isSleeping = false;
    public String currentDialogue = "";

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_35 = new Font("Arial", Font.PLAIN, 35);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        // DIALOGUE
        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

        // MESSAGES
        if (isSleeping) {
            drawBlankWindow();

            g2.setFont(arial_35);
            g2.setColor(Color.white);

            String text = "DAY " + gp.player.days;
            int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();

            int x = gp.screenWidth / 2 - textWidth / 2;
            int y = gp.screenHeight / 2 + textHeight / 2;

            g2.drawString(text, x, y);

            secondCounter++;
            if(secondCounter > 120) {
                secondCounter = 0;
                isSleeping = false;
            }
        } else {
            g2.setFont(arial_35);
            g2.setColor(Color.white);
            g2.drawString("Day " + gp.player.days,50,55);

            // MESSAGE
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message,gp.tileSize/2,gp.tileSize*5);

                messageCounter++;
                if(messageCounter > 60) {
                    messageCounter = 0;
                    messageOn = false;
                }
        }

        }
    }

    public void drawDialogueScreen() {

        // WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 8;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*3;

        drawSubWindow(x,y,width,height);

        x+= gp.tileSize;
        y += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(20F));
        g2.drawString(currentDialogue,x,y);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10,25,25);
    }

    public void drawBlankWindow() {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    }

}
