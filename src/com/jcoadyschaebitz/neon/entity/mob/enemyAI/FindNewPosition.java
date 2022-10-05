package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import java.util.TreeMap;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.particle.DebugParticle;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.util.Util;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class FindNewPosition extends BehaviourNode {

	private TreeMap<Integer, Vec2i> validPositions = new TreeMap<Integer, Vec2i>();
	private int optimalRange;

	public FindNewPosition(AIBlackboard bb, Mob mob, int optimalRange) {
		super(bb, mob);
		this.optimalRange = optimalRange;
	}

	@Override
	public void update() {
	}

	public void start() {
		currentState = NodeState.RUNNING;
//		int px = blackboard.getLastPlayerPos().x;
//		int py = blackboard.getLastPlayerPos().y;
		int px = blackboard.getPlayer().getMidX();
		int py = blackboard.getPlayer().getMidY();
		int mx = mob.getMidX();
		int my = mob.getMidY();
		int cost;
		int ax1 = px < mx ? (px - optimalRange * 2) >> 4 : (mx - optimalRange * 2) >> 4;
		int ax2 = mx > px ? (mx + optimalRange * 2) >> 4 : (px + optimalRange * 2) >> 4;
		int ay1 = py < my ? (py - optimalRange * 2) >> 4 : (my - optimalRange * 2) >> 4;
		int ay2 = my > py ? (my + optimalRange * 2) >> 4 : (py + optimalRange * 2) >> 4;
		for (int i = ax1; i < ax2; i++) {
			for (int j = ay1; j < ay2; j++) {
				if (!blackboard.getLevel().isSightline(new Vec2i(mx, my), new Vec2i((i << 4) + 8, (j << 4) + 8), true)) continue;
				if (!blackboard.getLevel().isSightline(new Vec2i((i << 4) + 8, (j << 4) + 8), new Vec2i(px, py), true)) continue; // this isn't working correctly
//				blackboard.getLevel().add(new DebugParticle(i << 4, j << 4, 50, Sprite.item_slot_outline));
//				blackboard.getLevel().add(new DebugParticle(px, py, 50, Sprite.particleCrimson));
				cost = (int) (Util.pythag(((i << 4) + 8) - mx, ((j << 4) + 8) - my) + Util.pythag(((i << 4) + 8) - px, ((j << 4) + 8) - py));
				validPositions.put(cost, new Vec2i(i << 4, j << 4));
			}
		}
		if (validPositions.size() > 0) {
			blackboard.setMoveToPos(validPositions.firstEntry().getValue());
			blackboard.getLevel().add(new DebugParticle(validPositions.firstEntry().getValue().x, validPositions.firstEntry().getValue().y, 50, Sprite.item_slot_outline));
			currentState = NodeState.SUCCESS;
		} else currentState = NodeState.FAILURE;
		validPositions.clear();
	}
}
