package main;

import entity.Entity;
import entity.Player;
import obj.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTING
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public NightEvent nEvent = new NightEvent(this);
    public Lighting lighting = new Lighting(this);
    public Cutscene cutscene = new Cutscene(this);
    public Sound sound = new Sound();
    public Sound music = new Sound();

    // ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public SuperObject obj[][] = new SuperObject[2][10];
    public Entity npc[] = new Entity[1];

    // WORLD SETTING
    public int currentMap = 0;
    public int map2Section = 1;

    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int dialogueState = 2;
    public final int gameOver = 3;
    public final int endingState = 4;

    BufferedImage[] endingImages = new BufferedImage[3];
    int endingIndex = 0;
    int endingTimer = 0;
    final int endingImageDuration = 240;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        gameState = playState;

        try {
            endingImages[0] = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/ending/scene1.png"));
            endingImages[1] = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/ending/s2.png"));
            endingImages[2] = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/ending/s3.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // GAME LOOP
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void update() {
        if(gameState == playState) {
            player.update();
            nEvent.update();
            aSetter.updateObjects();
            if (cutscene.active) cutscene.update();
        }
        else if (gameState == endingState) {
            endingTimer++;
            if (endingTimer > endingImageDuration) {
                endingTimer = 0;
                endingIndex++;
                if (endingIndex >= endingImages.length) {
                    System.exit(0);
                }
            }
        }
    }

    // TILE RENDERING

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == endingState) {
            if (endingImages[endingIndex] != null) {
                g2.drawImage(endingImages[endingIndex], 0, 0, screenWidth, screenHeight, null);
            }
            g2.dispose();
            return;
        }

        // TILE
        tileM.draw(g2);

        // OBJECT
        for (int i = 0; i < obj[1].length; i++) {
            if (obj[currentMap][i] != null) {
                obj[currentMap][i].draw(g2, this);
            }
        }

        // PLAYER
        player.draw(g2);

        // NPC
        for(int i = 0; i < npc.length; i++) {
            if(npc[i] != null && npc[i].visible) {
                npc[i].draw(g2);
            }
        }



        // LIGHTING
        lighting.draw(g2);

        // UI
        ui.draw(g2);

        g2.dispose();
    }
}
