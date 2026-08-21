package main;

import obj.OBJ_door;
import obj.SuperObject;

import java.awt.*;
import java.util.Random;
import obj.OBJ_bedroom;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_35;
    public boolean messageOn = false;
    public String message = "";
    public boolean taskComplete = false;
    int secondCounter = 0;
    public boolean isSleeping = false;
    public String currentDialogue = "";
    public boolean dialogueIsComplete = false;
    public boolean isPressingEnter = false;
    public SuperObject sObj = new SuperObject();
    int charIndex = 0;
    String combinedText = "";
    String text = "";
    boolean isNight = false;
    Random random = new Random();
    int minigameX = -1;
    int minigameY = -1;
    int lastSequenceIndex = -1;
    public boolean awaitingTaskCompleteDialogue = false;
    public SuperObject doorObj;
    boolean awaitingGameOverDialogue;
    public boolean cutsceneBlackout = false;
    int cutsceneBlackoutCounter = 0;
    public int cutsceneBlackoutDuration = 120; // ~2 seconds at 60 FPS

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

        // CUTSCENE BLACKOUT (takes priority over everything else)
        if (cutsceneBlackout) {
            drawBlankWindow();
            cutsceneBlackoutCounter++;
            if (cutsceneBlackoutCounter > cutsceneBlackoutDuration) {
                cutsceneBlackoutCounter = 0;
                cutsceneBlackout = false;
            }
            return;
        }



        // DIALOGUE
        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // GAME OVER
        else if(gp.gameState == gp.gameOver) {
            drawGameOverScreen();
            secondCounter++;

            if(secondCounter > 120) {
                secondCounter = 0;

                gp.player.x = 626;
                gp.player.y = 273;
                messageOn = false;

                gp.nEvent.isNight = false;
                isNight = false;

                gp.keyH.lastTypedChar = '\0';

                if (gp.nEvent.bedroomObj instanceof OBJ_bedroom) {
                    awaitingGameOverDialogue = true;
                    ((OBJ_bedroom) gp.nEvent.bedroomObj).getGameOverDialogue();
                } else {
                    gp.gameState = gp.playState;
                }
            }
        }

        // NIGHT EVENT
        else if(gp.nEvent.isNight && isNight) {
            drawBlankWindow();
            secondCounter++;

            if(secondCounter > 120) {
                secondCounter = 0;
                isNight = false;
            }
        } else if (isSleeping) {
            drawBlankWindow();

            g2.setFont(arial_35);
            g2.setColor(Color.white);

            text = "DAY " + (gp.player.days + 1);
            int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();

            int x = gp.screenWidth / 2 - textWidth / 2;
            int y = gp.screenHeight / 2 + textHeight / 2;

            g2.drawString(text, x, y);

            secondCounter++;

            if(secondCounter > 120) {
                secondCounter = 0;
                gp.player.days++;
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

                if(taskComplete) {
                    messageOn = false;
                    taskComplete = false;
                    gp.nEvent.isNight = false;
                    isSleeping = true;
                    gp.player.x = 551;
                    gp.player.y = 273;
                }
            }

            // DOOR HOLD MINIGAME
            if (gp.nEvent.isHolding) {
                drawHoldMinigame();
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

        if(sObj.dialogue[sObj.dialogueSet][sObj.dialogueIndex] != null) {
//            currentDialogue = sObj.dialogue[sObj.dialogueSet][sObj.dialogueIndex];

            char characters[] = sObj.dialogue[sObj.dialogueSet][sObj.dialogueIndex].toCharArray();
            if(charIndex < characters.length) {
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;

                char c = characters[charIndex];
                if (charIndex % 2 == 0 && c != ' ') {
                    gp.sound.setFile(2);
                    gp.sound.play();
                }

                charIndex++;
            }

            if(gp.keyH.enterPressed) {
                charIndex = 0;
                combinedText = "";
                if(gp.gameState == gp.dialogueState) {
                    sObj.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
        }  else {
        sObj.dialogueIndex = 0;

        if(gp.gameState == gp.dialogueState) {
            gp.gameState = gp.playState;

            if (awaitingTaskCompleteDialogue) {
                taskComplete = true;
                awaitingTaskCompleteDialogue = false;
            }

            if (awaitingGameOverDialogue) {
                awaitingGameOverDialogue = false;
            }
        }
    }

        int lineHeight = 25;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += lineHeight;
        }

    }

    public void drawSubWindow(int x, int y, int width, int height) {
        drawSubWindow(x, y, width, height, false);
    }

    public void drawSubWindow(int x, int y, int width, int height, boolean isWrong) {
        Color c = isWrong ? new Color(200,0,0,200) : new Color(0,0,0,200);
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

    public void drawHoldMinigame() {
        if (gp.nEvent.sequenceIndex >= gp.nEvent.targetSequence.length()) {
            return;
        }

        char nextChar = gp.nEvent.targetSequence.charAt(gp.nEvent.sequenceIndex);

        if (gp.nEvent.sequenceIndex != lastSequenceIndex) {
            minigameX = gp.tileSize * random.nextInt(11);
            minigameY = gp.tileSize * random.nextInt(11);
            lastSequenceIndex = gp.nEvent.sequenceIndex;
        }

        int width = gp.tileSize * 2;
        int height = gp.tileSize * 2;

        boolean showWrong = gp.nEvent.wrongFlashTimer > 0;
        drawSubWindow(minigameX, minigameY, width, height, showWrong);

        int textX = minigameX + width/2 - 10;
        int textY = minigameY + height/2 + 10;

        g2.setFont(g2.getFont().deriveFont(40F));
        g2.setColor(Color.white);
        g2.drawString(String.valueOf(nextChar), textX, textY);

        double secondsLeft = gp.nEvent.letterTimer / 60.0;
        String timerText = String.format("%.1f", secondsLeft);

        g2.setFont(g2.getFont().deriveFont(18F));
        g2.setColor(showWrong ? Color.RED : Color.white);

        int timerTextWidth = (int) g2.getFontMetrics().getStringBounds(timerText, g2).getWidth();
        int timerX = minigameX + width/2 - timerTextWidth/2;
        int timerY = minigameY - 8;

        g2.drawString(timerText, timerX, timerY);
    }

    public void drawGameOverScreen() {
        drawBlankWindow();

        g2.setFont(arial_35);
        g2.setColor(Color.white);

        text = "The door didn't hold";
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();

        int x = gp.screenWidth / 2 - textWidth / 2;
        int y = gp.screenHeight / 2 + textHeight / 2;

        g2.drawString(text, x, y);

        secondCounter++;

        if(secondCounter > 120) {
            secondCounter = 0;
        }
    }

    public void drawTitleScreen() {
        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
        g2.setColor(Color.WHITE);
        String text = "PRESS ENTER TO WAKE UP";
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();

        int x = gp.screenWidth / 2 - textWidth / 2;
        int y = gp.screenHeight / 2 + textHeight / 2;

        g2.drawString(text, x, y);
    }
}
