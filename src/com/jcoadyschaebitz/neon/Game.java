package com.jcoadyschaebitz.neon;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;
import com.jcoadyschaebitz.neon.graphics.UI.UIMenu;
import com.jcoadyschaebitz.neon.input.Keyboard;
import com.jcoadyschaebitz.neon.input.Mouse;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.sound.SoundClip;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {

	private static int width = 410;
	private static int height = width * 4 / 5;
	private static int[] screenDimensions = { (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()) };
	private static double scale = screenDimensions[1] / height;
	private static int xBlackBarsOffset = (int) (screenDimensions[0] - (width * scale)) / 2;
	private static String title = "neon";

	private Thread gameThread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;
	private GameState gameState;
	public PlayActiveState playState;
	public PauseMenuState pauseState;
	public MainMenuState mainMenuState;
	public CutSceneState cutSceneState;
	public PauseCutSceneState pausedSceneState;
	public UIMenu pauseSkillsMenu;
	public UIMenu pauseSettingsMenu;
	public UIMenu gamePlayUI;
	public UIMenu cutSceneUI;
	Font font;

	private static UIManager uiManager;

	private Screen screen;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int time;

	public Game() {
		Dimension size = new Dimension((int) (width * scale), (int) (height * scale));
		setPreferredSize(size);

		screen = new Screen(width, height, this);
		uiManager = new UIManager(this);
		playState = new PlayActiveState(this);
		pauseState = new PauseMenuState(this);
		mainMenuState = new MainMenuState(this);
		cutSceneState = new CutSceneState(this);
		pausedSceneState = new PauseCutSceneState(this);
		pauseSkillsMenu = new UIMenu();
		pauseSettingsMenu = new UIMenu();
		gamePlayUI = new UIMenu();
		cutSceneUI = new UIMenu();
		gameState = playState; // eventually change to main menu state
		uiManager.setMenu(gamePlayUI);
		frame = new JFrame();
		key = new Keyboard(this);
		Level.initiateLevelTransitions();
//		level = Level.level_1_bar;
		level = Level.level_4_pool;
		player = new Player(this, level.getPlayerSpawn(), key);
		Level.addPlayersToLevels(player);
		level.initPlayer(player);

		addKeyListener(key);

		Mouse mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseListener(player.getShield());
		addMouseMotionListener(mouse);
		addMouseWheelListener(mouse);
		for (MouseListener m : player.getMouseListeningButtons()) {
			addMouseListener(m);
		}
		setCursor();
	}

	private void setCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(this.getClass().getResource("/textures/UI/customCursor.png"));
		Point point = new Point(16, 16);
		Cursor cursor = toolkit.createCustomCursor(img, point, "customCursor");
		setCursor(cursor);
	}

	public void switchToLevel(Level level) {
		this.level = level;
		level.initPlayer(player);
		player.init(level);
		player.switchGunsToLevel(level);
	}

	public Keyboard getKeyboard() {
		return key;
	}

	public GameState getState() {
		return gameState;
	}

	public static int getWindowWidth() {
		return (int) (width * scale);
	}

	public static int getWindowHeight() {
		return (int) (height * scale);
	}

	public static double getWindowScale() {
		return scale;
	}

	public static int getXBarsOffset() {
		return xBlackBarsOffset;
	}

	public static UIManager getUIManager() {
		return uiManager;
	}

	public Level getLevel() {
		return level;
	}

	public synchronized void start() {
		running = true;
		gameThread = new Thread(this, "game");

		gameThread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1_000_000_000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				this.frame.setTitle(title + " | " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
	}
	
	public int getElapsedTime() {
		return time;
	}

	public void update() {
		time++;
		gameState.update();
		uiManager.update();
	}

	public void togglePause() {
		if (gameState == playState) {
			gameState = pauseState;
//			pauseState.recordMouse(Mouse.getX(), Mouse.getY());
			for (SoundClip clip : SoundClip.clips) {
				clip.pause();
			}
		} else if (gameState == pauseState) {
			gameState = playState;
//			Mouse.move(pauseState.lastMouseX, pauseState.lastMouseY);
			for (SoundClip clip : SoundClip.clips) {
				clip.resume();
			}
		} else if (gameState == cutSceneState) {
			gameState = pausedSceneState;
			for (SoundClip clip : SoundClip.clips) {
				clip.pause();
			}
		} else if (gameState == pausedSceneState) {
			gameState = cutSceneState;
			for (SoundClip clip : SoundClip.clips) {
				clip.resume();
			}
		}
	}

	public boolean toggleCutScene(CutScene scene) {
		if (gameState == playState) {
			gameState = cutSceneState;
			cutSceneState.setScene(scene);
			pausedSceneState.setScene(scene);
			level.setInSceneStatus(true);
			return true;
		}
		if (gameState == cutSceneState) {
			gameState = playState;
			level.setInSceneStatus(false);
			return true;
		} else
			return false;
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		int mouseXRelToMid = (Mouse.getX() - Game.getWindowWidth() / 2) - Game.getXBarsOffset();
		int mouseYRelToMid = Mouse.getY() - Game.getWindowHeight() / 2;
		double xs = player.getX() + mouseXRelToMid / 16 - screen.width / 2 + 8;
		double ys = player.getY() + mouseYRelToMid / 16 - screen.height / 2 + 14;
		gameState.render(screen, xs, ys);
		uiManager.render(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.screenPixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, xBlackBarsOffset, 0, getWindowWidth(), getWindowHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setUndecorated(true);
		game.frame.setResizable(false);
		game.frame.setTitle(title);
		game.frame.getContentPane().setBackground(Color.BLACK);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		game.frame.setVisible(true);
		Mouse.move(Game.getWindowWidth() / 2 + Game.getXBarsOffset(), Game.getWindowHeight() / 2);
		game.start();
		game.requestFocus();
	}
}
