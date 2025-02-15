package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class HealthBar implements UIComp {

	private double maxValue, value, percentageFilled; // 0 - 120
	private int backgroundColour, foregroundColour, x, y, width, height;

	public HealthBar(int x, int y, int width, int height, int bColour, int fColour, UIManager ui) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		backgroundColour = bColour;
		foregroundColour = fColour;
	}

	public void setPercentageFilled(double decimal) {
		if (decimal >= 0 && decimal <= 1) this.percentageFilled = decimal;
		else System.err.println("progressBar out of bounds");
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void setMaxValue(double mValue) {
		this.maxValue = mValue;
	}

	public void update() {
		if (value != 0 && value <= maxValue) {
			percentageFilled = value / maxValue;
		} else percentageFilled = 0;
	}

	public void render(Screen screen) {
		screen.drawRect(true, x - 1, y - 1, width + 2, height + 2, backgroundColour, false);
		screen.renderSprite(x + width + 1, y - 1, Sprite.healthBarEnd, false);
		screen.drawRect(true, x, y, width, height, 0xff820031, false);
		screen.renderSprite(x + width, y, Sprite.healthBarEnd3, false);
		if (percentageFilled > 0) screen.drawRect(true, x, y, (int) (percentageFilled * width), height, foregroundColour, false);
		if (percentageFilled > 0) screen.renderSprite((int) (x + percentageFilled * width), y, Sprite.healthBarEnd2, false);
		screen.renderSprite(4, 6, Sprite.healthIcon, false);
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}
}
