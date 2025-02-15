package com.jcoadyschaebitz.neon.input;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.input.InputManager.InputType;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	private InputManager inputManager;
	
	public Mouse(InputManager inputManager) {
		this.inputManager = inputManager;
	}

	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	private static int mouseWheelRotNo;

	public static int GetXWithinBars() {
		int newX = mouseX - Game.getXBarsOffset();
		return Math.max(Math.min(newX, 0), Game.getWindowWidth() - Game.getXBarsOffset());
	}
	
	public static int GetYWithinBars() {
		return mouseY;
	}
	
	public static int GetXRelCentre() {
		return GetXWithinBars() - (Game.getWindowWidth() - Game.getXBarsOffset() * 2) / 2;
	}
	
	public static int GetYRelCentre() {
		return GetYWithinBars() - Game.getWindowHeight() / 2;
	}
	
	public static int getX() {
		return mouseX;
	}

	public static int getY() {
		return mouseY;
	}

	public static int getB() {
		return mouseB;
	}

	public static int getScrollAmount() {
		return mouseWheelRotNo;
	}

	public void mouseDragged(MouseEvent e) {
		Game.getInputManager().setLastUsed(InputType.KEYBOARD);
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
//		if (Math.abs(mouseX - e.getX()) > 1 || Math.abs(mouseY - e.getY()) > 1)
//			Game.getInputManager().setLastUsed(InputType.KEYBOARD);
		
		mouseX = e.getX();
		mouseY = e.getY();
//		inputManager.setMousePos(mouseX, mouseY);
	}

	public void mouseClicked(MouseEvent e) {
		Game.getInputManager().setLastUsed(InputType.KEYBOARD);
		mouseB = -2;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		Game.getInputManager().setLastUsed(InputType.KEYBOARD);
		mouseB = e.getButton();
	}

	public void mouseReleased(MouseEvent e) {
		Game.getInputManager().setLastUsed(InputType.KEYBOARD);
		mouseB = -1;
	}
	
	public static void move(int x, int y) {
		try {
			Robot bot = new Robot();
			bot.mouseMove(x, y);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		Game.getInputManager().setLastUsed(InputType.KEYBOARD);
		mouseWheelRotNo = e.getWheelRotation();
		Game.getUIManager().scrollItemSlots(mouseWheelRotNo);
		mouseWheelRotNo = 0;
	}

}
