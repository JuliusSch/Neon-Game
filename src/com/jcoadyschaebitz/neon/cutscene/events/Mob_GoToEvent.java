package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.cutscene.Actor;
import com.jcoadyschaebitz.neon.cutscene.CutScene;

public class Mob_GoToEvent extends Event {
	
	private int x, y;
	private Actor actor;
	
	public Mob_GoToEvent(CutScene scene, int startTime, int duration, Actor actor, int x, int y) {
		super(scene, startTime, duration);
		this.x = x;
		this.y = y;
		this.actor = actor;
	}

	@Override
	public void update() {
	}

	@Override
	public void init() {
		actor.getMob().goTo(x, y);
		endEvent();
	}

}
