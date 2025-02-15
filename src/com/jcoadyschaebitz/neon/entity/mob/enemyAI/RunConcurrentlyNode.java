package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.entity.mob.Mob;

public class RunConcurrentlyNode extends BehaviourNode {

	private List<BehaviourNode> concurrentNodes = new ArrayList<BehaviourNode>();
	private List<ConcurrentNodeBehaviour> behaviours = new ArrayList<ConcurrentNodeBehaviour>();
	private BehaviourNode primaryNode;
	
	public enum ConcurrentNodeBehaviour {
		ONCE, REPEAT
	}
	
	public RunConcurrentlyNode(AIBlackboard bb, Mob mob, BehaviourNode primaryNode) {
		super(bb, mob);
		this.primaryNode = primaryNode;
	}
	
	public void addNode(BehaviourNode node, ConcurrentNodeBehaviour b) {
		concurrentNodes.add(node);
		behaviours.add(b);
	}
	
	public void softReset() {
		if (currentState == NodeState.SUCCESS || currentState == NodeState.FAILURE) {
			currentState = NodeState.READY;
			for (BehaviourNode node : concurrentNodes) node.softReset();
			primaryNode.softReset();
		}
	}
	
	public void hardReset() {
		currentState = NodeState.READY;
		for (BehaviourNode node : concurrentNodes) node.hardReset();
		primaryNode.hardReset();
	}

	@Override
	public void update() {
		if (currentState == NodeState.READY) {
			currentState = NodeState.RUNNING;
			primaryNode.start();
			for (BehaviourNode node : concurrentNodes) node.start();
		}
		if (currentState == NodeState.RUNNING) {
			primaryNode.update();
			for (BehaviourNode node : concurrentNodes) node.update();
			if (primaryNode.currentState == NodeState.SUCCESS) currentState = NodeState.SUCCESS;
			if (primaryNode.currentState == NodeState.FAILURE) currentState = NodeState.FAILURE;
			if (primaryNode.currentState == NodeState.RUNNING) currentState = NodeState.RUNNING;
			for (int i = 0; i < concurrentNodes.size(); i++) if (behaviours.get(i) == ConcurrentNodeBehaviour.REPEAT) concurrentNodes.get(i).softReset();
		}
	}

}
