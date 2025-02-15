package com.jcoadyschaebitz.neon.level;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.util.Rect;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class LevelTransition {

	public Vec2i entryPoint;
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
		int xp = (x >> 4) + 1;
		int yp = y >> 4;
		if (exitArea.getX_L() <= xp && exitArea.getX_R() >= xp) {
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
		Vec2i newPlayerPos = new Vec2i(entryPoint.x + playerOffsetX, entryPoint.y + playerOffsetY);
		levelFrom.getPlayer().game.switchToLevel(levelTo, newPlayerPos);
		levelFrom.getPlayer().game.resetCameraOnPlayer();
		Game.getUIManager().loadGameSubMenu.saveToSelectedSlot();
	}
	
	public void render(Screen screen) {
		if (switchDelay > 0) {
			screen.renderTranslucentSprite(0, 0, new Sprite(500, 500, 0xff000000), false, 1 - (double) switchDelay / (double) transitionLength);
			switchDelay--;
			if (switchDelay == 0) transitionPlayer();
		}
	}
	
}
