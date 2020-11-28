package com.jcoadyschaebitz.neon.cutscene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.jcoadyschaebitz.neon.cutscene.events.Event;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.particle.TextBubble;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.input.Keyboard;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.level.TileCoordinate;
import com.jcoadyschaebitz.neon.util.Vec2i;

public abstract class CutScene {

	public double currentScreenX, currentScreenY;
	protected List<Event> events = new ArrayList<Event>();
	protected int time, skipCounter;
	protected boolean playing, played;
	protected Level level;
	protected int trigX1, trigY1, trigX2, trigY2; 
	protected double xScroll, yScroll;
	protected Actor[] actors;
	protected Keyboard input;
	protected String[] script;
	protected LinkedList<TextBubble> bubbles = new LinkedList<TextBubble>();
	protected TextBubble currentTextBubble;
	
	public CutScene(TileCoordinate screenLockCentre, TileCoordinate triggerTopLeft, int triggerW, int triggerH, Level level, Mob[] members, Keyboard input) {
		this.currentScreenX = screenLockCentre.getX();
		this.currentScreenY = screenLockCentre.getY();
		trigX1 = triggerTopLeft.getX();
		trigY1 = triggerTopLeft.getY();
		trigX2 = trigX1 + triggerW;
		trigY2 = trigY1 + triggerH;
		this.level = level;
		this.input = input;
		actors = new Actor[members.length + 1];
		for (int i = 0; i < members.length; i++) {
			actors[i + 1] = new Actor(members[i]);
		}
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
	
	public double getXScroll() {
		return xScroll;
	}
	
	public double getYScroll() {
		return yScroll;
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
	
	public void setScrollValues(double xScroll, double yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
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
			if (time < 30) {
				screen.drawRect(true, 100, 0, screen.width - 200, (int) (time * 1), 0xff000000, false);
				screen.drawRect(true, 0, screen.height - (int) (time * 1), screen.width, (int) (time * 1.5), 0xff000000, false);
			} else {
				screen.drawRect(true, 0, 0, screen.width, 30, 0xff000000, true, 5);
				screen.drawRect(true, 0, screen.height - 30, screen.width, 30, 0xff000000, false);				
			}
			screen.renderSprite(320, 240, Sprite.assaultRifleIcon, false);
			if (skipCounter > 0) screen.drawCircleSegment(true, new Vec2i(320,  240), 8, 0, skipCounter * 2 * Math.PI / 60, 0xffcffcfc, false, 0.8);
//			if (skipCounter > 0) screen.renderTranslucentSprite(320, 240, Sprite.assaultRifleSlotSprite, false, (double) skipCounter / 60);
		}
	}
}
