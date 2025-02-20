package com.jcoadyschaebitz.neon.input;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.input.Gamepad.InputId;
import com.jcoadyschaebitz.neon.util.Vec2d;

public class InputManager implements WindowFocusListener {
	
	private enum CursorType {
		CROSSHAIR, GAMEPAD/* , MENU */
	}
	
	public enum InputType {
		KEYBOARD, GAMEPAD
	}
	
	public enum InputAction {
		PRIMARY_ACTION,
		SECONDARY_ACTION,
		MOVE_LEFT, MOVE_UP,
		MOVE_RIGHT,
		MOVE_DOWN,
		LOOK_LEFT,
		LOOK_UP,
		LOOK_RIGHT,
		LOOK_DOWN,
		LOOK_DIRECTION_X,
		LOOK_DIRECTION_Y,
		LOOK_DIRECTION,
		PAUSE,
		SHIELD,
		MENU_LEFT,
		MENU_UP,
		MENU_RIGHT,
		MENU_DOWN,
		MENU_SELECT,
		CYCLE_ITEM_LEFT,
		CYCLE_ITEM_RIGHT
	}
	
	private Keyboard keyboard;
	private Gamepad gamepad;
	private List<IInputObserver> inputObservers;
	private double[] inputValues;
	private boolean[] updatedInputValues;
	private double mouseX, mouseY;
	private InputType lastUsedInputType;
	private boolean isFocussed;
	private Game game;
	
	private Map<CursorType, Cursor> cursors = new HashMap<CursorType, Cursor>();
	
	public InputManager(Game game) {
		keyboard = new Keyboard(this);
		gamepad = new Gamepad(this);
		this.game = game;
		inputObservers = new ArrayList<IInputObserver>();
		inputValues = new double[InputAction.values().length];
		updatedInputValues = new boolean[inputValues.length];
		game.addKeyListener(keyboard);
		initialiseCursors();
	}
	
	public void update() {
		keyboard.update();
		gamepad.update();
		updateInputs();
		notifyObservers();
		
		// Should probably be somewhere else
		if (lastUsedInputType == InputType.KEYBOARD) {
			for (int i = 0; i < keyboard.numbers.length; i++) {
				if (keyboard.numbers[i] && Game.getUIManager().slots.get(i - 1).hasItem)
					Game.getUIManager().selectedItemSlot = i - 1;
			}
		}
		
		if (lastUsedInputType == InputType.GAMEPAD && !gamepad.getButtonInput(InputId.JOYSTICK_RIGHT_ACTIVE))
			lerpGamepadCursorToCentre();
	}
	
	public int mouseX() {
		return (int) mouseX;
	}
	
	public int mouseY() {
		return (int) mouseY;
	}
	
	public double getMouseXRelMid() {
		return mouseX - (Game.getWindowWidth() / 2);
	}
	
	public double getMouseXRelMidWithBars() {
		return (mouseX - (Game.getWindowWidth() / 2) - Game.getXBarsOffset()) * 0.95; // Trying to correct error in raycasting. Seems to work but confirmed jank
	}
	
	public double getMouseYRelMid() {
		return (mouseY - (Game.getWindowHeight() / 2));
	}

	public boolean isInput(InputAction action) {
		return getInput(action) != 0;
	}
	
	public void registerObserver(IInputObserver observer) {
		inputObservers.add(observer);
	}
	
	public void removeObserver(IInputObserver observer) {
		inputObservers.remove(observer);
	}
	
	public double getInput(InputAction action) {
		return inputValues[action.ordinal()];
	}
	
	private void notifyObservers() {
		for(int i = 0; i < updatedInputValues.length; i++) {
			if (updatedInputValues[i]) {
				for (IInputObserver observer : inputObservers)
					observer.InputReceived(InputAction.values()[i], inputValues[i]);
				
				updatedInputValues[i] = false;
			}
		}
	}
	
	private void updateValue(InputAction action, double value) {
//		if (lastUsedInputType == InputType.KEYBOARD)
		int i = action.ordinal();
		if (inputValues[i] != value) {
			if (value == 0 && (action == InputAction.LOOK_UP || action == InputAction.LOOK_DOWN || action == InputAction.LOOK_LEFT || action == InputAction.LOOK_RIGHT))
				return;
			
			updatedInputValues[i] = true;
			inputValues[i] = value;
		} else
			updatedInputValues[i] = false;
	}
	
	public void setLastUsed(InputType type) {
		if (type != lastUsedInputType) {
			lastUsedInputType = type;
			Game.getUIManager().inputTypeChanged(type);
		}
		if (type == InputType.GAMEPAD)
			setCursor(CursorType.GAMEPAD);
		else
			setCursor(CursorType.CROSSHAIR);
	}
	
	private void updateInputs() {
		updateValue(InputAction.SECONDARY_ACTION, keyboard.E ? 1f : gamepad.getButtonInputNum(InputId.BUTTON_Y));
		updateValue(InputAction.PRIMARY_ACTION, Math.max(Mouse.getB(), gamepad.getButtonInputNum(InputId.BUTTON_R1)));
		updateValue(InputAction.PAUSE, keyboard.esc ? 1f : gamepad.getButtonInputNum(InputId.START));
		updateValue(InputAction.SHIELD, keyboard.F ? 1f : gamepad.getButtonInputNum(InputId.BUTTON_L1));
		updateMoveInputs();
		
		if (lastUsedInputType == InputType.GAMEPAD && gamepad.getButtonInput(InputId.JOYSTICK_RIGHT_ACTIVE)) {
			double newX = mouseX + gamepad.getInput(InputId.JOYSTICK_RIGHT_X) * 10;
			double newY = mouseY + gamepad.getInput(InputId.JOYSTICK_RIGHT_Y) * 10;
			setMousePos((int) newX, (int) newY);
		} else if (lastUsedInputType == InputType.KEYBOARD) {
			double newX = Mouse.getX();
			double newY = Mouse.getY();
			setMousePos((int) newX, (int) newY);
		}
		
		// Combine menu and move axes at some point?
		updateValue(InputAction.MENU_LEFT, gamepad.getButtonInputNum(InputId.DPAD_LEFT));
		updateValue(InputAction.MENU_UP, gamepad.getButtonInputNum(InputId.DPAD_UP));
		updateValue(InputAction.MENU_RIGHT, gamepad.getButtonInputNum(InputId.DPAD_RIGHT));
		updateValue(InputAction.MENU_DOWN, gamepad.getButtonInputNum(InputId.DPAD_DOWN));
		
		updateValue(InputAction.MENU_SELECT, gamepad.getButtonInputNum(InputId.BUTTON_A));

		updateValue(InputAction.LOOK_LEFT, Math.min(mouseX, 0));
		updateValue(InputAction.LOOK_UP, Math.min(mouseY, 0));
		updateValue(InputAction.LOOK_RIGHT, Math.max(mouseX, 0));
		updateValue(InputAction.LOOK_DOWN, Math.max(mouseY, 0));
		
		updateValue(InputAction.LOOK_DIRECTION_X, gamepad.getInput(InputId.JOYSTICK_RIGHT_X));
		updateValue(InputAction.LOOK_DIRECTION_Y, gamepad.getInput(InputId.JOYSTICK_RIGHT_Y));
		updateValue(InputAction.LOOK_DIRECTION, gamepad.getInput(InputId.JOYSTICK_RIGHT_ANGLE));

		updateValue(InputAction.CYCLE_ITEM_LEFT, gamepad.getButtonInputNum(InputId.DPAD_LEFT));
		updateValue(InputAction.CYCLE_ITEM_RIGHT, gamepad.getButtonInputNum(InputId.DPAD_RIGHT));
	}
	
	private void updateMoveInputs() {
		// make sure these are actually consistent between keyboard and controller
		
		double xa, ya, magnitude;
		if (lastUsedInputType == InputType.GAMEPAD) {
			xa = gamepad.getInput(InputId.JOYSTICK_LEFT_X);
			ya = gamepad.getInput(InputId.JOYSTICK_LEFT_Y);
		} else {
			xa = keyboard.left && keyboard.right ? 0 : keyboard.left ? -1f : keyboard.right ? 1f : 0;
			ya = keyboard.up && keyboard.down ? 0 : keyboard.up ? -1f : keyboard.down ? 1f : 0;
		}
		magnitude = Math.sqrt(xa * xa + ya * ya);
		magnitude = Math.max(1f, magnitude);
		xa /= magnitude;
		ya /= magnitude;
		updateValue(InputAction.MOVE_UP, ya < 0 ? ya : 0);
		updateValue(InputAction.MOVE_DOWN, ya > 0 ? ya : 0);
		updateValue(InputAction.MOVE_LEFT, xa < 0 ? xa : 0);
		updateValue(InputAction.MOVE_RIGHT, xa > 0 ? xa : 0);
	}
	
	public void setInMenu(boolean inMenu) {
		if (lastUsedInputType == InputType.GAMEPAD)
			setCursor(CursorType.GAMEPAD);
		else if (lastUsedInputType == InputType.KEYBOARD)
			setCursor(CursorType.CROSSHAIR);			
	}
	
	public void setMousePos(int x, int y) {
		x = Math.min(x, Game.getWindowWidth() + Game.getXBarsOffset());
		x = Math.max(x, Game.getXBarsOffset());
		y = Math.min(y, Game.getWindowHeight());
		y = Math.max(y, 0);
		mouseX = x;
		mouseY = y;
		
		if (isFocussed)
			Mouse.move((int) mouseX, (int) mouseY);
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		isFocussed = true;
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		isFocussed = false;
	}
	
	private void initialiseCursors() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		Image img = toolkit.getImage(this.getClass().getResource("/textures/UI/customCursor.png"));
		Point point = new Point(16, 16);
		cursors.put(CursorType.CROSSHAIR, toolkit.createCustomCursor(img, point, "customCursor"));

		Image img3 = toolkit.getImage(this.getClass().getResource("/textures/UI/blankCursor.png"));
		Point point3 = new Point(0, 0);
		cursors.put(CursorType.GAMEPAD, toolkit.createCustomCursor(img3, point3, "customCursorMenu"));
		
		setCursor(CursorType.CROSSHAIR);
	}
	
	private void setCursor(CursorType type) {
//		if (type == CursorType.GAMEPAD)
//			return;
		game.setCursor(cursors.get(type));
	}
	
	private void lerpGamepadCursorToCentre() {

		Vec2d position = new Vec2d(getMouseXRelMidWithBars() / 0.95, getMouseYRelMid());
		double distance = Math.sqrt(position.x * position.x + position.y * position.y);
		
        if (distance < 128) // range from player
        	return;
		
		double panFactor = 0.98;

	    int finalX = (int) (Game.getWindowWidth() * 0.5 + Game.getXBarsOffset() + 0.5 + position.x * panFactor);
	    int finalY = (int) (Game.getWindowHeight() * 0.5 + 0.5 + position.y * panFactor);

	    setMousePos(finalX, finalY);
	}
}