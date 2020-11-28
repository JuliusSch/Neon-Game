package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.cutscene.events.CameraMoveEvent.Transition;

public class SmoothCameraMoveEvent extends Event {

	private double x, y, xa, ya, targetX, targetY, linXa, linYa;
	private Transition type = Transition.NULL;
	
	public SmoothCameraMoveEvent(CutScene scene, int startTime, int duration, double targX, double targY) {
		this(scene, startTime, duration, targX, targY, Transition.NULL);
	}
	
	public SmoothCameraMoveEvent(CutScene scene, int startTime, int duration, double targX, double targY, Transition t) {
		super(scene, startTime, duration);
		targetX = targX;
		targetY = targY;
		type = t;
	}
	
	public void init() {
		switch (type) {
		case IN:
			x = scene.getXScroll();
			y = scene.getYScroll();
			break;
		case OUT:
			targetX = scene.getXScroll();
			targetY = scene.getYScroll();
			x = scene.currentScreenX;
			y = scene.currentScreenY;
			break;
		default:
			x = scene.currentScreenX;
			y = scene.currentScreenY;
			break;
		}
		linXa = (targetX - x) / duration;
		linYa = (targetY - y) / duration;
		xa = linXa;
		ya = linYa;
	}
	
	public void update() {
		if (time > duration) {
			endEvent();
			return;
		}
		xa = 4 * (targetX - scene.currentScreenX) / duration;
		ya = 4 * (targetY - scene.currentScreenY) / duration;
		time++;
		scene.currentScreenX += xa;
		scene.currentScreenY += ya;
	}

}
