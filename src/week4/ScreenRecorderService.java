package week4;

import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

public class ScreenRecorderService {
    private final Component captureTarget;
    private final Path outputPath;
    private final int fps;
    private final Java2DFrameConverter converter = new Java2DFrameConverter();
    private final AtomicBoolean recording = new AtomicBoolean(false);

    private ScheduledExecutorService executor;
    private Robot robot;
    private FFmpegFrameRecorder recorder;

    public ScreenRecorderService(Component captureTarget, Path outputPath, int fps) {
        this.captureTarget = captureTarget;
        this.outputPath = outputPath;
        this.fps = fps;
    }

    public synchronized void start() throws Exception {
        if (recording.get()) {
            return;
        }

        Path parent = outputPath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        Rectangle captureBounds = getCaptureBounds();
        recorder = new FFmpegFrameRecorder(outputPath.toFile(), captureBounds.width, captureBounds.height);
        recorder.setFormat("mp4");
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setFrameRate(fps);
        recorder.setVideoBitrate(4_000_000);
        recorder.start();

        robot = new Robot();
        executor = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "week4-screen-recorder");
            thread.setDaemon(true);
            return thread;
        });

        recording.set(true);
        long periodMillis = Math.max(1, 1000L / fps);
        executor.scheduleAtFixedRate(this::captureFrameSafely, 0L, periodMillis, TimeUnit.MILLISECONDS);
    }

    public synchronized void stop() throws Exception {
        if (!recording.getAndSet(false)) {
            return;
        }

        if (executor != null) {
            executor.shutdownNow();
            executor.awaitTermination(2, TimeUnit.SECONDS);
            executor = null;
        }

        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }

        robot = null;
    }

    private void captureFrameSafely() {
        if (!recording.get()) {
            return;
        }

        try {
            Rectangle captureBounds = getCaptureBounds();
            BufferedImage image = robot.createScreenCapture(captureBounds);
            Frame frame = converter.convert(image);
            recorder.record(frame);
        } catch (Exception ignored) {
            try {
                stop();
            } catch (Exception stopError) {
                // Ignore stop failures inside the recorder thread.
            }
        }
    }

    private Rectangle getCaptureBounds() {
        GraphicsConfiguration configuration = captureTarget.getGraphicsConfiguration();
        Point location = captureTarget.getLocationOnScreen();
        Rectangle bounds = new Rectangle(location.x, location.y, captureTarget.getWidth(), captureTarget.getHeight());

        if (configuration != null && configuration.getBounds() != null) {
            Rectangle screenBounds = configuration.getBounds();
            bounds = bounds.intersection(screenBounds);
        }

        return bounds;
    }
}