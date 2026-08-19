package main;

import obj.OBJ_door;
import obj.SuperObject;

public class NightEvent {
    public SuperObject bedroomObj;
    GamePanel gp;
    public boolean isNight;
    public boolean isHolding;
    public String targetSequence = "";
    private final String[] daySequences = {
            "dlohvi",  // day 1
            "viyats",     // day 2
            "teiuq",   // day 3
            "llits",    // day 4
            "yrros" // day 5
    };
    public int sequenceIndex = 0;
    public SuperObject doorObj;
    int life = 3;
    public static final int LETTER_TIME_LIMIT = 240;
    public int letterTimer;
    public int wrongFlashTimer = 0;
    private boolean justStarted = false;


    public NightEvent(GamePanel gp) {
        this.gp = gp;
    }

    public void startEvent() {
        if (gp.player.days == 6) {
            gp.cutscene.start();
            return;
        }
        gp.ui.isNight = true;
        isNight = true;

        sequenceIndex = 0;
        letterTimer = LETTER_TIME_LIMIT;
        wrongFlashTimer = 0;
        gp.keyH.lastTypedChar = '\0';

        gp.ui.showMessage("Hold the door");

    }


    public void update() {
        if (justStarted) {
            justStarted = false;
            gp.keyH.lastTypedChar = '\0';
            return;
        }
        int dayIdx = gp.player.days - 1;
        if (dayIdx >= 0 && dayIdx < daySequences.length) {
            targetSequence = daySequences[dayIdx];
        }

        if (isNight && isHolding) {
            char typed = gp.keyH.lastTypedChar;

            if (typed != '\0') {
                if (typed == targetSequence.charAt(sequenceIndex)) {
                    sequenceIndex++;
                    letterTimer = LETTER_TIME_LIMIT;
                } else {
                    life--;
                    wrongFlashTimer = 15;
                    letterTimer = LETTER_TIME_LIMIT;
                }
                gp.keyH.lastTypedChar = '\0';
            } else {

                letterTimer--;
                if (letterTimer <= 0) {
                    life--;
                    wrongFlashTimer = 15;
                    letterTimer = LETTER_TIME_LIMIT;
                }
            }

            if (wrongFlashTimer > 0) {
                wrongFlashTimer--;
            }

            if (life <= 0) {
                isHolding = false;
                isNight = false;
                life = 3;

                gp.keyH.lastTypedChar = '\0';

                gp.gameState = gp.gameOver;
                return;
            }

            if (sequenceIndex >= targetSequence.length()) {
                isHolding = false;
                sequenceIndex = 0;
                life = 3;

                if (doorObj instanceof OBJ_door) {
                    gp.ui.awaitingTaskCompleteDialogue = true;
                    ((OBJ_door) doorObj).getCompleteDialogue();
                }
            }
        }
    }
    }