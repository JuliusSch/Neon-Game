package com.jcoadyschaebitz.neon.entity.spawner;

import com.jcoadyschaebitz.neon.entity.particle.Particle;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class ParticleSpawner extends Spawner {

	private int life;

	public ParticleSpawner(int x, int y, int amount, int life, Level level, Sprite sprite) {
		super(x, y, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, this.life, 2, 2, sprite));
		}
		remove();
	}

}
