package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.input.InputManager;

public interface UIComp {
	
	public UIManager uiManager = Game.getUIManager();
	
	public InputManager inputManager = Game.getInputManager();

	public abstract void update();

	public abstract void render(Screen screen);
	
	public abstract void activate();
	
	public abstract void deactivate();
	
//	public void inputTypeChanged(InputType type);
}
