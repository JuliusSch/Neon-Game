package com.jcoadyschaebitz.neon.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.input.InputManager.InputType;
import com.jcoadyschaebitz.neon.util.Vec2d;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class Gamepad {
	
	private final float deadZone = 0.1f; 		// This should probably be a slider in game settings
	private double lastJoystickMagnitude = 0, lastJoystickDirection = 0;
	private Vec2d lastJoystickDirectionVector = new Vec2d();
	
	private List<Controller> gamepads;
	private Map<InputId, Double> inputs;
	
	public enum InputId {
		BUTTON_0,			//	SQUARE
		BUTTON_1,			//	X
		BUTTON_2,			//	CIRCLE
		BUTTON_3,			//	TRIANGLE
		BUTTON_4,			//	L1
		BUTTON_5,			//	R1
		BUTTON_6,			//	L2
		BUTTON_7,			//	R2
		BUTTON_8,			//	SHARE
		BUTTON_9,			//	OPTIONS
		BUTTON_10,			//	LEFT STICK
		BUTTON_11,			//	RIGHT STICK
		BUTTON_12,			//	PS BUTTON
		BUTTON_13,			//	TOUCHPAD
		BUTTON_14,			//	
		BUTTON_15,			//	
		DPAD_UP,			//	
		DPAD_DOWN,			//	
		DPAD_LEFT,			//	
		DPAD_RIGHT,			//	
		JOYSTICK_LEFT_X,	//	
		JOYSTICK_LEFT_Y,	//	
		JOYSTICK_RIGHT_X,	//	
		JOYSTICK_RIGHT_Y;	//	
		
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
		
		for (InputId id : InputId.values()) {
			inputs.put(id, 0.0);
		}
		
		gamepads = new ArrayList<Controller>();
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for (Controller controller : controllers) {
			if (controller.getType() == Type.GAMEPAD)
				gamepads.add(controller);
			
//            System.out.println("Controller: " + controller.getName() + " - Type: " + controller.getType());
        }
	}
	
	public double getInput(InputId id) {
		return inputs.get(id);
	}
	
	public double getLastJoystickDirection() {
		return lastJoystickDirection;
	}
	
	public Vec2d getLastJoystickDirectionVector() {
		return lastJoystickDirectionVector;
	}
		
	private void updateInput(InputId id, double value) {
		if (value != 0 && Game.getUIManager().getCurrentInputType() != InputType.GAMEPAD)
			Game.getInputManager().setLastUsed(InputType.GAMEPAD);
		else
			inputs.put(id, value);
	}
	
	public void update(double deltaTime) {
		// Perhaps recheck for controllers periodically to detect newly inserted controllers
		
		Event event = new Event();
		for (Controller gamepad : gamepads) {
			gamepad.poll();
			
			EventQueue queue = gamepad.getEventQueue();

			while (queue.getNextEvent(event)) {
				Component comp = event.getComponent();
				Identifier componentIdentifier = comp.getIdentifier();
				
				// BUTTON
				if (componentIdentifier instanceof Button) {
					InputId id = InputId.fromString(comp.toString());
					if (id != null) {
						updateInput(id, comp.getPollData());
					}
					
				// D-PAD
				} else if (componentIdentifier == Axis.POV) {
					float value = comp.getPollData();
					if (value == 0.25f)
						updateInput(InputId.DPAD_UP, 1f);
					else if (value == 0.5f)
						updateInput(InputId.DPAD_RIGHT, 1f);
					else if (value == 0.75f)
						updateInput(InputId.DPAD_DOWN, 1f);
					else if (value == 1.0f)
						updateInput(InputId.DPAD_LEFT, 1f);
					else {
						updateInput(InputId.DPAD_UP, 0f);
						updateInput(InputId.DPAD_DOWN, 0f);
						updateInput(InputId.DPAD_LEFT, 0f);
						updateInput(InputId.DPAD_RIGHT, 0f);
					}
				// JOYSTICK
				} else if (comp.isAnalog()) {
					String id = comp.getIdentifier().toString().toUpperCase();
					float putValue = 0f;

					if (Math.abs(comp.getPollData()) > deadZone)
						putValue = comp.getPollData();
					
					switch (id) {
					case "X":
						updateInput(InputId.JOYSTICK_LEFT_X, putValue);
						break;
					case "Y":
						updateInput(InputId.JOYSTICK_LEFT_Y, putValue);
						break;
					case "Z":
						updateInput(InputId.JOYSTICK_RIGHT_X, putValue);
						break;
					case "RZ":
						updateInput(InputId.JOYSTICK_RIGHT_Y, putValue);
						break;
					}
				}
			}
			
			setJoystickDirection(deltaTime);
		}
	}
	
	private void setJoystickDirection(double deltaTime) {
//		Vec2d dirVec = new Vec2d(getInput(InputId.JOYSTICK_RIGHT_X), getInput(InputId.JOYSTICK_RIGHT_Y));
//		double magnitude = dirVec.magnitude();
//		
//		if (magnitude > lastJoystickMagnitude) {
//			
//			double smoothingFactor = 0.2 /* / (5 * deltaTime) */; // Lower = smoother, higher = more responsive
//			Vec2d smoothedDirection = new Vec2d(
//			    lastJoystickDirectionVector.x * (1 - smoothingFactor) + dirVec.x * smoothingFactor,
//			    lastJoystickDirectionVector.y * (1 - smoothingFactor) + dirVec.y * smoothingFactor
//			);
//	
//			lastJoystickDirectionVector = smoothedDirection;
//			lastJoystickDirection = Math.atan2(lastJoystickDirectionVector.y, lastJoystickDirectionVector.x);
//		}
//		lastJoystickMagnitude = magnitude;
	}
}
