package com.jcoadyschaebitz.neon.graphics.UI;

public class SaveButton extends UIButton {

	public SaveButton(int x, int y, int width, int height, String label, int fontColour) {
		super(x, y, width, height, label, fontColour);
	}


	public void doFunction() {
		System.exit(0);
	}

}
