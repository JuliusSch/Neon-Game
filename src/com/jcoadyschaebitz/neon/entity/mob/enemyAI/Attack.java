package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.weapon.Weapon.WeaponState;

public class Attack extends BehaviourNode {

	public Attack(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}
	
	@Override
	public void update() {
		timer++;
		if (currentState == NodeState.READY) {
			currentState = NodeState.RUNNING;
			mob.weapon.setState(WeaponState.ATTACKING);
			mob.weapon.beginPreAttackAnimations();
		}
		if (currentState == NodeState.RUNNING && timer > mob.weapon.getWeaponAttackBuildup()) {
			mob.weapon.attack(mob.getMidX(), mob.getMidY(), blackboard.getPlayerDirection(mob));
			currentState = NodeState.SUCCESS;
			mob.weapon.setState(WeaponState.IDLE);
		}
	}
	
}
