package week4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RecordingToolbar extends JPanel {
    private final Component captureTarget;
    private final JButton startButton;
    private final JButton stopButton;
    private final JLabel statusLabel;
    private final Path outputPath = Paths.get("docs", "weekly", "assets", "week04-demo.mp4");

    private ScreenRecorderService recorderService;

    public RecordingToolbar(Component captureTarget) {
        this.captureTarget = captureTarget;

        setLayout(new BorderLayout(8, 0));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setBackground(new Color(24, 24, 24));
        setPreferredSize(new Dimension(800, 48));

        startButton = new JButton("Start Recording");
        stopButton = new JButton("Stop Recording");
        stopButton.setEnabled(false);
        statusLabel = new JLabel("Recorder: idle");
        statusLabel.setForeground(Color.WHITE);

        startButton.addActionListener(e -> startRecording());
        stopButton.addActionListener(e -> stopRecordingIfActive());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        add(statusLabel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.EAST);
    }

    public void stopRecordingIfActive() {
        if (recorderService == null) {
            updateState(false, "Recorder: idle");
            return;
        }

        try {
            recorderService.stop();
            recorderService = null;
            updateState(false, "Recorder: saved to " + outputPath.toString());
        } catch (Exception ex) {
            recorderService = null;
            updateState(false, "Recorder: failed to stop");
            showError("Failed to stop recorder", ex);
        }
    }

    private void startRecording() {
        if (recorderService != null) {
            return;
        }

        try {
            recorderService = new ScreenRecorderService(captureTarget, outputPath, 15);
            recorderService.start();
            updateState(true, "Recorder: recording -> " + outputPath.toString());
        } catch (Exception ex) {
            recorderService = null;
            updateState(false, "Recorder: failed to start");
            showError("Failed to start recorder", ex);
        }
    }

    private void updateState(boolean recording, String message) {
        startButton.setEnabled(!recording);
        stopButton.setEnabled(recording);
        statusLabel.setText(message);
    }

    private void showError(String title, Exception ex) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                ex.getMessage(),
                title,
                JOptionPane.ERROR_MESSAGE));
    }
}