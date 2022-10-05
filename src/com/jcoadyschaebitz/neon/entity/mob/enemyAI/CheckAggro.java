package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Mob.MobState;

public class CheckAggro extends BehaviourNode {
	
	public CheckAggro(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}

	@Override
	public void update() {
		if (mob.aggro) {
			currentState = NodeState.FAILURE;
		}
		else {
			currentState = NodeState.SUCCESS;
			mob.setState(MobState.UNAWARE);
		}
	}

}
