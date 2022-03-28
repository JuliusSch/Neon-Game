package com.jcoadyschaebitz.neon.entity;

import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.input.Keyboard;

public abstract class InteractableEntity extends Entity {
	
	protected Keyboard input;
	protected int interactRadius = 50;
	protected Sprite key;
	
	public InteractableEntity(Keyboard input) {
		this.input = input;
	}
	
	@Override
	public void update() {
		if (input.E && level.isPlayerInRad(centreX, centreY, interactRadius)) interact("E");
	}

	@Override
	public void hitReceived(Projectile projectile) {
	}
	
	protected abstract void interact(String key);
	
	@Override
	public void render(Screen screen) {
		screen.renderSprite(getIntX(), getIntY(), sprite, true);
		if (level.isPlayerInRad(centreX, centreY, interactRadius)) screen.renderSprite(getIntX(), getIntY(), key, true);
	}
}
