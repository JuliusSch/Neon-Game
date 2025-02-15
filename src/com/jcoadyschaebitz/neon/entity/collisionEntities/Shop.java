package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.IInteractableItem;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.input.InputManager.InputAction;

public class Shop extends CollisionEntity implements IInteractableItem {

	protected Font font;
	protected boolean showPrompt;
	
	public Shop(int x, int y) {
		super(x, y);
		sprite = Sprite.shop;
		shadowSprite = Sprite.nullSprite;
		font = new Font(Font.SIZE_12x12, 0xffffffff, 1);
		int[] xPoints = { 2, 12, 22, 32, 42, 52, 62, 2, 12, 22, 32, 42, 52, 62, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62 };
		int[] yPoints = { 30, 30, 30, 30, 30, 30, 30, 121, 121, 121, 121, 121, 121, 121, 38, 46, 54, 62, 70, 78, 86, 94, 103, 112, 38, 46, 54, 62, 70, 78, 86, 94, 103, 112 };
		entityBounds = new CollisionBox(xPoints, yPoints);
		int[] xCorners = { 2, 62, 2, 62 };
		int[] yCorners = { 30, 30, 124, 124 };
		corners = new CollisionBox(xCorners, yCorners);
	}

	@Override
	public void onInteract(InputAction action) {
		Game.getUIManager().setMenu(Game.getUIManager().shopMenu);
		Game.getUIManager().getGame().switchToGameState(Game.getUIManager().getGame().shopMenuState);
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y + 1, shadowSprite, true, 0.5);
		screen.renderSprite((int) x, (int) y, sprite, true);
		if (showPrompt) font.render((int) x - 4, (int) y + sprite.getHeight() / 2, "E", screen, true);
//		screen.renderSprite((int) x, (int) y - sprite.getHeight(), sprite, true);
		entityBounds.renderBounds(screen, 0xffff00ff, (int) x, (int) y);
//		corners.renderBounds(screen, 0xffff00ff, (int) x, (int) y);
	}

	@Override
	public void setShowPrompt(boolean val) {
		showPrompt = val;
	}
}
