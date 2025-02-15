package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;

public class DirectMoveTo extends BehaviourNode {

	private double xa, ya, direction, speed;
	private int targetX, targetY;
	private boolean collided, failsOnCollision;
	
	public DirectMoveTo(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}
	
	public DirectMoveTo(AIBlackboard bb, Mob mob, double speed, boolean failsOnCollision) {
		super(bb, mob);
		this.speed = speed;
		this.failsOnCollision = failsOnCollision;
	}
	
	@Override
	public void update() {
		timer++;
		if (currentState == NodeState.READY) {
			if (blackboard.getMoveToPos() == null) currentState = NodeState.FAILURE;
			else {
				currentState = NodeState.RUNNING;
				targetX = blackboard.getMoveToPos().x;
				targetY = blackboard.getMoveToPos().y;
			}
		}
		if (currentState == NodeState.RUNNING) {
			mob.walking = true;
			direction = Math.atan2(targetY - mob.getIntY(), targetX - mob.getIntX());
			xa = Math.cos(direction) * speed;
			ya = Math.sin(direction) * speed;
			collided = !mob.move(xa, ya, true);
		}
		if (collided) currentState = failsOnCollision ? NodeState.FAILURE : NodeState.SUCCESS;
		if ((Math.abs(mob.getIntX() - targetX) <= 4) && (Math.abs(mob.getIntY() - targetY) <= 4)) currentState = NodeState.SUCCESS;// consider failure state if wall encountered
		if (currentState == NodeState.SUCCESS) mob.walking = false;
		if (currentState == NodeState.FAILURE) mob.walking = false;
	}
}
