package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        // index each sound file here, same pattern as image loading
        soundURL[0] = getClass().getResource("/sound/unlock.wav");
        soundURL[1] = getClass().getResource("/sound/knocking.wav");
        soundURL[2] = getClass().getResource("/sound/speak.wav");
        soundURL[3] = getClass().getResource("/sound/dooropen.wav");
        // add more as needed, keep indices documented
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
        clip.close();
    }
}