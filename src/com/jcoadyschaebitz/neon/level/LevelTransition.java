package com.jcoadyschaebitz.neon.level;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.util.Rect;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class LevelTransition {

	private Vec2i entryPoint;
	private Rect exitArea;
	private int switchDelay = 0;
	private final int switchDelaylength = 60;
	private Level levelFrom, levelTo;
	private final int transitionLength = 60;
	
	public LevelTransition(Rect exitA, Vec2i entryP, Level levelFrom, Level levelTo) {
		entryPoint = entryP;
		exitArea = exitA;
		this.levelFrom = levelFrom;
		this.levelTo = levelTo;
	}
	
	public void update(int x, int y) {
		checkForLevelChange(x, y);
	}
	
	private void checkForLevelChange(int x, int y) {
		int xp = x >> 4;
		int yp = y >> 4;
		if (exitArea.getX_L() <= xp + 1 && exitArea.getX_R() >= xp + 1) {
			if (exitArea.getY_T() <= yp && exitArea.getY_B() >= yp) {
				initLevelChange();
			}
		}
	}
	
	private void initLevelChange() {
		if (switchDelay == 0) switchDelay = switchDelaylength;
	}
	
	private void transitionPlayer() {
		int playerOffsetX = levelFrom.getPlayer().getIntX() - (exitArea.getX_L() << 4);
		int playerOffsetY = levelFrom.getPlayer().getIntY() - (exitArea.getY_T() << 4);
		levelFrom.getPlayer().game.switchToLevel(levelTo);
		levelFrom.getPlayer().goTo(entryPoint.x + playerOffsetX, entryPoint.y + playerOffsetY);
		levelFrom.getPlayer().game.resetCameraOnPlayer();
	}
	
	public void render(Screen screen) {
		if (switchDelay > 0) {
			screen.renderTranslucentSprite(0, 0, new Sprite(500, 400, 0xff000000), false, 1 - (double) switchDelay / (double) transitionLength);
			switchDelay--;
			if (switchDelay == 0) transitionPlayer();
		}
	}
	
}
