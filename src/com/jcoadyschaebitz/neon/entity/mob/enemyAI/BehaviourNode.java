package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import com.jcoadyschaebitz.neon.entity.mob.Mob;

public abstract class BehaviourNode {

	protected Mob mob;
	protected AIBlackboard blackboard;
	protected BehaviourNode child;
	protected NodeState currentState;
	protected int timer, interTimer;

	public BehaviourNode(AIBlackboard bb, Mob mob) {
		blackboard = bb;
		this.mob = mob;
		currentState = NodeState.READY;
	}
	
	public enum NodeState {
		SUCCESS, FAILURE, RUNNING, READY
	}	
	
	public void softReset() {
		if (currentState == NodeState.SUCCESS || currentState == NodeState.FAILURE) resetNode();
	}
	
	public void hardReset() {
		resetNode();
	}
	
	protected void resetNode() {
		currentState = NodeState.READY;
		timer = 0;
	}

	public abstract void update();
	
	public NodeState getState() {
		return currentState;
	}
		
	public void start() {
	}

}
//probably need start and end methods -- for example in soldierShoot to create a projectile object.

// instead of being marked done, nodes are marked as running, so each update the tree is traversed to find the
// running node, update it, then when it completes move to the next. navigation is done with liberal use of 
// conditional nodes.