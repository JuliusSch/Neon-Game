package com.jcoadyschaebitz.neon.graphics;

public class AnimatedSprite extends Sprite {

	private Sprite sprite;
	private int rate = 2;
	public int time = 0;
	private int frame = 0;
	private int animLength = -1;
	private int totalLength;
	private boolean playContinuous = true;
	public boolean active = true;

	public AnimatedSprite(Spritesheet sheet, int width, int height, int length, int rate) {
		super(sheet, width, height);
		this.rate = rate;
		animLength = length;
		totalLength = rate * length;
		sprite = sheet.getSprites()[0];
	}

	public void update() {
		if (active) {
			time++;
			if (playContinuous) {
				if (time > rate) {
					time = 0;
					if (frame >= animLength - 1) setFrame(0);
					else frame++;
					if (frame >= sheet.getSprites().length) sprite = sheet.getSprites()[sheet.getSprites().length - 1];
					else sprite = sheet.getSprites()[frame];
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
					if (frame >= sheet.getSprites().length) sprite = sheet.getSprites()[sheet.getSprites().length - 1];
					else sprite = sheet.getSprites()[frame];
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
		sprite = sheet.getSprites()[frame];
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
