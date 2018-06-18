package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.entity.mob.Player;

public class DmgReduceWShieldSkill extends LayeredSkillNode {
	
	public DmgReduceWShieldSkill(int x, int y, int tree, Player player, SkillTreeManager manager) {
		super(x, y, tree, player, manager);
		player.damageReductionMultiplier = 1;
	}

	protected void updateSkill(int layer) {
		player.damageReductionMultiplier = 1 - (layer * 0.06);
	}
	
	public void deactivate() {
		super.deactivate();
		layer = 0;
		prevLayer = 0;
		player.damageReductionMultiplier = 1;
	}

	public void playerKilledEnemy() {
	}

}
