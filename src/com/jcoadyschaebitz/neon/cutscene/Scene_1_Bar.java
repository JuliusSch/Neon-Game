package com.jcoadyschaebitz.neon.cutscene;

import com.jcoadyschaebitz.neon.cutscene.events.CameraMoveEvent;
import com.jcoadyschaebitz.neon.cutscene.events.CameraMoveEvent.Transition;
import com.jcoadyschaebitz.neon.cutscene.events.EndSceneEvent;
import com.jcoadyschaebitz.neon.cutscene.events.Mob_GoToEvent;
import com.jcoadyschaebitz.neon.cutscene.events.Mob_RenderGun;
import com.jcoadyschaebitz.neon.cutscene.events.Mob_SpeakEvent;
import com.jcoadyschaebitz.neon.cutscene.events.Mob_SwitchFacingEvent;
import com.jcoadyschaebitz.neon.cutscene.events.SmoothCameraMoveEvent;
import com.jcoadyschaebitz.neon.cutscene.events.WaitEvent;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Orientation;
import com.jcoadyschaebitz.neon.input.Keyboard;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.level.TileCoordinate;

public class Scene_1_Bar extends CutScene {

	public Scene_1_Bar(Level level, Mob[] members, Keyboard input) {
		super(new TileCoordinate(5, -2), new TileCoordinate(15, 10), 5 * 16, 5 * 16, level, members, input);
		script = loadTextFileArray("/scripts/bar.txt");
	}

	protected void initEvents() {
		actors[0] = new Actor(level.getPlayer());
		addEvent(new Mob_GoToEvent(this, 0, 0, actors[0], 17 * 16 + 8, 11 * 16));
		addEvent(new Mob_GoToEvent(this, 0, 0, actors[1], 9 * 16, 16 * 16));
		addEvent(new Mob_GoToEvent(this, 0, 0, actors[2], 11 * 16 - 6, 15 * 16 + 8));
		addEvent(new Mob_SwitchFacingEvent(this, 0, actors[0], Orientation.LEFT));
		addEvent(new WaitEvent(this, 0, 60));

		addEvent(new CameraMoveEvent(this, 120, 180, currentScreenX, currentScreenY + 6 * 16));

		addEvent(new Mob_SpeakEvent(this, 240, 240, actors[1], script[0]));
		addEvent(new Mob_SpeakEvent(this, 480, 120, actors[0], script[1]));
		addEvent(new Mob_RenderGun(this, 240, 60, actors[1], Math.PI / -2));

		addEvent(new WaitEvent(this, 300, 60));

		addEvent(new SmoothCameraMoveEvent(this, 560, 40, currentScreenX, currentScreenY + 8 * 16, Transition.OUT));

		addEvent(new EndSceneEvent(this, 600, 0));
	}
}
