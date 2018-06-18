package com.jcoadyschaebitz.neon.entity.particle;

import com.jcoadyschaebitz.neon.entity.spawner.ParticleSpawner;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.tile.Tile;

public class RainParticle extends Particle {

	public RainParticle(int x, int y, int spriteW, int spriteH, Sprite sprite) {
		super(x, y, 500, spriteW, spriteH, sprite);
	}

	public void update() {
		time++;
		if (y > level.getHeight() * 16) remove();
		y += 5;
		x += 0.75;
		Tile tile = level.getTile(x / 16, y / 16);
		if (!tile.isSolid() && tile.isOutdoors()) {
			if (random.nextInt(6) == 0 && random.nextInt(6) == 0) {
				level.add(new ParticleSpawner((int) x, (int) y, 5, 20, level, Sprite.small_rain_particle));
				remove();
			} 
		} else if (!tile.isOutdoors()) {
			remove();
		}
	}

	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y, sprite, true, 0.5);
	}

}
