package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;

public class WaitBehaviour extends BehaviourNode {
	
	private int period;

	public WaitBehaviour(AIBlackboard bb, Mob mob, int period) {
		super(bb, mob);
		this.period = period;
	}

	@Override
	public void update() {
		timer++;
		if (timer > period) currentState = NodeState.SUCCESS;
		else currentState = NodeState.RUNNING;
	}

}
