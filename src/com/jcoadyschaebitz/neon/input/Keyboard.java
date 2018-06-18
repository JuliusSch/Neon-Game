package com.jcoadyschaebitz.neon.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.jcoadyschaebitz.neon.Game;

public class Keyboard implements KeyListener {
	
	public Game game;
	
	public Keyboard(Game game) {
		this.game = game;
	}

	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right, F, esc;
	public boolean[] numbers = new boolean[10];
	public String[] numbersString = {"zero", "one", "two", "three", "four", "five", "six", "seven", "seven", "eight", "nine"};

	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		F = keys[KeyEvent.VK_F];
		esc = keys[KeyEvent.VK_R];
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
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			game.updatePauseStatus();
		}
	}

	public void keyTyped(KeyEvent e) {

	}

}
