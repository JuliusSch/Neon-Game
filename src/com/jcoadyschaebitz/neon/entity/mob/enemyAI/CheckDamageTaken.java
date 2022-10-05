package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Mob.MobState;

public class CheckDamageTaken extends BehaviourNode {

	public CheckDamageTaken(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}

	@Override
	public void update() {
		if (mob.damageDelay > 0) {
			currentState = NodeState.RUNNING;
		}
		else {
			currentState = NodeState.FAILURE;
			mob.setState(MobState.IDLE);
		}
	}

}
