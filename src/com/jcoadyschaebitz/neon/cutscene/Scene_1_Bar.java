package com.jcoadyschaebitz.neon.cutscene;

import com.jcoadyschaebitz.neon.cutscene.events.CameraMoveEvent;
import com.jcoadyschaebitz.neon.cutscene.events.CameraMoveEvent.Transition;
import com.jcoadyschaebitz.neon.cutscene.events.EndSceneEvent;
import com.jcoadyschaebitz.neon.cutscene.events.Mob_GoToEvent;
import com.jcoadyschaebitz.neon.cutscene.events.Mob_RenderGun;
import com.jcoadyschaebitz.neon.cutscene.events.Mob_SwitchFacingEvent;
import com.jcoadyschaebitz.neon.cutscene.events.SmoothCameraMoveEvent;
import com.jcoadyschaebitz.neon.cutscene.events.SpeakEvent;
import com.jcoadyschaebitz.neon.cutscene.events.WaitEvent;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Orientation;
import com.jcoadyschaebitz.neon.input.InputManager;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class Scene_1_Bar extends CutScene {

	public Scene_1_Bar(Level level, Mob[] members, InputManager input) {
		super(new Vec2i(5, -2), new Vec2i(15, 10), 5 * 16, 5 * 16, level, members, input);
		script = loadTextFileArray("/scripts/bar.txt");
	}

	protected void initEvents() {
		actors[0] = new Actor(level.getPlayer());
		addEvent(new Mob_GoToEvent(this, 80, 0, actors[0], 17 * 16 + 8, 11 * 16));
		addEvent(new Mob_GoToEvent(this, 0, 0, actors[1], 9 * 16, 16 * 16));
		addEvent(new Mob_GoToEvent(this, 0, 0, actors[2], 11 * 16 - 6, 15 * 16 + 8));
		addEvent(new Mob_SwitchFacingEvent(this, 0, actors[0], Orientation.LEFT));
		addEvent(new SmoothCameraMoveEvent(this, 0, 120, Transition.IN));
		addEvent(new WaitEvent(this, 120, 60));

		addEvent(new CameraMoveEvent(this, 240, 180, currentScreenX, currentScreenY + 6 * 16));

		addEvent(new SpeakEvent(this, 460, 140, actors[1], script[0]));
		addEvent(new SpeakEvent(this, 600, 120, actors[0], script[1]));
		addEvent(new Mob_RenderGun(this, 240, 60, actors[1], Math.PI / -2));

		addEvent(new WaitEvent(this, 420, 60));

		addEvent(new SmoothCameraMoveEvent(this, 680, 120, Transition.OUT));

		addEvent(new EndSceneEvent(this, 800, 0));
	}
}
