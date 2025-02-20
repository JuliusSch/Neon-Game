package com.jcoadyschaebitz.neon.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.jcoadyschaebitz.neon.input.InputManager.InputType;

public class Keyboard implements KeyListener {
	
	public InputManager inputManager;
	
	public Keyboard(InputManager manager) {
		inputManager = manager;
	}

	private boolean[] keys = new boolean[512];
	public boolean up, down, left, right, F, esc, E, tab;
	public boolean[] numbers = new boolean[10];
	public String[] numbersString = {"zero", "one", "two", "three", "four", "five", "six", "seven", "seven", "eight", "nine"};

	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		F = keys[KeyEvent.VK_F];
		esc = keys[KeyEvent.VK_ESCAPE];
		tab = keys[KeyEvent.VK_TAB];
		E = keys[KeyEvent.VK_E];
		numbers[0] = keys[KeyEvent.VK_0];
		numbers[1] = keys[KeyEvent.VK_1];
		numbers[2] = keys[KeyEvent.VK_2];
		numbers[3] = keys[KeyEvent.VK_3];
		numbers[4] = keys[KeyEvent.VK_4];
		numbers[5] = keys[KeyEvent.VK_5];
		numbers[6] = keys[KeyEvent.VK_6];
		numbers[7] = keys[KeyEvent.VK_7];
		numbers[8] = keys[KeyEvent.VK_8];
		numbers[9] = keys[KeyEvent.VK_9];
	}
	
	public void keyPressed(KeyEvent e) {
		setKeyCodeValue(e.getKeyCode(), true);
		inputManager.setLastUsed(InputType.KEYBOARD);		
	}
	
	private void setKeyCodeValue(int code, boolean value) {
		try {
			keys[code] = value;
		} catch (ArrayIndexOutOfBoundsException exception) {
			System.err.println("Unexpected key code input given. Input was '" + code + "'.");
		}
	}

	public void keyReleased(KeyEvent e) {
		setKeyCodeValue(e.getKeyCode(), false);	
	}

	public void keyTyped(KeyEvent e) {
	}

}
