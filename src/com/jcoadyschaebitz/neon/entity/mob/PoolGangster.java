package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AttackPlayer;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CheckAggro;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CheckDamageTaken;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.FindNewPosition;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.FindSightline;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.MoveTo;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.RandomSelectorNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SelectorNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SequencerNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SetState;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.WaitBehaviour;
import com.jcoadyschaebitz.neon.entity.weapon.DoublePistols;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class PoolGangster extends ShootingEnemy {

	public PoolGangster(int x, int y) {
		super(x, y);
		leftWalking = new AnimatedSprite(Spritesheet.poolGangsterLeftWalking, 24, 24, 8, 3);
		rightWalking = new AnimatedSprite(Spritesheet.poolGangsterRightWalking, 24, 24, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.poolGangsterLeftIdle, 24, 24, 4, 10);
		rightIdle = new AnimatedSprite(Spritesheet.poolGangsterRightIdle, 24, 24, 4, 10);
		leftDamage = new AnimatedSprite(Spritesheet.poolGangsterLeftDamage, 24, 24, 4, 5);
		rightDamage = new AnimatedSprite(Spritesheet.poolGangsterRightDamage, 24, 24, 4, 5);
		leftDying = new AnimatedSprite(Spritesheet.poolGangsterLeftDying, 24, 24, 6, 5);
		rightDying = new AnimatedSprite(Spritesheet.poolGangsterRightDying, 24, 24, 6, 5);
		currentAnim = leftIdle;
		sprite = leftIdle.getSprite();
		int[] xEntityCollisionValues = { 0, 16, 0, 16, 0, 16 };
		int[] yEntityCollisionValues = { 0, 0, 22, 22, 11, 11 };
		int[] xTileCollisionValues = { 0, 16, 0, 16 };
		int[] yTileCollisionValues = { 12, 12, 22, 22 };
		spriteWidth = 16;
		spriteHeight = 22;
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		entityBounds = new CollisionBox(xEntityCollisionValues, yEntityCollisionValues);
		maxHealth = 14;
		xpAmount = 4;
		health = maxHealth;
		deadSpriteRight = Sprite.soldier_dead_right;
		deadSpriteLeft = Sprite.soldier_dead_left;
		viewRange = 240;
		speed = 1.3;
	}
	
	public void update() {
		super.update();
		if (weapon == null) level.add(new DoublePistols(this));
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
//		behaviours.addNode(new CheckAggro(bb, this));
//		behaviours.addNode(new CheckDamageTaken(bb, this));
//		SequencerNode lowHealthFrenzy = new SequencerNode(bb, this);
//		lowHealthFrenzy.addNode(new HealthInRange(bb, this, 0, 0.5));
//		
//		SequencerNode standard = new SequencerNode(bb, this);
////		standard.addNode(node);
//		behaviours.addNode(lowHealthFrenzy);
////		behaviours.addNode(standard);
		behaviours.addNode(new CheckAggro(bb, this));
		behaviours.addNode(new CheckDamageTaken(bb, this));
		SelectorNode aggroBehaviours = new SelectorNode(bb, this);
		SequencerNode shootPlayer = new SequencerNode(bb, this);
		shootPlayer.addNode(new FindSightline(bb, this));
		shootPlayer.addNode(new WaitBehaviour(bb, this, 60));
		shootPlayer.addNode(new SetState(bb, this, MobState.ATTACKING));
		shootPlayer.addNode(new AttackPlayer(bb, this));
		shootPlayer.addNode(new AttackPlayer(bb, this));
		shootPlayer.addNode(new SetState(bb, this, MobState.IDLE));
		SequencerNode getNewSightline = new SequencerNode(bb, this);
		getNewSightline.addNode(new FindNewPosition(bb, this, 128));
		getNewSightline.addNode(new SetState(bb, this, MobState.WALKING));
		getNewSightline.addNode(new MoveTo(bb, this));
		getNewSightline.addNode(shootPlayer);
		RandomSelectorNode shootOrMove = new RandomSelectorNode(bb, this);
		shootOrMove.addNode(shootPlayer, 0.7);
		shootOrMove.addNode(getNewSightline, 0.3);
		aggroBehaviours.addNode(shootOrMove);
		aggroBehaviours.addNode(getNewSightline);
		behaviours.addNode(aggroBehaviours);
	}

}
