package com.jcoadyschaebitz.neon.entity.Item;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Screen;

public abstract class Item extends Entity {
	
	public Item(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void update() {
		if (level.isPlayerInRad(this, 20)) doPickUp();	
	}
	
	protected abstract void doPickUp();

	@Override
	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, sprite, true);		
	}

	@Override
	public void hitReceived(Projectile projectile) {
	}

}
