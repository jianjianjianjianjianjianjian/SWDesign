package Implementation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MiracleMorningSupporter {
    private List<Routine> routines;
    private History history;
    private Timer timer;
    private MusicPlayer musicPlayer;

    public MiracleMorningSupporter() {
        this.routines = new ArrayList<>();
        this.history = new History("user123");
        this.timer = new Timer();
        this.musicPlayer = new MusicPlayer();
    }

    public void addRoutine(Routine routine) {
        routines.add(routine);
    }

    public void viewRoutines(JTextArea textArea) {
        StringBuilder sb = new StringBuilder();
        for (Routine routine : routines) {
            sb.append("Routine ID: ").append(routine.getId()).append(", Name: ").append(routine.getName()).append("\n");
            for (Session session : routine.getSessions()) {
                sb.append("  Session: ").append(session.getName()).append(", Duration: ").append(session.getDuration()).append(" mins\n");
            }
        }
        textArea.setText(sb.toString());
    }

    public void editRoutine(String routineId, Routine newRoutine) {
        for (int i = 0; i < routines.size(); i++) {
            if (routines.get(i).getId().equals(routineId)) {
                routines.set(i, newRoutine);
                break;
            }
        }
    }

    public void deleteRoutine(String routineId) {
        routines.removeIf(routine -> routine.getId().equals(routineId));
    }

    public void runRoutine(String routineId) {
        for (Routine routine : routines) {
            if (routine.getId().equals(routineId)) {
                Run run = new Run(routine);
                run.runRoutine();
                break;
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
        JFrame frame = new JFrame("Miracle Morning Supporter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2));

        JButton viewRoutinesButton = new JButton("View Routines");
        JButton addRoutineButton = new JButton("Add Routine");
        JButton editRoutineButton = new JButton("Edit Routine");
        JButton deleteRoutineButton = new JButton("Delete Routine");
        JButton runRoutineButton = new JButton("Run Routine");
        JButton viewSuccessHistoryButton = new JButton("View Success History");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(viewRoutinesButton);
        buttonPanel.add(addRoutineButton);
        buttonPanel.add(editRoutineButton);
        buttonPanel.add(deleteRoutineButton);
        buttonPanel.add(runRoutineButton);
        buttonPanel.add(viewSuccessHistoryButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel);
        panel.add(scrollPane);

        frame.add(panel, BorderLayout.CENTER);

        viewRoutinesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewRoutines(textArea);
            }
        });

        addRoutineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(frame, "Enter Routine ID:");
                String name = JOptionPane.showInputDialog(frame, "Enter Routine Name:");
                Routine newRoutine = new Routine(id, name);
                addRoutine(newRoutine);
                JOptionPane.showMessageDialog(frame, "Routine added successfully.");
            }
        });

        editRoutineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String editId = JOptionPane.showInputDialog(frame, "Enter Routine ID to Edit:");
                String newName = JOptionPane.showInputDialog(frame, "Enter New Routine Name:");
                Routine editRoutine = new Routine(editId, newName);
                editRoutine(editId, editRoutine);
                JOptionPane.showMessageDialog(frame, "Routine edited successfully.");
            }
        });

        deleteRoutineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deleteId = JOptionPane.showInputDialog(frame, "Enter Routine ID to Delete:");
                deleteRoutine(deleteId);
                JOptionPane.showMessageDialog(frame, "Routine deleted successfully.");
            }
        });

        runRoutineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String runId = JOptionPane.showInputDialog(frame, "Enter Routine ID to Run:");
                runRoutine(runId);
            }
        });

        viewSuccessHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSuccessHistory(textArea);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MiracleMorningSupporter().createAndShowGUI();
            }
        });
    }
}
