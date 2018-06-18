package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.Screen;

public class NullSkill extends SkillTreeNode {

	public NullSkill(int x, int y, int tree, Player player, SkillTreeManager manager) {
		super(x, y, tree, player, manager);
	}

	public void playerKilledEnemy() {
		
	}

	protected void updateSkill() {
		
	}
	
	public void render(Screen screen, int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		screen.renderSprite(xOffset + spritePositionX, yOffset + spritePositionY, inactiveSprite, false);
		if (mouseInBounds) {
			screen.renderTranslucentSprite(xOffset + spritePositionX - 2, yOffset + spritePositionY - 2, hoverSprite, false, 0.2);
			screen.renderSprite(xOffset + spritePositionX, yOffset + spritePositionY, activeSprite, false);
		}
	}

}
