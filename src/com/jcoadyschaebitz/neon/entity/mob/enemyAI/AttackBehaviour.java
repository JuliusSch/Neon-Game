package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Mob.MobState;
import com.jcoadyschaebitz.neon.entity.weapon.Weapon.WeaponState;

public class AttackBehaviour extends BehaviourNode {

	public AttackBehaviour(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}

	@Override
	public void update() {
		timer++;
		if (currentState == NodeState.READY) {
			currentState = NodeState.RUNNING;
			mob.setState(MobState.ATTACKING);
			mob.weapon.setState(WeaponState.ATTACKING);
			mob.weapon.beginPreAttackAnimations();
		}
		if (timer > mob.weapon.getWeaponAttackBuildup()) {
			mob.weapon.attack(mob.getX(), mob.getY(), blackboard.getPlayerDirection(mob));
			currentState = NodeState.SUCCESS;
			mob.setState(MobState.IDLE);
			mob.weapon.setState(WeaponState.IDLE);
		}
	}
	
}
