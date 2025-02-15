package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class PursuePlayer extends BehaviourNode {

	private int maxTime;
	private double speed;
	
	public PursuePlayer(AIBlackboard bb, Mob mob, int maxTime) {
		super(bb, mob);
		this.maxTime = maxTime;
		speed = mob.speed;
	}
	
	public PursuePlayer(AIBlackboard bb, Mob mob, int maxTime, double speed) {
		this(bb, mob, maxTime);
		this.speed = speed;
	}

	@Override
	public void update() {
		timer++;
		if (currentState == NodeState.READY) currentState = NodeState.RUNNING;
		if (currentState == NodeState.RUNNING) {
			if (timer > maxTime) currentState = NodeState.FAILURE;
			else if (!blackboard.getLevel().isSightline(mob.getMidPos(), blackboard.getPlayer().getMidPos(), true)) currentState = NodeState.FAILURE;
			else {
				mob.walking = true;
				Vec2i playerP = blackboard.getPlayer().getMidPos();
				double direction = Math.atan2(playerP.y - mob.getMidY(), playerP.x - mob.getMidX());
				double xa = Math.cos(direction) * speed;
				double ya = Math.sin(direction) * speed;
				mob.move(xa, ya, true);
			}
			if (blackboard.getLevel().getDistanceToPlayer(mob.getMidX(), mob.getMidY()) < 24) currentState = NodeState.SUCCESS;
		}
	}

}
