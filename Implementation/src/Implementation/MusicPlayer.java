package Implementation;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class MusicPlayer {
    private String currentTrack;
    private Clip clip;

    public void setTrack(String trackUrl) {
        this.currentTrack = trackUrl;
    }

    public void turnOn() {
        try {
            URL url = new URL(currentTrack);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void turnOff() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
