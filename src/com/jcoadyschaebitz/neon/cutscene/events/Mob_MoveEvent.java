package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.cutscene.Actor;
import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.entity.mob.Mob.MobState;

public class Mob_MoveEvent extends Event {
	
	private double xa, ya;
	private Actor actor1;

	public Mob_MoveEvent(CutScene scene, int startTime, int duration, Actor actor, double xa, double ya) {
		super(scene, startTime, duration);
		this.xa = xa;
		this.ya = ya;
		actor1 = actor;
	}

	public void update() {
		if (time > duration) {
			endEvent();
			actor1.getMob().setState(MobState.IDLE);
			return;
		}
		time++;
		actor1.move(xa, ya);
	}

	@Override
	public void init() {
		actor1.getMob().setState(MobState.WALKING);
	}

}
