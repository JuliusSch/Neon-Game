package com.jcoadyschaebitz.neon.entity.particle;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Particle extends Entity {

	protected Sprite sprite;

	protected int life;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	public Particle(double x, double y, int maxLife, int spriteW, int spriteH, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		life = maxLife - random.nextInt(30);
		this.sprite = sprite;
		
		spriteWidth = spriteW;
		spriteHeight = spriteH;

		xa = random.nextGaussian() / 3;
		ya = random.nextGaussian() / 3;
		zz = random.nextDouble() * 10;
	}

	public void update() {
		time++;
		if (time > life) remove();
		za -= 0.1;
		
		if (zz < 0) {
			zz= 0;
			za *= -0.4;
			xa *= 0.5;
			ya *= 0.5;
		}
		
		move(xx + xa, (yy + ya) + (zz + za));
	}

	private void move(double x, double y) {
		if (collision(x, y)) {
			xa *= -0.9;
			ya *= -0.9;
//			za *= -0.8;
		}
		xx += xa;
		yy += ya;
		zz += za;
		
	}
	
	public boolean collision(double x, double y) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt =  (x + c % 2) / 16;
			double yt =  (y + c / 2) / 16;
			if (level.getTile(xt, yt).isSolid()) solid = true;
		}
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xx, (int) yy - (int) zz, sprite, true);
	}

}
