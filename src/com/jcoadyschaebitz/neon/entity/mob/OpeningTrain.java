package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.BehaviourNode.NodeState;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.Move;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SequencerNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.VehicleAccelerate;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.WaitBehaviour;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class OpeningTrain extends Mob {

	public OpeningTrain(int x, int y) {
		super(x, y);
		sprite = Sprite.openingTrain;
	}
	
	public void update() {
		time++;
		if (!blackboard.beenInitted) blackboard.init(level);
//		updateAnimSprites();
		if (behaviours.getState() == NodeState.SUCCESS || behaviours.getState() == NodeState.FAILURE) behaviours.softReset();
		behaviours.update();
		checkIfDead();
	}

	
	public boolean move(double xa, double ya, boolean stairChange) {
		x += xa;
		y += ya;
		return true;
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
		SequencerNode pullOff = new SequencerNode(bb, this);
		pullOff.addNode(new WaitBehaviour(bb, this, 1280));
		pullOff.addNode(new VehicleAccelerate(bb, this, 180, 6, 2, new Vec2i((int) x, (int) y), new Vec2i((int) x + 16 * 16, (int) y)));
		pullOff.addNode(new Move(bb, this, Math.atan2(0, 1), 240, 6));
		behaviours.addNode(pullOff);
	}
	
	public void render(Screen screen) {
//		sprite = currentAnim.getSprite();
//		screen.renderTranslucentSprite((int) x - 3, (int) y + 3, Sprite.shadow, true, 0.6);
		screen.renderSprite((int) x, (int) y, getSprite(), true);
//		font.render((int) x, (int) y, state.toString(), screen, true);
	}

}
