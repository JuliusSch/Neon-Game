package com.jcoadyschaebitz.neon.entity.mob;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.Shield;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.weapon.MeleeWeapon;
import com.jcoadyschaebitz.neon.entity.weapon.PlayerWeapon;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.graphics.UI.AmmoDisplay;
import com.jcoadyschaebitz.neon.graphics.UI.AmmoDisplayPaused;
import com.jcoadyschaebitz.neon.graphics.UI.ExitButton;
import com.jcoadyschaebitz.neon.graphics.UI.HealthBar;
import com.jcoadyschaebitz.neon.graphics.UI.LoadMenuButton;
import com.jcoadyschaebitz.neon.graphics.UI.MapButton;
import com.jcoadyschaebitz.neon.graphics.UI.PlayButton;
import com.jcoadyschaebitz.neon.graphics.UI.ResumeButton;
import com.jcoadyschaebitz.neon.graphics.UI.SaveButton;
import com.jcoadyschaebitz.neon.graphics.UI.SettingsButton;
import com.jcoadyschaebitz.neon.graphics.UI.SkillTreeSwitcherButton;
import com.jcoadyschaebitz.neon.graphics.UI.SkillTreeSwitcherButton.Pointing;
import com.jcoadyschaebitz.neon.graphics.UI.SkillsButton;
import com.jcoadyschaebitz.neon.graphics.UI.UIButton;
import com.jcoadyschaebitz.neon.graphics.UI.UIItemSlot;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;
import com.jcoadyschaebitz.neon.graphics.UI.XPDisplay;
import com.jcoadyschaebitz.neon.graphics.UI.skillTrees.SkillTreeManager;
import com.jcoadyschaebitz.neon.input.Keyboard;
import com.jcoadyschaebitz.neon.input.Mouse;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.level.TileCoordinate;

public class Player extends Mob implements Serializable {

	private static final long serialVersionUID = -2532504155227064417L;
	private Keyboard input;
	private int fireRate, damageDelay;
	public int xpProgress = 0, xpLevel = 0, initialMaxHealth;
	private double direction;
	public double damageReductionMultiplier, bulletSpeedMultiplier, movementSpeedMultiplier;
	public static int currentAmmo;
	public PlayerWeapon weapon;
	private Shield shield;
	private UIManager ui;
	private HealthBar healthBar;
	private UIItemSlot itemSlot1, itemSlot2, itemSlot3, itemSlot4;
	private XPDisplay xpDisplay;
	private AmmoDisplay ammoDisplay;
	private SkillTreeManager skillTreeManager;
	private ResumeButton resumeButton;
	private SettingsButton settingsButton;
	private SkillsButton skillsButton;
	private SaveButton saveButton;
	private MapButton mapButton;
	private LoadMenuButton loadMenuButton;
	private SkillTreeSwitcherButton leftButton, rightButton;
	private AmmoDisplayPaused ammoDisplayPaused;
	private PlayButton playButton;
	private ExitButton exitButton;
	public List<UIItemSlot> slots = new ArrayList<UIItemSlot>();
	private List<UIButton> buttons = new ArrayList<UIButton>();
	public Game game;

	public boolean hasGun;

	public Player(Game game, TileCoordinate spawn, Keyboard input) {
		super(spawn.getX(), spawn.getY());
		this.game = game;
		this.x = spawn.getX();
		this.y = spawn.getY();
		this.input = input;
		sprite = Sprite.playerR;
		int[] xEntityCollisionValues = { 0, 16, 0, 16, 0, 16 };
		int[] yEntityCollisionValues = { 0, 0, 22, 22, 11, 11 };
		int[] xTileCollisionValues = { 1, 16, 1, 16 };
		int[] yTileCollisionValues = { 12, 12, 22, 22 };
		spriteWidth = xTileCollisionValues[1];
		spriteHeight = yTileCollisionValues[2];
		entityBounds = new CollisionBox(xEntityCollisionValues, yEntityCollisionValues);
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		speed = 1.6; //  1.4? 1.55?
		initialMaxHealth = 32;
		maxHealth = initialMaxHealth;
		health = maxHealth;
		damageReductionMultiplier = 1;
		bulletSpeedMultiplier = 1;
		movementSpeedMultiplier = 1;

		leftWalking = new AnimatedSprite(Spritesheet.player_left_walking, 16, 16, 8, 5);//3
		rightWalking = new AnimatedSprite(Spritesheet.player_right_walking, 16, 16, 8, 5);//3
		leftIdle = new AnimatedSprite(Spritesheet.player_left_idle, 24, 24, 4, 15);//10
		rightIdle = new AnimatedSprite(Spritesheet.player_right_idle, 24, 24, 4, 15);//10
		leftDamage = new AnimatedSprite(Spritesheet.player_left_damage, 24, 24, 4, 5);
		rightDamage = new AnimatedSprite(Spritesheet.player_right_damage, 24, 24, 4, 5);
		leftDying = new AnimatedSprite(Spritesheet.player_left_dying, 24, 24, 4, 10);
		rightDying = new AnimatedSprite(Spritesheet.player_right_dying, 24, 24, 4, 10);
		currentAnim = leftIdle;

		ui = Game.getUIManager();
		ui.initialisePlayer(this);
		healthBar = new HealthBar(19, 10, 80, 5, 0xffBC0045, 0xffff0059, ui);
		itemSlot1 = new UIItemSlot(18, 18, this);
		itemSlot2 = new UIItemSlot(34, 18, this);
		itemSlot3 = new UIItemSlot(50, 18, this);
		itemSlot4 = new UIItemSlot(66, 18, this);
		xpDisplay = new XPDisplay(84, 21, ui);
		ammoDisplay = new AmmoDisplay(this);
		skillTreeManager = new SkillTreeManager(game, this);
//		actionSkillManager = new ActionSkillManager(this);
		resumeButton = new ResumeButton(13, 27, 61, 37, "Continue", 0xffADFFFF);
		settingsButton = new SettingsButton(13, 67, 61, 37, "Settings", 0xffADFFFF, game);
		skillsButton = new SkillsButton(13, 107, 61, 37, "Skills", 0xffADFFFF, game);
		mapButton = new MapButton(13, 147, 61, 37, "Map", 0xffADFFFF);
		saveButton = new SaveButton(13, 187, 61, 37, "Save and \nExit", 0xffADFFFF);
		leftButton = new SkillTreeSwitcherButton(216, 143, 5, 9, "", 0xffADFFFF, skillTreeManager, Pointing.LEFT);
		rightButton = new SkillTreeSwitcherButton(297, 143, 5, 9, "", 0xffADFFFF, skillTreeManager, Pointing.RIGHT);
		playButton = new PlayButton(60, 120, 60, 30, "Play", 0xffADFFFF);
		loadMenuButton = new LoadMenuButton(60, 160, 61, 37, "Load", 0xffADFFFF);
		exitButton = new ExitButton(60, 200, 61, 37, "Exit", 0xffADFFFF);
		
		buttons.add(resumeButton);
		buttons.add(settingsButton);
		buttons.add(skillsButton);
		buttons.add(mapButton);
		buttons.add(saveButton);
		buttons.add(leftButton);
		buttons.add(rightButton);
		buttons.add(playButton);
		buttons.add(exitButton);
		buttons.add(loadMenuButton);
		
		slots.add(itemSlot1);
		slots.add(itemSlot2);
		slots.add(itemSlot3);
		slots.add(itemSlot4);
		ammoDisplayPaused = new AmmoDisplayPaused(316, 40, this);
		healthBar.setMaxValue(maxHealth);
		healthBar.setValue(health);
		shield = skillTreeManager.getShield();
		game.gamePlayUI.addComp(healthBar);
		game.gamePlayUI.addComp(itemSlot1);
		game.gamePlayUI.addComp(itemSlot2);
		game.gamePlayUI.addComp(itemSlot3);
		game.gamePlayUI.addComp(itemSlot4);
		game.gamePlayUI.addComp(ammoDisplay);
		game.gamePlayUI.addComp(xpDisplay);
		game.gamePlayUI.addComp(shield.actionSkillDisplay);
		game.pauseSkillsMenu.addComp(resumeButton);
		game.pauseSkillsMenu.addComp(settingsButton);
		game.pauseSkillsMenu.addComp(skillsButton);
		game.pauseSkillsMenu.addComp(mapButton);
		game.pauseSkillsMenu.addComp(saveButton);
		game.pauseSkillsMenu.addComp(leftButton);
		game.pauseSkillsMenu.addComp(rightButton);
		game.pauseSkillsMenu.addComp(skillTreeManager);
		game.pauseSkillsMenu.addComp(ammoDisplayPaused);
		game.pauseSettingsMenu.addComp(resumeButton);
		game.pauseSettingsMenu.addComp(settingsButton);
		game.pauseSettingsMenu.addComp(skillsButton);
		game.pauseSettingsMenu.addComp(mapButton);
		game.pauseSettingsMenu.addComp(saveButton);
//		game.cutSceneUI.addComp(skipButton);	add skip button as button (find out where thats currently being done.
		game.mainMenu.addComp(playButton);
		game.mainMenu.addComp(exitButton);
		game.mainMenu.addComp(loadMenuButton);
		game.loadMenu.addComp(playButton);
		game.loadMenu.addComp(exitButton);
		
		ui.addMenu(game.pauseSkillsMenu);
		ui.setMenu(game.mainMenu);
	}

	public Shield getShield() {
		return shield;
	}
	
	public void addButton(UIButton button) {
		buttons.add(button);
	}

	public void changeHealth(double amount) {
		if (health + amount > maxHealth) health = maxHealth;
		else health += amount;
		healthBar.setValue(health);
	}

	public void setHealth(double amount) {
		if (amount > health) health = maxHealth;
		else health = amount;
		healthBar.setValue(health);
	}

	public List<UIButton> getMouseListeningButtons() {
		return buttons;
	}
	
	public boolean addWeapon(PlayerWeapon weapon) {
		if (this.weapon == null) this.weapon = weapon;
		return ui.addWeapon(weapon);
	}

	public double getDirection() {
		return direction;
	}

	public void update() {
		time++;
		if (!inCutscene) updateInput();
		updateAnimSprites();
		shield.update();
		healthBar.setMaxValue(maxHealth);

		if (fireRate > 0) fireRate--;
		if (damageDelay > 0) damageDelay--;

		if (xa != 0 || ya != 0) {
			move(xa, ya, true);
			walking = true;
		} else {
			walking = false;
		}

		if (weapon != null) {
			weapon.update();
			weapon.updateSprite(direction);
			currentAmmo = weapon.ammoCount;
			if (Mouse.getB() != 1) weapon.mouseReleased();
		} else currentAmmo = 0;
	}

	private void updateInput() {
		double dx = (Mouse.getX() - Game.getWindowWidth() / 2) - Game.getXBarsOffset();
		double dy = Mouse.getY() - Game.getWindowHeight() / 2;
		double direction = Math.atan2(dy, dx);
		this.direction = direction;

		xa = 0;
		ya = 0;
		if (input.up) ya -= speed * movementSpeedMultiplier;
		if (input.down) ya += speed * movementSpeedMultiplier;
		if (input.left) xa -= speed * movementSpeedMultiplier;
		if (input.right) xa += speed * movementSpeedMultiplier;
		
		if (xa != 0 && ya != 0) {
			xa *= 0.71;
			ya *= 0.71;
		}

		for (int i = 0; i < input.numbers.length; i++) {
			if (input.numbers[i] && slots.get(i - 1).hasItem) ui.selectedItemSlot = i - 1;
		}

		if (Mouse.getX() >= Game.getWindowWidth() / 2 + Game.getXBarsOffset()) dir = Orientation.RIGHT;
		if (Mouse.getX() < Game.getWindowWidth() / 2 + Game.getXBarsOffset()) dir = Orientation.LEFT;

		if (weapon != null) updateShooting(direction);
	}

	public void hitReceived(Projectile projectile) {
		if (health > 0 && damageDelay == 0) {
			changeHealth(-projectile.getDamage());
			damageDelay = 1;
			level.addTrauma(0.4);
		}
	}

	public void hitReceived(MeleeWeapon weapon) {
		if (health > 0 && damageDelay == 0) {
			changeHealth(-weapon.damage);
			damageDelay = 5;
			level.addTrauma(0.6);
		}
	}

	private void updateShooting(double direction) {
		if (Mouse.getB() == 1 && fireRate == 0 && weapon.ammoCount > 0) {
			weapon.attack(true, x, y, direction, bulletSpeedMultiplier);
			weapon.ammoChange(-1);
			fireRate = weapon.getCooldown();
			level.addScreenRecoil(weapon.getRecoil(), direction);
		}
	}

	public UIItemSlot getFirstEmptySlot() {
		if (!itemSlot1.hasItem) return itemSlot1;
		if (!itemSlot2.hasItem) return itemSlot2;
		if (!itemSlot3.hasItem) return itemSlot3;
		if (!itemSlot4.hasItem) return itemSlot4;
		return null;
	}

	public UIItemSlot getSelectedSlot() {
		for (int i = 0; i < slots.size(); i++) {
			if (slots.get(i).selected) return slots.get(i);
		}
		return slots.get(0);
	}

	public void setGun(PlayerWeapon weapon) {
		this.weapon = weapon;
	}

	public void addXP(int amount) {
		xpProgress += amount;
		if (xpProgress >= 200) {
			xpProgress -= 200;
			xpLevel++;
		}
		xpDisplay.setValues(xpProgress, xpLevel);
	}

	public SkillTreeManager getSkillTreeManager() {
		return skillTreeManager;
	}

	public void switchGunsToLevel(Level newL) {
		for (UIItemSlot s : slots) {
			try {
				newL.add(s.getWeapon());
			} catch (NullPointerException e) {}
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void goTo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void render(Screen screen) {
		if (dir == Orientation.RIGHT) {
			if (walking) currentAnim = rightWalking;
			else currentAnim = rightIdle;
		}

		if (dir == Orientation.LEFT) {
			if (walking) currentAnim = leftWalking;
			else currentAnim = leftIdle;
		}
		sprite = currentAnim.getSprite();
		screen.renderTranslucentSprite((int) x - 3, (int) y + 3, Sprite.shadow, true, 0.6);
		if (shield.getYAnchor() < getYAnchor()) {
			shield.render(screen);
			screen.renderSprite((int) x, (int) y, sprite, true);
			if (weapon != null) weapon.renderOnOwner(screen, (currentAnim.getFrame() / 2) % 2);
		} else {
			screen.renderSprite((int) x, (int) y, sprite, true);
			if (weapon != null) weapon.renderOnOwner(screen, (currentAnim.getFrame() / 2) % 2);
			shield.render(screen);
		}
//		screen.renderSprite((int) x - 7, (int) y - 4, Sprite.shieldOutline, true);
//		screen.renderTranslucentSprite((int) x - 7, (int) y - 4, Sprite.shieldOutlineGlow, true, 0.2);
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {	
	}

}
