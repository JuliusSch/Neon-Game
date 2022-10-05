package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.util.Util;

public class CheckDistanceToPlayer extends BehaviourNode {
	
	int minDist = -1, maxDist = -1;

	public CheckDistanceToPlayer(AIBlackboard bb, Mob mob, int minDist) {
		super(bb, mob);
		this.minDist = minDist;
	}
	
	public CheckDistanceToPlayer(AIBlackboard bb, Mob mob, int minDist, int maxDist) {
		super(bb, mob);
		this.minDist = minDist;
		this.maxDist = maxDist;
	}

	@Override
	public void update() {
		double dist = Util.pythag(blackboard.getPlayer().getIntX() - mob.getIntX(), blackboard.getPlayer().getIntY() - mob.getIntY());
		if (maxDist == -1) {
			if (dist >= minDist) currentState = NodeState.SUCCESS;
			else currentState = NodeState.FAILURE;
		} else {
			if (dist >= minDist && dist <= maxDist) currentState = NodeState.SUCCESS;
			else currentState = NodeState.FAILURE;
		}
	}
	
	protected void resetNode() {
		currentState = NodeState.READY;
		timer = 0;
		minDist = -1;
		maxDist = -1;
	}

}
