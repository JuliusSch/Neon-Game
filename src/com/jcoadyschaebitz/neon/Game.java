package com.jcoadyschaebitz.neon;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

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

	private static double scale = 3;
	private static int width = 400;
	private static int height = width * 10 / 16;
	private static int xRenderOffset = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - (width * scale)) / 2;
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
	public UIMenu pauseSkillsMenu;
	public UIMenu pauseSettingsMenu;
	public UIMenu gamePlayUI;
	Font font;

	private static UIManager uiManager;

	private Screen screen;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension((int) (width * scale), (int) (height * scale));
		setPreferredSize(size);

		screen = new Screen(width, height, this);
		uiManager = new UIManager(this);
		playState = new PlayActiveState(this);
		pauseState = new PauseMenuState(this);
		mainMenuState = new MainMenuState(this);
		pauseSkillsMenu = new UIMenu();
		pauseSettingsMenu = new UIMenu();
		gamePlayUI= new UIMenu();
		gameState = playState; // eventually change to main menu state
		uiManager.setMenu(gamePlayUI);
		frame = new JFrame();
		key = new Keyboard(this);
		Level.initiateLevelTransitions();
		level = Level.level1;
		player = new Player(this, level.getPlayerSpawn(), key);
		level.add(player);
		level.initPlayer(player);

		addKeyListener(key);

		Mouse mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseListener(player.getActionSkillManager());
		addMouseMotionListener(mouse);
		addMouseWheelListener(mouse);
		for (MouseListener m : player.getMouseListeningButtons()) {
		addMouseListener(m);
		}
	}
	
	public void switchToLevel(Level level) {
		this.level = level;
		level.add(player);
		level.initPlayer(player);
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
	
	public static int getXRenderOffset() {
		return xRenderOffset;
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

	public void update() {
		gameState.update();
		uiManager.update();
	}

	public void updatePauseStatus() {
		if (gameState == playState) {
			gameState = pauseState;
			for (SoundClip clip : SoundClip.clips) {
				clip.pause();
			}
		} else if (gameState == pauseState) {
			gameState = playState;
			for (SoundClip clip : SoundClip.clips) {
				clip.resume();
			}
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		int mouseXRelToMid = (Mouse.getX() - Game.getWindowWidth() / 2) - Game.getXRenderOffset();
		int mouseYRelToMid = Mouse.getY() - Game.getWindowHeight() / 2;
		double xScroll = player.getX() + mouseXRelToMid / 16 - screen.width / 2 + 8;
		double yScroll = player.getY() + mouseYRelToMid / 16 - screen.height / 2 + 14;
		gameState.render(screen, (int) xScroll, (int) yScroll);
		uiManager.render(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.screenPixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, xRenderOffset, 0, getWindowWidth(), getWindowHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(title);
		game.frame.getContentPane().setBackground(Color.BLACK);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		game.frame.setResizable(false);
		game.frame.setVisible(true);

		game.start();
		game.requestFocus();
	}
}
