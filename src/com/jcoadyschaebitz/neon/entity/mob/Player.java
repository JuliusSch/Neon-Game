package com.jcoadyschaebitz.neon.entity.mob;

import java.util.List;
import java.util.Map;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.IInteractableItem;
import com.jcoadyschaebitz.neon.entity.Shield;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.entity.projectile.Bolt;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.weapon.MeleeWeapon;
import com.jcoadyschaebitz.neon.entity.weapon.PlayerWeapon;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;
import com.jcoadyschaebitz.neon.graphics.UI.skillTrees.SkillTreeManager;
import com.jcoadyschaebitz.neon.input.IInputObserver;
import com.jcoadyschaebitz.neon.input.InputManager;
import com.jcoadyschaebitz.neon.input.InputManager.InputAction;
import com.jcoadyschaebitz.neon.input.InputManager.InputType;
import com.jcoadyschaebitz.neon.input.Mouse;
import com.jcoadyschaebitz.neon.level.TileCoordinate;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class Player extends Mob implements IInputObserver {

	private InputManager inputManager;
	private int fireRate, damageDelay;
	private double direction;
	public int xpProgress = 0, xpLevel = 0, initialMaxHealth;
	public double damageReductionMultiplier, bulletSpeedMultiplier, movementSpeedMultiplier;
	public static int currentAmmo;
	public PlayerWeapon weapon;
	private UIManager uiManager;
	public Game game;

	public boolean hasGun;

	public Player(Game game, TileCoordinate spawn, InputManager input) {
		super(spawn.getX(), spawn.getY());
		this.game = game;
		this.x = spawn.getX();
		this.y = spawn.getY();
		this.inputManager = input;
		input.registerObserver(this);
		sprite = Sprite.playerR;
		int[] xEntityCollisionValues = { 2, 14, 2, 14, 2, 14 };
		int[] yEntityCollisionValues = { 4, 4, 18, 18, 11, 11 };
		int[] xTileCollisionValues = { 2, 15, 2, 15 };
		int[] yTileCollisionValues = { 12, 12, 22, 22 };
		spriteWidth = xTileCollisionValues[1];
		spriteHeight = yTileCollisionValues[2];
		entityBounds = new CollisionBox(xEntityCollisionValues, yEntityCollisionValues);
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		speed = 1.8; // 1.4? 1.55? // maintain 1.6/1.8
		initialMaxHealth = 32;
		maxHealth = initialMaxHealth;
		health = maxHealth;
		damageReductionMultiplier = 1;
		bulletSpeedMultiplier = 1;
		movementSpeedMultiplier = 1;

		leftWalking = new AnimatedSprite(Spritesheet.player_left_walking, 16, 16, 8, 5);// 3
		rightWalking = new AnimatedSprite(Spritesheet.player_right_walking, 16, 16, 8, 5);// 3
		leftIdle = new AnimatedSprite(Spritesheet.player_left_idle, 24, 24, 4, 15);// 10
		rightIdle = new AnimatedSprite(Spritesheet.player_right_idle, 24, 24, 4, 15);// 10
		leftDamage = new AnimatedSprite(Spritesheet.player_left_damage, 24, 24, 4, 5);
		rightDamage = new AnimatedSprite(Spritesheet.player_right_damage, 24, 24, 4, 5);
		leftDying = new AnimatedSprite(Spritesheet.player_left_dying, 24, 24, 6, 10);
		rightDying = new AnimatedSprite(Spritesheet.player_right_dying, 24, 24, 6, 10);
		currentAnim = leftIdle;

		uiManager = Game.getUIManager();
		uiManager.initialisePlayer(this);
		uiManager.initialiseMenus();
	}

	public Map<String, String> getSaveData() {
		Map<String, String> result = uiManager.getSaveData();
		result.put("PlayerPosition", (new Vec2i((int) x, (int) y)).getString());
		return result;
	}

	public Shield getShield() {
		return uiManager.shield;
	}

	public void changeHealth(double amount) {
		if (health + amount > maxHealth)
			health = maxHealth;
		else
			health += amount;
		
		uiManager.healthBar.setValue(health);
	}

	public void setHealth(double amount) {
		if (amount > health) health = maxHealth;
		else health = amount;
		uiManager.healthBar.setValue(health);
	}

	public boolean addWeapon(PlayerWeapon weapon) {
		if (this.weapon == null) this.weapon = weapon;
		return uiManager.addWeapon(weapon);
	}

	public double getDirection() {
		return direction;
	}

	public void update() {
		time++;
		if (!immobilised) updateInput();
		updateAnimSprites();
		uiManager.shield.update();
		uiManager.healthBar.setMaxValue(maxHealth);

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
			if (Mouse.getB() != 1) weapon.mouseReleased(); // amend to use input system
		} else currentAmmo = 0;
		if (beingKnockedBack) {
			move(knockbackX, knockbackY, true);
			knockbackX -= knockbackX * 0.1;
			knockbackY -= knockbackY * 0.1;
		}
		if (knockbackX < 0.1 && knockbackX > -0.1 && knockbackY < 0.1 && knockbackY > -0.1) beingKnockedBack = false;

	}
	
	@Override
	public void InputReceived(InputAction input, double value) {
		if (level == null)												// This is jank, need a better way to handle input received updates separation between in-game and menus
			return;
		if (input == InputAction.SECONDARY_ACTION && value == 1f) {
			List<IInteractableItem> items = level.getInteractableItemsInRadius(40);
			if (items.size() > 0)
				items.get(0).onInteract(InputAction.SECONDARY_ACTION);
		}
	}

	private void updateInput() {
		if (uiManager.getCurrentInputType() == InputType.KEYBOARD) {
			direction = Math.atan2(inputManager.getMouseYRelMid(), inputManager.getMouseXRelMidWithBars());
			if (inputManager.getMouseXRelMidWithBars() >= 0)
				dir = Orientation.RIGHT;
			else
				dir = Orientation.LEFT;
		} else {
			direction = inputManager.getInput(InputAction.LOOK_DIRECTION);
			if (Math.abs(direction) <=  Math.PI / 2)
				dir = Orientation.RIGHT;
			else
				dir = Orientation.LEFT;
		}
		
		xa = 0;
		ya = 0;

		ya += speed * movementSpeedMultiplier * inputManager.getInput(InputAction.MOVE_UP);
		ya += speed * movementSpeedMultiplier * inputManager.getInput(InputAction.MOVE_DOWN);
		xa += speed * movementSpeedMultiplier * inputManager.getInput(InputAction.MOVE_LEFT);
		xa += speed * movementSpeedMultiplier * inputManager.getInput(InputAction.MOVE_RIGHT);
		
		// Need to account for greater movement speed on diagonal
//
//		for (int i = 0; i < inputManager.keyboard.numbers.length; i++) {
//			if (inputManager.keyboard.numbers[i] && uiManager.slots.get(i - 1).hasItem) uiManager.selectedItemSlot = i - 1;
//		}

		if (weapon != null) updateShooting(direction);
	}

	public void hitReceived(Projectile projectile) {
		if (health > 0/* && damageDelay == 0 */) { // Need some other solution for hit immunity
			changeHealth(-projectile.getDamage());
			if (health <= 0) {
				damageDelay = leftDamage.getTotalLength();
				knockback(projectile.angle, 5 * knockbackMultiplier * projectile.getKnockbackMultiplier());
				level.addTrauma(0.8);
				state = MobState.DYING;
				Game.getUIManager().getGame().switchToGameState(Game.getUIManager().getGame().playerDiedState);
			} else {
				damageDelay = leftDying.getTotalLength();
				knockback(projectile.angle, 2 * knockbackMultiplier);
				level.addTrauma(.6);
				if (projectile instanceof Bolt) ((Bolt) projectile).impaled(this);
				state = MobState.TAKINGDAMAGE;
			}
			rightDamage.playOnce();
			leftDamage.playOnce();
			rightDying.playOnce();
			leftDying.playOnce();
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
		if (inputManager.isInput(InputAction.PRIMARY_ACTION) && fireRate == 0 && weapon.ammoCount > 0) {
			weapon.attack(true, getMidX(), getMidY(), direction, bulletSpeedMultiplier);
			weapon.ammoChange(-1);
			fireRate = weapon.getCooldown();
			level.addScreenRecoil(weapon.getRecoil(), direction);
		}
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
		uiManager.xpDisplay.setValues(xpProgress, xpLevel);
	}

	public SkillTreeManager getSkillTreeManager() {
		return uiManager.skillTreeManager;
	}

	public void goTo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void render(Screen screen) {
		if (dir == Orientation.RIGHT && damageDelay == 0) {
			if (walking) currentAnim = rightWalking;
			else currentAnim = rightIdle;
		}

		if (dir == Orientation.LEFT) {
			if (walking) currentAnim = leftWalking;
			else currentAnim = leftIdle;
		}
		sprite = currentAnim.getSprite();
		screen.renderTranslucentSprite((int) x - 3, (int) y + 3, Sprite.shadow, true, 0.6);
		if (uiManager.shield.getYAnchor() < getYAnchor()) {
			uiManager.shield.render(screen);
			screen.renderSprite((int) x, (int) y, sprite, true);
			if (weapon != null) weapon.renderOnOwner(screen, (currentAnim.getFrame() / 2) % 2);
		} else {
			screen.renderSprite((int) x, (int) y, sprite, true);
			if (weapon != null) weapon.renderOnOwner(screen, (currentAnim.getFrame() / 2) % 2);
			uiManager.shield.render(screen);
		}
//		screen.renderSprite((int) x - 7, (int) y - 4, Sprite.shieldOutline, true);
//		screen.renderTranslucentSprite((int) x - 7, (int) y - 4, Sprite.shieldOutlineGlow, true, 0.2);
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
	}
}
