package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.Shield;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.SkillDescriptionDisplay;
import com.jcoadyschaebitz.neon.graphics.UI.UIComp;

public class SkillTreeManager implements UIComp {

	Player player;
	public boolean stationaryShieldUnlocked, shieldReflectUnlocked;
	public double shieldReflectChance = 0;
	public List<Shield> activeShields = new ArrayList<Shield>();
	public SkillTree tree1, tree2, tree3;
	public SkillDescriptionDisplay skillDescriptionDisplay;
	private Font font;
	
	public int availableSkillPoints;

	private SkillTree[] trees = new SkillTree[3];

	public SkillTreeManager(Game game, Player player) {
		this.player = player;
		SkillTreeNode[] tree1Skills = new SkillTreeNode[10];
		SkillTreeNode[] tree2Skills = new SkillTreeNode[10];
		SkillTreeNode[] tree3Skills = new SkillTreeNode[10];
		
		tree1Skills[0] = new Health5perLx5(0, 0, 0, player, this);
		tree1Skills[1] = new ShieldDurationSkill(40, 0, 0, player, this);
		tree1Skills[2] = new NullSkill(0, 20, 0, player, this);
		tree1Skills[3] = new DmgReduceWShieldSkill(40, 20, 0, player, this);
		tree1Skills[4] = new NullSkill(0, 40, 0, player, this);
		tree1Skills[5] = new ShieldDurationWKillsSkill(20, 40, 0, player, this);
		tree1Skills[6] = new BulletReflectionSkill(40, 40, 0, player, this);
		tree1Skills[7] = new NullSkill(20, 60, 0, player, this);
		tree1Skills[8] = new NullSkill(20, 80, 0, player, this);
		tree1Skills[9] = new TwinShieldSkill(20, 100, 0, player, this);
		tree2Skills[0] = new NullSkill(0, 0, 1, player, this);
		tree2Skills[1] = new NullSkill(40, 0, 1, player, this);
		tree2Skills[2] = new BulletSpeedSkill(0, 20, 1, player, this);
		tree2Skills[3] = new NullSkill(40, 20, 1, player, this);
		tree2Skills[4] = new NullSkill(0, 40, 1, player, this);
		tree2Skills[5] = new NullSkill(20, 40, 1, player, this);
		tree2Skills[6] = new NullSkill(40, 40, 1, player, this);
		tree2Skills[7] = new NullSkill(20, 60, 1, player, this);
		tree2Skills[8] = new NullSkill(20, 80, 1, player, this);
		tree2Skills[9] = new StationaryShieldSkill(20, 100, 1, player, this);
		tree3Skills[0] = new NullSkill(0, 0, 2, player, this);
		tree3Skills[1] = new NullSkill(40, 0, 1, player, this);
		tree3Skills[2] = new NullSkill(0, 20, 1, player, this);
		tree3Skills[3] = new NullSkill(40, 20, 1, player, this);
		tree3Skills[4] = new NullSkill(0, 40, 1, player, this);
		tree3Skills[5] = new NullSkill(20, 40, 1, player, this);
		tree3Skills[6] = new NullSkill(40, 40, 1, player, this);
		tree3Skills[7] = new NullSkill(20, 60, 1, player, this);
		tree3Skills[8] = new NullSkill(20, 80, 1, player, this);
		tree3Skills[9] = new NullSkill(20, 100, 1, player, this);
		
		skillDescriptionDisplay = new SkillDescriptionDisplay(95, 224, 280, 60, tree1Skills[0]);

		trees[0] = tree1 = new SkillTree(0, "name1", 125, 83, tree1Skills);
		trees[1] = tree2 = new SkillTree(1, "name2", 200, 83, tree2Skills);
		trees[2] = tree3 = new SkillTree(2, "name3", 275, 83, tree3Skills);
		for (SkillTree tree : trees) {
			for (SkillTreeNode node : tree.skills) {
				if (node != null) game.addMouseListener(node);
			}
		}
		
		font =  new Font(Font.SIZE_16x16, 0xffBAFFDA, 1);
		availableSkillPoints = 3;
	}

	public void update() {
		for (SkillTree tree : trees) tree.update();
		skillDescriptionDisplay.update();
	}

	public void enemyKilled() {
		for (SkillTree tree : trees) {
			tree.enemyKilled();
		}
	}
	
	public Shield getShield() {
		return new Shield(player.getX(), player.getY(), player.getDirection(), player);
	}

	public void render(Screen screen) {
		for (SkillTree tree : trees) tree.render(screen);
		skillDescriptionDisplay.render(screen);
		font.render(100, 40, Integer.toString(availableSkillPoints), screen, false);
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}
}
