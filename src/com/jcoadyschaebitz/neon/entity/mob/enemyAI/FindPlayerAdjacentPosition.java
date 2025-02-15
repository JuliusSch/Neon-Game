package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.util.Vec2d;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class FindPlayerAdjacentPosition extends BehaviourNode {

	public FindPlayerAdjacentPosition(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}

	@Override
	public void update() {
	}
	
	public void start() {
		if (currentState == NodeState.READY) {
			Vec2i posRelToPlayer = mob.getPos().subtract(blackboard.getPlayer().getPos());
			Vec2d scaledPos = new Vec2d(posRelToPlayer).normalise().scale(16);
			blackboard.setMoveToPos(new Vec2i(scaledPos).add(blackboard.getPlayer().getPos()));
			currentState = NodeState.SUCCESS;
		}
	}

}
