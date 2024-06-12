package Implementation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MiracleMorningSupporter {
    private List<Routine> routines;
    private History history;
    private CustomTimer timer;
    private MusicPlayer musicPlayer;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel, addRoutinePanel, runRoutinePanel, editRoutinePanel;
    private JButton pauseButton, resumeButton, playMusicButton, stopMusicButton;
    private JLabel currentSessionLabel, timerLabel;
    private Routine currentRunningRoutine;
    private int currentSessionIndex;
    private boolean routineRunning;
    private Timer updateTimer;

    public MiracleMorningSupporter() {
        this.routines = new ArrayList<>();
        this.history = new History("user123");
        this.timer = new CustomTimer();
        this.musicPlayer = new MusicPlayer();
        createAndShowGUI();
    }

    public void addRoutine(Routine routine) {
        routines.add(routine);
    }

    public void viewRoutines(JTextArea textArea) {
        StringBuilder sb = new StringBuilder();
        for (Routine routine : routines) {
            sb.append("Routine Name: ").append(routine.getName()).append("\n");
            for (Session session : routine.getSessions()) {
                sb.append("  Session: ").append(session.getName()).append(", Duration: ").append(session.getDuration()).append(" secs\n");
            }
        }
        textArea.setText(sb.toString());
    }

    public void editRoutine(String routineName, Routine newRoutine) {
        for (int i = 0; i < routines.size(); i++) {
            if (routines.get(i).getName().equals(routineName)) {
                routines.set(i, newRoutine);
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Routine not found.");
    }

    public void deleteRoutine(String routineName) {
        boolean found = routines.removeIf(routine -> routine.getName().equals(routineName));
        if (found) {
            JOptionPane.showMessageDialog(frame, "Routine deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(frame, "Routine not found.");
        }
    }

    public void runRoutine(String routineName) {
        for (Routine routine : routines) {
            if (routine.getName().equals(routineName)) {
                currentRunningRoutine = routine;
                currentSessionIndex = 0;
                routineRunning = true;
                List<Session> sessions = routine.getSessions();
                cardLayout.show(frame.getContentPane(), "RunRoutine");
                runSessionsSequentially(sessions, 0);
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Routine not found.");
    }

    private void runSessionsSequentially(List<Session> sessions, int index) {
        if (index < sessions.size()) {
            Session session = sessions.get(index);
            currentSessionLabel.setText("Running session: " + session.getName());
            System.out.println("Running session: " + session.getName() + " for " + session.getDuration() + " seconds");
            timer.setTimer(session.getDuration());
            timer.start(() -> runSessionsSequentially(sessions, index + 1));
            currentSessionIndex = index;

            // Update the remaining time on the UI
            if (updateTimer != null) {
                updateTimer.cancel();
            }
            updateTimer = new Timer();
            updateTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        int remainingTime = timer.getRemainingTime();
                        timerLabel.setText("Time Remaining: " + remainingTime + " seconds");
                    });
                }
            }, 0, 1000);
        } else {
            JOptionPane.showMessageDialog(frame, "All sessions completed!");
            history.addSuccess(new Date());
            System.out.println("All sessions completed.");
            cardLayout.show(frame.getContentPane(), "Main");
            routineRunning = false;
            if (updateTimer != null) {
                updateTimer.cancel();
            }
        }
    }

    public void viewSuccessHistory(JTextArea textArea) {
        List<Date> successDates = history.viewHistory();
        StringBuilder sb = new StringBuilder();
        for (Date date : successDates) {
            sb.append("Success Date: ").append(date).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Miracle Morning Supporter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);

        mainPanel = new JPanel();
        addRoutinePanel = new JPanel();
        runRoutinePanel = new JPanel();
        editRoutinePanel = new JPanel();

        // Main Panel
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(800, 300)); // 텍스트 영역 크기 확대

        mainPanel.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 2, 10, 10));

        JButton viewRoutinesButton = createButton("View Routines");
        JButton addRoutineButton = createButton("Add Routine");
        JButton editRoutineButton = createButton("Edit Routine");
        JButton deleteRoutineButton = createButton("Delete Routine");
        JButton runRoutineButton = createButton("Run Routine");
        JButton viewSuccessHistoryButton = createButton("View Success History");
        JButton exitButton = createButton("Exit");

        buttonPanel.add(viewRoutinesButton);
        buttonPanel.add(addRoutineButton);
        buttonPanel.add(editRoutineButton);
        buttonPanel.add(deleteRoutineButton);
        buttonPanel.add(runRoutineButton);
        buttonPanel.add(viewSuccessHistoryButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH); // 버튼 패널을 위쪽으로 이동
        mainPanel.add(scrollPane, BorderLayout.CENTER); // 텍스트 영역을 가운데로 이동

        // Add Routine Panel
        addRoutinePanel.setLayout(new GridLayout(7, 2, 10, 10)); // 레이아웃 행 수 조정
        JTextField routineNameField = new JTextField();
        JTextField session1NameField = new JTextField();
        JTextField session1DurationField = new JTextField();
        JTextField session2NameField = new JTextField();
        JTextField session2DurationField = new JTextField();
        JTextField session3NameField = new JTextField();
        JTextField session3DurationField = new JTextField();
        JTextField session4NameField = new JTextField();
        JTextField session4DurationField = new JTextField();
        JTextField session5NameField = new JTextField();
        JTextField session5DurationField = new JTextField();
        JButton saveRoutineButton = createButton("Save Routine");
        JButton backButton1 = createButton("Back");

        addRoutinePanel.add(new JLabel("Routine Name"));
        addRoutinePanel.add(routineNameField);
        addRoutinePanel.add(new JLabel("Session 1 Name"));
        addRoutinePanel.add(session1NameField);
        addRoutinePanel.add(new JLabel("Session 1 Duration (seconds)"));
        addRoutinePanel.add(session1DurationField);
        addRoutinePanel.add(new JLabel("Session 2 Name"));
        addRoutinePanel.add(session2NameField);
        addRoutinePanel.add(new JLabel("Session 2 Duration (seconds)"));
        addRoutinePanel.add(session2DurationField);
        addRoutinePanel.add(new JLabel("Session 3 Name"));
        addRoutinePanel.add(session3NameField);
        addRoutinePanel.add(new JLabel("Session 3 Duration (seconds)"));
        addRoutinePanel.add(session3DurationField);
        addRoutinePanel.add(new JLabel("Session 4 Name"));
        addRoutinePanel.add(session4NameField);
        addRoutinePanel.add(new JLabel("Session 4 Duration (seconds)"));
        addRoutinePanel.add(session4DurationField);
        addRoutinePanel.add(new JLabel("Session 5 Name"));
        addRoutinePanel.add(session5NameField);
        addRoutinePanel.add(new JLabel("Session 5 Duration (seconds)"));
        addRoutinePanel.add(session5DurationField);
        addRoutinePanel.add(saveRoutineButton);
        addRoutinePanel.add(backButton1);

        // Edit Routine Panel
        editRoutinePanel.setLayout(new GridLayout(7, 2, 10, 10)); // 레이아웃 행 수 조정
        JTextField editRoutineNameField = new JTextField();
        JTextField editSession1NameField = new JTextField();
        JTextField editSession1DurationField = new JTextField();
        JTextField editSession2NameField = new JTextField();
        JTextField editSession2DurationField = new JTextField();
        JTextField editSession3NameField = new JTextField();
        JTextField editSession3DurationField = new JTextField();
        JTextField editSession4NameField = new JTextField();
        JTextField editSession4DurationField = new JTextField();
        JTextField editSession5NameField = new JTextField();
        JTextField editSession5DurationField = new JTextField();
        JButton saveEditRoutineButton = createButton("Save Routine");
        JButton backButton3 = createButton("Back");

        editRoutinePanel.add(new JLabel("Routine Name"));
        editRoutinePanel.add(editRoutineNameField);
        editRoutinePanel.add(new JLabel("Session 1 Name"));
        editRoutinePanel.add(editSession1NameField);
        editRoutinePanel.add(new JLabel("Session 1 Duration (seconds)"));
        editRoutinePanel.add(editSession1DurationField);
        editRoutinePanel.add(new JLabel("Session 2 Name"));
        editRoutinePanel.add(editSession2NameField);
        editRoutinePanel.add(new JLabel("Session 2 Duration (seconds)"));
        editRoutinePanel.add(editSession2DurationField);
        editRoutinePanel.add(new JLabel("Session 3 Name"));
        editRoutinePanel.add(editSession3NameField);
        editRoutinePanel.add(new JLabel("Session 3 Duration (seconds)"));
        editRoutinePanel.add(editSession3DurationField);
        editRoutinePanel.add(new JLabel("Session 4 Name"));
        editRoutinePanel.add(editSession4NameField);
        editRoutinePanel.add(new JLabel("Session 4 Duration (seconds)"));
        editRoutinePanel.add(editSession4DurationField);
        editRoutinePanel.add(new JLabel("Session 5 Name"));
        editRoutinePanel.add(editSession5NameField);
        editRoutinePanel.add(new JLabel("Session 5 Duration (seconds)"));
        editRoutinePanel.add(editSession5DurationField);
        editRoutinePanel.add(saveEditRoutineButton);
        editRoutinePanel.add(backButton3);

        // Run Routine Panel
        runRoutinePanel.setLayout(new BorderLayout());
        currentSessionLabel = new JLabel("Current Session: ");
        timerLabel = new JLabel("Time Remaining: ");
        JPanel runInfoPanel = new JPanel(new GridLayout(2, 1));
        runInfoPanel.add(currentSessionLabel);
        runInfoPanel.add(timerLabel);

        JPanel runButtonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        pauseButton = createButton("Pause");
        resumeButton = createButton("Resume");
        playMusicButton = createButton("Play Music");
        stopMusicButton = createButton("Stop Music");
        JButton backButton2 = createButton("Back");

        runButtonPanel.add(pauseButton);
        runButtonPanel.add(resumeButton);
        runButtonPanel.add(playMusicButton);
        runButtonPanel.add(stopMusicButton);
        runButtonPanel.add(backButton2);

        runRoutinePanel.add(runInfoPanel, BorderLayout.CENTER);
        runRoutinePanel.add(runButtonPanel, BorderLayout.SOUTH);

        // Add panels to frame
        frame.add(mainPanel, "Main");
        frame.add(addRoutinePanel, "AddRoutine");
        frame.add(editRoutinePanel, "EditRoutine");
        frame.add(runRoutinePanel, "RunRoutine");

        viewRoutinesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewRoutines(textArea);
            }
        });

        addRoutineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "AddRoutine");
            }
        });

        editRoutineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String editName = JOptionPane.showInputDialog(frame, "Enter Routine Name to Edit:");
                Routine routineToEdit = null;
                for (Routine routine : routines) {
                    if (routine.getName().equals(editName)) {
                        routineToEdit = routine;
                        break;
                    }
                }

                if (routineToEdit != null) {
                    editRoutineNameField.setText(routineToEdit.getName());
                    List<Session> sessions = routineToEdit.getSessions();
                    JTextField[] sessionNameFields = { editSession1NameField, editSession2NameField, editSession3NameField, editSession4NameField, editSession5NameField };
                    JTextField[] sessionDurationFields = { editSession1DurationField, editSession2DurationField, editSession3DurationField, editSession4DurationField, editSession5DurationField };
                    for (int i = 0; i < sessions.size() && i < sessionNameFields.length; i++) {
                        sessionNameFields[i].setText(sessions.get(i).getName());
                        sessionDurationFields[i].setText(String.valueOf(sessions.get(i).getDuration()));
                    }
                    cardLayout.show(frame.getContentPane(), "EditRoutine");
                } else {
                    JOptionPane.showMessageDialog(frame, "Routine not found.");
                }
            }
        });

        deleteRoutineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deleteName = JOptionPane.showInputDialog(frame, "Enter Routine Name to Delete:");
                deleteRoutine(deleteName);
            }
        });

        runRoutineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String runName = JOptionPane.showInputDialog(frame, "Enter Routine Name to Run:");
                runRoutine(runName);
            }
        });

        viewSuccessHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSuccessHistory(textArea);
            }
        });

        playMusicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String trackUrl = JOptionPane.showInputDialog(frame, "Enter Track URL:");
                musicPlayer.setTrack(trackUrl);
                musicPlayer.turnOn();
            }
        });

        stopMusicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicPlayer.turnOff();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        saveRoutineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = routineNameField.getText();
                Routine newRoutine = new Routine(name);
                JTextField[] sessionNameFields = { session1NameField, session2NameField, session3NameField, session4NameField, session5NameField };
                JTextField[] sessionDurationFields = { session1DurationField, session2DurationField, session3DurationField, session4DurationField, session5DurationField };
                for (int i = 0; i < sessionNameFields.length; i++) {
                    if (!sessionNameFields[i].getText().isEmpty() && isNaturalNumber(sessionDurationFields[i].getText())) {
                        String sessionName = sessionNameFields[i].getText();
                        int sessionDuration = Integer.parseInt(sessionDurationFields[i].getText());
                        newRoutine.addSession(new Session(sessionName, sessionDuration));
                    }
                }
                addRoutine(newRoutine);
                JOptionPane.showMessageDialog(frame, "Routine added successfully.");
                cardLayout.show(frame.getContentPane(), "Main");
            }
        });

        saveEditRoutineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = editRoutineNameField.getText();
                Routine newRoutine = new Routine(name);
                JTextField[] sessionNameFields = { editSession1NameField, editSession2NameField, editSession3NameField, editSession4NameField, editSession5NameField };
                JTextField[] sessionDurationFields = { editSession1DurationField, editSession2DurationField, editSession3DurationField, editSession4DurationField, editSession5DurationField };
                for (int i = 0; i < sessionNameFields.length; i++) {
                    if (!sessionNameFields[i].getText().isEmpty() && isNaturalNumber(sessionDurationFields[i].getText())) {
                        String sessionName = sessionNameFields[i].getText();
                        int sessionDuration = Integer.parseInt(sessionDurationFields[i].getText());
                        newRoutine.addSession(new Session(sessionName, sessionDuration));
                    }
                }
                editRoutine(name, newRoutine);
                cardLayout.show(frame.getContentPane(), "Main");
            }
        });

        backButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "Main");
            }
        });

        backButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "Main");
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.pause();
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.resume();
            }
        });

        backButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (routineRunning) {
                    int confirm = JOptionPane.showConfirmDialog(frame, "Routine is still running. Are you sure you want to exit?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        timer.stop();
                        routineRunning = false;
                        cardLayout.show(frame.getContentPane(), "Main");
                    }
                } else {
                    cardLayout.show(frame.getContentPane(), "Main");
                }
            }
        });

        frame.setVisible(true);
    }

    private boolean isNaturalNumber(String str) {
        try {
            int num = Integer.parseInt(str);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MiracleMorningSupporter();
            }
        });
    }
}