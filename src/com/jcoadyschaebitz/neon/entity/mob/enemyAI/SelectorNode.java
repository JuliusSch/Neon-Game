package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.entity.mob.Mob;

public class SelectorNode extends BehaviourNode {
	
	private List<BehaviourNode> childNodes = new ArrayList<BehaviourNode>();
	private BehaviourNode currentNode;
	private int childNodeIter;
	
	public SelectorNode(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}

	public void addNode(BehaviourNode node) {
		childNodes.add(node);
	}
	
	public void softReset() {
		if (currentState == NodeState.SUCCESS || currentState == NodeState.FAILURE) {
			currentState = NodeState.READY;
			for (BehaviourNode node : childNodes) node.softReset();
		}
	}
	
	public void hardReset() {
		currentState = NodeState.READY;
		for (BehaviourNode node : childNodes) node.hardReset();
	}

	@Override
	public void update() {
		if ((currentNode == null || currentState == NodeState.READY) && childNodes.size() > 0) {
			childNodeIter = 0;
			currentNode = childNodes.get(0);
			currentNode.start();
		}
		if (currentNode == null) return;
		currentNode.update();
		if (currentNode.currentState == NodeState.SUCCESS) currentState = NodeState.SUCCESS;
		if (currentNode.currentState == NodeState.RUNNING) currentState = NodeState.RUNNING;
		if (currentNode.currentState == NodeState.FAILURE) {
			if (childNodeIter < childNodes.size() - 1) {
				currentNode = childNodes.get(++childNodeIter);
				currentNode.start();
				currentState = NodeState.RUNNING;
			} else currentState = NodeState.FAILURE;
		}
	}
}
