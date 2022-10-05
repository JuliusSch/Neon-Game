package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.entity.mob.Player;

public class Health5perLx5 extends LayeredSkillNode {

	public Health5perLx5(int x, int y, int tree, Player player, SkillTreeManager manager) {
		super(x, y, tree, player, manager);
		description = "Health is increased\nby 5% for 5 levels.\nTotal 25% increase.";
	}

	protected void updateSkill(int layer) {
	}
	
	public void activate() {
		super.activate();
//		double newHealth;
//		double change = (prevLayer * 0.05) + 1;
//		newHealth = player.maxHealth / change;
//		newHealth = newHealth * ((layer * 0.05) + 1);
//		player.maxHealth = newHealth;
	}
	
	public void deactivate() {
		super.deactivate();
//		player.maxHealth = player.maxHealth / layer;
	}

	public void playerKilledEnemy() {
	}

}
