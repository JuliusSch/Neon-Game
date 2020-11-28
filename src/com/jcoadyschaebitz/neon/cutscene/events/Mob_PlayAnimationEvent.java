package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.cutscene.Actor;
import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.entity.mob.Mob.MobState;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;

public class Mob_PlayAnimationEvent extends Event {

	private Actor actor;
	private AnimatedSprite anim;
	
	public Mob_PlayAnimationEvent(CutScene scene, int startTime, int duration, AnimatedSprite anim, Actor act) {
		super(scene, startTime, duration);
		this.anim = anim;
	}
	
	public void update() {
		actor.getMob().setAnimation(anim);
		if (time < duration) {
			endEvent();
			actor.getMob().setState(MobState.IDLE);
		}
		time++;
	}

	@Override
	public void init() {
		actor.getMob().setState(MobState.ANIMATING);
	}
	
}
