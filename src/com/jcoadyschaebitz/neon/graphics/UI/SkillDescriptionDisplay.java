package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.graphics.UI.skillTrees.SkillTreeNode;

public class SkillDescriptionDisplay implements UIComp {
	
	private SkillTreeNode skill;
	private Font font;
	private int x, y;
	private Sprite sprite;
	
	public SkillDescriptionDisplay(int x, int y, int width, int height, SkillTreeNode skill) {
		this.x = x;
		this.y = y;
		this.skill = skill;
		font = new Font(Font.SIZE_8x8, 0xffBAFFDA, 1);
		sprite = Sprite.createButtonSprite(Spritesheet.buttonCorners, width, height, false);
	}

	public void update() {
	}

	public void render(Screen screen) {
		screen.renderSprite(x, y, sprite, false);
		font.render(x + 6, y + 6, 0, skill.description, screen, false);
	}
	
	public void updateSkill(SkillTreeNode skill) {
		this.skill = skill;
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}
}
