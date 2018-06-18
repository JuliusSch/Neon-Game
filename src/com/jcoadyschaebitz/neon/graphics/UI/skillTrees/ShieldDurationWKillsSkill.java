package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.entity.mob.Player;

public class ShieldDurationWKillsSkill extends SkillTreeNode {

	public ShieldDurationWKillsSkill(int x, int y, int tree, Player player, SkillTreeManager manager) {
		super(x, y, tree, player, manager);
	}

	protected void updateSkill() {
	}

	public void activate() {
		super.activate();
	}

	public void deactivate() {
		super.deactivate();
	}

	public void playerKilledEnemy() {
		for (int i = 0; i < manager.activeShields.size(); i++) {
			manager.activeShields.get(i).changeDuration(30);
		}
	}

}
