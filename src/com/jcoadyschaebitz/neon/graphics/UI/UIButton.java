package com.jcoadyschaebitz.neon.graphics.UI;

import java.util.Map;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.graphics.UI.INavigableMenu.NavigationDirection;
import com.jcoadyschaebitz.neon.input.IInputObserver;
import com.jcoadyschaebitz.neon.input.InputManager.InputAction;
import com.jcoadyschaebitz.neon.input.InputManager.InputType;
import com.jcoadyschaebitz.neon.input.Mouse;
import com.jcoadyschaebitz.neon.util.IIdentifiable;

public abstract class UIButton implements UIComp, IInputObserver, IIdentifiable {

	protected int x, y, width, height;
	public boolean mousePressedInArea = false;
	protected boolean buttonHighlighted;
	protected String label;
	protected Font font;
	Sprite defaultSprite, highlightedSprite, ClickedSprite;
	protected UIMenu menu;
	protected boolean buttonActive;
	protected String navigationId;
	protected Map<NavigationDirection, String> navigationNeighbours;

	public UIButton(int x, int y, int width, int height, String label, int colour) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.label = label;
		font = new Font(Font.SIZE_8x8, colour, 1);
		defaultSprite = Sprite.createButtonSprite(Spritesheet.buttonCorners, width, height, colour, false);
		highlightedSprite = Sprite.createButtonSprite(Spritesheet.buttonCornersHighlight, width, height, colour, true);
		inputManager.registerObserver(this);
	}
	
	public abstract void doFunction();

	public void update() {
		if (!buttonActive) return;
		if (uiManager.getCurrentInputType() == InputType.KEYBOARD) {
			double s = Game.getWindowScale();
			if (Mouse.getX() > (x * s) + Game.getXBarsOffset() && Mouse.getX() < (x + width) * s + Game.getXBarsOffset()) {
				if (Mouse.getY() > (y * s) && Mouse.getY() < (y + height) * s) {
					if (!buttonHighlighted)
						setHighlighted(true);
				} else if (buttonHighlighted)
					setHighlighted(false);
			} else if (buttonHighlighted)
				setHighlighted(false);			
		}
	}
	
	public void setHighlighted(boolean isHighlighted) {
		buttonHighlighted = isHighlighted;
	}

	public void render(Screen screen) {
		screen.renderSprite(x, y, defaultSprite, false);
		if (buttonHighlighted)
			screen.renderTranslucentSprite(x, y, highlightedSprite, false, 0.2);
		
		font.render(x + 6, y + 10, 0, label, screen, false);
	}
	
	@Override
	public void InputReceived(InputAction input, double value) {
		if (input == InputAction.PRIMARY_ACTION && uiManager.getCurrentInputType() == InputType.KEYBOARD) {
			double s = Game.getWindowScale();
			if (inputManager.mouseX() > (x * s) + Game.getXBarsOffset() && inputManager.mouseX() < (x + width) * s + Game.getXBarsOffset()) {
				if (inputManager.mouseY() > (y * s) && inputManager.mouseY() < (y + height) * s) {
					if (value == 1f) {
						if (buttonActive)
							mousePressedInArea = true;						
					} else {
						if (mousePressedInArea)
							doFunction();
					}
				}					
			}
			if (value == 0f && mousePressedInArea)
				mousePressedInArea = false;
		}
	}

	@Override
	public void activate() {
		buttonActive = true;
	}

	@Override
	public void deactivate() {
		buttonActive = false;
	}
}