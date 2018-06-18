package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.entity.mob.Player;

public class BulletSpeedSkill extends LayeredSkillNode {

	public BulletSpeedSkill(int x, int y, int tree, Player player, SkillTreeManager manager) {
		super(x, y, tree, player, manager);
	}

	protected void updateSkill(int layer) {
		player.bulletSpeedMultiplier = 1 + (layer * 0.04);
	}

	public void deactivate() {
		super.deactivate();
		player.bulletSpeedMultiplier = 1;
		layer = 0;
		prevLayer = 0;
	}

	public void playerKilledEnemy() {
	}

}
