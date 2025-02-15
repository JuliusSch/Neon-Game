package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class VehicleAccelerate extends BehaviourNode {

	private int duration, smoothness;
	private double direction, maxSpeed;
	private Vec2i start, target;
	
	public VehicleAccelerate(AIBlackboard bb, Mob mob, int duration, double maxSpeed, int smoothness, Vec2i startPos, Vec2i newPos) {
		super(bb, mob);
		this.duration = duration;
		this.maxSpeed = maxSpeed;
		this.smoothness = smoothness;
		start = startPos;
		target = newPos;
		direction = Math.atan2(target.y - start.y, target.x - start.x);
	}

	@Override
	public void update() {
		timer++;
		currentState = NodeState.RUNNING;
		if (timer > duration) {
			currentState = NodeState.SUCCESS;
			return;
		}
		double xComp = (double) timer / duration;
		double newMagnitude = 1 / (1 + Math.pow((xComp / (1 - xComp)), -smoothness)); //	Simple sigmoid function with domain [0,1] and range [0,1]
		mob.move(Math.cos(direction) * newMagnitude * maxSpeed, Math.sin(direction) * newMagnitude * maxSpeed, true);
	}

}
