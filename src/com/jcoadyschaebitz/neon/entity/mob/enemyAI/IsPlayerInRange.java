package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;

public class IsPlayerInRange extends BehaviourNode {

	double min, max;
	
	public IsPlayerInRange(double min, double max, AIBlackboard bb, Mob mob) {
		super(bb, mob);
		this.min = min;
		this.max = max;
	}

	@Override
	public void update() {
		double distance = blackboard.getLevel().getDistanceToPlayer(mob.getMidX(), mob.getMidY());
		if (distance >= min && distance < max) currentState = NodeState.SUCCESS;
		else currentState = NodeState.FAILURE;
	}

}
