package com.jcoadyschaebitz.neon.entity.particle;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class LineParticle extends Particle {
	
	int colour;
	Vec2i p1, p2;

	public LineParticle(Vec2i p1, Vec2i p2, int maxLife, int colour) {
		super(p1.x, p1.y, maxLife, 0, 0, null);
		this.colour = colour;
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public void render(Screen screen) {
		screen.renderLine(p1.x, p1.y, p2.x, p2.y, colour, 1);
	}

}
