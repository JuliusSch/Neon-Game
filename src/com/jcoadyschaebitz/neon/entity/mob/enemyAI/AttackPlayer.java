package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.weapon.Weapon.WeaponState;

public class AttackPlayer extends BehaviourNode {

	private boolean isSecondaryAttack;
	
	public AttackPlayer(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}
	
	public AttackPlayer(AIBlackboard bb, Mob mob, boolean secondaryAttack) {
		this(bb, mob);
		isSecondaryAttack = secondaryAttack;
	}

	@Override
	public void update() {
		timer++;
		if (currentState == NodeState.READY) {
			currentState = NodeState.RUNNING;
			mob.weapon.setState(WeaponState.ATTACKING);
			mob.weapon.beginPreAttackAnimations();
		}
		if (currentState == NodeState.RUNNING && timer > mob.weapon.getWeaponAttackBuildup(isSecondaryAttack)) {
			if (isSecondaryAttack) mob.weapon.secondaryAttack(mob.getMidX(), mob.getMidY(), blackboard.getPlayerDirection(mob));
			else mob.weapon.attack(mob.getMidX(), mob.getMidY(), blackboard.getPlayerDirection(mob));
			currentState = NodeState.SUCCESS;
			mob.weapon.setState(WeaponState.IDLE);
		}
	}
	
}
