package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.UI.UIComp;
import com.jcoadyschaebitz.neon.input.Mouse;

public abstract class SkillTreeNode implements MouseListener {

	protected boolean active, mouseInBounds;
	protected Sprite inactiveSprite, activeSprite, hoverSprite;
	protected int spritePositionX, spritePositionY, width, height, xOffset, yOffset;
	public final int tree;
	public String description;
	protected Player player;
	protected Font font;
	protected SkillTreeManager manager;

	public SkillTreeNode(int x, int y, int tree, Player player, SkillTreeManager manager) {
		this.spritePositionX = x;
		this.spritePositionY = y;
		this.manager = manager;
		width = 16;
		height = 16;
		inactiveSprite = Sprite.skillOutline;
		activeSprite = Sprite.skillOutlineSelected;
		hoverSprite = Sprite.skillOutlineHover;
		this.player = player;
		font = new Font(Font.SIZE_5x5, 0xffBAFFDA, 1);
		this.tree = tree;
		description = "There isn't a description";
	}

	public void update() {
		updateSkill();
		
		double s = Game.getWindowScale();
		if (Mouse.getX() > (xOffset + spritePositionX) * s + Game.getXBarsOffset() && Mouse.getX() < (xOffset + spritePositionX + width) * s + Game.getXBarsOffset()) {
			if (Mouse.getY() > (yOffset + spritePositionY) * s && Mouse.getY() < (yOffset + spritePositionY + height) * s) {
				mouseInBounds = true;
				manager.skillDescriptionDisplay.updateSkill(this);
			} else mouseInBounds = false;
		} else mouseInBounds = false;
	}

	public void activate() {
		if (!active && manager.availableSkillPoints > 0) {
			active = true;
			manager.availableSkillPoints--;
		}
	}

	public void deactivate() {
		if (active) {
			active = false;
			manager.availableSkillPoints++;
		}
	}

	public abstract void playerKilledEnemy();

	protected abstract void updateSkill();

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (!UIComp.uiManager.getGame().getState().equals(UIComp.uiManager.getGame().shopMenuState)) return;
		double s = Game.getWindowScale();
		if (Mouse.getX() > (xOffset + spritePositionX) * s + Game.getXBarsOffset() && Mouse.getX() < (xOffset + spritePositionX + width) * s + Game.getXBarsOffset()) {
			if (Mouse.getY() > (yOffset + spritePositionY) * s && Mouse.getY() < (yOffset + spritePositionY + height) * s) {
				if (e.getButton() == MouseEvent.BUTTON1) activate();
				else if (e.getButton() == MouseEvent.BUTTON3) deactivate();
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
	}
	
	public void render(Screen screen, int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		if (mouseInBounds) screen.renderTranslucentSprite(xOffset + spritePositionX - 2, yOffset + spritePositionY - 2, hoverSprite, false, 0.2); 
		if (active) {
			screen.renderSprite(xOffset + spritePositionX, yOffset + spritePositionY, activeSprite, false);
			font.render(xOffset + spritePositionX + 3, yOffset + spritePositionY + 6, "1/1", screen, false);
		} else {
			if (mouseInBounds) screen.renderSprite(xOffset + spritePositionX, yOffset + spritePositionY, activeSprite, false);
			else screen.renderSprite(xOffset + spritePositionX, yOffset + spritePositionY, inactiveSprite, false);
			font.render(xOffset + spritePositionX + 3, yOffset + spritePositionY + 6, "0/1", screen, false);
		}	
	}
}
