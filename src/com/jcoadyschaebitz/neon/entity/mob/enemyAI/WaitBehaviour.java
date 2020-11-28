package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Mob.MobState;

public class WaitBehaviour extends BehaviourNode {
	
	private int period;

	public WaitBehaviour(AIBlackboard bb, Mob mob, int period) {
		super(bb, mob);
		this.period = period;
	}

	@Override
	public void update() {
		timer++;
		mob.setState(MobState.IDLE);
		if (timer > period) currentState = NodeState.SUCCESS;
		else currentState = NodeState.RUNNING;
	}

}
