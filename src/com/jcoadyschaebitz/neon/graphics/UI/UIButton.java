package com.jcoadyschaebitz.neon.graphics.UI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.input.Mouse;

public abstract class UIButton implements UIComp, MouseListener {

	protected int x, y, width, height;
	public boolean mousePressedInArea = false, buttonHighlighted;
	protected String label;
	protected Font font;
	Sprite defaultSprite, highlightedSprite, ClickedSprite;

	public UIButton(int x, int y, int width, int height, String label, int fontColour) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.label = label;
		font = new Font(Font.SIZE_8x8, fontColour);
		defaultSprite = Sprite.buttonOutline;
		highlightedSprite = Sprite.buttonHighlighted;
	}

	public abstract void doFunction();

	public void update() {
		double s = Game.getWindowScale();
		if (Mouse.getX() > (x * s) + Game.getXRenderOffset() && Mouse.getX() < (x + width) * s + Game.getXRenderOffset()) {
			if (Mouse.getY() > (y * s) && Mouse.getY() < (y + height) * s) {
				buttonHighlighted = true;
			} else buttonHighlighted = false;
		} else buttonHighlighted = false;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void render(Screen screen) {
		screen.renderSprite(x, y, defaultSprite, false);
		if (buttonHighlighted) screen.renderTranslucentSprite(x - 1, y - 1, highlightedSprite, false, 0.2);
		font.render(x + 6, y + 10, 0, label, screen, false);
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (!UIComp.ui.getGame().getState().equals(UIComp.ui.getGame().pauseState)) return;
		double s = Game.getWindowScale();
		if (Mouse.getX() > (x * s) + Game.getXRenderOffset() && Mouse.getX() < (x + width) * s + Game.getXRenderOffset()) {
			if (Mouse.getY() > (y * s) && Mouse.getY() < (y + height) * s) {
				mousePressedInArea = true;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (mousePressedInArea) {
			double s = Game.getWindowScale();
			if (Mouse.getX() > (x * s) + Game.getXRenderOffset() && Mouse.getX() < (x + width) * s + Game.getXRenderOffset()) {
				if (Mouse.getY() > (y * s) && Mouse.getY() < (y + height) * s) {
					doFunction();
				}
			}
		}
		mousePressedInArea = false;
	}

}