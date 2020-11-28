package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;

public class HealthInRange extends BehaviourNode {

	private double lower, upper;
	
	public HealthInRange(AIBlackboard bb, Mob mob, double lowerB, double upperB) {
		super(bb, mob);
		lower = lowerB;
		upper = upperB;
	}

	@Override
	public void update() {
		double val = mob.getHealth() / mob.maxHealth;
		if (val >= lower && val <= upper) currentState = NodeState.SUCCESS;
		else currentState = NodeState.FAILURE;
	}

}
