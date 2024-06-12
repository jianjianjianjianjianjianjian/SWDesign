package Implementation;

public class Run {
    private Routine selectedRoutine;

    public Run(Routine routine) {
        this.selectedRoutine = routine;
    }

    public void runRoutine() {
        System.out.println("Running routine: " + selectedRoutine.getName());
    }

    public void stopRoutine() {
        System.out.println("Stopped routine: " + selectedRoutine.getName());
    }

    public void pauseRoutine() {
        System.out.println("Paused routine: " + selectedRoutine.getName());
    }

    public void resumeRoutine() {
        System.out.println("Resumed routine: " + selectedRoutine.getName());
    }
}
