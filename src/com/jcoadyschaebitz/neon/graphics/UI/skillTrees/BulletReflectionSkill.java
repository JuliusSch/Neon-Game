package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.entity.mob.Player;

public class BulletReflectionSkill extends LayeredSkillNode {

	public BulletReflectionSkill(int x, int y, int tree, Player player, SkillTreeManager manager) {
		super(x, y, tree, player, manager);
	}

	protected void updateSkill(int layer) {
		manager.shieldReflectChance = layer * 0.08;
	}

	public void activate() {
		super.activate();
		manager.shieldReflectUnlocked = true;
	}
	
	public void deactivate() {
		super.deactivate();
		manager.shieldReflectUnlocked = false;
	}

	public void playerKilledEnemy() {
	}
}
