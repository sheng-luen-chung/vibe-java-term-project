package week3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Week3CircleFollowsMouse {
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
        JFrame frame = new JFrame("Week 3 - Boundary and Shooting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CirclePanel panel = new CirclePanel();
        frame.setContentPane(panel);
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static class Star {
        private final Random random;
        private double x;
        private double y;
        private double speed;
        private int size;

        Star(int panelWidth, int panelHeight, Random sharedRandom) {
            random = sharedRandom;
            x = random.nextDouble() * panelWidth;
            y = random.nextDouble() * panelHeight;
            speed = 1 + random.nextDouble() * 2;
            size = 2 + random.nextInt(2);
        }

        void update(int panelWidth, int panelHeight) {
            y += speed;
            if (y > panelHeight) {
                y = 0;
                x = random.nextDouble() * Math.max(panelWidth, 1);
            }
        }

        void draw(Graphics g) {
            g.fillOval((int) x, (int) y, size, size);
        }
    }

    private static class Meteor {
        private final Random random;
        private double x;
        private double y;
        private double speed;
        private int size;

        Meteor(int panelWidth, Random sharedRandom) {
            random = sharedRandom;
            respawn(panelWidth);
        }

        void respawn(int panelWidth) {
            int safeWidth = Math.max(panelWidth, 1);
            size = 20 + random.nextInt(21);
            x = random.nextDouble() * safeWidth;
            y = -size - random.nextInt(200);
            speed = 2 + random.nextDouble() * 3;
        }

        void update(int panelWidth, int panelHeight) {
            y += speed;
            if (y - size > panelHeight) {
                respawn(panelWidth);
            }
        }

        double getCenterX() {
            return x + size / 2.0;
        }

        double getCenterY() {
            return y + size / 2.0;
        }

        int getRadius() {
            return size / 2;
        }

        void draw(Graphics2D g2d) {
            g2d.setColor(Color.GRAY);
            g2d.fillOval((int) x, (int) y, size, size);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillOval((int) x + size / 4, (int) y + size / 4, size / 3, size / 3);
        }
    }

    private static class BouncingMeteor {
        private final Random random;
        private double x;
        private double y;
        private double velocityX;
        private double velocityY;
        private int size;

        BouncingMeteor(int panelWidth, int panelHeight, Random sharedRandom) {
            random = sharedRandom;
            respawn(panelWidth, panelHeight);
        }

        void respawn(int panelWidth, int panelHeight) {
            int safeWidth = Math.max(panelWidth, 1);
            int safeHeight = Math.max(panelHeight, 1);
            size = 25 + random.nextInt(26);
            x = random.nextDouble() * safeWidth;
            y = random.nextDouble() * (safeHeight / 2.0);
            velocityX = (random.nextDouble() - 0.5) * 6;
            if (Math.abs(velocityX) < 1.0) {
                velocityX = velocityX < 0 ? -1.0 : 1.0;
            }
            velocityY = 1 + random.nextDouble() * 2;
        }

        void update(int panelWidth, int panelHeight) {
            x += velocityX;
            y += velocityY;

            if (x - getRadius() < 0) {
                x = getRadius();
                velocityX = -velocityX;
            } else if (x + getRadius() > panelWidth) {
                x = panelWidth - getRadius();
                velocityX = -velocityX;
            }

            if (y - getRadius() < 0) {
                y = getRadius();
                velocityY = -velocityY;
            } else if (y + getRadius() > panelHeight) {
                y = panelHeight - getRadius();
                velocityY = -velocityY;
            }
        }

        double getCenterX() {
            return x;
        }

        double getCenterY() {
            return y;
        }

        int getRadius() {
            return size / 2;
        }

        void draw(Graphics2D g2d) {
            g2d.setColor(new Color(255, 100, 100));
            g2d.fillOval((int) (x - size / 2), (int) (y - size / 2), size, size);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval((int) (x - size / 2), (int) (y - size / 2), size, size);
        }
    }

    private static class Bullet {
        private final int radius = 5;
        private final double speed = 10;
        private double x;
        private double y;

        Bullet(double startX, double startY) {
            x = startX;
            y = startY;
        }

        void update() {
            y -= speed;
        }

        boolean isOutOfScreen() {
            return y + radius < 0;
        }

        double getCenterX() {
            return x;
        }

        double getCenterY() {
            return y;
        }

        int getRadius() {
            return radius;
        }

        void draw(Graphics2D g2d) {
            g2d.setColor(Color.YELLOW);
            g2d.fillOval((int) (x - radius), (int) (y - radius), radius * 2, radius * 2);
        }
    }

    private static class CirclePanel extends JPanel implements MouseMotionListener {
        private static final int CIRCLE_RADIUS = 20;
        private static final int STAR_COUNT = 50;
        private static final int FRAME_MS = 16;
        private static final double EASING_FACTOR = 0.1;

        private final Random random = new Random();
        private final ArrayList<Star> stars = new ArrayList<Star>();
        private final ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        private final Meteor meteor;
        private final BouncingMeteor bouncingMeteor;
        private final Timer gameTimer;

        private double circleX = WINDOW_WIDTH / 2.0;
        private double circleY = WINDOW_HEIGHT / 2.0;
        private int targetX = WINDOW_WIDTH / 2;
        private int targetY = WINDOW_HEIGHT / 2;
        private long frameCount = 0;
        private int score = 0;
        private boolean gameOver = false;

        CirclePanel() {
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
            addMouseMotionListener(this);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    shootBullet();
                }
            });

            for (int i = 0; i < STAR_COUNT; i++) {
                stars.add(new Star(WINDOW_WIDTH, WINDOW_HEIGHT, random));
            }

            meteor = new Meteor(WINDOW_WIDTH, random);
            bouncingMeteor = new BouncingMeteor(WINDOW_WIDTH, WINDOW_HEIGHT, random);

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
            if (gameOver) {
                return;
            }

            frameCount++;

            double dx = targetX - circleX;
            double dy = targetY - circleY;
            circleX += dx * EASING_FACTOR;
            circleY += dy * EASING_FACTOR;

            int panelWidth = getWidth();
            if (circleX - CIRCLE_RADIUS < 0) {
                circleX = CIRCLE_RADIUS;
            }
            if (circleX + CIRCLE_RADIUS > panelWidth) {
                circleX = panelWidth - CIRCLE_RADIUS;
            }

            int width = getWidth();
            int height = getHeight();
            for (Star star : stars) {
                star.update(width, height);
            }

            meteor.update(width, height);
            bouncingMeteor.update(width, height);

            updateBullets();
            checkBulletCollision();
            checkPlayerCollision();
        }

        private void shootBullet() {
            if (!gameOver) {
                bullets.add(new Bullet(circleX, circleY - CIRCLE_RADIUS));
            }
        }

        private void updateBullets() {
            Iterator<Bullet> iterator = bullets.iterator();
            while (iterator.hasNext()) {
                Bullet bullet = iterator.next();
                bullet.update();
                if (bullet.isOutOfScreen()) {
                    iterator.remove();
                }
            }
        }

        private void checkBulletCollision() {
            Iterator<Bullet> iterator = bullets.iterator();
            while (iterator.hasNext()) {
                Bullet bullet = iterator.next();

                if (collides(bullet.getCenterX(), bullet.getCenterY(), bullet.getRadius(),
                        meteor.getCenterX(), meteor.getCenterY(), meteor.getRadius())) {
                    iterator.remove();
                    meteor.respawn(getWidth());
                    score += 10;
                    System.out.println("hit");
                    continue;
                }

                if (collides(bullet.getCenterX(), bullet.getCenterY(), bullet.getRadius(),
                        bouncingMeteor.getCenterX(), bouncingMeteor.getCenterY(), bouncingMeteor.getRadius())) {
                    iterator.remove();
                    bouncingMeteor.respawn(getWidth(), getHeight());
                    score += 10;
                    System.out.println("hit");
                }
            }
        }

        private void checkPlayerCollision() {
            if (collides(circleX, circleY, CIRCLE_RADIUS,
                    meteor.getCenterX(), meteor.getCenterY(), meteor.getRadius())) {
                gameOver = true;
                gameTimer.stop();
                return;
            }

            if (collides(circleX, circleY, CIRCLE_RADIUS,
                    bouncingMeteor.getCenterX(), bouncingMeteor.getCenterY(), bouncingMeteor.getRadius())) {
                gameOver = true;
                gameTimer.stop();
            }
        }

        private boolean collides(double x1, double y1, int r1, double x2, double y2, int r2) {
            double dx = x1 - x2;
            double dy = y1 - y2;
            double radiusSum = r1 + r2;
            return dx * dx + dy * dy <= radiusSum * radiusSum;
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
            bouncingMeteor.draw(g2d);

            for (Bullet bullet : bullets) {
                bullet.draw(g2d);
            }

            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 18));
            g2d.drawString("Frame: " + frameCount, 16, 28);
            g2d.drawString("Score: " + score, 16, 54);

            g2d.setColor(Color.RED);
            int drawX = (int) (circleX - CIRCLE_RADIUS);
            int drawY = (int) (circleY - CIRCLE_RADIUS);
            g2d.fillOval(drawX, drawY, CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2);

            if (gameOver) {
                g2d.setColor(new Color(0, 0, 0, 200));
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 48));
                String gameOverText = "GAME OVER";
                FontMetrics fm = g2d.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(gameOverText)) / 2;
                int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(gameOverText, textX, textY);

                g2d.setFont(new Font("Arial", Font.PLAIN, 24));
                String finalScoreText = "Score: " + score;
                fm = g2d.getFontMetrics();
                textX = (getWidth() - fm.stringWidth(finalScoreText)) / 2;
                g2d.drawString(finalScoreText, textX, textY + 50);
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
    }
}
