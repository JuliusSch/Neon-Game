package com.jcoadyschaebitz.neon.input;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.jcoadyschaebitz.neon.Game;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	public Mouse(Game game) {
	}

	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	private static int mouseWheelRotNo;

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
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
		mouseB = -2;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
	}

	public void mouseReleased(MouseEvent e) {
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

//	public static boolean clicked(int x, int y, int width, int height) {
//		double s = Game.getWindowScale();
//		if (Mouse.getX() > (x * s) && Mouse.getX() < (x + width) * s) {
//			if (Mouse.getY() > (y * s) && Mouse.getY() < (y + height) * s) {
//				if (Mouse.getB() == 1) return true;
//			}
//		}
//		return false;
//	}
//
//	public static boolean clickedAndReleased(int x, int y, int width, int height) {
//		double s = Game.getWindowScale();
//		if (Mouse.getX() > (x * s) && Mouse.getX() < (x + width) * s) {
//			if (Mouse.getY() > (y * s) && Mouse.getY() < (y + height) * s) {
//				if (Mouse.getB() == -2) {
//					mouseB = -1;
//					return true;
//				}
//			}
//		}
//		return false;
//	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheelRotNo = e.getWheelRotation();
		Game.getUIManager().scrollItemSlots(mouseWheelRotNo);
		mouseWheelRotNo = 0;
	}

}
