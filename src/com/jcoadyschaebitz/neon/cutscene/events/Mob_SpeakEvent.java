package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.cutscene.Actor;
import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.entity.particle.TextBubble;

public class Mob_SpeakEvent extends Event {
	
	private static final int DEFAULT_DURATION = 180;
	private TextBubble textBub;

	public Mob_SpeakEvent(CutScene scene, int startTime, int duration, Actor actor, String text) {
		super(scene, startTime, duration);
		textBub = new TextBubble(startTime, duration, text, true, actor.getMob());
		scene.addTextBubble(textBub);
	}
	
	public Mob_SpeakEvent(CutScene scene, int startTime, Actor actor, String text) {
		this(scene, startTime, DEFAULT_DURATION, actor, text);
	}

	@Override
	public void update() {
		if (time > duration) {
			endEvent();
			scene.switchTextBubbles();
			return;
		}
		time++;
	}
	
	@Override
	public void endEvent() {
		super.endEvent();
		textBub.remove();
	}

	@Override
	public void init() {
		scene.getLevel().add(textBub);
	}

}
