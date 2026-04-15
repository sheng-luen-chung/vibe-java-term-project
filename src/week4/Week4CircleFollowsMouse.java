package week4;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Week4CircleFollowsMouse {
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
        JFrame frame = new JFrame("Week 4 - Keyboard Control");
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

    private static class CirclePanel extends JPanel implements MouseListener {
        private static final int CIRCLE_RADIUS = 20;
        private static final int STAR_COUNT = 50;
        private static final int FRAME_MS = 16;
        private static final double MOVE_SPEED = 6.0;
        private static final int MAX_BULLETS = 3;

        private final Random random = new Random();
        private final ArrayList<Star> stars = new ArrayList<Star>();
        private final ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        private Meteor meteor;
        private BouncingMeteor bouncingMeteor;
        private final Timer gameTimer;

        private double circleX = WINDOW_WIDTH / 2.0;
        private double circleY = WINDOW_HEIGHT / 2.0;
        private boolean leftKeyHeld;
        private boolean rightKeyHeld;
        private boolean upKeyHeld;
        private boolean downKeyHeld;
        private boolean spaceKeyHeld;

        private long frameCount = 0;
        private int score = 0;
        private boolean gameOver = false;
        private boolean paused = false;

        CirclePanel() {
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
            setFocusable(true);
            addMouseListener(this);

            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int code = e.getKeyCode();
                    if (code == KeyEvent.VK_LEFT) {
                        leftKeyHeld = true;
                    } else if (code == KeyEvent.VK_RIGHT) {
                        rightKeyHeld = true;
                    } else if (code == KeyEvent.VK_UP) {
                        upKeyHeld = true;
                    } else if (code == KeyEvent.VK_DOWN) {
                        downKeyHeld = true;
                    } else if (code == KeyEvent.VK_SPACE) {
                        if (!spaceKeyHeld) {
                            spaceKeyHeld = true;
                            shootBullet();
                        }
                    } else if (code == KeyEvent.VK_P) {
                        if (!gameOver) {
                            paused = !paused;
                        }
                    } else if (code == KeyEvent.VK_R) {
                        resetGame();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    int code = e.getKeyCode();
                    if (code == KeyEvent.VK_LEFT) {
                        leftKeyHeld = false;
                    } else if (code == KeyEvent.VK_RIGHT) {
                        rightKeyHeld = false;
                    } else if (code == KeyEvent.VK_UP) {
                        upKeyHeld = false;
                    } else if (code == KeyEvent.VK_DOWN) {
                        downKeyHeld = false;
                    } else if (code == KeyEvent.VK_SPACE) {
                        spaceKeyHeld = false;
                    }
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

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    requestFocusInWindow();
                }
            });
        }

        private void updateGame() {
            if (gameOver || paused) {
                return;
            }

            frameCount++;

            double moveX = 0;
            if (leftKeyHeld) {
                moveX -= MOVE_SPEED;
            }
            if (rightKeyHeld) {
                moveX += MOVE_SPEED;
            }
            circleX += moveX;

            double moveY = 0;
            if (upKeyHeld) {
                moveY -= MOVE_SPEED;
            }
            if (downKeyHeld) {
                moveY += MOVE_SPEED;
            }
            circleY += moveY;

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            if (circleX - CIRCLE_RADIUS < 0) {
                circleX = CIRCLE_RADIUS;
            }
            if (panelWidth > 0 && circleX + CIRCLE_RADIUS > panelWidth) {
                circleX = panelWidth - CIRCLE_RADIUS;
            }
            if (circleY - CIRCLE_RADIUS < 0) {
                circleY = CIRCLE_RADIUS;
            }
            if (panelHeight > 0 && circleY + CIRCLE_RADIUS > panelHeight) {
                circleY = panelHeight - CIRCLE_RADIUS;
            }

            for (Star star : stars) {
                star.update(panelWidth, panelHeight);
            }

            meteor.update(panelWidth, panelHeight);
            bouncingMeteor.update(panelWidth, panelHeight);
            updateBullets();
            checkBulletCollision();
            checkPlayerCollision();
        }

        private void shootBullet() {
            if (gameOver || paused) {
                return;
            }
            if (bullets.size() >= MAX_BULLETS) {
                return;
            }
            bullets.add(new Bullet(circleX, circleY - CIRCLE_RADIUS));
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
                    continue;
                }

                if (collides(bullet.getCenterX(), bullet.getCenterY(), bullet.getRadius(),
                        bouncingMeteor.getCenterX(), bouncingMeteor.getCenterY(), bouncingMeteor.getRadius())) {
                    iterator.remove();
                    bouncingMeteor.respawn(getWidth(), getHeight());
                    score += 10;
                }
            }
        }

        private void checkPlayerCollision() {
            if (collides(circleX, circleY, CIRCLE_RADIUS,
                    meteor.getCenterX(), meteor.getCenterY(), meteor.getRadius())) {
                gameOver = true;
                return;
            }

            if (collides(circleX, circleY, CIRCLE_RADIUS,
                    bouncingMeteor.getCenterX(), bouncingMeteor.getCenterY(), bouncingMeteor.getRadius())) {
                gameOver = true;
            }
        }

        private boolean collides(double x1, double y1, int r1, double x2, double y2, int r2) {
            double dx = x1 - x2;
            double dy = y1 - y2;
            double radiusSum = r1 + r2;
            return dx * dx + dy * dy <= radiusSum * radiusSum;
        }

        private void resetGame() {
            int panelWidth = Math.max(getWidth(), 1);
            int panelHeight = Math.max(getHeight(), 1);

            circleX = panelWidth / 2.0;
            circleY = panelHeight - 80.0;
            frameCount = 0;
            score = 0;
            gameOver = false;
            paused = false;

            leftKeyHeld = false;
            rightKeyHeld = false;
            upKeyHeld = false;
            downKeyHeld = false;
            spaceKeyHeld = false;

            bullets.clear();
            meteor = new Meteor(panelWidth, random);
            bouncingMeteor = new BouncingMeteor(panelWidth, panelHeight, random);
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
            String statusText = gameOver ? "Status: GAME OVER" : (paused ? "Status: PAUSED" : "Status: RUNNING");
            g2d.drawString(statusText, 16, 80);
            g2d.drawString("SPACE: Shoot (max 3)  P: Pause  R: Restart", 16, 106);

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
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            requestFocusInWindow();
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
