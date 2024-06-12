package Implementation;

import java.util.ArrayList;
import java.util.List;

public class Routine {
    private String name;
    private List<Session> sessions;

    public Routine(String name) {
        this.name = name;
        this.sessions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void addSession(Session session) {
        if (sessions.size() < 5) {
            sessions.add(session);
        }
    }
}
