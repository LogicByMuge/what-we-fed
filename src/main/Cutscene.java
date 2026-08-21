package main;

import entity.Entity;
import entity.NPC_Survivor;
import obj.OBJ_cutsceneNarrator;

public class Cutscene {

    GamePanel gp;
    public boolean active = false;
    int step = 0;
    int pauseTimer = 0;
    boolean waitingForDialogue = false;

    public NPC_Survivor npc;
    OBJ_cutsceneNarrator narrator;

    // waypoints for the walk to the door
    final int[][] waypoints = {
            {551, 477},
            {143, 477},
            {143, 515}
    };
    int waypointIndex = 0;

    // NPC pop position (same spot player holds the door)
    final int npcPopX = 144;
    final int npcPopY = 515;

    public Cutscene(GamePanel gp) {
        this.gp = gp;
    }

    public void start() {
        active = true;
        step = 0;
        waypointIndex = 0;
        gp.player.canMove = false;

        // set starting position during blackout, before anything is visible
        gp.player.x = 551;
        gp.player.y = 273;

        gp.ui.cutsceneBlackout = true;

        narrator = new OBJ_cutsceneNarrator();
        narrator.setGp(gp);
    }

    public void update() {
        if (!active) return;

        if (waitingForDialogue) {
            if (gp.gameState == gp.playState) {
                waitingForDialogue = false;
                step++;
            }
            return;
        }

        switch (step) {

            case 0: // wait out the blackout
                if (!gp.ui.cutsceneBlackout) {
                    gp.sound.setFile(1);
                    gp.sound.play();
                    waypointIndex = 0;
                    step = 1;
                }
                break;

            case 1: // walk the waypoint queue to the door
                int[] target = waypoints[waypointIndex];
                walkTo(gp.player, target[0], target[1]);
                animate(gp.player);
                if (reached(gp.player, target[0], target[1])) {
                    gp.sound.setFile(1);
                    gp.sound.play();
                    waypointIndex++;
                    if (waypointIndex >= waypoints.length) {
                        idle(gp.player);
                        pauseTimer = 30;
                        step = 2;
                    }
                }
                break;

            case 2: // holding the door, brief pause
                if(pauseTimer == 30) {
                    gp.sound.setFile(1);
                    gp.sound.play();
                }
                if (--pauseTimer <= 0) {
                    step = 3;
                }
                break;

            case 3: // door budges (hook your visual/sprite swap here)
                gp.sound.setFile(3);
                gp.sound.play();
                pauseTimer = 60; // ~1 second before NPC pops
                step = 4;
                break;

            case 4:
                if (--pauseTimer <= 0) step = 5;
                break;

            case 5: // player backs away
                int backX = 143;
                int backY = 515 - gp.tileSize * 2;
                walkTo(gp.player, backX, backY);
                animate(gp.player);
                if (reached(gp.player, backX, backY)) {
                    gp.player.y += 5;
                    gp.player.direction = "down"; // face down
                    idle(gp.player);

                    pauseTimer = 20;
                    step = 6;
                }
                break;


            case 6:
                if (--pauseTimer <= 0) step = 7;
                break;

            case 7: // NPC pops in, no walk, just appears
                if (npc == null) npc = gp.aSetter.survivor;
                npc.x = npcPopX;
                npc.y = npcPopY;
                npc.direction = "up";
                npc.visible = true;
                pauseTimer = 30;
                step = 8;
                break;

            case 8:
                if (--pauseTimer <= 0) step = 9;
                break;

            case 9: sayLine("He doesn't attack."); break;
            case 10: sayLine("\"Mara. It's me.\" A voice she knows."); break;
            case 11: sayLine("\"Please. You can't keep doing this.\""); break;
            case 12: sayLine("\"There's nothing left out there anymore.\nNo one's coming to fix this.\nIt's just us now, whoever's left.\""); break;
            case 13: sayLine("\"I know you don't want to hear it but shes already dead.\""); break;
            case 14: sayLine("\"It wasn't your fault. None of us saw it coming.\""); break;

            case 15:
                // Player walks DOWN toward the NPC
                if (gp.player.y < npc.y) {

                    gp.player.y += gp.player.speed;
                    gp.player.direction = "down";
                    animate(gp.player);

                } else {

                    // Stop exactly at the NPC's Y position
                    gp.player.y = npc.y - 15;
                    gp.sound.setFile(5);
                    gp.sound.play();

                    idle(gp.player);
                    // Continue to the next dialogue
                    pauseTimer = 60;
                    step = 16;
                }
                break;

            case 16:
                if (--pauseTimer <= 0) step = 17;
                break;

            case 17: sayLine("\"I was there too. I couldn't reach either of you in time.\""); break;
            case 18: sayLine("\"I just wanted to bring you back.\""); break;

            case 19:
                gp.sound.setFile(6);
                gp.sound.play();
                npc.isDead = true;
                pauseTimer = 40;
                step = 20;
                break;

            case 20:
                if (--pauseTimer <= 0) step = 21;
                break;

            case 21:
                gp.ui.isSleeping = true;
                step = 22;
                break;

            case 22:
                if (!gp.ui.isSleeping) {
                    active = false;
                    gp.gameState = gp.endingState;
                    gp.endingIndex = 0;
                    gp.endingTimer = 0;
                }
                break;
        }
    }

    private void sayLine(String text) {
        narrator.dialogue[0][0] = text;
        narrator.dialogueIndex = 0;
        narrator.forceStartDialogue(narrator, 0);
        waitingForDialogue = true;
    }

    private void walkTo(Entity e, int tx, int ty) {
        if (e.x < tx) { e.x += e.speed; e.direction = "right"; }
        else if (e.x > tx) { e.x -= e.speed; e.direction = "left"; }
        else if (e.y < ty) { e.y += e.speed; e.direction = "down"; }
        else if (e.y > ty) { e.y -= e.speed; e.direction = "up"; }
    }

    private boolean reached(Entity e, int tx, int ty) {
        return Math.abs(e.x - tx) <= e.speed && Math.abs(e.y - ty) <= e.speed;
    }

    // manually drive the same walk-cycle toggle Player.update() does,
    // since the cutscene bypasses normal input-driven movement
    private void animate(Entity e) {
        e.spriteCounter++;
        if (e.spriteCounter > 12) {
            e.spriteNumber = (e.spriteNumber == 1) ? 2 : 1;
            e.spriteCounter = 0;
        }
    }

    private void idle(Entity e) {
        e.spriteNumber = 1;
        e.spriteCounter = 0;
    }
}