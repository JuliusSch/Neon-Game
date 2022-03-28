package com.jcoadyschaebitz.neon.graphics.UI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.input.Mouse;

public abstract class UIButton implements UIComp, MouseListener {

	protected int x, y, width, height;
	public boolean mousePressedInArea = false, buttonHighlighted;
	protected String label;
	protected Font font;
	Sprite defaultSprite, highlightedSprite, ClickedSprite;
	protected UIMenu menu;
	protected boolean buttonActive;

	public UIButton(int x, int y, int width, int height, String label, int colour) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.label = label;
		font = new Font(Font.SIZE_8x8, colour, 1);
		defaultSprite = Sprite.createButtonSprite(Spritesheet.buttonCorners, width, height, colour, false);
		highlightedSprite = Sprite.createButtonSprite(Spritesheet.buttonCornersHighlight, width, height, colour, true);
	}

	public abstract void doFunction();

	public void update() {
		if (!buttonActive) return;
		double s = Game.getWindowScale();
		if (Mouse.getX() > (x * s) + Game.getXBarsOffset() && Mouse.getX() < (x + width) * s + Game.getXBarsOffset()) {
			if (Mouse.getY() > (y * s) && Mouse.getY() < (y + height) * s) {
				if (!buttonHighlighted) buttonHighlighted = true;
			} else if (buttonHighlighted) buttonHighlighted = false;
		} else if (buttonHighlighted) buttonHighlighted = false;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void render(Screen screen) {
		screen.renderSprite(x, y, defaultSprite, false);
		if (buttonHighlighted) screen.renderTranslucentSprite(x, y, highlightedSprite, false, 0.2);
		font.render(x + 6, y + 10, 0, label, screen, false);
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void activate() {
		buttonActive = true;
	}

	@Override
	public void deactivate() {
		buttonActive = false;
	}

	public void mousePressed(MouseEvent e) {
		if (!buttonActive) return;
		double s = Game.getWindowScale();
		if (Mouse.getX() > (x * s) + Game.getXBarsOffset() && Mouse.getX() < (x + width) * s + Game.getXBarsOffset()) {
			if (Mouse.getY() > (y * s) && Mouse.getY() < (y + height) * s) {
				mousePressedInArea = true;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (!mousePressedInArea) return;
		double s = Game.getWindowScale();
		if (Mouse.getX() > (x * s) + Game.getXBarsOffset() && Mouse.getX() < (x + width) * s + Game.getXBarsOffset()) {
			if (Mouse.getY() > (y * s) && Mouse.getY() < (y + height) * s) {
				doFunction();
			}
		}

		mousePressedInArea = false;
	}

}