package core.engine.graphic;

import core.engine.*;
import core.game.ui.*;

import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel implements Runnable {

    private static final int NO_DELAYS_PER_YIELD = 16;

    private static final int NUM_FPS = 10;

	private long statsInterval = 0L;
	private long prevStatsTime;
	private long totalElapsedTime = 0L;
	private long gameStartTime;

	private long frameCount = 0;
	private double fpsStore[];
	private long statsCount = 0;
	protected double averageFPS = 0.0;

	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private double upsStore[];

	private Thread animator;

	protected volatile boolean running = false;

	protected volatile boolean isPaused = false;

	private final long period;

	protected Graphics dbg;

	private Image dbImage = null;

	public Screen(final long period, final int width, final int height) {
		this.period = period;

        setBackground(Color.white);
        setPreferredSize(new Dimension(width, height));

        setFocusable(true);
        requestFocus();

        fpsStore = new double[NUM_FPS];
        upsStore = new double[NUM_FPS];
		for (int i = 0; i < NUM_FPS; i++) {
            fpsStore[i] = 0.0;
            upsStore[i] = 0.0;
		}
	}

	public final void addNotify() {
		addNotify();
        startGame();
	}

	protected void startGame() {
		if (animator == null || !running) {
            animator = new Thread(this, "GameLoop");
            animator.start();
		}
	}

	public final void resumeGame() {
        isPaused = false;
	}

	public final void pauseGame() {
        isPaused = true;
	}

	public final void stopGame() {
        running = false;
	}

	public void run() {
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;

        gameStartTime = System.nanoTime();
        prevStatsTime = gameStartTime;
		beforeTime = gameStartTime;

		running = true;

		timeDiff = beforeTime;
		while (running) {

            gameUpdate(timeDiff);
            checkInput();
            checkTimeEvent();
            gameUpdate(timeDiff);
            moveObjects(timeDiff);
            gameRender();
            paintScreen();

			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;

			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime / 1000000L);
				} catch (InterruptedException ignored) { }
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			} else {
				excess -= sleepTime;
				overSleepTime = 0L;

				if (++noDelays >= NO_DELAYS_PER_YIELD) {
					Thread.yield();
					noDelays = 0;
				}
			}

			beforeTime = System.nanoTime();

			int skips = 0;
            final int MAX_FRAME_SKIPS = 5;
            while ((excess > period) && (skips < MAX_FRAME_SKIPS)) {
				excess -= period;
                gameUpdate(timeDiff);
				skips++;
			}
            framesSkipped += skips;

            storeStats();
		}

		System.exit(0);
	}

	protected abstract void checkInput();

	private void moveObjects(final long timeDiff) {
        final SpriteSet spriteSet = SpriteSet.getInstance();
		if (!isPaused) {
            for (final Sprite sprite : spriteSet.getActors()) {
                if (sprite instanceof Moveable) {
                    ((Moveable) sprite).move(timeDiff);
                }
            }
		}
	}

	protected abstract void checkTimeEvent();

	protected abstract void gameUpdate(final long timeDiff);

	protected void gameRender() {
		if (null == dbImage) {
            dbImage = createImage(GameFrame.pWidth, GameFrame.pHeight);
			if (null == dbImage) {
				return;
			} else
                dbg = dbImage.getGraphics();
		}

        dbg.setColor(Color.white);
        dbg.fillRect(0, 0, GameFrame.pWidth, GameFrame.pHeight);
	}

	private void paintScreen() {
		Graphics g;
		try {
			g = getGraphics();
			if ((null != g) && (null != dbImage)) {
				g.drawImage(dbImage, 0, 0, null);
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	private void storeStats() {
        frameCount++;
        statsInterval += period;

        final var MAX_STATS_INTERVAL = 1000000000L;
        if (statsInterval >= MAX_STATS_INTERVAL) {
			final var TIME_NOW = System.nanoTime();

			final var REAL_ELAPSED_TIME = TIME_NOW - prevStatsTime;
            totalElapsedTime += REAL_ELAPSED_TIME;
            totalFramesSkipped += framesSkipped;

			double actualFPS = 0;
			double actualUPS = 0;
			if (totalElapsedTime > 0) {
				actualFPS = (((double) frameCount / totalElapsedTime) * 1000000000L);
				actualUPS = (((double) (frameCount + totalFramesSkipped) / totalElapsedTime) * 1000000000L);
			}

            fpsStore[(int) statsCount % NUM_FPS] = actualFPS;
            upsStore[(int) statsCount % NUM_FPS] = actualUPS;
            statsCount = statsCount + 1;

			double totalFPS = 0.0;
			for (int i = 0; i < NUM_FPS; i++) {
				totalFPS += fpsStore[i];
			}

			if (statsCount < NUM_FPS) {
                averageFPS = totalFPS / statsCount;
			} else {
                averageFPS = totalFPS / NUM_FPS;
			}

            framesSkipped = 0;
            prevStatsTime = TIME_NOW;
            statsInterval = 0L;
		}
	}
}