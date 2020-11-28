package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.cutscene.Actor;
import com.jcoadyschaebitz.neon.cutscene.CutScene;

public class Mob_RenderGun extends Event {
	
	private double direction;
	private Actor actor;

	public Mob_RenderGun(CutScene scene, int startTime, int duration, Actor actor, double direction) {
		super(scene, startTime, duration);
		this.actor = actor;
		this.direction = direction;
	}

	@Override
	public void update() {
		actor.getMob().weapon.updateSprite(direction);
	}

	@Override
	public void init() {
		
	}

}
