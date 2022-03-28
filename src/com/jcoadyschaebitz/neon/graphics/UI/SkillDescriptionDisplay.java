package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.skillTrees.SkillTreeNode;

public class SkillDescriptionDisplay implements UIComp {
	
	private SkillTreeNode skill;
	private Font font;
	private int x, y;
	
	public SkillDescriptionDisplay(int x, int y, SkillTreeNode skill) {
		this.x = x;
		this.y = y;
		this.skill = skill;
		font = new Font(Font.SIZE_8x8, 0xffBAFFDA, 1);
	}

	public void update() {
	}

	public void render(Screen screen) {
		screen.drawRect(true, x + 2, y, 112, 1, 0xffBAFFDA, false);
		screen.drawRect(true, x, y + 2, 1, 60, 0xffBAFFDA, false);
		screen.drawRect(true, x + 113, y + 2, 1, 60, 0xffBAFFDA, false);
		screen.drawRect(true, x, y + 61, 112, 1, 0xffBAFFDA, false);
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
