package com.jcoadyschaebitz.neon.input;

import com.jcoadyschaebitz.neon.input.InputManager.InputAction;

public interface IInputObserver {
	
	public void InputReceived(InputAction input, double value);
	
}
