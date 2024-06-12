package Implementation;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicPlayer {
    private String currentTrack;
    private boolean isPlaying;
    private Clip clip;

    public MusicPlayer() {
        this.currentTrack = "resources/music.wav"; // 기본 음악 파일 경로 설정
    }

    public void turnOn() {
        isPlaying = true;
        new Thread(() -> {
            try {
                playMusic();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void playMusic() throws Exception {
        File audioFile = new File(currentTrack);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }

    public void turnOff() {
        isPlaying = false;
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
