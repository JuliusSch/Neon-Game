package com.jcoadyschaebitz.neon.level.tile;

import java.util.List;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class ReflectionRenderer implements Renderer {
	
	private boolean flipped;
	private double intensity;

	public ReflectionRenderer(boolean flipped, double intensity) {
		this.flipped = flipped;
		this.intensity = intensity;
	}
	
	@Override
	public void render(int x, int y, Screen screen, Level level, long seed) {
		screen.renderTranslucentSprite((x << 4), (y << 4), getReflectionSprite(x, y, level, flipped), true, intensity);
	}
	
	public static Sprite getReflectionSprite(int x, int y, Level level, boolean flipped) {
		Sprite reflection = Sprite.nullSprite;
		List<Entity> nearby = level.getEntitiesInRad((x << 4) + 8, (y << 4) + 8, 48);
		for (Entity e : nearby) {
			Sprite eSpr = e.getSprite();
			if (eSpr == null) continue;
			int dx = (x << 4) - e.getIntX();
			int dy = (y << 4) - e.getIntY();
			if (flipped) reflection = reflection.overlay(Sprite.mirrorVert(eSpr), dx, dy - e.getSpriteH());
			else reflection = reflection.overlay(eSpr, dx, dy + (e.getSpriteH() / 2));
		}
		return reflection;
	}

}
