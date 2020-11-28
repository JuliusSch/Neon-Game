package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;

public class FindSightline extends BehaviourNode {
	
	private boolean sightlineFound;
	
	public FindSightline(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}

	@Override
	public void update() {
		timer++;
		if (sightlineFound) {
			currentState = NodeState.SUCCESS;
			blackboard.updateLastPlayerPos();
		}
		else if (timer < 10) {
			currentState = NodeState.RUNNING;
			checkForSightline();
		}
		else currentState = NodeState.FAILURE;
	}
	
	private void checkForSightline() {
		int xx = mob.getMidX();
		int yy = mob.getMidY();
		sightlineFound = blackboard.getLevel().isSightline(xx, yy, blackboard.getPlayerDirection(xx, yy), blackboard.getPlayer());
	}
	
	protected void resetNode() {
		currentState = NodeState.READY;
		timer = 0;
		sightlineFound = false;
	}
}
