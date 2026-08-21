package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed,downPressed,leftPressed,rightPressed,enterPressed,spacePressed;
    public char lastTypedChar = '\0';

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            if(code == KeyEvent.VK_ENTER) {
                gp.sound.play();
                gp.gameState = gp.playState;
            }
        }

        // GAME STATE
        if(gp.gameState == gp.playState) {

            // DOOR HOLD MINIGAME
            if (gp.nEvent.isHolding) {
                if (code >= KeyEvent.VK_A && code <= KeyEvent.VK_Z) {
                    lastTypedChar = Character.toLowerCase((char) code);
                }
                return;
            }

            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            if(code == KeyEvent.VK_SPACE) {
                spacePressed = true;
            }
        }
        // DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState) {
            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
    }
}
