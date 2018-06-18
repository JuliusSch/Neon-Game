package com.jcoadyschaebitz.neon.entity.mob;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.Shield;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.weapon.MeleeWeapon;
import com.jcoadyschaebitz.neon.entity.weapon.PlayerWeapon;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.graphics.UI.ActionSkillManager;
import com.jcoadyschaebitz.neon.graphics.UI.AmmoDisplay;
import com.jcoadyschaebitz.neon.graphics.UI.AmmoDisplayPaused;
import com.jcoadyschaebitz.neon.graphics.UI.HealthBar;
import com.jcoadyschaebitz.neon.graphics.UI.MapButton;
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
	private PlayerWeapon[] weapons = new PlayerWeapon[4];
	private UIManager ui;
	private HealthBar healthBar;
	private UIItemSlot itemSlot1;
	private UIItemSlot itemSlot2;
	private UIItemSlot itemSlot3;
	private UIItemSlot itemSlot4;
	private XPDisplay xpDisplay;
	private AmmoDisplay ammoDisplay;
	private SkillTreeManager skillTreeManager;
	private ActionSkillManager actionSkillManager;
	private ResumeButton resumeButton;
	private SettingsButton settingsButton;
	private SkillsButton skillsButton;
	private SaveButton saveButton;
	private MapButton mapButton;
	private SkillTreeSwitcherButton leftButton;
	private SkillTreeSwitcherButton rightButton;
	private AmmoDisplayPaused ammoDisplayPaused;
	public List<UIItemSlot> slots = new ArrayList<UIItemSlot>();
	private List<UIButton> buttons = new ArrayList<UIButton>();
	public Game game;

	public boolean hasGun;

	public Player(Game game, TileCoordinate spawn, Keyboard input) {
		this.game = game;
		this.x = spawn.getX();
		this.y = spawn.getY();
		this.input = input;
		sprite = Sprite.playerR;
		int[] xEntityCollisionValues = { 0, 16, 0, 16, 0, 16 };
		int[] yEntityCollisionValues = { 0, 0, 22, 22, 11, 11 };
		int[] xTileCollisionValues = { 0, 16, 0, 16 };
		int[] yTileCollisionValues = { 12, 12, 22, 22 };
		spriteWidth = xTileCollisionValues[1];
		spriteHeight = yTileCollisionValues[2];
		entityBounds = new CollisionBox(xEntityCollisionValues, yEntityCollisionValues);
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		speed = 1.55; // normally 1.4
		initialMaxHealth = 32;
		maxHealth = initialMaxHealth;
		health = maxHealth;
		damageReductionMultiplier = 1;
		bulletSpeedMultiplier = 1;
		movementSpeedMultiplier = 1;

		leftWalking = new AnimatedSprite(Spritesheet.player_left_walking, 16, 16, 8, 3);
		rightWalking = new AnimatedSprite(Spritesheet.player_right_walking, 16, 16, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.player_left_idle, 24, 24, 4, 10);
		rightIdle = new AnimatedSprite(Spritesheet.player_right_idle, 24, 24, 4, 10);
		leftDamage = new AnimatedSprite(Spritesheet.player_left_damage, 24, 24, 4, 5);
		rightDamage = new AnimatedSprite(Spritesheet.player_right_damage, 24, 24, 4, 5);
		leftDying = new AnimatedSprite(Spritesheet.player_left_dying, 24, 24, 4, 10);
		rightDying = new AnimatedSprite(Spritesheet.player_right_dying, 24, 24, 4, 10);
		currentAnim = leftWalking;

		ui = Game.getUIManager();
		ui.initialisePlayer(this);
		healthBar = new HealthBar(19, 10, 80, 4, 0xffBC0045, 0xffff0059, ui);
		itemSlot1 = new UIItemSlot(18, 18, this);
		itemSlot2 = new UIItemSlot(34, 18, this);
		itemSlot3 = new UIItemSlot(50, 18, this);
		itemSlot4 = new UIItemSlot(66, 18, this);
		xpDisplay = new XPDisplay(84, 21, ui);
		ammoDisplay = new AmmoDisplay(this);
		skillTreeManager = new SkillTreeManager(game, this);
		actionSkillManager = new ActionSkillManager(this);
		resumeButton = new ResumeButton(13, 27, 58, 36, "Continue", 0xffBAFFDA);
		settingsButton = new SettingsButton(13, 67, 58, 36, "Settings", 0xffBAFFDA, game);
		skillsButton = new SkillsButton(13, 107, 58, 36, "Skills", 0xffBAFFDA, game);
		mapButton = new MapButton(13, 147, 58, 36, "Map", 0xffBAFFDA);
		saveButton = new SaveButton(13, 187, 58, 36, "Save and \nExit", 0xffBAFFDA);
		leftButton = new SkillTreeSwitcherButton(216, 143, 5, 9, "", 0xffBAFFDA, skillTreeManager, Pointing.LEFT);
		rightButton = new SkillTreeSwitcherButton(297, 143, 5, 9, "", 0xffBAFFDA, skillTreeManager, Pointing.RIGHT);
		buttons.add(resumeButton);
		buttons.add(settingsButton);
		buttons.add(skillsButton);
		buttons.add(mapButton);
		buttons.add(saveButton);
		buttons.add(leftButton);
		buttons.add(rightButton);
		slots.add(itemSlot1);
		slots.add(itemSlot2);
		slots.add(itemSlot3);
		slots.add(itemSlot4);
		ammoDisplayPaused = new AmmoDisplayPaused(316, 40, this);
		healthBar.setMaxValue(maxHealth);
		healthBar.setValue(health);
		game.gamePlayUI.addComp(healthBar);
		game.gamePlayUI.addComp(itemSlot1);
		game.gamePlayUI.addComp(itemSlot2);
		game.gamePlayUI.addComp(itemSlot3);
		game.gamePlayUI.addComp(itemSlot4);
		game.gamePlayUI.addComp(ammoDisplay);
		game.gamePlayUI.addComp(xpDisplay);
		game.gamePlayUI.addComp(actionSkillManager.actionSkillDisplay);
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
		ui.addMenu(game.pauseSkillsMenu);
	}

	public void changeHealth(double amount) {
		if (health + amount > maxHealth) health = maxHealth;
		else health += amount;
		healthBar.setValue(health);
	}

	public List<UIButton> getMouseListeningButtons() {
		return buttons;
	}

	public void setHealth(double amount) {
		if (amount > health) health = maxHealth;
		else health = amount;
		healthBar.setValue(health);
	}

	public void addWeapon(PlayerWeapon weapon) {
		if (this.weapon == null) this.weapon = weapon;
		ui.addWeapon(weapon);
		try {
			weapons[getFirstEmptySlot().thisSlot] = weapon;
		} catch (NullPointerException e) {}
	}

	public double getDirection() {
		return direction;
	}

	public ActionSkillManager getActionSkillManager() {
		return actionSkillManager;
	}

	public void update() {
		double dx = (Mouse.getX() - Game.getWindowWidth() / 2) - Game.getXRenderOffset();
		double dy = Mouse.getY() - Game.getWindowHeight() / 2;
		double direction = Math.atan2(dy, dx);
		this.direction = direction;

		updateAnimSprites();
		if (weapon != null) {
			weapon.update();
			weapon.updateSprite(direction);
			updateShooting(direction);
			currentAmmo = weapon.ammoCount;
		} else currentAmmo = 0;
		actionSkillManager.update();
		healthBar.setMaxValue(maxHealth);

		if (fireRate > 0) fireRate--;
		if (damageDelay > 0) damageDelay--;
		xa = 0;
		ya = 0;

		if (input.up) ya -= speed * movementSpeedMultiplier;
		if (input.down) ya += speed * movementSpeedMultiplier;
		if (input.left) xa -= speed * movementSpeedMultiplier;
		if (input.right) xa += speed * movementSpeedMultiplier;

		for (int i = 0; i < input.numbers.length; i++) {
			if (input.numbers[i] && slots.get(i - 1).hasItem) ui.selectedItemSlot = i - 1;
		}

		if (Mouse.getX() > Game.getWindowWidth() / 2 + Game.getXRenderOffset()) dir = Direction.RIGHT;
		if (Mouse.getX() < Game.getWindowWidth() / 2 + Game.getXRenderOffset()) dir = Direction.LEFT;

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}

		if (recoilMeter > 0) {
			recoilMeter--;
			move(recoilX, recoilY);
			recoilX -= recoilX / 10;
			recoilY -= recoilY / 10;
		} else {
			recoilX = 0;
			recoilY = 0;
		}
	}

	public void hitReceived(Projectile projectile) {
		if (health > 0 && damageDelay == 0) {
			if (actionSkillManager.shieldActive) changeHealth(-projectile.getDamage() * damageReductionMultiplier);
			else changeHealth(-projectile.getDamage());
			damageDelay = 1;
			level.addTrauma(0.4);
		}
	}

	public void hitReceived(MeleeWeapon weapon) {
		if (health > 0 && damageDelay == 0) {
			if (actionSkillManager.shieldActive) changeHealth(-weapon.damage * damageReductionMultiplier);
			else changeHealth(-weapon.damage);
			damageDelay = 5;
			level.addTrauma(0.6);
		}
	}

	private void updateShooting(double direction) {
		if (Mouse.getB() == 1 && fireRate == 0 && weapon.ammoCount > 0) {
			weapon.attack(true, x, y, direction, bulletSpeedMultiplier);
			weapon.ammoChange(-1);
			fireRate = weapon.getCooldown();
			level.addScreenRecoil(/* 0.2 */weapon.getRecoil(), direction);
		}
	}

	public void render(Screen screen) {

		if (dir == Direction.RIGHT) {
			if (walking) currentAnim = rightWalking;
			else currentAnim = rightIdle;
		}

		if (dir == Direction.LEFT) {
			if (walking) currentAnim = leftWalking;
			else currentAnim = leftIdle;
		}
		sprite = currentAnim.getSprite();
		screen.renderTranslucentSprite((int) x - 3, (int) y + 3, Sprite.shadow, true, 0.6);
		screen.renderSprite((int) x, (int) y, sprite, true);
		if (weapon != null) weapon.renderOnOwner(screen, (currentAnim.getFrame() / 2) % 2);
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

	public Shield createShield(int timeLeft) {
		return skillTreeManager.createShield((int) x, (int) y, direction, timeLeft, this, level);
	}
	
	public void switchGunsToLevel(Level newL) {
		for (PlayerWeapon w : weapons) {
			try {
				newL.add(w);
			} catch (NullPointerException e) {}
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
