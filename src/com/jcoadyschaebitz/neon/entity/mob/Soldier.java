package com.jcoadyschaebitz.neon.entity.mob;

import java.util.Random;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.Attack;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CheckAggro;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CheckDamageTaken;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CheckDistanceToPlayer;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.FindNewPosition;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.FindSightline;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.MoveTo;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.RandomSelectorNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SelectorNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SequencerNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SetState;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.WaitBehaviour;
import com.jcoadyschaebitz.neon.entity.weapon.EnemyGun;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class Soldier extends ShootingEnemy {

	public Soldier(int x, int y) {
		super(x, y);
		leftWalking = new AnimatedSprite(Spritesheet.soldier_left_walking, 24, 24, 8, 3);
		rightWalking = new AnimatedSprite(Spritesheet.soldier_right_walking, 24, 24, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.soldier_left_idle, 24, 24, 4, 10);
		rightIdle = new AnimatedSprite(Spritesheet.soldier_right_idle, 24, 24, 4, 10);
		leftDamage = new AnimatedSprite(Spritesheet.soldier_left_damage, 24, 24, 4, 5);
		rightDamage = new AnimatedSprite(Spritesheet.soldier_right_damage, 24, 24, 4, 5);
		leftDying = new AnimatedSprite(Spritesheet.soldier_left_dying, 24, 24, 6, 8);
		rightDying = new AnimatedSprite(Spritesheet.soldier_right_dying, 24, 24, 6, 8);
		currentAnim = leftIdle;
		sprite = leftIdle.getSprite();
		int[] xEntityCollisionValues = { 0, 15, 0, 15, 0, 15 };
		int[] yEntityCollisionValues = { 0, 0, 22, 22, 11, 11 };
		int[] xTileCollisionValues = { 0, 15, 0, 15 };
		int[] yTileCollisionValues = { 12, 12, 22, 22 };
		spriteWidth = 16;
		spriteHeight = 22;
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		entityBounds = new CollisionBox(xEntityCollisionValues, yEntityCollisionValues);
		maxHealth = 12;
		xpAmount = 3;
		health = maxHealth;
		deadSpriteRight = Sprite.soldier_dead_right;
		deadSpriteLeft = Sprite.soldier_dead_left;
		viewRange = 240;
		speed = 1.3;
	}

	public void update() {
		super.update();
		if (weapon == null) level.add(new EnemyGun(this));
			//	if close move away, if far move close, else circle occasionally and shoot.
			// Need to sort out correct place and frequency for this.
			// change move functionality for all mobs: move() called once, all changes
			// to movement are just setting move variables in the class.
			// e.g. move(x,y) called once, set x and y to set movements.
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
		Random random = new Random();
		SelectorNode aggroBehaviours = new SelectorNode(bb, this);
		SequencerNode shootPlayer = new SequencerNode(bb, this);
		shootPlayer.addNode(new FindSightline(bb, this));
		shootPlayer.addNode(new CheckDistanceToPlayer(bb, this, 32, 160));
		shootPlayer.addNode(new WaitBehaviour(bb, this, random.nextInt(15) + 20));
		shootPlayer.addNode(new SetState(bb, this, MobState.ATTACKING));
		shootPlayer.addNode(new Attack(bb, this));
		shootPlayer.addNode(new SetState(bb, this, MobState.IDLE));
		SequencerNode getNewSightline = new SequencerNode(bb, this);
		getNewSightline.addNode(new FindNewPosition(bb, this, 80));
		getNewSightline.addNode(new SetState(bb, this, MobState.WALKING));
		getNewSightline.addNode(new MoveTo(bb, this));
		getNewSightline.addNode(shootPlayer);
		SelectorNode tryFindSightline = new SelectorNode(bb, this);
		tryFindSightline.addNode(getNewSightline);
		tryFindSightline.addNode(new WaitBehaviour(bb, this, 30));
		RandomSelectorNode shootOrMove = new RandomSelectorNode(bb, this);
		shootOrMove.addNode(shootPlayer, 0.7);
		shootOrMove.addNode(tryFindSightline, 0.3);
		aggroBehaviours.addNode(shootOrMove);
		aggroBehaviours.addNode(tryFindSightline);
		behaviours.addNode(new CheckAggro(bb, this));
		behaviours.addNode(new CheckDamageTaken(bb, this));
		behaviours.addNode(aggroBehaviours);
	}
}
