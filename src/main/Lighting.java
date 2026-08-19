package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gp;

    public Lighting(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {

        // Only activate at night (or during the Day 6 cutscene)
        if (!gp.nEvent.isNight && !gp.cutscene.active && gp.currentMap == 0) {
            return;
        }

        int screenWidth = gp.screenWidth;
        int screenHeight = gp.screenHeight;

        // Create darkness overlay
        BufferedImage darkness = new BufferedImage(
                screenWidth,
                screenHeight,
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = darkness.createGraphics();

        // Darkness
        g.setColor(new Color(0, 0, 0, 220));
        g.fillRect(0, 0, screenWidth, screenHeight);

        // Player's position on screen
        int playerX = gp.player.x + gp.tileSize / 2;
        int playerY = gp.player.y + gp.tileSize / 2;

        // =========================
        // LIGHT SIZE
        // =========================
        int lightRadius = gp.tileSize * 3;

        // Gradient stops
        float[] dist = {
                0.0f,
                0.4f,
                0.75f,
                1.0f
        };

        Color[] colors = {
                new Color(255, 255, 255, 255),
                new Color(255, 255, 255, 180),
                new Color(255, 255, 255, 60),
                new Color(255, 255, 255, 0)
        };

        // Create circular gradient
        RadialGradientPaint light = new RadialGradientPaint(
                new Point(playerX, playerY),
                lightRadius,
                dist,
                colors
        );

        // Remove darkness where the light is
        g.setPaint(light);
        g.setComposite(
                AlphaComposite.getInstance(
                        AlphaComposite.DST_OUT
                )
        );

        g.fillOval(
                playerX - lightRadius,
                playerY - lightRadius,
                lightRadius * 2,
                lightRadius * 2
        );

        g.dispose();

        // Draw darkness onto the game
        g2.drawImage(darkness, 0, 0, null);
    }
}