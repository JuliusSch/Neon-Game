package com.jcoadyschaebitz.neon.cutscene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.cutscene.events.Event;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.particle.TextBubble;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.input.Keyboard;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.util.Vec2i;

public abstract class CutScene {

	public double currentScreenX, currentScreenY;
	public static int BLACK_BAR_HEIGHT = 15;
	protected List<Event> events = new ArrayList<Event>();
	protected int time, skipCounter, duration;
	protected boolean playing, played;
	protected Level level;
	protected int trigX1, trigY1, trigX2, trigY2; 
	protected Actor[] actors;
	protected Keyboard input;
	protected String[] script;
	protected LinkedList<TextBubble> bubbles = new LinkedList<TextBubble>();
	protected TextBubble currentTextBubble;
	
	public CutScene(Vec2i screenLockCentre, Vec2i triggerTopLeft, int triggerW, int triggerH, Level level, Mob[] members, Keyboard input) {
		this.currentScreenX = screenLockCentre.x << 4;
		this.currentScreenY = screenLockCentre.y << 4;
		trigX1 = triggerTopLeft.x << 4;
		trigY1 = triggerTopLeft.y << 4;
		trigX2 = trigX1 + triggerW;
		trigY2 = trigY1 + triggerH;
		this.level = level;
		this.input = input;
		actors = new Actor[members.length + 1];
		for (int i = 0; i < members.length; i++) {
			actors[i + 1] = new Actor(members[i]);
		}
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getX() {
		return (int) currentScreenX;
	}

	public int getY() {
		return (int) currentScreenY;
	}
	
	public Level getLevel() {
		return level;
	}
	
	protected abstract void initEvents();
	
	protected void addEvent(Event e) {
		events.add(e);
	}
	
	public void addTextBubble(TextBubble bubble) {
		bubbles.offer(bubble);
	}
	
	public void triggerScene() {
		time = 0;
		playing = true;
		initEvents();
		level.getPlayer().game.toggleCutScene(this);
	}
	
	public double getXOffset() {
		return Game.getUIManager().getGame().getScreen().xOffset;
	}
	
	public double getYOffset() {
		return Game.getUIManager().getGame().getScreen().yOffset;
	}
	
	public void exitScene() {
		playing = false;
		played = true;
		level.getPlayer().game.toggleCutScene(this);
		for (Event e : events) {
			e.endEvent();
		}
	}
	
	public void switchTextBubbles() {
		currentTextBubble = bubbles.poll();
	}
	
	public void setScreenLock(double screenLockX2, double screenLockY2) {
		currentScreenX = screenLockX2;
		currentScreenY = screenLockY2;
	}
	
	public void update(int playerX, int playerY) {
		if (playerX > trigX1 && playerX < trigX2) {
			if (playerY > trigY1 && playerY < trigY2) {
				if (!playing && !played) {
					triggerScene();
				}
			}
		}
		if (currentTextBubble != null && currentTextBubble.start <= time) {
			currentTextBubble.update();
		}
		if (!playing) return;
		for (Event e : events) {
			if (time == e.getStartTime()) e.init();
			if (time >= e.getStartTime() && !e.timeElapsed) e.update();
		}
		for (Actor a : actors) a.update();
		if (!input.F) skipCounter = 0;
		if (input.F) skipCounter++;
		if (skipCounter > 60) exitScene();
		time++;
	}
	
	public static String[] loadTextFileArray(String fileName) {
		List<String> result = new ArrayList<String>();
		String[] convertResult = new String[1];
		try {
			File file = new File(CutScene.class.getResource(fileName).getFile());
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) result.add(line);
			fr.close();
			convertResult = new String[result.size()];
			for (int i = 0; i < convertResult.length; i++) {
				convertResult[i] = result.get(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return convertResult;
	}
	
	public void render(Screen screen) {
		if (playing) {
			if (time < BLACK_BAR_HEIGHT) {
				screen.drawRect(true, 0, 0, screen.width, (int) (time * 1), 0xff000000, false);
				screen.drawRect(true, 0, screen.height - (int) (time * 1), screen.width, (int) (time * 1.5), 0xff000000, false);
			} else if (time > duration - BLACK_BAR_HEIGHT) {
				screen.drawRect(true, 0, 0, screen.width, (int) (duration - time * 1), 0xff000000, false);
				screen.drawRect(true, 0, screen.height - (int) (duration - time * 1), screen.width, (int) (duration - time * 1.5), 0xff000000, false);
			} else {
				screen.drawRect(true, 0, 0, screen.width, BLACK_BAR_HEIGHT, 0xff000000, false);
				screen.drawRect(true, 0, screen.height - BLACK_BAR_HEIGHT, screen.width, BLACK_BAR_HEIGHT, 0xff000000, false);				
			}
			screen.renderSprite(320, 240, Sprite.assaultRifleIcon, false);
			if (skipCounter > 0) screen.drawCircleSegment(true, new Vec2i(320,  240), 8, 0, skipCounter * 2 * Math.PI / 60, 0xffcffcfc, false, 0.8);
//			if (skipCounter > 0) screen.renderTranslucentSprite(320, 240, Sprite.assaultRifleSlotSprite, false, (double) skipCounter / 60);
		}
	}
}
