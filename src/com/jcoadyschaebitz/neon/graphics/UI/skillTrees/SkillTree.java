package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class SkillTree {

	public final int treeNumber;
	private String name;
	private Font font;
	private int x, y;

	public SkillTreeNode[] skills = new SkillTreeNode[10];

	public SkillTree(int treeNumber, String name, int x, int y, SkillTreeNode[] skills) {
		this.treeNumber = treeNumber;
		this.name = name;
		this.skills = skills;
		font = new Font(Font.SIZE_8x8, 0xffBAFFDA, 1);
		this.x = x;
		this.y = y;
	}

	public void enemyKilled() {
		for (SkillTreeNode skill : skills) {
			if (skill != null) {
				skill.playerKilledEnemy();
			}
		}
	}

	public void update() {
		for (int i = 0; i < skills.length; i++) {
			if (skills[i] != null) {
				skills[i].update();
			}
		}
	}

	public void render(Screen screen) {
		screen.renderSprite(x, y, Sprite.SkillTreeOutline, false);
		for (int i = 0; i < skills.length; i++) {
			if (skills[i] != null) {
				skills[i].render(screen, x + 6, y + 6);
			}
		}
		font.render(x + 16, y - 10, 1, name, screen, false);
	}

}
