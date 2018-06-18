package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.entity.mob.Player;

public class FireRateDamageSkill extends LayeredSkillNode {

	public FireRateDamageSkill(int x, int y, int tree, Player player, SkillTreeManager manager) {
		super(x, y, tree, player, manager);
	}

	protected void updateSkill(int layer) {
	}

	public void activate() {
		super.activate();
	}
	
	public void deactivate() {
		super.deactivate();
		prevLayer = 0;
		layer = 0;
	}

	public void playerKilledEnemy() {
	}
	
}