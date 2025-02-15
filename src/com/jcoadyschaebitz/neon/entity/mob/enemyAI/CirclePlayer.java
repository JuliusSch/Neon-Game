package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.weapon.Weapon.WeaponState;
import com.jcoadyschaebitz.neon.util.Vec2d;

public class CirclePlayer extends BehaviourNode {
	
	private double speed, circleDistance;
	private int maxDuration;
	private RotationDirection rotDirection;
	
	public enum RotationDirection {
		CLOCKWISE, ANTICLOCKWISE
	}

	public CirclePlayer(AIBlackboard bb, Mob mob, double speed, int maxDuration, RotationDirection dir, double circleDistance) {
		super(bb, mob);
		this.speed = speed;
		this.maxDuration = maxDuration;
		this.circleDistance = circleDistance;
		rotDirection = dir;
	}

	@Override
	public void update() {
		timer++;
		if (currentState == NodeState.READY) {
			currentState = NodeState.RUNNING;
			mob.weapon.setState(WeaponState.BLOCKING);
		}
		if (currentState == NodeState.RUNNING) {
			Vec2d targetPos = (new Vec2d(blackboard.getPlayer().getPos().subtract(mob.getPos()))).normalise().scale(circleDistance);

			double angle = rotDirection == RotationDirection.CLOCKWISE ? 0.02 : -0.02;
			Vec2d rotatedTargetPos = new Vec2d(Math.cos(angle) * targetPos.x - Math.sin(angle) * targetPos.y, Math.sin(angle) * targetPos.x + Math.cos(angle) * targetPos.y);

			targetPos = new Vec2d(blackboard.getPlayer().getPos()).subtract(rotatedTargetPos);
//			blackboard.getLevel().add(new DebugParticle(targetPos.x, targetPos.y, 1, 2, 2, Sprite.particleBlue));
			Vec2d move = targetPos.subtract(new Vec2d(mob.getPos())).normalise().scale(speed);
			if (!mob.move(move.x, move.y, true)) rotDirection = rotDirection == RotationDirection.CLOCKWISE ? RotationDirection.ANTICLOCKWISE : RotationDirection.CLOCKWISE;

			double distanceP = blackboard.getLevel().getDistanceToPlayer(mob.getMidX(), mob.getMidY());
			if (distanceP > circleDistance * 1.5 || distanceP < circleDistance * 0.5) {
				currentState = NodeState.FAILURE;
				mob.weapon.setState(WeaponState.IDLE);
			}
			if (timer > maxDuration) {
				currentState = NodeState.SUCCESS;
				mob.weapon.setState(WeaponState.IDLE);
			}
		}
	}

}
