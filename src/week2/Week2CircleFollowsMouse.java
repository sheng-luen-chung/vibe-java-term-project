package week2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Week2CircleFollowsMouse {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGui();
            }
        });
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("Circle Follows Mouse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CirclePanel circlePanel = new CirclePanel();
        frame.setContentPane(circlePanel);
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static class Star {
        private final Random rand;
        private double x;
        private double y;
        private double speed;
        private int size;

        Star(int panelWidth, int panelHeight, Random random) {
            this.rand = random;
            this.x = rand.nextDouble() * panelWidth;
            this.y = rand.nextDouble() * panelHeight;
            this.speed = 1 + rand.nextDouble() * 2;
            this.size = 2 + rand.nextInt(2);
        }

        void update(int panelWidth, int panelHeight) {
            y += speed;
            if (y > panelHeight) {
                y = 0;
                x = rand.nextDouble() * Math.max(panelWidth, 1);
            }
        }

        void draw(Graphics g) {
            g.fillOval((int) x, (int) y, size, size);
        }
    }

    private static class Meteor {
        private final Random rand;
        private double x;
        private double y;
        private double speed;
        private int size;

        Meteor(int panelWidth, Random random) {
            this.rand = random;
            respawn(panelWidth);
        }

        private void respawn(int panelWidth) {
            int safeWidth = Math.max(panelWidth, 1);
            size = 20 + rand.nextInt(21);
            x = rand.nextDouble() * safeWidth;
            y = -size - rand.nextInt(200);
            speed = 2 + rand.nextDouble() * 3;
        }

        void update(int panelWidth, int panelHeight) {
            y += speed;
            if (y - size > panelHeight) {
                respawn(panelWidth);
            }
        }

        void draw(Graphics2D g2d) {
            g2d.setColor(Color.GRAY);
            g2d.fillOval((int) x, (int) y, size, size);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillOval((int) x + size / 4, (int) y + size / 4, size / 3, size / 3);
        }
    }

    private static class CirclePanel extends JPanel implements MouseMotionListener, MouseListener {
        private static final int CIRCLE_RADIUS = 20;
        private static final int STAR_COUNT = 50;
        private static final int FRAME_MS = 16;
        private static final double EASING_FACTOR = 0.1;
        private static final int BOOST_DURATION_MS = 200;

        private final Random random = new Random();
        private final ArrayList<Star> stars = new ArrayList<Star>();
        private final Meteor meteor;
        private final Timer gameTimer;

        private double circleX = WINDOW_WIDTH / 2.0;
        private double circleY = WINDOW_HEIGHT / 2.0;
        private int targetX = WINDOW_WIDTH / 2;
        private int targetY = WINDOW_HEIGHT / 2;
        private boolean boosting = false;
        private long frameCount = 0;

        CirclePanel() {
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
            addMouseMotionListener(this);
            addMouseListener(this);

            for (int i = 0; i < STAR_COUNT; i++) {
                stars.add(new Star(WINDOW_WIDTH, WINDOW_HEIGHT, random));
            }
            meteor = new Meteor(WINDOW_WIDTH, random);

            gameTimer = new Timer(FRAME_MS, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateGame();
                    repaint();
                }
            });
            gameTimer.start();
        }

        private void updateGame() {
            frameCount++;

            double dx = targetX - circleX;
            double dy = targetY - circleY;
            circleX += dx * EASING_FACTOR;
            circleY += dy * EASING_FACTOR;

            int width = getWidth();
            int height = getHeight();
            for (Star star : stars) {
                star.update(width, height);
            }
            meteor.update(width, height);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(Color.WHITE);
            for (Star star : stars) {
                star.draw(g2d);
            }

            meteor.draw(g2d);

            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 18));
            g2d.drawString("Frame: " + frameCount, 16, 28);

            if (boosting) {
                g2d.setColor(Color.YELLOW);
                int boostedRadius = CIRCLE_RADIUS * 2;
                int drawX = (int) (circleX - boostedRadius);
                int drawY = (int) (circleY - boostedRadius);
                g2d.fillOval(drawX, drawY, boostedRadius * 2, boostedRadius * 2);
            } else {
                g2d.setColor(Color.RED);
                int drawX = (int) (circleX - CIRCLE_RADIUS);
                int drawY = (int) (circleY - CIRCLE_RADIUS);
                g2d.fillOval(drawX, drawY, CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            targetX = e.getX();
            targetY = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            targetX = e.getX();
            targetY = e.getY();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!boosting) {
                boosting = true;
                System.out.println("Engine Boost!");

                Timer boostTimer = new Timer(BOOST_DURATION_MS, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        boosting = false;
                        repaint();
                    }
                });
                boostTimer.setRepeats(false);
                boostTimer.start();

                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
