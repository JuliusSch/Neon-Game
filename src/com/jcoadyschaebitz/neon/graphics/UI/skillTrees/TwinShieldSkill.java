package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.entity.mob.Player;

public class TwinShieldSkill extends SkillTreeNode {

	public TwinShieldSkill(int x, int y, int tree, Player player, SkillTreeManager manager) {
		super(x, y, tree, player, manager);
	}

	protected void updateSkill() {
	}
	
	public void activate() {
		super.activate();
		player.getActionSkillManager().twinShieldUnlocked = true;
	}
	
	public void deactivate() {
		super.deactivate();
		player.getActionSkillManager().twinShieldUnlocked = false;
	}

	public void playerKilledEnemy() {
	}
	
}
