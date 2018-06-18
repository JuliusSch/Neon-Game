package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.entity.mob.Player;

public class ShieldDurationSkill extends LayeredSkillNode {

	public ShieldDurationSkill(int x, int y, int tree, Player player, SkillTreeManager manager) {
		super(x, y, tree, player, manager);
	}

	protected void updateSkill(int layer) {
	}
	
	public void activate() {
		super.activate();
//		player.getActionSkillManager().totalActionSkillDuration -= prevLayer * 30;
//		player.getActionSkillManager().totalActionSkillDuration += layer * 30;
	}
	
	public void deactivate() {
		super.deactivate();
//		player.getActionSkillManager().totalActionSkillDuration -= layer * 30;
		layer = 0;
		prevLayer = 0;
	}

	public void playerKilledEnemy() {
	}

}
