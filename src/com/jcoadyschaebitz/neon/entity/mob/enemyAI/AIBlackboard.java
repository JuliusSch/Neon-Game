package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class AIBlackboard {

	public boolean beenInitted = false;
	private Level level;
	private Vec2i lastPlayerPos;
	private Vec2i moveToPosition;

	public void init(Level level) {
		this.level = level;
		beenInitted = true;
	}

	public void updateLastPlayerPos() {
		lastPlayerPos = new Vec2i(getPlayer().getIntX(), getPlayer().getIntY());
	}

	public Vec2i getLastPlayerPos() {
		return lastPlayerPos;
	}

	public void setMoveToPos(Vec2i pos) {
		moveToPosition = pos;
	}

	public Vec2i getMoveToPos() {
		return moveToPosition;
	}

	public Player getPlayer() {
		return level.getPlayer();
	}

	public Level getLevel() {
		return level;
	}

	public double getPlayerDirection(Mob mob) {
		return Math.atan2((getPlayer().getMidY() - mob.getMidY()), (getPlayer().getMidX() - mob.getMidX()));
	}

	public double getPlayerDirection(int x, int y) {
		return Math.atan2((getPlayer().getMidY() - y), (getPlayer().getMidX() - x));
	}
}
