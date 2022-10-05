package com.jcoadyschaebitz.neon.entity.particle;

import java.util.Random;

import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class TextBubble extends Particle {

	String displayText;
	Font font;
	Random rand = new Random();
	boolean scrolling;
	private int scrollSpeed = 2;
	public int start;
	private Mob mob;

	public TextBubble(int start, int maxLife, String text, boolean scroll, Sprite speakerSprite) {
		super(80, CutScene.BLACK_BAR_HEIGHT + 10, maxLife, -1, -1, speakerSprite, 2);
		this.start = start;
		life = maxLife;
		displayText = text;
		scrolling = scroll;
		font = new Font(Font.SIZE_12x12, 0xff87FFFB, 1);
	}
	
	public TextBubble(int start, int maxLife, String text, boolean scroll, Sprite speakerSprite, Mob mob) {
		this(start, maxLife, text, scroll, speakerSprite);
		this.mob = mob;
	}

	public void update() {
		time++;
		if (time > life) remove();
	}

	public void render(Screen screen) {
		screen.renderTranslucentSprite(10, (int) yy, sprite, false);
		if (time > 20) {
			String s = displayText.substring(0, Math.min(displayText.length(), (time - 20) / scrollSpeed));
			if (scrolling) font.render((int) xx, (int) yy, s, screen, false); 
			else font.render((int) xx, (int) yy, displayText, screen, false);
			if (mob != null) screen.renderTranslucentSprite(mob.getIntX(), mob.getIntY() - 16, Sprite.SpeakingIcon, true);			
		}
	}

}
