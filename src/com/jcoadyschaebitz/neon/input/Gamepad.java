package com.jcoadyschaebitz.neon.input;

import java.util.HashMap;
import java.util.Map;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.input.InputManager.InputType;
import com.studiohartman.jamepad.ControllerIndex;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

public class Gamepad {
	
	private final float deadZone = 0.1f, lookDeadZone = 0.3f; 		// This should probably be a slider in game settings
	private Map<InputId, Boolean> buttonInputs;
	private Map<InputId, Double> inputs;
	
	ControllerManager controllerManager;
	ControllerIndex currentController;
	
	public enum InputId {
		BUTTON_X,				//	SQUARE		A
		BUTTON_A,				//	X			X
		BUTTON_B,				//	CIRCLE		B
		BUTTON_Y,				//	TRIANGLE	Y
		BUTTON_L1,				//	L1
		BUTTON_R1,				//	R1
		BUTTON_L2,				//	L2
		BUTTON_R2,				//	R2
		BUTTON_8,				//	SHARE
		BUTTON_9,				//	OPTIONS
		BUTTON_L_STICK,			//	LEFT STICK
		BUTTON_R_STICK,			//	RIGHT STICK
		BUTTON_12,				//	PS BUTTON
		BUTTON_13,				//	TOUCHPAD
		BUTTON_14,				//	
		BUTTON_15,				//	
		DPAD_UP,				//	
		DPAD_DOWN,				//	
		DPAD_LEFT,				//	
		DPAD_RIGHT,				//	
		JOYSTICK_LEFT_X,		//	
		JOYSTICK_LEFT_Y,		//	
		JOYSTICK_RIGHT_X,		//	
		JOYSTICK_RIGHT_Y,		//	
//		JOYSTICK_LEFT_ANGLE,	//
		JOYSTICK_RIGHT_ANGLE,	//
		JOYSTICK_LEFT_ACTIVE,	//
		JOYSTICK_RIGHT_ACTIVE;	//
		
		public static InputId fromString(String value) {
	        try {
	            return InputId.valueOf(value.toUpperCase().replace(' ', '_'));
	        } catch (IllegalArgumentException e) {
	            return null;
	        }
	    }
	}

	public Gamepad() {
		inputs = new HashMap<InputId, Double>();
		buttonInputs = new HashMap<InputId, Boolean>();
		
		for (InputId id : InputId.values()) {
			inputs.put(id, 0.0);
			buttonInputs.put(id,  false);
		}

		controllerManager = new ControllerManager();
		controllerManager.initSDLGamepad();
		
		currentController = controllerManager.getControllerIndex(0);
	}
	
	public double getInput(InputId id) {
		return inputs.get(id);
	}
	
	public boolean getButtonInput(InputId id) {
		return buttonInputs.get(id);
	}
	
	public double getButtonInputNum(InputId id) {
		return buttonInputs.get(id) ? 1.0 : 0.0;
	}

	private void updateInput(InputId id, double value) {
		if (value != 0 && Game.getUIManager().getCurrentInputType() != InputType.GAMEPAD)
			Game.getInputManager().setLastUsed(InputType.GAMEPAD);
		else
			inputs.put(id, value);
	}
	
	private void updateButtonInput(InputId id, boolean value) {
		if (value && Game.getUIManager().getCurrentInputType() != InputType.GAMEPAD)
			Game.getInputManager().setLastUsed(InputType.GAMEPAD);
		else
			buttonInputs.put(id, value);
	}
	
	public void update() {
		// Perhaps recheck for controllers periodically to detect newly inserted controllers
		
		// Add management and checking of multiple controllers or if one plugged in
		

		
		ControllerState currentState = controllerManager.getState(0);
		
		if (!currentState.isConnected) {
			System.out.println("Gamepad disconnected");
			// do something
		}
		
		updateButtonInput(InputId.BUTTON_A, currentState.a);
		updateButtonInput(InputId.BUTTON_B, currentState.b);
		updateButtonInput(InputId.BUTTON_X, currentState.x);
		updateButtonInput(InputId.BUTTON_Y, currentState.y);
		
		updateButtonInput(InputId.BUTTON_L1, currentState.lb);
		updateButtonInput(InputId.BUTTON_R1, currentState.rb);
		updateButtonInput(InputId.BUTTON_L2, currentState.leftTrigger > deadZone);
		updateButtonInput(InputId.BUTTON_R2, currentState.rightTrigger > deadZone);
		
		updateButtonInput(InputId.DPAD_LEFT, currentState.dpadLeft);
		updateButtonInput(InputId.DPAD_UP, currentState.dpadUp);
		updateButtonInput(InputId.DPAD_RIGHT, currentState.dpadRight);
		updateButtonInput(InputId.DPAD_DOWN, currentState.dpadDown);
		
		updateButtonInput(InputId.BUTTON_9, currentState.start);
		
		if (currentState.leftStickMagnitude > deadZone) {
			updateButtonInput(InputId.JOYSTICK_LEFT_ACTIVE, true);
//			updateInput(InputId.JOYSTICK_LEFT_ANGLE, Math.toRadians(currentState.leftStickAngle));
			updateInput(InputId.JOYSTICK_LEFT_X, currentState.leftStickX);
			updateInput(InputId.JOYSTICK_LEFT_Y, -currentState.leftStickY);
		} else {
			updateButtonInput(InputId.JOYSTICK_LEFT_ACTIVE, false);
			updateInput(InputId.JOYSTICK_LEFT_X, 0);
			updateInput(InputId.JOYSTICK_LEFT_Y, 0);
		}
		if (currentState.rightStickMagnitude > lookDeadZone) {
			updateButtonInput(InputId.JOYSTICK_RIGHT_ACTIVE, true);
			updateInput(InputId.JOYSTICK_RIGHT_ANGLE, Math.toRadians(currentState.rightStickAngle));
			updateInput(InputId.JOYSTICK_RIGHT_X, currentState.rightStickX);
			updateInput(InputId.JOYSTICK_RIGHT_Y, -currentState.rightStickY);
		} else {
			updateButtonInput(InputId.JOYSTICK_RIGHT_ACTIVE, false);
//			updateInput(InputId.JOYSTICK_RIGHT_X, 0);
//			updateInput(InputId.JOYSTICK_RIGHT_Y, 0);
		}
	}
}
