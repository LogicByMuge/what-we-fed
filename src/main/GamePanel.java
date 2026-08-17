package main;

import entity.Player;
import obj.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

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

    // ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public SuperObject obj[][] = new SuperObject[2][5];

    // WORLD SETTING
    public int currentMap = 0;
    public int map2Section = 1;

    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int dialogueState = 2;
    public final int gameOver = 3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        gameState = playState;
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


    // INPUTS
    public void update() {
        if(gameState == playState) {
            player.update();
            nEvent.update();
            aSetter.updateObjects();
        }
    }

    // TILE RENDERING

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

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

        // LIGHTING
        lighting.draw(g2);

        // UI
        ui.draw(g2);

        g2.dispose();
    }
}
