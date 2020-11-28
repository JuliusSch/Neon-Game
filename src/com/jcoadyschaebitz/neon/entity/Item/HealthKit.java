package com.jcoadyschaebitz.neon.entity.Item;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class HealthKit extends Item {

	private double amount;
	private int yROffset;
	private KitType type;
	public enum KitType {
		WALLFRONT, WALLLEFT, WALLRIGHT, FREE 
	};

	public HealthKit(int x, int y, double amount, KitType type) {
		super(x, y);
		this.amount = amount;
		sprite = Sprite.healthKit;
		this.type = type;
		switch (type) {
		case FREE: 
			break;
		case WALLFRONT:
			break;
		case WALLLEFT:
			break;
		case WALLRIGHT:
			break;
		default:
			yROffset = 0;
			break;
		}
	}
	
	public int getYAnchor() {
		if (type == KitType.WALLFRONT) return (int) y + 24;
		else return (int) y + getSpriteH();
	}

	public HealthKit(int x, int y, KitType type) {
		this(x, y, 8, type);
	}
	
	public HealthKit(int x, int y) {
		this(x, y, 8, KitType.FREE);
	}

	public void update() {
		if (level.isPlayerInRad(x, y + yROffset, 15)) doPickUp();
	}
	
	@Override
	protected void doPickUp() {		
		if (level.getPlayer().getHealth() == level.getPlayer().maxHealth) return;
		level.getPlayer().changeHealth(amount);
		remove();
	}

	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y + yROffset, Sprite.healthKitShadow, true, 0.7);
		screen.renderSprite((int) x, (int) y + yROffset, sprite, true);
	}
}
