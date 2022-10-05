package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.Item.HealthKit;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AttackPlayer;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CheckAggro;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CheckDamageTaken;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CheckDistanceToPlayer;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.FindNewPosition;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.FindSightline;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.InverterNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.MoveTo;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.RunConcurrentlyNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.RunConcurrentlyNode.ConcurrentNodeBehaviour;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SelectorNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SequencerNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SetState;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.WaitBehaviour;
import com.jcoadyschaebitz.neon.entity.weapon.MiniGun;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class Heavy extends ShootingEnemy {

	private double secondAttackDelayMeter = 0;

	public Heavy(int x, int y) {
		super(x, y);
		leftWalking = new AnimatedSprite(Spritesheet.heavyLeftWalking, 24, 24, 8, 5);
		rightWalking = new AnimatedSprite(Spritesheet.heavyRightWalking, 24, 24, 8, 5);
		leftIdle = new AnimatedSprite(Spritesheet.heavyLeftIdle, 24, 24, 4, 10);
		rightIdle = new AnimatedSprite(Spritesheet.heavyRightIdle, 24, 24, 4, 10);
		leftDamage = new AnimatedSprite(Spritesheet.heavyLeftDamage, 24, 24, 4, 5);
		rightDamage = new AnimatedSprite(Spritesheet.heavyRightDamage, 24, 24, 4, 5);
		leftDying = new AnimatedSprite(Spritesheet.heavyLeftDying, 24, 24, 4, 5);
		rightDying = new AnimatedSprite(Spritesheet.heavyRightDying, 24, 24, 4, 5);
		currentAnim = leftWalking;
		sprite = leftWalking.getSprite();
		viewRange = 200;
		int[] xCollisionValues = { 0, 24, 0, 24, 12, 12, 0, 24 };
		int[] yCollisionValues = { 0, 0, 24, 24, 0, 24, 12, 12 };
		int[] xTileCollisionValues = { 0, 24, 0, 24 };
		int[] yTileCollisionValues = { 8, 8, 24, 24 };
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		maxHealth = 90;
		health = maxHealth;
		deadSpriteLeft = Sprite.soldier_dead_left;
		deadSpriteRight = Sprite.soldier_dead_right;
		xpAmount = 20;
		speed = 0.7;
		knockbackMultiplier = 0.2;
		maxPoise = 25;
	}

	public void update() {
		super.update();
		if (weapon == null) level.add(new MiniGun(this));
	}

	protected void attackPlayer() {
//		if (time % 120 == 0 && random.nextInt(1) == 0) {
			if (weapon != null) {
				if (random.nextInt(2) == 1) {
					weapon.attack(getMidX(), getMidY(), directionP, 3);
					weapon.attack(getMidX(), getMidY(), directionP - 0.15, 3);
					weapon.attack(getMidX(), getMidY(), directionP + 0.15, 3);
				} else secondAttackDelayMeter = 60;
			}
//		}
		if (secondAttackDelayMeter > 0) {
			secondAttackDelayMeter--;
			if (secondAttackDelayMeter % 5 == 0) weapon.attack(getMidX(), getMidY(), directionP - 1 + (secondAttackDelayMeter / 30), 1);
		}
	}

	public void checkForDrops() {
		double healthPercent = level.getPlayer().getHealth() / level.getPlayer().maxHealth;
		double healthDropChance = 0;
		if (healthPercent <= 0.6) healthDropChance = 0.2;
		if (healthPercent <= 0.4) healthDropChance = 0.3;
		if (healthPercent <= 0.2) healthDropChance = 0.4;
		if (random.nextDouble() < healthDropChance) level.add(new HealthKit((int) x, (int) y));
		for (int i = 0; i < level.getPlayer().slots.size(); i++) {
			double percent;
			if (level.getPlayer().slots.get(i).getWeapon() != null) {
				percent = level.getPlayer().slots.get(i).getWeapon().checkAmmoPercent();
			} else return;
			if (percent >= 0.4 && percent < 0.6) level.getPlayer().slots.get(i).getWeapon().checkAmmoDrop(0.1, x, y);
			if (percent >= 0.2 && percent < 0.4) level.getPlayer().slots.get(i).getWeapon().checkAmmoDrop(0.2, x, y);
			if (percent < 0.2) level.getPlayer().slots.get(i).getWeapon().checkAmmoDrop(0.35, x, y);
		}
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
//		Random random = new Random();
		SelectorNode aggroBehaviours = new SelectorNode(bb, this);
		SequencerNode shootPlayer = new SequencerNode(bb, this);
		
		SequencerNode attack = new SequencerNode(bb, this);
		attack.addNode(new SetState(bb, this, MobState.ATTACKING));
		attack.addNode(new AttackPlayer(bb, this));
		attack.addNode(new SetState(bb, this, MobState.IDLE));
		
		shootPlayer.addNode(new FindSightline(bb, this));
		shootPlayer.addNode(new CheckDistanceToPlayer(bb, this, 32, 160));
//		shootPlayer.addNode(new WaitBehaviour(bb, this, random.nextInt(15) + 20));
		shootPlayer.addNode(attack);

		SequencerNode attack2 = new SequencerNode(bb, this);
		attack2.addNode(new SetState(bb, this, MobState.ATTACKING));
		attack2.addNode(new AttackPlayer(bb, this));
		attack2.addNode(new SetState(bb, this, MobState.IDLE));
		attack2.addNode(new WaitBehaviour(bb, this, 10));
		
		RunConcurrentlyNode moveAndShoot = new RunConcurrentlyNode(bb, this, new MoveTo(bb, this));
		moveAndShoot.addNode(attack2, ConcurrentNodeBehaviour.REPEAT);

		SequencerNode closeIfFar = new SequencerNode(bb, this);
		closeIfFar.addNode(new InverterNode(new CheckDistanceToPlayer(bb, this, 0, 160)));
		closeIfFar.addNode(new FindNewPosition(bb, this, 48));
		closeIfFar.addNode(new SetState(bb, this, MobState.WALKING));
		closeIfFar.addNode(moveAndShoot);
		closeIfFar.addNode(new SetState(bb, this, MobState.IDLE));
		
		SequencerNode getNewSightline = new SequencerNode(bb, this);
		getNewSightline.addNode(new FindNewPosition(bb, this, 80));
		getNewSightline.addNode(new SetState(bb, this, MobState.WALKING));
		getNewSightline.addNode(new MoveTo(bb, this));
		getNewSightline.addNode(new SetState(bb, this, MobState.IDLE));
//		moveAndShoot.addNode(attack, ConcurrentNodeBehaviour.REPEAT);
//		getNewSightline.addNode(moveAndShoot);
//		getNewSightline.addNode(shootPlayer);
		SelectorNode tryFindSightline = new SelectorNode(bb, this);
		tryFindSightline.addNode(getNewSightline);
		tryFindSightline.addNode(new WaitBehaviour(bb, this, 30));
//		RandomSelectorNode shootOrMove = new RandomSelectorNode(bb, this);
//		shootOrMove.addNode(shootPlayer, 0.7);
//		shootOrMove.addNode(tryFindSightline, 0.3);
//		aggroBehaviours.addNode(shootOrMove);
		aggroBehaviours.addNode(closeIfFar);
		aggroBehaviours.addNode(shootPlayer);
		aggroBehaviours.addNode(tryFindSightline);
		behaviours.addNode(new CheckAggro(bb, this));
		behaviours.addNode(new CheckDamageTaken(bb, this));
		behaviours.addNode(aggroBehaviours);
	}
}
