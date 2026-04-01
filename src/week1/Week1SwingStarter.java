package week1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Week1SwingStarter {
    private int clickCount = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Week1SwingStarter().createAndShowGui());
    }

    private void createAndShowGui() {
        JFrame frame = new JFrame("Week 1 - Swing Starter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        JLabel titleLabel = new JLabel("Java Swing Game Lab");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerPanel.add(titleLabel);

        JPanel centerPanel = new JPanel(new BorderLayout(8, 8));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setText("Welcome to Week 1.\nTry typing your name and click Start.\n");

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Event Log"));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        JLabel nameLabel = new JLabel("Player Name:");
        JTextField nameField = new JTextField(18);
        JButton startButton = new JButton("Start");
        JButton clearButton = new JButton("Clear Log");

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(startButton);
        inputPanel.add(clearButton);
        centerPanel.add(inputPanel, BorderLayout.SOUTH);

        JLabel footerLabel = new JLabel("Clicks: 0");
        footerLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        startButton.addActionListener(event -> {
            clickCount++;
            String playerName = nameField.getText().trim();
            if (playerName.isEmpty()) {
                playerName = "Unknown Pilot";
            }
            logArea.append("Start button clicked by " + playerName + "\n");
            footerLabel.setText("Clicks: " + clickCount);
        });

        clearButton.addActionListener(event -> {
            logArea.setText("Log cleared.\n");
            clickCount = 0;
            footerLabel.setText("Clicks: 0");
        });

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(footerLabel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
