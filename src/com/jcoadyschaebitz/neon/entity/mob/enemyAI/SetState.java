package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Mob.MobState;

public class SetState extends BehaviourNode {

	private MobState state;
	
	public SetState(AIBlackboard bb, Mob mob, MobState state) {
		super(bb, mob);
		this.state = state;
	}

	@Override
	public void update() {
	}

	public void start() {
		if (currentState == NodeState.READY) {
			mob.setState(state);
			currentState = NodeState.SUCCESS;
		}
	}
	
}
