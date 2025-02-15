package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;

public class Move extends BehaviourNode {

	private double xa, ya;
	private int duration;
	
	public Move(AIBlackboard bb, Mob mob, double direction, int duration, double speed) {
		super(bb, mob);
		this.duration = duration;
		xa = Math.cos(direction) * speed;
		ya = Math.sin(direction) * speed;
	}

	@Override
	public void update() {
		timer++;
		if (timer > duration) currentState = NodeState.SUCCESS;
		else currentState = NodeState.RUNNING;
		mob.move(xa, ya, false);
	}

}
