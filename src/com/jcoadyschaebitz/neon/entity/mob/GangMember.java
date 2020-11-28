package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class GangMember extends ShootingEnemy {

	public GangMember(int x, int y) {
		super(x, y);
		leftWalking = new AnimatedSprite(Spritesheet.soldier_left_walking, 24, 24, 8, 3);
		rightWalking = new AnimatedSprite(Spritesheet.soldier_right_walking, 24, 24, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.gangMemberLeftIdle, 24, 24, 4, 12);
		rightIdle = new AnimatedSprite(Spritesheet.gangMemberRightIdle, 24, 24, 4, 12);
		leftDamage = new AnimatedSprite(Spritesheet.soldier_left_damage, 24, 24, 6, 3);
		rightDamage = new AnimatedSprite(Spritesheet.soldier_right_damage, 24, 24, 6, 3);
		leftDying = new AnimatedSprite(Spritesheet.soldier_left_dying, 24, 24, 10, 3);
		rightDying = new AnimatedSprite(Spritesheet.soldier_right_dying, 24, 24, 10, 3);
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
		maxHealth = 8;
		xpAmount = 3;
		health = maxHealth;
		deadSpriteRight = Sprite.soldier_dead_right;
		deadSpriteLeft = Sprite.soldier_dead_left;
		viewRange = 240;
		speed = 1.5;
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
		// TODO Auto-generated method stub
		
	}

}
