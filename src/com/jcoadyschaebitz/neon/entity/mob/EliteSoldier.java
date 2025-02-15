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
import com.jcoadyschaebitz.neon.entity.weapon.EliteRifle;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class EliteSoldier extends ShootingEnemy {

	public EliteSoldier(int x, int y) {
		super(x, y);
		leftIdle = new AnimatedSprite(Spritesheet.eliteSoldierLeftIdle, 24, 48, 4, 15);
		leftWalking = new AnimatedSprite(Spritesheet.eliteSoldierLeftWalking, 24, 48, 8, 3);
		leftDamage = new AnimatedSprite(Spritesheet.eliteSoldierLeftDamage, 24, 48, 4, 5);
		leftDying = new AnimatedSprite(Spritesheet.eliteSoldierLeftDying, 24, 48, 6, 8);
		rightIdle = new AnimatedSprite(Sprite.mirrorSprites(Spritesheet.eliteSoldierLeftIdle.getSprites()), 24, 48, 4, 15 );
		rightWalking = new AnimatedSprite(Sprite.mirrorSprites(Spritesheet.eliteSoldierLeftWalking.getSprites()), 24, 48, 8, 3);
		rightDamage = new AnimatedSprite(Sprite.mirrorSprites(Spritesheet.eliteSoldierLeftDamage.getSprites()), 24, 48, 4, 5);
		rightDying = new AnimatedSprite(Sprite.mirrorSprites(Spritesheet.eliteSoldierLeftDying.getSprites()), 24, 48, 6, 8);
		currentAnim = leftIdle;
		sprite = leftIdle.getSprite();
		int[] xCollisionValues = { 8, 26, 8, 26, 8, 26, 8, 26 };
		int[] yCollisionValues = { 2, 2, 32, 32, 12, 12, 22, 22 };
		int[] xTileCollisionValues = { 8, 26, 8, 26 };
		int[] yTileCollisionValues = { 21, 21, 32, 32 };
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		maxHealth = 40;
		xpAmount = 15;
		health = maxHealth;
//		deadSpriteRight = Sprite.soldier_dead_right;
//		deadSpriteLeft = Sprite.soldier_dead_left;
		viewRange = 240;
		speed = 1.9;	}

	public void update() {
		super.update();
		if (weapon == null) level.add(new EliteRifle(this));
		if (distanceP < 250 && playerSightline()) aggro = true;
		
		if (damageDelay > 0) damageDelay--;
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
		Random random = new Random();
		
		SequencerNode ifNotAggro = new SequencerNode(bb, this);
		ifNotAggro.addNode(new CheckAggro(bb, this));
		SelectorNode aggroTypes = new SelectorNode(bb, this);
		aggroTypes.addNode(new CheckDamageTaken(bb, this));
		SequencerNode aggroKick = new SequencerNode(bb, this);
		aggroKick.addNode(new CheckDistanceToPlayer(bb, this, 0, 32));
		// aggroKick.add kick player
		aggroTypes.addNode(aggroKick);
		
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
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y + 22, Sprite.shadow, true, 0.5);
		screen.renderTranslucentSprite((int) x, (int) y, currentAnim.getSprite(), true);
		if (weapon != null) weapon.renderOnOwner(screen, (currentAnim.getFrame() / 4) % 2);
//		font.render((int) x, (int) y, state.toString(), screen, true);
	}

}
