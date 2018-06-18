package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.entity.mob.Player;

public class StationaryShieldSkill extends SkillTreeNode {

	public StationaryShieldSkill(int x, int y, int tree, Player player, SkillTreeManager manager) {
		super(x, y, tree, player, manager);
	}

	protected void updateSkill() {

	}

	public void activate() {
		super.activate();
		manager.stationaryShieldUnlocked = true;
	}

	public void deactivate() {
		super.deactivate();
		manager.stationaryShieldUnlocked = false;
	}

	public void playerKilledEnemy() {
	}

}
