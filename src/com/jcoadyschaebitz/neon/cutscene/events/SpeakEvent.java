package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.cutscene.Actor;
import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.entity.particle.TextBubble;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class SpeakEvent extends Event {
	
	private static final int DEFAULT_DURATION = 180;
	private TextBubble textBub;

	public SpeakEvent(CutScene scene, int startTime, int duration, Actor actor, String text) {
		super(scene, startTime, duration);
		textBub = new TextBubble(startTime, duration, text, true, Sprite.oldManEye, actor.getMob());
		scene.addTextBubble(textBub);
	}
	
	public SpeakEvent(CutScene scene, int startTime, int duration, String text, Sprite speakerSprite) {
		super(scene, startTime, duration);
		textBub = new TextBubble(startTime, duration, text, true, speakerSprite);
		scene.addTextBubble(textBub);
	}
	
	public SpeakEvent(CutScene scene, int startTime, Actor actor, String text) {
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
