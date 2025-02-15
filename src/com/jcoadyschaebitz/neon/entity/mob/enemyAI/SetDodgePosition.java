package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import java.util.Random;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.util.Vec2d;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class SetDodgePosition extends BehaviourNode {
	
	private Random random;
	private boolean forwardBackward;

	public SetDodgePosition(AIBlackboard bb, Mob mob, boolean forwardBackward) {
		super(bb, mob);
		random = new Random();
		this.forwardBackward = forwardBackward;
	}

	@Override
	public void update() {
		if (currentState == NodeState.READY) {
			currentState = NodeState.RUNNING;
			if (blackboard.beenTargeted() && random.nextInt(3) == 0) {
				blackboard.setTargeted(false);
				Vec2d moveVector = new Vec2d(blackboard.getPlayer().getPos().subtract(mob.getPos())).normalise().scale(80);
				double angleOfDodge = (random.nextDouble() / 6.0 + (1.0/6.0)) * Math.PI;
				angleOfDodge = random.nextInt(1) == 0 ? angleOfDodge : -angleOfDodge;
				Vec2d angledMoveVec;
				if (forwardBackward) {
					angledMoveVec = new Vec2d(Math.cos(angleOfDodge) * moveVector.x - Math.sin(angleOfDodge) * moveVector.y, Math.sin(angleOfDodge) * moveVector.x + Math.cos(angleOfDodge) * moveVector.y);
				} else {
					moveVector = new Vec2d(-moveVector.x, -moveVector.y);
					angledMoveVec = new Vec2d(Math.cos(angleOfDodge) * moveVector.x - Math.sin(angleOfDodge) * moveVector.y, Math.sin(angleOfDodge) * moveVector.x + Math.cos(angleOfDodge) * moveVector.y);
				}
				blackboard.setMoveToPos(mob.getPos().add(new Vec2i(angledMoveVec)));
				currentState = NodeState.SUCCESS;
			}
			else currentState = NodeState.FAILURE;
		}
	}

}
