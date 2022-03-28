package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.graphics.Screen;

public interface UIComp {
	
	public UIManager ui = Game.getUIManager();

	public abstract void update();

	public abstract void render(Screen screen);
	
	public abstract void activate();
	
	public abstract void deactivate();
	
}
