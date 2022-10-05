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
import java.util.List;

import javax.swing.JFrame;

import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.LoadGameSubMenu;
import com.jcoadyschaebitz.neon.graphics.UI.SaveSlot;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;
import com.jcoadyschaebitz.neon.graphics.UI.UIMenu;
import com.jcoadyschaebitz.neon.input.Keyboard;
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

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {

//	private static int width = 410;
//	private static int height = width * 4 / 5
	private static int[] screenDimensions = { (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()) };
	private static int height = screenDimensions[1] / 3;
	private static int width = height * 5 / 4;
	private static double scale = screenDimensions[1] / height;		// 	NEED TO DECIDE HOW THIS SHOULD WORK FOR DIFFERENT SIZED SCREENS
//	private static double screenScale = screenDimensions[1] / (height * scale);
	private static int xBlackBarsOffset = (int) (screenDimensions[0] - (width * scale)) / 2;
	private static String title = "neon";															//	SCREEN DIMENSIONS: 640 x 360 scaled by 3

	private Thread gameThread;
	private JFrame frame;
	private static Keyboard key;
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
	public UIMenu shopMenu, pauseSettingsMenu, gamePlayUI, cutSceneUI, mainMenu, loadMenu, deadMenu;
	public LoadGameSubMenu loadSaveSelection;
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

		screen = new Screen(width, height, this);
		uiManager = new UIManager(this);
		playState = new PlayActiveState(this);
		pauseState = new PauseMenuState(this);
		mainMenuState = new MainMenuState(this);
		cutSceneState = new CutSceneState(this);
		pausedSceneState = new PauseCutSceneState(this);
		playerDiedState = new PlayerDiedState(this);
		shopMenuState = new ShopMenuState(this);
		
		shopMenu = new UIMenu(null);
		pauseSettingsMenu = new UIMenu(null);
		gamePlayUI = new UIMenu(null);
		cutSceneUI = new UIMenu(null);
		mainMenu = new UIMenu(null);
		loadMenu = new UIMenu(null);
		deadMenu = new UIMenu(null);
		
		cameraPos = new Vec2d(0, 0);
		prevCameraPos = new Vec2d(0, 0);
		
		gameState = mainMenuState;
		frame = new JFrame();
		key = new Keyboard(this);
		Level.initiateLevelTransitions();
		level = Level.level_0_menu;
//		level = Level.level_4_pool;
		
		player = new Player(this, level.getPlayerSpawn(), key);
		prevCameraPos = new Vec2d(player.getX() - screen.width / 2 + 8, player.getY() - screen.height / 2 + 14);		// See render method for why these are initial calculations.
		Level.addPlayerToLevels(player);
		level.initPlayer(player);

		loadSaveSelection = new LoadGameSubMenu(122, 122, player, level);
		List<SaveSlot> loadSaveSlots = loadSaveSelection.getSlots();
		for (SaveSlot slot : loadSaveSlots) player.addButton(slot);
		loadMenu.addComp(loadSaveSelection);
		
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

	public static Keyboard getKeyboard() {
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
	
	public void switchToLevel(Level level, Vec2i newPlayerPosition) {
		this.level = level;
		level.initPlayer(player);
		player.init(level);
		player.switchGunsToLevel(level);
		player.goTo(newPlayerPosition.x, newPlayerPosition.y);
	}
	
	public void loadDataFromSelectedSave() {
		SavedGame save = uiManager.loadGameMenu.getSelectedSave();
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
		int mouseXRelToMid = (Mouse.getX() - Game.getWindowWidth() / 2) - Game.getXBarsOffset();
		int mouseYRelToMid = Mouse.getY() - Game.getWindowHeight() / 2;
		double xs = player.getX() + mouseXRelToMid / 16 - screen.width / 2 + 8;
		double ys = player.getY() + mouseYRelToMid / 12 - screen.height / 2 + 14;
		if (gameState == playerDiedState) {
			xs -= mouseXRelToMid / 16;
			ys -= mouseYRelToMid / 12;
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
		g.drawImage(image, xBlackBarsOffset, 0, getWindowWidth(), getWindowHeight(), null); // 	SCALE IMAGE TO SCREEN BY SCALING WINDOWWITDTH() AND WINDOWHEIGHT()
		g.dispose();
		bs.show();
	}
	
	public void resetCameraOnPlayer() {
		int mouseXRelToMid = (Mouse.getX() - Game.getWindowWidth() / 2) - Game.getXBarsOffset();
		int mouseYRelToMid = Mouse.getY() - Game.getWindowHeight() / 2;
		prevCameraPos.x = player.getX() + mouseXRelToMid / 16 - screen.width / 2 + 8;
		prevCameraPos.y = player.getY() + mouseYRelToMid / 12 - screen.height / 2 + 14;
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
