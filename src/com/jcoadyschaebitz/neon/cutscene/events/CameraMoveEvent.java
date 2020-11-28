package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.cutscene.CutScene;

public class CameraMoveEvent extends Event {
	
	private double xa, ya, targetX, targetY;
	private Transition type = Transition.NULL;
	public enum Transition {
		IN, OUT, NULL
	};

	public CameraMoveEvent(CutScene scene, int startTime, int duration, double targX, double targY) {
		super(scene, startTime, duration);
		xa = (targX - scene.currentScreenX) / duration;
		ya = (targY - scene.currentScreenY) / duration;
	}
	
	public CameraMoveEvent(CutScene scene, int startTime, int duration, double targX, double targY, Transition t) {
		super(scene, startTime, duration);
		type = t;
		targetX = targX;
		targetY = targY;
		switch(t) {
		case IN:
			xa = (targX - scene.getXScroll()) / duration;
			ya = (targY - scene.getYScroll()) / duration;
			break;
		case OUT:
			xa = (scene.getXScroll() - targX) / duration;
			ya = (scene.getYScroll() - targY) / duration;
			break;
		default:
			xa = 0;
			ya = 0;
			break;
		}
	}

	public void update() {
		if (time > duration) {
			endEvent();
			return;
		}
		time++;
		switch(type) {
		case IN:
			xa = (targetX - scene.getXScroll()) / duration;
			ya = (targetY - scene.getYScroll()) / duration;
			break;
		case OUT:
			xa = (scene.getXScroll() - targetX) / duration;
			ya = (scene.getYScroll() - targetY) / duration;
			break;
		default:
			break;
		}
		scene.currentScreenX += xa;
		scene.currentScreenY += ya;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
