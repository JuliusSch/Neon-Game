package com.jcoadyschaebitz.neon.graphics;

public class AnimatedSprite extends Sprite {

	private Sprite sprite;
	private Sprite[] sprites;
	private int rate = 2;
	public int time = 0;
	private int frame = 0;
	private int animLength = -1;
	private int totalLength;
	private boolean playContinuous = true;
	public boolean active = true;

	public AnimatedSprite(Spritesheet sheet, int width, int height, int length, int rate) {
		this(sheet.getSprites(), width, height, length, rate);
	}
	
	public AnimatedSprite(Sprite[] sprts, int width, int height, int length, int rate) {
		super(Spritesheet.x16, width, height);
		this.rate = rate;
		animLength = length;
		totalLength = rate * length;
		sprite = sprts[0];
		sprites = sprts;
	}

	public void update() {
		if (active) {
			time++;
			if (playContinuous) {
				if (time > rate) {
					time = 0;
					if (frame >= animLength - 1) setFrame(0);
					else frame++;
					if (frame >= sprites.length) sprite = sprites[sprites.length - 1];
					else sprite = sprites[frame];
				}
			}
			if (!playContinuous) {
				if (time > rate) {
					time = 0;
					if (frame >= animLength - 1) {
						active = false;
						setFrame(0);
						return;
					}
					frame++;
					if (frame >= sprites.length) sprite = sprites[sprites.length - 1];
					else sprite = sprites[frame];
				}
			}
		}
	}

	public void playContinuous() {
		active = true;
		playContinuous = true;
		time = 0;
	}

	public void deactivate() {
		active = false;
		time = 0;
	}

	public void playOnce() {
		active = true;
		playContinuous = false;
		time = 0;
		setFrame(0);
		sprite = sprites[frame];
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setFrameRate(int rate) {
		this.rate = rate;
	}

	public void setFrame(int frame) {
		this.frame = frame;
		time = 0;
	}

	public int getFrame() {
		return frame;
	}
	
	public int getTotalLength() {
		return totalLength;
	}

}
