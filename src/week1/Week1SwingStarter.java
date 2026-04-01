package week1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Week1SwingStarter {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int BALL_RADIUS = 20;
    private static final int STAR_COUNT = 50;
    private static final int FRAME_MS = 16;
    private static final int BOOST_DURATION_MS = 200;
    private static final int BOOST_SCALE = 2;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGui();
            }
        });
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("Mouse Follower");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        GamePanel panel = new GamePanel();
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static class GamePanel extends JPanel {
        private final Random random = new Random();
        private final Star[] stars = new Star[STAR_COUNT];
        private double ballX = WINDOW_WIDTH / 2.0;
        private double ballY = WINDOW_HEIGHT / 2.0;
        private double targetX = WINDOW_WIDTH / 2.0;
        private double targetY = WINDOW_HEIGHT / 2.0;
        private double velocityX = 0.0;
        private double velocityY = 0.0;
        private boolean boosted = false;
        private final Timer boostResetTimer;

        GamePanel() {
            setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
            setBackground(Color.BLACK);
            initializeStars();
            boostResetTimer = new Timer(BOOST_DURATION_MS, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boosted = false;
                }
            });
            boostResetTimer.setRepeats(false);

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    updateTargetPosition(e);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    updateTargetPosition(e);
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    updateTargetPosition(e);
                    triggerBoost();
                }
            });

            Timer timer = new Timer(FRAME_MS, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateBallWithInertia();
                    updateStars();
                    repaint();
                }
            });
            timer.start();
        }

        private void initializeStars() {
            for (int i = 0; i < STAR_COUNT; i++) {
                Star star = new Star();
                star.x = random.nextDouble() * WINDOW_WIDTH;
                star.y = random.nextDouble() * WINDOW_HEIGHT;
                star.speed = 0.4 + random.nextDouble() * 1.2;
                star.size = 1 + random.nextInt(3);
                stars[i] = star;
            }
        }

        private void updateTargetPosition(MouseEvent e) {
            targetX = e.getX();
            targetY = e.getY();
        }

        private void triggerBoost() {
            boosted = true;
            boostResetTimer.restart();
            System.out.println("Engine Boost!");
        }

        private void updateBallWithInertia() {
            double spring = 0.12;
            double damping = 0.82;
            double accelX = (targetX - ballX) * spring;
            double accelY = (targetY - ballY) * spring;

            velocityX = (velocityX + accelX) * damping;
            velocityY = (velocityY + accelY) * damping;
            ballX += velocityX;
            ballY += velocityY;
        }

        private void updateStars() {
            for (Star star : stars) {
                star.y += star.speed;
                if (star.y > WINDOW_HEIGHT) {
                    star.y = -star.size;
                    star.x = random.nextDouble() * WINDOW_WIDTH;
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.WHITE);
            for (Star star : stars) {
                g.fillRect((int) star.x, (int) star.y, star.size, star.size);
            }

            int currentRadius = boosted ? BALL_RADIUS * BOOST_SCALE : BALL_RADIUS;
            g.setColor(boosted ? Color.YELLOW : Color.RED);
            g.fillOval((int) (ballX - currentRadius), (int) (ballY - currentRadius),
                    currentRadius * 2, currentRadius * 2);
        }

        private static class Star {
            private double x;
            private double y;
            private double speed;
            private int size;
        }
    }
}
