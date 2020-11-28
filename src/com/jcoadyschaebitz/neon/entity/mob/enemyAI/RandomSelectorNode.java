package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.entity.mob.Mob;

public class RandomSelectorNode extends BehaviourNode {

	private List<BehaviourNode> childNodes = new ArrayList<BehaviourNode>();
	private List<Double> nodeWeightings = new ArrayList<Double>();
	private BehaviourNode currentNode;
	private double totalWeight;
	
	public RandomSelectorNode(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}

	public void addNode(BehaviourNode node, double weighting) {
		childNodes.add(node);
		nodeWeightings.add(weighting);
		totalWeight += weighting;
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
			double rand = Math.random() * totalWeight;
			for (int i = 0; i < nodeWeightings.size(); i++) {
				rand -= nodeWeightings.get(i);
				if (rand <= 0) {
					currentNode = childNodes.get(i);
					break;
				}
			}
		}
		if (currentNode == null) return;
		currentNode.update();
		if (currentNode.currentState == NodeState.SUCCESS) currentState = NodeState.SUCCESS;
		if (currentNode.currentState == NodeState.FAILURE) currentState = NodeState.FAILURE;
		if (currentNode.currentState == NodeState.RUNNING) currentState = NodeState.RUNNING;
	}
	
}
