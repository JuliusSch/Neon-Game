package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

public class InverterNode extends DecoratorNode {
	
	public InverterNode(BehaviourNode node) {
		super(node);
	}

	@Override
	public void update() {
		node.update();
		switch (node.currentState) {
		case RUNNING:
			currentState = NodeState.RUNNING;
		case SUCCESS:
			currentState = NodeState.FAILURE;
		case FAILURE:
			currentState = NodeState.SUCCESS;
		default:
			currentState = NodeState.FAILURE;
		}
	}

}
