package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;

public class Barman extends Mob {
	
	public Barman(int x, int y) {
		super(x, y);
	}

	@Override							// dialogue interface
	public void update() {
		
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
	}

}
