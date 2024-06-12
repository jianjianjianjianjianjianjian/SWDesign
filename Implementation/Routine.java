package Implementation;

import java.util.ArrayList;
import java.util.List;

public class Routine {
    private String id;
    private String name;
    private List<Session> sessions;

    public Routine(String id, String name) {
        this.id = id;
        this.name = name;
        this.sessions = new ArrayList<>();
    }

    public void addSession(Session session) {
        this.sessions.add(session);
    }

    public void editSession(String sessionId, Session newSession) {
        for (Session session : sessions) {
            if (session.getName().equals(sessionId)) {
                session.setName(newSession.getName());
                session.setDuration(newSession.getDuration());
                break;
            }
        }
    }

    public void deleteSession(String sessionId) {
        sessions.removeIf(session -> session.getName().equals(sessionId));
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
