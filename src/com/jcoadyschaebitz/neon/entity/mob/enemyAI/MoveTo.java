package com.jcoadyschaebitz.neon.entity.mob.enemyAI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Mob.MobState;
import com.jcoadyschaebitz.neon.util.GridGraph2b;
import com.jcoadyschaebitz.neon.util.Node;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class MoveTo extends BehaviourNode {

	public MoveTo(AIBlackboard bb, Mob mob) {
		super(bb, mob);
	}

	private double xa, ya, direction;
	private int targetX, targetY, predTravelTime;
	private Vec2i currentNodeDest;
	private Stack<Vec2i> path;
	private boolean nodeReached;

	@Override
	public void update() {
		timer++;
		if (currentState == NodeState.READY) {
			if (blackboard.getMoveToPos() == null) currentState = NodeState.FAILURE;
			else {
				currentState = NodeState.RUNNING;
				targetX = blackboard.getMoveToPos().x;
				targetY = blackboard.getMoveToPos().y;
				path = findPath(new Vec2i(mob.getMidX() >> 4, (mob.getMidY()) >> 4), new Vec2i(targetX >> 4, targetY >> 4));
				if (path.size() == 0) currentState = NodeState.FAILURE;
			}
		}
		if (currentState == NodeState.RUNNING) {
			mob.walking = true;
			if (currentNodeDest == null || nodeReached) {
				nodeReached = false;
				if (path.size() != 0) {
					currentNodeDest = path.pop();
					if ((currentNodeDest.y << 4) - (mob.getIntY()) == 0 && (currentNodeDest.x << 4) - mob.getIntX() == 0) nodeReached = true;
					direction = Math.atan2((currentNodeDest.y << 4) - (mob.getIntY()), (currentNodeDest.x << 4) - mob.getIntX());
					xa = Math.cos(direction) * mob.speed;
					ya = Math.sin(direction) * mob.speed;
					predTravelTime = (int) (timer + 15);
				} else currentState = NodeState.SUCCESS;
			}
			mob.move(xa, ya, true);
			if ((mob.getIntX() == currentNodeDest.x << 4 && mob.getIntY() == currentNodeDest.y << 4) || predTravelTime < timer) {
//				blackboard.getLevel().add(new DebugParticle(currentNodeDest.X() << 4, currentNodeDest.Y() << 4, 200, 4, 4, Sprite.particleYellow));
				nodeReached = true;
			}
		}
		if ((Math.abs(mob.getIntX() - targetX) <= 4) && (Math.abs(mob.getIntY() - targetY) <= 4)) currentState = NodeState.SUCCESS;// consider failure state if wall encountered
		if (currentState == NodeState.SUCCESS) mob.walking = false;
		if (currentState == NodeState.FAILURE) mob.walking = false;
	}

	protected void resetNode() {
		super.resetNode();
		xa = 0;
		ya = 0;
		direction = 0;
		targetX = 0;
		targetY = 0;
		predTravelTime = 0;
		currentNodeDest = null;
		path = null;
		nodeReached = false;
	}

	public class CostComparator implements Comparator<Node> {

		public int compare(Node n1, Node n2) {
			double c1 = n1.getCost();
			double c2 = n2.getCost();
			return c1 < c2 ? -1 : (c1 == c2) ? 0 : 1;
		}
	}

	public Stack<Vec2i> findPath(Vec2i start, Vec2i goal) {
		int gridW = 31;
		int gridH = 31;
		boolean[] subMap = blackboard.getLevel().getSubAICollisionMap(start.x - 16, start.y - 16, gridW, gridH);
		GridGraph2b graph = new GridGraph2b(subMap, gridW, gridH);
		Stack<Vec2i> path = new Stack<Vec2i>();

		List<Node> closedList = new ArrayList<Node>();
		Queue<Node> openQueue = new PriorityQueue<Node>(new CostComparator());
		Node current = new Node(start, null, 0, Vec2i.getDistance(start, goal));
		openQueue.add(current);

		while (openQueue.size() > 0) {
			current = openQueue.remove();
			if (current.equals(goal)) {
//				for (Node s : closedList) blackboard.getLevel().add(new DebugParticle((s.X()) << 4, (s.Y()) << 4, 200, 4, 4, Sprite.item_slot));
				while (current.getLink() != null) {
//					blackboard.getLevel().add(new DebugParticle(current.X() << 4, current.Y() << 4, 200, 4, 4, Sprite.item_slot_outline));
					path.add(current);
					current = current.getLink();
				}
				openQueue.clear();
				closedList.clear();
				return path;
			}
			Vec2i offset = new Vec2i(16, 16).subtract(start);
			Map<Vec2i, Double> neighbours = graph.getNeighbours(current.add(offset));
			for (Vec2i next : neighbours.keySet()) {
				double nextSCost = current.costFromStart + neighbours.get(next);
				next = next.subtract(offset);
				double hCost = Vec2i.getDistance(next, goal);
				Node newNode = new Node(next, current, nextSCost, hCost);
				Node openNode = getMember(openQueue, newNode);
				Node closedNode = getMember(closedList, newNode);
				if (openNode != null) {														// If adjacent node in open queue
					if (newNode.costFromStart > openNode.costFromStart) continue;
				} else if (closedNode != null) {											// If adjacent node in closed list
					if (newNode.costFromStart < closedNode.costFromStart) {
						closedList.remove(closedNode);
						openQueue.add(newNode);
					}
				} else openQueue.add(newNode);
			}
			closedList.add(current);
		}
		return path;
	}

	public Node getMember(Queue<Node> set, Vec2i v) {
		for (Node n : set) if (n.equals(v)) return n;
		return null;
	}

	public Node getMember(List<Node> set, Vec2i v) {
		for (Node n : set) if (n.equals(v)) return n;
		return null;
	}

	public void start() {
		mob.setState(MobState.WALKING);
	}

}
