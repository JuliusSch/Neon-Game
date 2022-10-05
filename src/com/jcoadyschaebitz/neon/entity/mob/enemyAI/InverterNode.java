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
			break;
		case SUCCESS:
			currentState = NodeState.FAILURE;
			break;
		case FAILURE:
			currentState = NodeState.SUCCESS;
			break;
		default:
			currentState = NodeState.FAILURE;
			break;
		}
	}

}
