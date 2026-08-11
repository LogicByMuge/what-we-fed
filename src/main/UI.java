package main;

import java.awt.*;

public class UI {
    GamePanel gp;
    Font arial_35;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    int secondCounter = 0;
    public boolean isSleeping = false;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_35 = new Font("Arial", Font.PLAIN, 35);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        if (isSleeping) {
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
                if(messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
        }

        }
    }
}
