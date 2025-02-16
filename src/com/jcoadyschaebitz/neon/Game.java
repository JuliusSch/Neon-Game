package com.jcoadyschaebitz.neon;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;
import com.jcoadyschaebitz.neon.input.InputManager;
import com.jcoadyschaebitz.neon.input.Mouse;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.save.SavedGame;
import com.jcoadyschaebitz.neon.state.CutSceneState;
import com.jcoadyschaebitz.neon.state.GameState;
import com.jcoadyschaebitz.neon.state.MainMenuState;
import com.jcoadyschaebitz.neon.state.PauseCutSceneState;
import com.jcoadyschaebitz.neon.state.PauseMenuState;
import com.jcoadyschaebitz.neon.state.PlayActiveState;
import com.jcoadyschaebitz.neon.state.PlayerDiedState;
import com.jcoadyschaebitz.neon.state.ShopMenuState;
import com.jcoadyschaebitz.neon.util.Vec2d;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -1738116867381702027L;
	
//	private static int width = 410;
//	private static int height = width * 4 / 5
//	private static double screenScale = screenDimensions[1] / (height * scale);

	private static Vec2i screenDimensions = new Vec2i((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
	public static final int height = screenDimensions.y / 3;
	public static final int width = height * 5 / 4;
	private static double scale = screenDimensions.y / height;		// 	NEED TO DECIDE HOW THIS SHOULD WORK FOR DIFFERENT SIZED SCREENS
	private static int xBarsOffset = (int) (screenDimensions.x - (width * scale)) / 2;
	private static String title = "neon";															//	SCREEN DIMENSIONS: 640 x 360 scaled by 3
	
	private Thread gameThread;
	private JFrame frame;
	private static InputManager inputManager;
	private Level level;
	private Player player;
	private boolean running = false;
	private GameState gameState;
	public PlayActiveState playState;
	public PauseMenuState pauseState;
	public MainMenuState mainMenuState;
	public CutSceneState cutSceneState;
	public PauseCutSceneState pausedSceneState;
	public PlayerDiedState playerDiedState;
	public ShopMenuState shopMenuState;
	Font font;

	private static UIManager uiManager;

	private Screen screen;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int time;
	private Vec2d cameraPos, prevCameraPos;

	public Game() {
		Dimension size = new Dimension((int) (width * scale), (int) (height * scale));
		setPreferredSize(size);

		inputManager = new InputManager(this);
		uiManager = new UIManager(this);
		screen = new Screen(width, height, this);
		
		playState = new PlayActiveState(this);
		pauseState = new PauseMenuState(this);
		mainMenuState = new MainMenuState(this);
		cutSceneState = new CutSceneState(this);
		pausedSceneState = new PauseCutSceneState(this);
		playerDiedState = new PlayerDiedState(this);
		shopMenuState = new ShopMenuState(this);
		
		cameraPos = new Vec2d(0, 0);
		prevCameraPos = new Vec2d(0, 0);
		
		gameState = mainMenuState;
		frame = new JFrame();
		Level.initiateLevelTransitions();
		level = Level.level_0_menu;

		player = new Player(this, level.getPlayerSpawn(), inputManager);
		prevCameraPos = new Vec2d(player.getX() - screen.width / 2 + 8, player.getY() - screen.height / 2 + 14);		// See render method for why these are initial calculations.
		Level.addPlayerToLevels(player);
		level.initPlayer(player);

		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		addMouseWheelListener(mouse);
	}

	public static InputManager getInputManager() {
		return inputManager;
	}

	public GameState getState() {
		return gameState;
	}
	
	public static int getXWindowCentre() {
		return xBarsOffset + getWindowWidth() / 2;
	}
	
	public static int getYWindowCentre() {
		return getWindowHeight() / 2;
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
		return xBarsOffset;
	}

	public static UIManager getUIManager() {
		return uiManager;
	}
	
	public void switchToLevel(Level level, Vec2i newPlayerPosition) {
		this.level = level;
		level.initPlayer(player);
		player.init(level);
		uiManager.switchGunsToLevel(level);
		player.goTo(newPlayerPosition.x, newPlayerPosition.y);
	}
	
	public void loadDataFromSelectedSave() {
		SavedGame save = uiManager.loadGameSubMenu.getSelectedSave();
		switchToLevel(Level.getLevelFromName(save.saveData.get("Level")), Vec2i.decodeString(save.saveData.get("PlayerPosition")));
		resetCameraOnPlayer();
		Vec2i pos = Vec2i.decodeString(save.saveData.get("PlayerPosition"));
		player.goTo(pos.x, pos.y);
//		level.loadPlayerWeaponsFromData(save.saveData);
	}

	public Level getLevel() {
		return level;
	}
	
	public Vec2d getCameraPos() {
		return cameraPos;
	}
	
	public void setCameraPos(Vec2d pos) {
		cameraPos = pos;
	}
	
	public Screen getScreen() {
		return screen;
	}
	
	public void togglePause() {
		if (getState() == playState) switchToGameState(pauseState);
		else if (getState() == pauseState) switchToGameState(playState);	
		else if (getState() == cutSceneState) switchToGameState(pausedSceneState);	
		else if (getState() == pausedSceneState) switchToGameState(cutSceneState);
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
		long now;
		double deltaDiff;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1_000_000_000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			now = System.nanoTime();
			deltaDiff = (now - lastTime) / ns;
			delta += deltaDiff;
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
		inputManager.update();
	}

	public void switchToGameState(GameState newState) {
		gameState.exitState();
		gameState = newState;
		gameState.enterState();
	}

	public boolean toggleCutScene(CutScene scene) {
		if (gameState == playState) {
			switchToGameState(cutSceneState);
			cutSceneState.setScene(scene);
			pausedSceneState.setScene(scene);
			level.toggleMobility(true);
			return true;
		}
		if (gameState == cutSceneState) {
			switchToGameState(playState);
			level.toggleMobility(false);
			return true;
		}
		return false;
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		
		double xs = player.getX() + inputManager.getMouseXRelMidWithBars() / 16 - screen.width / 2 + 8;
		double ys = player.getY() + inputManager.getMouseYRelMid() / 12 - screen.height / 2 + 14;
		if (gameState == playerDiedState) {
			xs -= inputManager.getMouseXRelMidWithBars() / 16;
			ys -= inputManager.getMouseYRelMid() / 12;
		}
		xs = (prevCameraPos.x + (xs - prevCameraPos.x) / 45);
		ys = (prevCameraPos.y + (ys - prevCameraPos.y) / 45);
		prevCameraPos.x = xs;
		prevCameraPos.y = ys;
		cameraPos.x = xs;
		cameraPos.y = ys;
		gameState.render(screen, xs, ys);
		uiManager.render(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.screenPixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, xBarsOffset, 0, getWindowWidth(), getWindowHeight(), null); // 	SCALE IMAGE TO SCREEN BY SCALING WINDOWWITDTH() AND WINDOWHEIGHT()
		g.dispose();
		bs.show();
	}
	
	public void resetCameraOnPlayer() {
		prevCameraPos.x = player.getX() + inputManager.getMouseXRelMid() / 16 - screen.width / 2 + 8;
		prevCameraPos.y = player.getY() + inputManager.getMouseYRelMid() / 12 - screen.height / 2 + 14;
	}

	public static void main(String[] args) {
		// Add window resizability
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
		game.frame.addWindowFocusListener(getInputManager());
		
		game.frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
//				System.out.println("resized");
			}
		});
		inputManager.setMousePos(getXWindowCentre(), getYWindowCentre());
		
//		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		GraphicsDevice[] gd = ge.getScreenDevices();
//		game.frame.setLocation(gd[1].getDefaultConfiguration().getBounds().x, game.frame.getY());
		
//		Mouse.move(Game.getWindowWidth() / 2 + Game.getXBarsOffset(), Game.getWindowHeight() / 2);
		game.start();
		game.requestFocus();
	}
}
