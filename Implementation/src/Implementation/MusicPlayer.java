package Implementation;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class MusicPlayer {
    private String currentTrack;
    private boolean isPlaying;
    private Player player;

    public void setTrack(String trackUrl) {
        this.currentTrack = trackUrl;
    }

    public void turnOn() {
        isPlaying = true;
        new Thread(() -> {
            try {
                InputStream is = new URL(currentTrack).openStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                player = new Player(bis);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void turnOff() {
        isPlaying = false;
        if (player != null) {
            player.close();
        }
    }
}
