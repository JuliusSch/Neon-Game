package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.cutscene.Actor;
import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Orientation;

public class Mob_SwitchFacingEvent extends Event {
	
	private Actor actor;
	private Orientation dir;

	public Mob_SwitchFacingEvent(CutScene scene, int startTime, Actor actor, Orientation dir) {
		super(scene, startTime, startTime);
		this.actor = actor;
		this.dir = dir;
	}
	
	public void update() {
		actor.getMob().setOrientation(dir);
		endEvent();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}
	
	

}
