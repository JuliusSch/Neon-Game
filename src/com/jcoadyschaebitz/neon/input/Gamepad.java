package com.jcoadyschaebitz.neon.input;

import java.util.HashMap;
import java.util.Map;

import org.libsdl.SDL_Error;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.input.InputManager.InputType;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.utils.Array;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;

public class Gamepad {
	
	private final float deadZone = 0.1f, lookDeadZone = 0.3f; 		// This should probably be a slider in game settings
	private Map<InputId, Boolean> buttonInputs;
	private Map<InputId, Double> inputs;
	private Controller selectedController;
	private int time = 0;
	private InputManager inputManager;

	SDL2ControllerManager controllerManager;
	Array<Controller> controllers;
	
	private enum GamepadAxes {
		LEFT_STICK_X,
		LEFT_STICK_Y,
		RIGHT_STICK_X,
		RIGHT_STICK_Y
	}
	
	public enum InputId {
		BUTTON_A,				//	X			A
		BUTTON_B,				//	CIRCLE		B
		BUTTON_X,				//	SQUARE		X
		BUTTON_Y,				//	TRIANGLE	Y
		SELECT,
		GUIDE,
		START,
		BUTTON_L_STICK,			//	LEFT STICK
		BUTTON_R_STICK,			//	RIGHT STICK
		BUTTON_L1,				//	L1
		BUTTON_R1,				//	R1
		DPAD_UP,				//	
		DPAD_DOWN,				//	
		DPAD_LEFT,				//	
		DPAD_RIGHT,				//
		MISC_1,
		BUTTON_L2,				//	L2
		BUTTON_R2,				//	R2
		BUTTON_L3,
		BUTTON_R3,
		TOUCHPAD,
		MAX,
//		BUTTON_8,				//	SHARE
//		BUTTON_9,				//	OPTIONS
//		BUTTON_12,				//	PS BUTTON
//		BUTTON_13,				//	TOUCHPAD
//		BUTTON_14,				//	
//		BUTTON_15,				//	
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

	public Gamepad(InputManager manager) {
		inputs = new HashMap<InputId, Double>();
		buttonInputs = new HashMap<InputId, Boolean>();
		inputManager = manager;
		
		for (InputId id : InputId.values()) {
			inputs.put(id, 0.0);
			buttonInputs.put(id,  false);
		}

		controllerManager = new SDL2ControllerManager();
		controllers = controllerManager.getControllers();
		if (controllers.size > 0)
			selectedController = controllers.get(0);
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
			inputManager.setLastUsed(InputType.GAMEPAD);
		else
			inputs.put(id, value);
	}
	
	private void updateButtonInput(InputId id, boolean value) {
		if (value != buttonInputs.get(id)) {
			if (Game.getUIManager().getCurrentInputType() != InputType.GAMEPAD)
				inputManager.setLastUsed(InputType.GAMEPAD);
			else
				buttonInputs.put(id, value);
		}
	}
	
	private void refreshControllers() {
		controllers = controllerManager.getControllers();
		if (controllers.size > 0) {
				selectedController = controllers.get(0);			
		}
	}
	
	public void update() {
		time++;
		if (time > 60) {
			time = 0;
			refreshControllers();
		}
		
		try {
			controllerManager.pollState();
		} catch (SDL_Error e) {
			e.printStackTrace();
		}
		
		// Must be after poll state where new controllers are detected
		if (selectedController == null)
			return;		
		
		for (InputId id : InputId.values()) {
			updateButtonInput(id, selectedController.getButton(id.ordinal()));
		}

		// Add axis enum codes
		
		float leftStickX = selectedController.getAxis(GamepadAxes.LEFT_STICK_X.ordinal());
		float leftStickY = selectedController.getAxis(GamepadAxes.LEFT_STICK_Y.ordinal());
		float rightStickX = selectedController.getAxis(GamepadAxes.RIGHT_STICK_X.ordinal());
		float rightStickY = selectedController.getAxis(GamepadAxes.RIGHT_STICK_Y.ordinal());
		
		double joystickRightAngle = Math.atan2(rightStickY, rightStickX);
		
		double leftStickMagnitude = Math.sqrt(leftStickX * leftStickX + leftStickY * leftStickY);
		double rightStickMagnitude = Math.sqrt(rightStickX * rightStickX + rightStickY * rightStickY);
		
		if (leftStickMagnitude > deadZone) {
			updateButtonInput(InputId.JOYSTICK_LEFT_ACTIVE, true);
			updateInput(InputId.JOYSTICK_LEFT_X, leftStickX);
			updateInput(InputId.JOYSTICK_LEFT_Y, leftStickY);
		} else {
			updateButtonInput(InputId.JOYSTICK_LEFT_ACTIVE, false);
			updateInput(InputId.JOYSTICK_LEFT_X, 0);
			updateInput(InputId.JOYSTICK_LEFT_Y, 0);
		}
		
		if (rightStickMagnitude > lookDeadZone) {
			updateButtonInput(InputId.JOYSTICK_RIGHT_ACTIVE, true);
			updateInput(InputId.JOYSTICK_RIGHT_ANGLE, joystickRightAngle);
			updateInput(InputId.JOYSTICK_RIGHT_X, rightStickX);
			updateInput(InputId.JOYSTICK_RIGHT_Y, rightStickY);
		} else {
			updateButtonInput(InputId.JOYSTICK_RIGHT_ACTIVE, false);
//			updateInput(InputId.JOYSTICK_RIGHT_X, 0);
//			updateInput(InputId.JOYSTICK_RIGHT_Y, 0);
		}		
	}
}
