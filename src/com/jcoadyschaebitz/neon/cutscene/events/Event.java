package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.cutscene.CutScene;

public abstract class Event {
	
	protected CutScene scene;
	protected int time, duration, startTime;
	public boolean timeElapsed = false;
	
	public Event(CutScene scene, int startTime, int duration) {
		this.scene = scene;
		this.startTime = startTime;
		this.duration = duration;
	}
	
	public abstract void update();
	
	public void endEvent() {
		timeElapsed = true;
	}
	
	public int getStartTime() {
		return startTime;
	}
	
	public abstract void init();
}
