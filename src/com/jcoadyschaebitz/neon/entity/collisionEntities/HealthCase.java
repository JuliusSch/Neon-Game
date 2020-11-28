package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.Item.HealthKit;
import com.jcoadyschaebitz.neon.entity.Item.HealthKit.KitType;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Orientation;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.spawner.ParticleSpawner;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class HealthCase extends CollisionEntity {

	public HealthCase(int x, int y, Orientation or) {
		super(x, y);
		this.x = x;
		this.y = y;
		health = 1;
		int[] xPoints = { 0, 15, 0, 15 };
		int[] yPoints = { -10, -10, 16, 16 };
		corners = new CollisionBox(xPoints, yPoints);
	}
	
	public int getYAnchor() {
		return (int) y + 24;
	}
	
	public void hitReceived(Projectile projectile) {
		if (health > 0) {
			level.add(new HealthKit((int) x, (int) y - 3, KitType.WALLFRONT));
			level.add(new ParticleSpawner((int) x + 8, (int) y + 16, 100, 240, level, Sprite.glassParticle));
			health = 0;
		}
	}
	
	public void render(Screen screen) {
		if (health == 0) screen.renderSprite((int) x, (int) y, Sprite.emptyWallKitFront, true);
		else {
			screen.renderSprite((int) x, (int) y, Sprite.wallFrontKit, true);
			screen.renderTranslucentSprite((int) x, (int) y, Sprite.WallKitFrontGlass, true, 0.4);
		}
	}

}
