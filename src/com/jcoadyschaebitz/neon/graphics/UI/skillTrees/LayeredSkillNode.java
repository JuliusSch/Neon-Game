package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.Screen;

public abstract class LayeredSkillNode extends SkillTreeNode {

	protected int prevLayer, layer;

	public LayeredSkillNode(int x, int y, int tree, Player player, SkillTreeManager manager) {
		super(x, y, tree, player, manager);
		if (layer >= 0 && layer <= 5) {
			this.layer = 0;
			this.prevLayer = 0;
		}
	}
	
	public void activate() {
		super.activate();
		setLayer(layer + 1);
	}
	
	public void setLayer(int layer) {
		if (this.layer == 5) prevLayer = 5;
		if (layer > 0 && layer <= 5) {
			prevLayer = this.layer;
			this.layer = layer;
		}
		if (layer == 0) deactivate();
	}

	protected void updateSkill() {
		updateSkill(layer);
	}

	protected abstract void updateSkill(int layer);

	public void render(Screen screen, int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		if (mouseInBounds) screen.renderTranslucentSprite(xOffset + spritePositionX - 2, yOffset + spritePositionY - 2, hoverSprite, false, 0.2); 
		if (active) screen.renderSprite(xOffset + spritePositionX, yOffset + spritePositionY, activeSprite, false);
		else if (mouseInBounds) screen.renderSprite(xOffset + spritePositionX, yOffset + spritePositionY, activeSprite, false);
		else screen.renderSprite(xOffset + spritePositionX, yOffset + spritePositionY, inactiveSprite, false);
		font.render(xOffset + spritePositionX + 3, yOffset + spritePositionY + 6, layer + "/5", screen, false);
	}
}
