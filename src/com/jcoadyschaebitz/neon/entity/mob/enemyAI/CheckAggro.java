package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;

public class CheckAggro extends BehaviourNode {
	
	public CheckAggro(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}

	@Override
	public void update() {
		if (mob.aggro) currentState = NodeState.FAILURE;
		else currentState = NodeState.SUCCESS;
	}

}
