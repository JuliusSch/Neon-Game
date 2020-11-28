package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.cutscene.CutScene;

public class EndSceneEvent extends Event {

	public EndSceneEvent(CutScene scene, int startTime, int duration) {
		super(scene, startTime, duration);
	}
	
	public void update() {
		scene.exitScene();
		endEvent();
	}

	@Override
	public void init() {
	}

}
