package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.cutscene.CutScene;

public class WaitEvent extends Event {

	public WaitEvent(CutScene scene, int startTime, int duration) {
		super(scene, startTime, duration);
	}

	@Override
	public void update() {
		if (time > duration) {
			endEvent();
			return;
		}
		time++;
		
	}

	@Override
	public void init() {
	}

}
