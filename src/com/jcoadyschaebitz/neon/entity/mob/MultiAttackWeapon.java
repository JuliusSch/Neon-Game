package com.jcoadyschaebitz.neon.entity.mob;

public interface MultiAttackWeapon {

//	protected AnimatedSprite SecondaryAttackAnimation, TertiaryAttackAnimation;
	
	public void secondaryAttack(double x, double y, double direction);
	
	public void tertiaryAttack(double x, double y, double direction);
	
}
