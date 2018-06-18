package com.jcoadyschaebitz.neon.entity.spawner;

import com.jcoadyschaebitz.neon.entity.particle.RainParticle;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class RainSpawner extends Spawner {

	public RainSpawner(double x, double y, int amount, Level level) {
		super(x, y, level);
	}

	public void update() {
		for (int i = 0; i < 8; i++) {
		int xx = random.nextInt(level.getWidth() * 16);
		int yy = level.getPlayer().getIntY() - 16 * 8;
		level.add(new RainParticle(xx, yy, 2, 4, Sprite.rain_particle));
		}
	}

}
