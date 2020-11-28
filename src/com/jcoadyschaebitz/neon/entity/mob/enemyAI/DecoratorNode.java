package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

public abstract class DecoratorNode extends BehaviourNode {
	
	BehaviourNode node;
	
	public DecoratorNode(BehaviourNode node) {
		super(node.blackboard, node.mob);
		this.node = node;
	}

}
