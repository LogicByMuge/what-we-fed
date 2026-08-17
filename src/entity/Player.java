package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    public int days = 1;
    public boolean canSleep = false;
    public int objIndex = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // HITBOX
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 16;

        setDafaultValues();
        getPlayerImage();
    }

    public void setDafaultValues() {
        x = 200;
        y = 300;
        speed = 3;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right2.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        if (keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true) {
            if(keyH.upPressed == true) {
                direction = "up";
            }
            else if(keyH.downPressed == true) {
                direction = "down";
            }
            else if(keyH.leftPressed == true) {
                direction = "left";
            }
            else if(keyH.rightPressed == true) {
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            gp.cChecker.checkObject(this, true);

                if (collisionOn == false) {
                    switch (direction) {
                        case "up":
                            y -= speed;
                            break;
                        case "down":
                            y += speed;
                            break;
                        case "left":
                            x -= speed;
                            break;
                        case "right":
                            x += speed;
                            break;
                    }
                }

            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }


        }

        if (keyH.enterPressed) {
            objIndex = gp.cChecker.checkObjectInFront(this, gp.obj[gp.currentMap]);
            if (objIndex != 999) {
                interactObject(objIndex);
            }
            keyH.enterPressed = false;
        }

        // MAP 2 SCREEN TRANSITION
        if (gp.currentMap == 1) {

            // RIGHT SIDE → LEFT SIDE
            if (gp.map2Section == 1 && x <= 41) {

                gp.map2Section = 0;

                // Put player on the right side of the LEFT screen
                x = gp.screenWidth - gp.tileSize;
            }

            // LEFT SIDE → RIGHT SIDE
            else if (gp.map2Section == 0 && x >= gp.screenWidth - gp.tileSize) {

                gp.map2Section = 1;

                // Put player on the left side of the RIGHT screen
                x = 53;
            }
        }

    }

    public void interactObject(int i) {
        if(i != 999) {
            String objectName = gp.obj[gp.currentMap][i].name;
            switch (objectName) {
                case "Table":
                    gp.obj[0][i].getDialogue();
                    if(!gp.nEvent.isNight) {
                        canSleep = true;
                    }
                    break;
                case "Bedroom Door":
                    if (canSleep) {
                        canSleep = false;
                        gp.nEvent.bedroomObj = gp.obj[0][i];
                        gp.nEvent.startEvent();
                       // gp.ui.isSleeping = true;

                        for (int j = 0; j < gp.obj.length; j++) {
                            if (gp.obj[j] != null) {
                                gp.obj[0][j].alreadyAte = false;
                            }
                        }
                    } else {
                        gp.obj[0][i].getDialogue();
                    }
                    break;
                case "Fridge":
                    gp.obj[0][i].getDialogue();
                    break;
                case "Chair":
                    gp.obj[0][i].getDialogue();
                    break;
                case "Door":
                    gp.currentMap = 1;
                    y = 300;
                    x = 200;
                    //gp.obj[0][i].getDialogue();
                    if(gp.nEvent.isNight) {
                        gp.nEvent.isHolding = true;
                        gp.nEvent.doorObj = gp.obj[0][i];
                    }
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNumber == 1) {
                    image = up1;
                }
                if(spriteNumber == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNumber == 1) {
                    image = down1;
                }
                if(spriteNumber == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNumber == 1) {
                    image = left1;
                }
                if(spriteNumber == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNumber == 1) {
                    image = right1;
                }
                if(spriteNumber == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize,null);
    }
}
