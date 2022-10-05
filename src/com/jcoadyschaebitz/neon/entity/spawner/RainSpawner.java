package com.jcoadyschaebitz.neon.entity.spawner;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.particle.RainParticle;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class RainSpawner extends Spawner {

	public RainSpawner(double x, double y, int amount, Level level) {
		super(x, y, level);
	}

	public void update() {
		for (int i = 0; i < 6; i++) {
		int xx = (int) Game.getUIManager().getGame().getScreen().xOffset - 12 * 16 + random.nextInt(48 * 16);
		int yy = (int) Game.getUIManager().getGame().getScreen().yOffset - 4 * 16;
		level.add(new RainParticle(xx, yy, 2, 4, Sprite.rainParticle, 2));
		}
	}

}
