package Implementation;

import java.util.Timer;
import java.util.TimerTask;

public class CustomTimer {
    private int duration;
    private Timer timer;
    private TimerTask task;
    private Runnable onFinish;
    private int remainingTime;
    private boolean isPaused;

    public CustomTimer() {
        this.timer = new Timer();
    }

    public void setTimer(int duration) {
        this.duration = duration;
        this.remainingTime = duration;
        this.isPaused = false;
    }

    public void start(Runnable onFinish) {
        this.onFinish = onFinish;
        task = new TimerTask() {
            @Override
            public void run() {
                if (remainingTime > 0 && !isPaused) {
                    System.out.println("Remaining time: " + remainingTime + " seconds");
                    remainingTime--;
                } else if (remainingTime == 0) {
                    task.cancel();
                    if (onFinish != null) {
                        onFinish.run();
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public int getRemainingTime() {
        return remainingTime;
    }
}
