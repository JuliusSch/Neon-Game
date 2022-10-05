package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AttackPlayer;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CheckAggro;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CheckDamageTaken;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CirclePlayer;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CirclePlayer.RotationDirection;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.DirectMoveTo;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.FindNewPosition;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.FindSightline;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.InverterNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.IsPlayerInRange;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.MoveTo;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.PursuePlayer;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.RandomSelectorNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SelectorNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SequencerNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SetDodgePosition;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SetRetreatPosition;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SetState;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.weapon.LaserSword;
import com.jcoadyschaebitz.neon.entity.weapon.Weapon.WeaponState;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class MeleeEnemy extends Mob {

	public MeleeEnemy(int x, int y) {
		super(x, y);
		leftWalking = new AnimatedSprite(Spritesheet.swordEnemyLeftWalking, 24, 24, 8, 3);
		rightWalking = new AnimatedSprite(Spritesheet.swordEnemyRightWalking, 24, 24, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.swordEnemyLeftIdle, 24, 24, 4, 10);
		rightIdle = new AnimatedSprite(Spritesheet.swordEnemyRightIdle, 24, 24, 4, 10);
		leftDamage = new AnimatedSprite(Spritesheet.swordEnemyLeftDamage, 24, 24, 6, 3);
		rightDamage = new AnimatedSprite(Spritesheet.swordEnemyRightDamage, 24, 24, 6, 3);
		leftDying = new AnimatedSprite(Spritesheet.swordEnemyLeftDying, 24, 24, 10, 3);
		rightDying = new AnimatedSprite(Spritesheet.swordEnemyRightDying, 24, 24, 10, 3);
		currentAnim = leftIdle;
		deadSpriteRight = Sprite.swordGuy_dead_right;
		deadSpriteLeft = Sprite.swordGuy_dead_left;
		sprite = rightWalking.getSprite();
		int[] xCollisionValues = { 0, 16, 0, 16, 0, 16 };
		int[] yCollisionValues = { 0, 0, 22, 22, 11, 11 };
		int[] xTileCollisionValues = { 0, 16, 0, 16 };
		int[] yTileCollisionValues = { 12, 12, 22, 22 };
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		maxHealth = 16;
		health = maxHealth;
		speed = 2 + (random.nextDouble() / 8);
		xpAmount = 4;
	}

	public void update() {
		super.update();
		if (weapon == null) level.add(new LaserSword(this));

		if (damageDelay == 0) {
			if (level.getPlayer().getIntX() < x) dir = Orientation.LEFT;
			else dir = Orientation.RIGHT;
			if (xa != 0 || ya != 0) {
				move(xa, ya, true);
				walking = true;
			} else {
				walking = false;
			}
		}
		if (weapon != null) {
			if (aggro) weapon.updateSprite(directionP);
			weapon.update();
		}
		if (distanceP < 250 && playerSightline()) aggro = true;
	}
	
	public void hitReceived(Projectile projectile) {
		if (weapon.getState() == WeaponState.BLOCKING) weapon.projectileBlocked(projectile);
		else super.hitReceived(projectile);
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
		SelectorNode aggroBehaviours = new SelectorNode(bb, this);

		// If aggro and not taking damage, proceed
		behaviours.addNode(new CheckAggro(bb, this));
		behaviours.addNode(new CheckDamageTaken(bb, this));
		
		//Check for incoming projectile
		SequencerNode dodgeProjectile = new SequencerNode(bb, this);
		dodgeProjectile.addNode(new SetDodgePosition(bb, this, true));
		dodgeProjectile.addNode(new DirectMoveTo(bb, this, 2 * speed, false));
		dodgeProjectile.addNode(new CirclePlayer(bb, this, 1, 180, RotationDirection.ANTICLOCKWISE, 144));
		aggroBehaviours.addNode(dodgeProjectile);
		
		// If can't see player, relocate
		SequencerNode establishSightline = new SequencerNode(bb, this);
		establishSightline.addNode(new InverterNode(new FindSightline(bb, this)));
		establishSightline.addNode(new FindNewPosition(bb, this, 48));
		establishSightline.addNode(new SetState(bb, this, MobState.WALKING));
		establishSightline.addNode(new MoveTo(bb, this));
		aggroBehaviours.addNode(establishSightline);
		
		SelectorNode playerInSightBehaviours = new SelectorNode(bb, this);
		
		SequencerNode isPlayerCloseRange = new SequencerNode(bb, this);
		isPlayerCloseRange.addNode(new IsPlayerInRange(0, 24, bb, this));
		
		SequencerNode closeRangeBehaviours = new SequencerNode(bb, this);
		closeRangeBehaviours.addNode(new SetState(bb, this, MobState.ATTACKING));
		closeRangeBehaviours.addNode(new AttackPlayer(bb, this));
		closeRangeBehaviours.addNode(new SetState(bb, this, MobState.IDLE));
		closeRangeBehaviours.addNode(new SetRetreatPosition(bb, this, 80));
		closeRangeBehaviours.addNode(new SetState(bb, this, MobState.DASHING));
		closeRangeBehaviours.addNode(new DirectMoveTo(bb, this, 2 * speed, false));
		closeRangeBehaviours.addNode(new SetState(bb, this, MobState.IDLE));
		
		isPlayerCloseRange.addNode(closeRangeBehaviours);
		playerInSightBehaviours.addNode(isPlayerCloseRange);
		
		SequencerNode isPlayerMidRange = new SequencerNode(bb, this);
		isPlayerMidRange.addNode(new IsPlayerInRange(24, 128, bb, this));
		
		RandomSelectorNode midRangeBehaviours = new RandomSelectorNode(bb, this);

		SequencerNode circlePlayerThenAttack = new SequencerNode(bb, this);
		circlePlayerThenAttack.addNode(new SetState(bb, this, MobState.WALKING));
		circlePlayerThenAttack.addNode(new CirclePlayer(bb, this, 1, 120, RotationDirection.CLOCKWISE, 64));
		circlePlayerThenAttack.addNode(new SetState(bb, this, MobState.DASHING));
		circlePlayerThenAttack.addNode(new PursuePlayer(bb, this, 90, 2 * speed));
		circlePlayerThenAttack.addNode(new SetState(bb, this, MobState.ATTACKING));
		circlePlayerThenAttack.addNode(new AttackPlayer(bb, this));
		circlePlayerThenAttack.addNode(new SetState(bb, this, MobState.IDLE));
		circlePlayerThenAttack.addNode(new SetRetreatPosition(bb, this, 80));
		circlePlayerThenAttack.addNode(new SetState(bb, this, MobState.DASHING));
		circlePlayerThenAttack.addNode(new DirectMoveTo(bb, this, 2 * speed, false));
		circlePlayerThenAttack.addNode(new SetState(bb, this, MobState.IDLE));

		midRangeBehaviours.addNode(circlePlayerThenAttack, 0.5);
		
		SequencerNode attackFromMid = new SequencerNode(bb, this);
		attackFromMid.addNode(new SetState(bb, this, MobState.DASHING));
		attackFromMid.addNode(new PursuePlayer(bb, this, 90, 2 * speed));
		attackFromMid.addNode(new SetState(bb, this, MobState.ATTACKING));
		attackFromMid.addNode(new AttackPlayer(bb, this));
		attackFromMid.addNode(new SetState(bb, this, MobState.IDLE));
		attackFromMid.addNode(new SetRetreatPosition(bb, this, 80));
		attackFromMid.addNode(new SetState(bb, this, MobState.DASHING));
		attackFromMid.addNode(new DirectMoveTo(bb, this, 2 * speed, false));
		attackFromMid.addNode(new SetState(bb, this, MobState.IDLE));

		midRangeBehaviours.addNode(attackFromMid, 0.2);
		
		SequencerNode retreatAndShoot = new SequencerNode(bb, this);
		retreatAndShoot.addNode(new SetState(bb, this, MobState.IDLE));
		retreatAndShoot.addNode(new SetRetreatPosition(bb, this, 176));
		retreatAndShoot.addNode(new SetState(bb, this, MobState.DASHING));
		retreatAndShoot.addNode(new DirectMoveTo(bb, this, 2 * speed, false));
		retreatAndShoot.addNode(new SetState(bb, this, MobState.ATTACKING));
		retreatAndShoot.addNode(new AttackPlayer(bb, this, true));
		
		midRangeBehaviours.addNode(retreatAndShoot, 0.3);
		
		isPlayerMidRange.addNode(midRangeBehaviours);
		playerInSightBehaviours.addNode(isPlayerMidRange);
		
		aggroBehaviours.addNode(playerInSightBehaviours);
		
		SequencerNode getNewSightline = new SequencerNode(bb, this);
		getNewSightline.addNode(new FindNewPosition(bb, this, 48));
		getNewSightline.addNode(new MoveTo(bb, this));
		aggroBehaviours.addNode(getNewSightline);
		
		behaviours.addNode(aggroBehaviours);
		
	}

}
