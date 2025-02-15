package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.util.Vec2d;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class SetRetreatPosition extends BehaviourNode {

	private int distance;
	
	public SetRetreatPosition(AIBlackboard bb, Mob mob, int distance) {
		super(bb, mob);
		this.distance = distance;
	}

	@Override
	public void update() {
	}
	
	public void start() {
		Vec2i posRelToPlayer = mob.getPos().subtract(blackboard.getPlayer().getPos());
		Vec2d scaledPos = new Vec2d(posRelToPlayer).normalise().scale(distance);
		blackboard.setMoveToPos(new Vec2i(scaledPos).add(blackboard.getPlayer().getPos()));
		currentState = NodeState.SUCCESS;
	}

}
