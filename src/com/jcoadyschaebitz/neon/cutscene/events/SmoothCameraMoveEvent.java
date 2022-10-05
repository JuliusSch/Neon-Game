package com.jcoadyschaebitz.neon.cutscene.events;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.cutscene.events.CameraMoveEvent.Transition;
import com.jcoadyschaebitz.neon.input.Mouse;
import com.jcoadyschaebitz.neon.util.Vec2d;

public class SmoothCameraMoveEvent extends Event {

	private double startX, startY, targetX, targetY, distance, direction;
	private int mouseX, mouseY;
	private Transition type = Transition.NULL;
	
	public SmoothCameraMoveEvent(CutScene scene, int startTime, int duration) {
		this(scene, startTime, duration, Transition.NULL);
	}
	
	public SmoothCameraMoveEvent(CutScene scene, int startTime, int duration, Transition t) {
		super(scene, startTime, duration);
		type = t;
	}
	
	public void init() {
		switch (type) {
		case IN:
			startX = scene.getXOffset();
			startY = scene.getYOffset();
			targetX = scene.currentScreenX;
			targetY = scene.currentScreenY;
			mouseX = Mouse.getX();
			mouseY = Mouse.getY();
			break;
		case OUT:
			targetX = Game.getUIManager().getGame().getCameraPos().x;
			targetY = Game.getUIManager().getGame().getCameraPos().y;
			startX = scene.currentScreenX;
			startY = scene.currentScreenY;
			mouseX = Mouse.getX();
			mouseY = Mouse.getY();
			break;
		default:
			break;
		}
		distance = Vec2d.getDistance(new Vec2d(startX, startY), new Vec2d(targetX, targetY));
		direction = Math.atan2(targetY - startY, targetX - startX);
	}
	
	public void update() {
		if (time == duration) {
			Mouse.move(mouseX, mouseY);
			Game.getUIManager().getGame().resetCameraOnPlayer();
			endEvent();
			return;
		}
		double xComp = (double) time / duration;
		double curveSeverity = -4;
		double newMagnitude = 1 / (1 + Math.pow((xComp / (1 - xComp)), curveSeverity)); //	Simple sigmoid function with domain [0,1] and range [0,1]
		scene.currentScreenX = startX + Math.cos(direction) * newMagnitude * distance;
		scene.currentScreenY = startY + Math.sin(direction) * newMagnitude * distance;
		time++;
	}

}
