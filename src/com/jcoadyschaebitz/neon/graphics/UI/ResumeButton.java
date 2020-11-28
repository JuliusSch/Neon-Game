package com.jcoadyschaebitz.neon.graphics.UI;

public class ResumeButton extends UIButton {

	public ResumeButton(int x, int y, int width, int height, String label, int fontColour) {
		super(x, y, width, height, label, fontColour);
	}

	public void doFunction() {
			ui.getGame().togglePause();
	}

}
