package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.MultiAttackWeapon;
import com.jcoadyschaebitz.neon.entity.weapon.Weapon.WeaponState;

public class TertiaryAttack extends BehaviourNode {

	public TertiaryAttack(AIBlackboard bb, Mob mob) {
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
			if (mob.weapon instanceof MultiAttackWeapon) {
				((MultiAttackWeapon) mob.weapon).tertiaryAttack(mob.getMidX(), mob.getMidY(), blackboard.getPlayerDirection(mob));
				currentState = NodeState.SUCCESS;
				mob.weapon.setState(WeaponState.IDLE);
			} else {
				currentState = NodeState.FAILURE;
				mob.weapon.setState(WeaponState.IDLE);
			}
		}		
	}

}
