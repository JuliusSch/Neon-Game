package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.particle.DebugParticle;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class FindNewPosition extends BehaviourNode {

	private Vec2i newPosition;
	private List<Vec2i> validPositions = new ArrayList<Vec2i>();

	public FindNewPosition(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}

	@Override
	public void update() {
		timer++;
	}

	protected void resetNode() {
		currentState = NodeState.READY;
		timer = 0;
		newPosition = new Vec2i();
	}

	public void start() {
		currentState = NodeState.RUNNING;
		int px = blackboard.getPlayer().getMidX();
		int py = blackboard.getPlayer().getMidY();
		int mx = mob.getMidX();
		int my = mob.getMidY();
		int ax1 = px < mx ? (px >> 4) - 4 : (mx >> 4) - 4;
		int ax2 = mx > px ? (mx >> 4) + 4 : (px >> 4) + 4;
		int ay1 = py < my ? (py >> 4) - 4 : (my >> 4) - 4;
		int ay2 = my > py ? (my >> 4) + 4 : (py >> 4) + 4;
		for (int i = ax1; i < ax2; i++) {
			for (int j = ay1; j < ay2; j++) {
				if (!blackboard.getLevel().isSightline(mx, my, i << 4, j << 4)) continue;
				if (!blackboard.getLevel().isSightline(i << 4, j << 4, px, py)) continue;
				validPositions.add(new Vec2i(i << 4, j << 4));
				blackboard.getLevel().add(new DebugParticle(i << 4, j << 4, 50, 16, 16, Sprite.item_slot_outline));
			}
		}
		if (validPositions.size() > 0) {
			Random rand = new Random();
			newPosition = validPositions.get(rand.nextInt(validPositions.size()));
			blackboard.setMoveToPos(newPosition);
			currentState = NodeState.SUCCESS;
		} else currentState = NodeState.FAILURE;
		validPositions.clear();
	}
}
